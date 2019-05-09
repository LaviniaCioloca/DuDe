/*
  ********
  ********   FAST
  ********
  ********

  Copyright Sema Group - 1996

  ********
*/
#ifndef MEM_BUF_H
#define MEM_BUF_H

/* ********************************************************************** */
/** CTODOC INTRO
.pp
It is often a pain for programmers to manage the memory of character buffers,
especially when they are used for temporary operations, such as
concatenation and formatting (for example by using the
.=q sprintf
function).
.pp
This interface provides a way by which strings operations can be realized
without calculating the size of the character buffers into which they are
done. This goal is achieved by using a
.=e "dynamic memory buffer" +
\&. The programming interface of dynamic memory buffers provides an equivalent
for the most common string operations (such as
.=q strcpy +
,
.=q strcat +
,
.=q sprintf +
, ...). Within this programming interface, the size of the buffer is
dynamically enlarged when needed.
.pp
In order to reduce the number of memory reallocations, a buffer is always
enlarged by blocks. The block size used by a buffer can be specified at the
buffer creation time or later on.
**/

#include <stddef.h>
#include <sys/types.h>
#include <stdarg.h>
#include <stdcto.h>

#undef FAST_H_DLL
#define FAST_H_DLL CTOKERNEL_LIB
#include <fastdll.h>

#define MEMBUF_DEFAULT_BLKSZ	256


/* ********************************************************************** */
/** CTODOC TYPES
.<S MemBufS
.=T "C TYPE"
.=H ctokernel "membuf.h"
.>S
Represents a dynamic memory buffer.
.<S MemBufP
.=T "C TYPE"
.=H ctokernel "membuf.h"
.>S
Represents a pointer to a dynamic memory buffer.
**/
typedef struct {
    /* private: */
    size_t	blksz ;
    char *	buf ;
    size_t	size ;
    size_t	pos ;
} MemBufS, * MemBufP ;


/* ********************************************************************** */
/** CTODOC MANAGEMENT
.<D MemBuf_Init
.=A buf MemBufP
.=A blksz size_t
.=R void
.=T "C FUNCTION"
.=H ctokernel "membuf.h"
.>D
Initialize the buffer
.=q buf
and sets its block size to
.=q blksz
or to a default value if
.=q blksz
is 0.
.pp
The insertion position of the buffer is set to 0.
.pp
For convenience reason, it is possible to use a buffer without calling the
.=q MemBuf_Init
function, provided the buffer has been declared static. In that case, the block
size used by the buffer will be set to a default value (unless the block size
is explictly set with the
.=q MemBuf_SetBlockSize
function).
.<X
/\&* Use of static buffer with a default block size *\&/
char *
Catenate (const char * str1, const char * str2)
{
    static MemBufS _tmpBuf ;
    MemBuf_Strcpy(&_tmpBuf, str1) ;
    MemBuf_Strcat(&_tmpBuf, str2) ;
    return MemBuf_String(&_tmpBuf) ;
}

/\&* Use of static buffer, specifying a block size *\&/
char *
Catenate (const char * str1, const char * str2)
{
    static MemBufS _tmpBuf ;
    MemBuf_SetBlockSize(&_tmpBuf, 1024) ;
    MemBuf_Strcpy(&_tmpBuf, str1) ;
    MemBuf_Strcat(&_tmpBuf, str2) ;
    return MemBuf_String(&_tmpBuf) ;
}
.>X
**/
IMPORT void MemBuf_Init (MemBufP me, size_t blksz) ;

/* ********************************************************************** */
/** CTODOC MANAGEMENT
.<D MemBuf_Free
.=A buf MemBufP
.=R void
.=T "C FUNCTION"
.=H ctokernel "membuf.h"
.>D
Frees the memory that has been allocated in
.=q buf +
\&.
.pp
The buffer
.=q buf
itself is not freed, its insertion position and its size are set to 0, so it is
still possible to use it normally.
**/
IMPORT void MemBuf_Free (MemBufP me) ;

