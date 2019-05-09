/*
**********
**********   FAST environment
**********   
**********
*
* Copyright Sema Group 1996 - 1998
*
**********
 Wael Kombar - mars 1996
  Creation
**********/

#ifndef UTILCTOMISC_H
#define UTILCTOMISC_H

/* Y a-t-il encore une raison pour cela ? */
#if defined(DECSTATION) || defined(VAXUNIX) || defined(VAXVMS)
        typedef char *VoidP ;
#else
        typedef void *VoidP ;
#endif

typedef short	Int2;
typedef long	Int4;
typedef unsigned short Word2;
typedef unsigned long Word4;


#ifdef VAXVMS
#define GLOBALREF globalref
#define GLOBALDEF globaldef
#else
#define GLOBALREF extern
#define GLOBALDEF
#endif

#ifdef WIN32PC
#define EXPORT __declspec( dllexport )
#define IMPORT __declspec( dllimport )
#else
#define EXPORT
#define IMPORT
#endif

#define ITERATOR_MAP(function, object, elem)		\
	{						\
	function##_IteratorS	_iter;			\
	for(elem = function##_First(object, &_iter);	\
		! function##_Stop(&_iter);		\
		elem = function##_Next(&_iter))		\
		{

#define ITERATOR_END_MAP(function)			\
		}					\
	function##_DelIter(&_iter);			\
	}

#define ITERATOR_MAP2(function, object, param, elem)		\
	{							\
	function##_IteratorS	_iter;				\
	for(elem = function##_First(object, param, &_iter);	\
		! function##_Stop(&_iter);			\
		elem = function##_Next(&_iter))			\
		{

#define ITERATOR_MAP_X(function, object, elem)		\
	{						\
	function##_IteratorS	_iter;			\
	elem = function##_First(object, &_iter);	\
	for(; ! function##_Stop(&_iter);		\
		elem = function##_Next(&_iter)) {       \
           Exception_Begin();                           \
           if (Exception_Catch()) {                     \
             function##_DelIter(&_iter);                \
             Exception_Reraise();                       \
           } else {

#define ITERATOR_END_MAP_X(function)			\
           }                                            \
           Exception_End();                             \
		}					\
	function##_DelIter(&_iter);			\
	}

#endif /* UTILCTOMISC_H */
