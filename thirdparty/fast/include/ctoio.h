/*
  ********
  ********   FAST
  ********
  ********

  Copyright Sema Group - 1996

  Jerome Huchard - 8 novembre 2000
  - XXX/nnn rajout d'une '\n' avant EOF si necessaire.
  ajout du flag at_eof dans la structure _CIO_InputS.
  ********
*/
#ifndef CTO_IO_H
#define CTO_IO_H

#include <stdio.h>

#undef FAST_H_DLL
#define FAST_H_DLL CTOKERNEL_LIB
#include <fastdll.h>

typedef struct _CIO_InputS CIO_InputS, *CIO_InputP;
typedef struct _CIO_PositionS  CIO_PositionS, *CIO_PositionP;

typedef struct
	{
	void	(*destroy)(CIO_InputP input);
	int	(*input)(CIO_InputP input);
	void	(*seek)(CIO_InputP input, CIO_PositionP pos);
	void	(*getblock)(CIO_InputP input, char *block,
			CIO_PositionP start, CIO_PositionP end);
	} CIO_InputManagerS, *CIO_InputManagerP;


struct _CIO_PositionS
{
  int	line;
  int	col;
  int	rank;
};

struct _CIO_InputS
{
  CIO_InputManagerP	manager;
  CIO_PositionS	pos;
  int 	    	last_line_length;
  char		*unput_buf;
  int		unput_buf_size;
  char		*unput_buf_ptr;
  char	    	*name;
  char		at_eof;
};

typedef struct
	{
	CIO_InputS	input;
	char		*buf;
	char		*ptr;
	} StringInputS, *StringInputP;

typedef struct
	{
	CIO_InputS	input;
	FILE		*fp;
	} FileInputS, *FileInputP;

typedef struct
{
  struct _CIO_Manager	*manager;
} CIO_OutputS, *CIO_OutputP;

Exception_Declare(EX_CIO);
Exception_Declare(EX_CIO_FILE);
Exception_Declare(EX_CIO_FILE_SEEK);

IMPORT void
CIO_InputDestroy(CIO_InputP stream);

IMPORT void
CIO_InputSetName(CIO_InputP stream, char *name);

#define CIO_InputGetName(stream)    \
    (((CIO_InputP)stream)->name)

IMPORT void
CIO_InputInit(CIO_InputP stream, char *name);

IMPORT int
CIO_InputGetChar(CIO_InputP stream);

IMPORT void
CIO_InputUnput(CIO_InputP stream, int c);

IMPORT void
CIO_InputUnputString(CIO_InputP stream, char *s);

IMPORT void
CIO_InputSeek(CIO_InputP stream, CIO_PositionP pos);

#define CIO_InputTell(i, p)	(*(p) = ((CIO_InputP)(i))->pos)

IMPORT void
CIO_InputGetBlock(CIO_InputP stream, char *block,
		       CIO_PositionP startPos,
		       CIO_PositionP endPos);

IMPORT CIO_InputP
StringInput_Create(char *buf);

#define FileInput_FP(stream)	(((FileInputP)(stream))->fp)

IMPORT CIO_InputP
FileInput_Attach(FILE *fp);

IMPORT CIO_InputP
FileInput_Open(char *file);

IMPORT void
FileInput_Close(CIO_InputP stream);

#ifdef WIN32PC

typedef struct
{
  CIO_InputS	input;
  FILE		*fp;
  char		*buf;
  long		buf_pos;
  int		ptable_size;
  long		*ptable;
  int		last_was_eol;
} TextFileInputS, *TextFileInputP;

#define TextFileInput_FP(stream)	(((TextFileInputP)(stream))->fp)

IMPORT CIO_InputP
TextFileInput_Attach(FILE *fp);

IMPORT CIO_InputP
TextFileInput_Open(char *file);

IMPORT void
TextFileInput_Close(CIO_InputP stream);

IMPORT long
TextFileInput_PhysicalOffset(CIO_InputP stream, CIO_PositionP pos);

#else /* WIN32PC */

#define TextFileInput_FP	FileInput_FP
#define TextFileInput_Attach	FileInput_Attach
#define TextFileInput_Open	FileInput_Open
#define TextFileInput_Close	FileInput_Close

#define TextFileInput_PhysicalOffset(stream, pos)	((long)((pos)->rank))

#endif /* WIN32PC */


#endif /* CTO_IO_H */