/* ********************************************************************** */
/** CTODOC MANAGEMENT
.<D MemBuf_BlockSize
.=A buf MemBufP
.=R size_t
.=T "C MACRO"
.=H ctokernel "membuf.h"
.>D
Returns the block size used by the buffer.
**/
#define MemBuf_BlockSize(me) 					\
  ((me)->blksz ? (me)->blksz : MEMBUF_DEFAULT_BLKSZ)

/* ********************************************************************** */
/** CTODOC MANAGEMENT
.<D MemBuf_SetBlockSize
.=A buf MemBufP
.=A blksz size_t
.=R void
.=T "C MACRO"
.=H ctokernel "membuf.h"
.>D
Sets the block size used by the buffer to
.=q blksz
or to a default value if
.=q blksz
is 0.
**/
#define MemBuf_SetBlockSize(me, val)					\
do {									\
    size_t _blksz = val ;						\
    ((me)->blksz = (_blksz ? _blksz : MEMBUF_DEFAULT_BLKSZ));		\
} while(0)

/* ********************************************************************** */
/** CTODOC MANAGEMENT
.<D MemBuf_Size
.=A buf MemBufP
.=R size_t
.=T "C MACRO"
.=H ctokernel "membuf.h"
.>D
Returns the number of characters the buffer can handle.
**/
#define MemBuf_Size(me)						\
    ((me)->size							\
     ? ((me)->size-1)						\
     : ((MemBuf_Init(me,(me)->blksz)), (me)->size-1))

/* ********************************************************************** */
/** CTODOC MANAGEMENT
.<D MemBuf_Position
.=A buf MemBufP
.=R size_t
.=T "C MACRO"
.=H ctokernel "membuf.h"
.>D
Returns the insertion position of the buffer. This position can be greater than
the size of the buffer.
**/
#define MemBuf_Position(me)	((me)->pos)

/* ********************************************************************** */
/** CTODOC MANAGEMENT
.<D MemBuf_SetPosition
.=A buf MemBufP
.=A pos size_t
.=R void
.=T "C MACRO"
.=H ctokernel "membuf.h"
.>D
Sets the insertion position of the buffer to
.=q pos +
\&.
This position can be greater than the size of the buffer (the size of the
buffer will be automatically enlarged when characters are inserted).
**/
#define MemBuf_SetPosition(me,val)	((me)->pos = val)

/* ********************************************************************** */
/** CTODOC MANAGEMENT
.<D MemBuf_Buf
.=A buf MemBufP
.=R "char *"
.=T "C MACRO"
.=H ctokernel "membuf.h"
.>D
Returns a pointer to the characters handled by
.=q buf +
\&. The number of characters that can be handled in that pointer is given by
.=q MemBuf_Size +
\&.
.pp
If the size of the buffer is changed, then the pointer returned by
.=q MemBuf_Buf
is no longer valid and an other call to
.=q Membuf_Buf
must be done.
**/
#define MemBuf_Buf(me)						\
    ((me)->size							\
     ? (me)->buf						\
     : (MemBuf_Init(me,(me)->blksz), (me)->buf))

/* ********************************************************************** */
/** CTODOC MANAGEMENT
.<D MemBuf_String
.=A buf MemBufP
.=R "char *"
.=T "C MACRO"
.=H ctokernel "membuf.h"
.>D
Returns a pointer to the characters handled by
.=q buf
as a null terminated string.
.pp
This is achieved by adding a null character at the insertion position of the
buffer (the insertion position is not changed). Nevertheless, the returned
string might be shorter (from the
.=q strlen
point of view) if the buffer contains a null character before its insertion
position.
.pp
If the size of the buffer is changed, then the pointer returned by
.=q MemBuf_String
is no longer valid and an other call to
.=q Membuf_Buf
must be done.
**/
#define MemBuf_String(me)    ((*(MemBuf_Buf(me)+(me)->pos) = '\0'), (me)->buf)

