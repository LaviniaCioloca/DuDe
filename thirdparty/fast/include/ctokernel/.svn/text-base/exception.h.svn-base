
/*
  ********
  ********   FAST
  ********
  ********

  Copyright Sema Group - 1996

  ********

  Wael Kombar - 16 novembre 1993
  - correction de sgb/33

  Mary-Anne Roche 5 aout 1991.
  - correction des utilisations de GLOBALREF et GLOBALDEF.
*/
#ifndef CTO_EXCEPTION_H
#define CTO_EXCEPTION_H

#include <setjmp.h>

#undef FAST_H_DLL
#define FAST_H_DLL CTOKERNEL_LIB
#include <fastdll.h>

typedef struct _Exception Exception;

typedef void (*Exception_HandlerFunc)(Exception *ex, VoidP val);
typedef char *(*Exception_StringHandlerFunc)(Exception *ex, VoidP val);
     
typedef int Exception_HandlerTypeS, *Exception_HandlerTypeP;

IMPORT GLOBALREF Exception_HandlerTypeP EX_HANDLER_STRING();

typedef struct
{
  Exception_HandlerTypeP    (*type)();
  Exception_HandlerFunc	    func;
} Exception_HandlerTable;

struct _Exception
	{
	char *name;
	Exception *(*father)();
	Exception_HandlerTable *handlers;
	};

typedef struct _Exception_SavedVar
	{
	struct _Exception_SavedVar	*next;
	VoidP	area;
	int	size;
	char	first_byte;
	} _Exception_SavedArea;

typedef struct _Exception_Lock
	{
	struct _Exception_Lock *saved_lock;
	jmp_buf jmp;
	Exception *saved_exception;
	VoidP saved_value;
	_Exception_SavedArea *saved_area_list;
	} _Exception_Lock;

IMPORT GLOBALREF _Exception_Lock * _exception_CurLock;
IMPORT GLOBALREF VoidP _exception_Value;
IMPORT GLOBALREF Exception * _exception_Raised;

#define Exception_Declare(exc)				\
IMPORT GLOBALREF Exception exc;				\
IMPORT GLOBALREF Exception *ExceptionFunc_##exc()

Exception_Declare(EX_ANY);
Exception_Declare(EX_NO_HANDLER);

#define Exception_Define(exc, father, handlers)	\
EXPORT GLOBALREF Exception exc;				\
EXPORT GLOBALDEF Exception *ExceptionFunc_##exc()	\
{							\
  return &exc;						\
}							\
EXPORT GLOBALDEF Exception exc				\
= { #exc, ExceptionFunc_##father, handlers}		\

#define Exception_DefineStatic(exc, father, handlers)	\
static Exception exc					\
= { #exc, ExceptionFunc_##father, handlers};		\
static Exception *ExceptionFunc_##exc()			\
{							\
  return &exc;						\
}							\
extern void ExceptionDummyFunc_##exc(void)


#define Exception_Value()	_exception_Value

#define Exception_Begin()				\
do						\
	{ _Exception_Lock _exception_Var;		\
	do					\
		{				\
		_exception_Var.saved_lock=_exception_CurLock;	\
		_exception_Var.saved_exception=_exception_Raised;	\
		_exception_Var.saved_value=_exception_Value;	\
		_exception_Var.saved_area_list = 0;		\
		_exception_CurLock= &_exception_Var;	\
		_exception_Raised=0;

#define Exception_Catch()	setjmp(_exception_Var.jmp)

#define Exception_End()				\
		_exception_CurLock=_exception_Var.saved_lock;	\
		_exception_Raised=_exception_Var.saved_exception;	\
		_exception_Value=_exception_Var.saved_value;	\
		}				\
	while(0);				\
	}					\
while(0)


#define Exception_SaveVar(v) Exception_SaveArea(sizeof(v), &(v))

#define Exception_Raise(e,v) _Exception_Raise(&(e),(VoidP)(v))

#define Exception_Reraise() _Exception_Raise(_exception_Raised,_exception_Value)

#define Exception_Raised(e) (_Exception_Raised(&(e)))

#define Exception_Name()	(_exception_Raised->name)

#define Exception_GetHandler(type)  \
     _Exception_GetHandler(_exception_Raised, type)
IMPORT 
void _Exception_Raise(Exception *ex, VoidP val);
IMPORT int _Exception_Raised(Exception *ex);
IMPORT void Exception_SaveArea(VoidP area, int size);

IMPORT Exception_HandlerFunc
_Exception_GetHandler(Exception *ex, Exception_HandlerTypeP type);

#define Exception_GetString()	\
(*(Exception_StringHandlerFunc)Exception_GetHandler(EX_HANDLER_STRING()))(_exception_Raised, _exception_Value)

#endif /* CTO_EXCEPTION_H */
