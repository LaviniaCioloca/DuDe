/*
********
********   CONCERTO PPML
********
********

Copyright SEMA GROUP 1995 - 1995

********

Wael Kombar - Aout 1995
 Creation

*/

#ifndef _PP_STREAM_H_
#define _PP_STREAM_H_

#include <stdio.h>

#undef FAST_H_DLL
#define FAST_H_DLL VTPKIT_LIB
#include <fastdll.h>

/**
  A context describe some user information about strings and separators

  It is a bit array, in which each bit has a meaning defined by a name.
  Mapping between bits and names is done using ContextData
  **/
typedef unsigned long PP_Context;

typedef struct
{
  char	*name;
  PP_Context value;
} PP_ContextDatumS, *PP_ContextDatumP;

typedef struct
{
  int	size;		/* number of contextes */
  PP_ContextDatumP data;
} PP_ContextDataS, *PP_ContextDataP;


/**
  Separators define how to put a string after another one
  **/
#define PP_H_SEP 1

#define PP_V_SEP 2

#define PP_HV_SEP 3

#define PP_HOV_SEP 4

typedef struct {
  int type;
} PP_SepS, *PP_SepP;

typedef struct {
  int type;
  short dh;
} PP_HSepS, *PP_HSepP;

typedef struct {
  int type;
  short dhv;
  short dv;
} PP_VSepS, *PP_VSepP;

typedef struct {
  int type;
  short dh;
  short dhv;
  short dv;
} PP_HVSepS, *PP_HVSepP;

typedef struct {
  int type;
  short dh;
  short dhv;
  short dv;
} PP_HOVSepS, *PP_HOVSepP;

/**
  Attributes describe how something is displayed
  They are specific to a given displayer
  **/
typedef VoidP PP_Attribute;


/**
  AttrCache is a utility to quickly find an attribute
  from a context information
  **/
typedef struct _PP_AttrCache *PP_AttrCacheP;

IMPORT PP_AttrCacheP
PP_AttrCacheCreate(void);

/**
  destroy : the attribute destruction callback
  data	  : a free parameter passed to the callback routine
  **/
IMPORT void
PP_AttrCacheDestroy(PP_AttrCacheP cache,
		    void (*destroy)(PP_Attribute, VoidP),
		    VoidP data);

/**
  def : a default attribute value if attribute is not found in cache
  this is generaly a value that is not a valid attribute, and that is
  used to detect that the cache do not contain a relevant attribute
  **/
IMPORT PP_Attribute
PP_AttrCacheGet(PP_AttrCacheP cache,
		PP_Context context, PP_ContextDataP cdata,
		PP_Attribute def);

IMPORT void
PP_AttrCachePut(PP_AttrCacheP cache,
		PP_Context context, PP_ContextDataP cdata,
		PP_Attribute val);


/**
  Result of a composition attempt
  **/
typedef enum
{
  PP_COMPOSE_FAILED,		/* Could not compose with given constraints */
  PP_COMPOSE_SINGLE_LINE,	/* Composed in one line */
  PP_COMPOSE_MULTI_LINE		/* Composed in more than one line */
} PP_ComposeStatus;

/**
  Basic description of a line (in displayer units)
  **/
typedef struct
{
  short	height;			/* the height of the line */
  short	baseline;		/* the baseline to align
				   text of different heights */
} PP_LineInfoS, *PP_LineInfoP;


/** A non rectangular zone

     x4      x1       x3        x2
     |       |        |         |
   
  y1          ___________________
             |                  |
  y2  _______|                  |
     |                          |
     |                          |
  y3 |                 _________|
     |                |
  y4 |________________|


           (x1, y1)           (x2, y1)   
              ___________________
  (x4, y2)   |                  |
      _______|                  |
     |                          |
     |              (x3, y3)    |
     |                 _________|
     |                |      (x2, y3)
     |________________|
    (x4, y4)        (x3, y4)
 
    **/

typedef struct
{
  int x1, x2, x3, x4;
  int y1, y2, y3, y4;
} PP_StreamZoneS, *PP_StreamZoneP;

/**
  A stream is used to output strings and separators
  to a displayer
  **/