/***************************************************************/
/** CTODOC ALLOCATION
.pp
Although the functions of the
.=q MemBuf
interface always make sure that the buffer is large enough to contain the
characters that are inserted, the two following functions provide a way to
explicitly manage the memory used by a buffer (for example to reduce the
number of reallocation if it is known that a large amount of space is needed).
.<D MemBuf_SetSize
.=A buf MemBufP
.=A size size_t
.=R void
.=T "C FUNCTION"
.=H ctokernel "membuf.h"
.>D
Sets the size of the buffer
.=q buf
so that it can handle at least
.=q size
characters.
.pp
This might lead to increase or reduce the size of the buffer, nevertheless,
the size of the buffer is always kept greater than the current insertion
position.
**/
IMPORT void MemBuf_SetSize (MemBufP me, size_t size) ;

/***************************************************************/
/** CTODOC ALLOCATION
.<D MemBuf_Enlarge
.=A buf MemBufP
.=A size size_t
.=R void
.=T "C MACRO"
.=H ctokernel "membuf.h"
.>D
If necessary, allocates memory into
.=q buf
so that it can handle at least
.=q size
characters after its insertion position.
**/
#define MemBuf_Enlarge(me,sz) MemBuf_SetSize((me), sz+(me)->pos)

/***************************************************************/
/** CTODOC APPEND
.<D MemBuf_Strcat
.=A buf MemBufP
.=A str "const char *"
.=R void
.=T "C FUNCTION"
.=H ctokernel "membuf.h"
.>D
Appends the string
.=q str
at the insertion position of
.=q buf +
\&. The insertion position of
.=q buf
is set after the last inserted character.
.pp
If necessary, the buffer
.=q buf
is enlarged.
**/
IMPORT void MemBuf_Strcat (MemBufP me, const char * str) ;

/***************************************************************/
/** CTODOC APPEND
.<D MemBuf_Strncat
.=A buf MemBufP
.=A str "const char *"
.=A n size_t
.=R void
.=T "C FUNCTION"
.=H ctokernel "membuf.h"
.>D
Appends
.=q n
characters from the string
.=q str
at the insertion position of
.=q buf +
\&. If
.=q str
is shorter that
.=q n
then null characters are inserted until exactly
.=q n
characters have been inserted. The insertion position of
.=q buf
is set after the last character inserted from the string (that is at
the first null character).
.pp
If necessary, the buffer
.=q buf
is enlarged.
**/
IMPORT void MemBuf_Strncat (MemBufP me, const char * str, size_t n) ;

/***************************************************************/
/** CTODOC APPEND
.<D MemBuf_Charcat
.=A buf MemBufP
.=A ch char
.=R void
.=T "C FUNCTION"
.=H ctokernel "membuf.h"
.>D
Appends the character
.=q ch
at the insertion position of
.=q buf +
\&. The insertion position of
.=q buf
is set after the inserted character.
.pp
If necessary, the buffer
.=q buf
is enlarged.
**/
IMPORT void MemBuf_Charcat (MemBufP me, char ch) ;

/***************************************************************/
/** CTODOC APPEND
.<D MemBuf_Memcat
.=A buf MemBufP
.=A s2 "const void *"
.=A n size_t
.=R void
.=T "C FUNCTION"
.=H ctokernel "membuf.h"
.>D
Appends
.=q n
bytes from memory area
.=q s2
at the insertion position of
.=q buf +
\&. The insertion position of
.=q buf
is set after the last inserted character.
.pp
If necessary, the buffer
.=q buf
is enlarged.
**/
IMPORT void MemBuf_Memcat (MemBufP me, const void * s2, size_t n) ;

