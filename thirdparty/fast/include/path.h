/*
  ********
  ********   FAST
  ********
  ********

  Copyright Sema Group - 1996

**********
* Jean-Marc Letteron - Septembre 1997
*  Ajout de Path_Lastname
* Wael Kombar - 1997
*  Creation
***********
*/
#ifndef PATH_H
#define PATH_H

#if defined(WIN32PC)
#include <io.h>
#include <windows.h>
typedef struct
{
struct _finddata_t fdata;
long	handle;
} Path_Child_IteratorS, *Path_Child_IteratorP;
#define DIR_SEP_STR	"\\"
#define DIR_SEP_CHAR	'\\'
#define DSK_SEP_CHAR	':'
#define ESCAPE_CHAR     '|'
#define Getdcwd(d,b,l)	((d<0)?getcwd(b, l):_getdcwd(d, b, l))

#else

#define DIR_SEP_STR	"/"
#define DIR_SEP_CHAR	'/'
#define ESCAPE_CHAR     '\\'
#define Getdcwd(d,b,l)	getcwd(b, l)
#include <unistd.h>
#include <dirent.h>
typedef DIR *Path_Child_IteratorS, **Path_Child_IteratorP;

#endif

#include <sys/stat.h>

#include <tlist.h>

#undef FAST_H_DLL
#define FAST_H_DLL CTOKERNEL_LIB
#include <fastdll.h>

IMPORT int
Path_FileType(char *name);
IMPORT 
char *
Path_Parent(char *name);
IMPORT 
char *
Path_Append (char *directory, char *name);
IMPORT 
char *
Path_Fullname (char *name);
IMPORT 
char *
Path_Lastname (char *name);
IMPORT 
char *
Path_Relname (char *name, char *directory);

#define PATH_NM_NOMATCH     0       /* Match failed. */
#define PATH_NM_MATCH       1       /* Match succeded */
#define PATH_NM_NOESCAPE    0x01    /* Disable backslash escaping. */
#define PATH_NM_PATHNAME    0x02    /* Slash must be matched by slash. */
#define PATH_NM_PERIOD      0x04    /* Period must be matched by period. */

IMPORT int
Path_NameMatch(char *name, char *filter, int flags);
IMPORT 
char *
Path_Child_First(char *file, Path_Child_IteratorP iterP);
IMPORT 
char *
Path_Child_Next(Path_Child_IteratorP iterP);

IMPORT int
Path_Child_Stop(Path_Child_IteratorP iterP);

IMPORT void
Path_Child_DelIter(Path_Child_IteratorP iterP);

typedef struct {
  char *name;
  int is_dir;
  TListS stack;
} Path_ChildRec_IteratorS, *Path_ChildRec_IteratorP;
IMPORT 
char *
Path_ChildRec_First(char *name, Path_ChildRec_IteratorP iterP);
IMPORT 
char *
Path_ChildRec_Next(Path_ChildRec_IteratorP iterP);

IMPORT int
Path_ChildRec_Stop(Path_ChildRec_IteratorP iterP);

IMPORT void
Path_ChildRec_DelIter(Path_ChildRec_IteratorP iterP);

/* get the name of the path's owner */
IMPORT int
Path_Owner(const char * path,int deref,char * name,unsigned long int length);

/* Unix: do lstat() */
/* Windows: do stat() */
IMPORT int
Path_Lstat( const char *path, struct stat *buffer );

#endif /* PATH_H */