typedef void (*PP_VoidFunc)();

typedef struct {
  PP_VoidFunc startpp;
  VoidP	      (*endpp)();
  PP_VoidFunc setbox;
  PP_VoidFunc putstring;
  PP_VoidFunc putchar;
  PP_VoidFunc startbox;
  PP_VoidFunc endbox;
  PP_VoidFunc putsep;
  VoidP	      (*hilite)();
  PP_VoidFunc unhilite;
  PP_VoidFunc movehilite;
#ifdef TODO
  PP_VoidFunc insertbox;
  PP_VoidFunc replacebox;
  PP_VoidFunc deletebox;
#endif /* TODO */
} PP_StreamTypeS, *PP_StreamTypeP;

typedef struct {
  PP_StreamTypeP	type;

  /* The user data */
  VoidP		udata;

  /* Number of chars in the stream */
  int		length;
} PP_StreamS, *PP_StreamP;


#define PP_DISPLAYER_SYNC	0	/* Outputs when asked to */
#define PP_DISPLAYER_ASYNC	1	/* Outputs depending on external
					   events
					   */


/**
  A displayer outputs the result of a composition to a given
  output support (file, string, screen, postscript, ...)
  */
typedef struct _PP_Displayer PP_DisplayerS, *PP_DisplayerP;

typedef struct
{
  /**** INTERFACE TO GET INFORMATION ****/

  /* Flags for the displayer */
  unsigned  flags;

  /* Gets maximum width for composition */
  int	    (*get_max_width)(PP_DisplayerP d);
  
  /* size of a string in a given attribute */
  void	    (*get_size)(PP_DisplayerP d,
			char *str, int len,
			PP_Attribute attr,
			int *widthp, int *heightp, int *baselinep);

  /* attribute value from context data */
  PP_Attribute (*get_attr)(PP_DisplayerP, PP_Context, PP_ContextDataP);


  /**** ARE THOSE REALLY NEEDED ??? ****/

  /* initialize before an output operation */
  void	    (*initialize)(PP_DisplayerP);

  /* terminate an output operation */
  void	    (*terminate)(PP_DisplayerP);


  /**** INTERFACE TO OUTPUT DATA ****/
  
  /* output a string with the given attributes, at the current cursor pos */
  void	    (*put_string)(PP_DisplayerP d, char *str, int len,
			  PP_Attribute attr,
			  int width, int height, int baseline,
			  PP_Context hcontext, PP_ContextDataP hdata);

  /* output a filled area with the given attribute, at current cursor pos */
  void	    (*put_area)(PP_DisplayerP, PP_Attribute, int width, int height,
			  PP_Context hcontext, PP_ContextDataP hdata);

  /* Hilite a given zone delimited by the given zone */
  void	    (*hilite)(PP_DisplayerP d, PP_Attribute attr,
		      PP_StreamZoneP zone);

  /* Move the cursor to the given position */
  void	    (*locate)(PP_DisplayerP d, int x, int y);


  /**** INTERFACE TO NOTIFY CHANGES ****/

  /* Notify changes in contents */
  void	    (*update)(PP_DisplayerP d,
		      int x, int y, int width, int height);
  
  /* Notify creation or deletion of a hilite */
  void	    (*update_hilite)(PP_DisplayerP d, PP_Attribute attr,
		      PP_StreamZoneP zone, int deletion);

  /* Notify a hilite move or extension */
  void	    (*move_hilite)(PP_DisplayerP d, PP_Attribute attr,
			   PP_StreamZoneP from, PP_StreamZoneP to);
  
			     
  /* Set the size of the displayer used zone */
  void	    (*set_size)(PP_DisplayerP d, int width, int height);

} PP_DisplayerTypeS, *PP_DisplayerTypeP;

struct _PP_Displayer
{
  PP_DisplayerTypeP	type;

  /* The stream connected to this displayer */
  PP_StreamP stream;

  /* Function that should be called to get info (for async displayer) */
  void	(*sync)(PP_StreamP s, int x, int y, int width, int height);
};


/**
  The file displayer outputs to a file
  **/