/***************************************************************/
/** CTODOC INSERT
.<D MemBuf_Strcpy
.=A buf MemBufP
.=A str "const char *"
.=R void
.=T "C FUNCTION"
.=H ctokernel "membuf.h"
.>D
Copies the string
.=q str
at the beginning of
.=q buf +
\&. The insertion position of
.=q buf
is set after the last inserted character.
.pp
If necessary, the buffer
.=q buf
is enlarged.
**/
#define MemBuf_Strcpy(me,str)	     (((me)->pos=0),MemBuf_Strcat(me,str))

/***************************************************************/
/** CTODOC INSERT
.<D MemBuf_Strncpy
.=A buf MemBufP
.=A str "const char *"
.=A n size_t
.=R void
.=T "C FUNCTION"
.=H ctokernel "membuf.h"
.>D
Copies
.=q n
characters from the string
.=q str
at the beginning of
.=q buf +
\&. If
.=q str
is shorter that
.=q n
then null characters are inserted until exactly
.=q n
characters have been inserted. The insertion position of
.=q buf
is set after the last character inserted from the string (that is at
the first null character).
.pp
If necessary, the buffer
.=q buf
is enlarged.
**/
#define MemBuf_Strncpy(me,str,n)     (((me)->pos=0),MemBuf_Strncat(me,str,n))

/***************************************************************/
/** CTODOC INSERT
.<D MemBuf_Charcpy
.=A buf MemBufP
.=A ch char
.=R void
.=T "C FUNCTION"
.=H ctokernel "membuf.h"
.>D
Copies the character
.=q ch
at the beginning of
.=q buf +
\&. The insertion position of
.=q buf
is set after the inserted character.
.pp
If necessary, the buffer
.=q buf
is enlarged.
**/
#define MemBuf_Charcpy(me,ch)	     (((me)->pos=0),MemBuf_Charcat(me,ch))

/***************************************************************/
/** CTODOC INSERT
.<D MemBuf_Memcpy
.=A buf MemBufP
.=A s2 "const void *"
.=A n size_t
.=R void
.=T "C FUNCTION"
.=H ctokernel "membuf.h"
.>D
Copies
.=q n
bytes from memory area
.=q s2
at the beginning of
.=q buf +
\&. The insertion position of
.=q buf
is set after the last inserted character.
.pp
If necessary, the buffer
.=q buf
is enlarged.
**/
#define MemBuf_Memcpy(me,s2,n)	     (((me)->pos=0),MemBuf_Memcpy(me,s2,n))

/***************************************************************/
/** CTODOC FORMAT
.<D MemBuf_Sprintf
.=A buf MemBufP
.=A format "const char *"
.=A arg "" repopt
.=R void
.=T "C FUNCTION"
.=H ctokernel "membuf.h"
.>D
This function is functionnally equivalent to the standard
.=q sprintf
function, except that the formatting of the arguments
.=q "arg..."
is appended at the insertion position of the buffer
.=q buf +
\&. The insertion position of
.=q buf
is set after the last inserted character.
.pp
If necessary, the buffer
.=q buf
is enlarged.
**/
IMPORT void MemBuf_Sprintf (MemBufP me, const char * format, ...) ;

/***************************************************************/
/** CTODOC FORMAT
.<D MemBuf_Vsprintf
.=A buf MemBufP
.=A format "const char *"
.=A args "va_list *"
.=R void
.=T "C FUNCTION"
.=H ctokernel "membuf.h"
.>D
This function is the same as
.=q MemBuf_Sprintf
except that instead of being called with a variable number of arguments, it
is  called  with  a pointer to an  argument list as defined by the
.=q "stdarg.h"
header.
.pp
The fact that
.=q args
is a pointer to a list of arguments enables the use of this function in a
function which has itself a variable number of arguments because once
.=q MemBuf_Vsprintf
is done, the next call to
.=q va_arg
applied on
.=q "*args"
will return the argument following the last argument converted by
.=q format +
\&.
**/
IMPORT void MemBuf_Vsprintf (
    MemBufP		me,
    const char *	format,
    va_list *		args) ;

#endif /* MEM_BUF_H */