typedef struct _PP_FileDisplayer PP_FileDisplayerS, *PP_FileDisplayerP;

/* Creates a file displayer to the given file */
IMPORT PP_FileDisplayerP
PP_FileDisplayerMake(char *name);

/* Creates a file displayer to the given name, with the given file pointer */
IMPORT PP_FileDisplayerP
PP_FileDisplayerMakeAttach(char *name, FILE *fp);

/* Destroys the file displayer (and close the file) */
IMPORT void
PP_FileDisplayerDestroy(PP_FileDisplayerP d);

/**
  The string displayer outputs in an internal string buffer
  **/
typedef struct _PP_StringDisplayer PP_StringDisplayerS, *PP_StringDisplayerP;

/* Creates a string displayer */
IMPORT PP_StringDisplayerP
PP_StringDisplayerMake();

/* Destroys the string displayer */
IMPORT void
PP_StringDisplayerDestroy(PP_StringDisplayerP d);

IMPORT void
PP_StringDisplayerReset(PP_StringDisplayerP fd);

/* Return the string built */
IMPORT char *
PP_StringDisplayerGetString(PP_StringDisplayerP fd);


/**
  PP_QuickStream is a simple composer
  with no backtrack
  **/
typedef struct _PP_QuickStream *PP_QuickStreamP;

IMPORT PP_QuickStreamP
PP_QuickStreamMake (void);

IMPORT void
PP_QuickStreamDestroy (PP_QuickStreamP pp);

IMPORT void
PP_QuickStreamSetDisplayer(PP_QuickStreamP out, PP_DisplayerP d);

IMPORT PP_DisplayerP
PP_QuickStreamGetDisplayer(PP_QuickStreamP out);


/**
  PP_BoxStream is the full composer
  **/
typedef struct _PP_BoxStream *PP_BoxStreamP;

/* Result of a position search in a box stream */
typedef enum
{
  PP_BOXSTREAM_POS_NOT_FOUND,	/* position was not found at all */
  PP_BOXSTREAM_POS_EXACT,	/* position was found in an atom */
  PP_BOXSTREAM_POS_AFTER	/* position is between atoms
				   (in a separator), after the
				   returned char */
} PP_BoxStreamPosStatus;



IMPORT PP_BoxStreamP
PP_BoxStreamMake (void);

IMPORT void
PP_BoxStreamDestroy (PP_BoxStreamP pp);

IMPORT void
PP_BoxStreamSetDisplayer(PP_BoxStreamP out, PP_DisplayerP d);

IMPORT PP_DisplayerP
PP_BoxStreamGetDisplayer(PP_BoxStreamP out);

IMPORT PP_BoxStreamPosStatus
PP_BoxStreamPosToCRank(PP_BoxStreamP out,
	   int xzone, int yzone, int *crankp);
     

#define PP_PRETTY(out, dispatch, tree, context, holo) \
do { \
  VoidP _pp_box; PP_StreamP _pp_out = (PP_StreamP)out; \
  _pp_out->udata = (VoidP)0; \
  (_pp_out->type->startpp)(_pp_out); \
  PP_Reccall(_pp_out, dispatch, tree, context, holo); \
  _pp_box = (_pp_out->type->endpp)(_pp_out); \
  (_pp_out->type->setbox)(_pp_out, _pp_box); \
} while(0)

#define PP_PRETTY_COORD(out, dispatch, tree, context, holo) \
do { \
  VoidP _pp_box; PP_StreamP _pp_out = (PP_StreamP)out; \
  _pp_out->udata = (VoidP)PP_TreeCoordCreate(); \
  PP_TreeCoordStartStack((PP_TreeCoordP)(_pp_out->udata), 0); \
  (_pp_out->type->startpp)(_pp_out); \
  PP_Reccall(_pp_out, dispatch, tree, context, holo); \
  _pp_box = (_pp_out->type->endpp)(_pp_out); \
  PP_TreeCoordEndStack((PP_TreeCoordP)(_pp_out->udata)); \
  (_pp_out->type->setbox)(_pp_out, _pp_box); \
} while(0)

#endif /* _PP_STREAM_H_ */
