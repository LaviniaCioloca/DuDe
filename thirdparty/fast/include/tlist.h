/*
  ********
  ********   FAST
  ********
  ********

  Copyright Sema Group - 1996

  ********
*/
#ifndef TLIST_H
#define TLIST_H

#undef FAST_H_DLL
#define FAST_H_DLL CTOKERNEL_LIB
#include <fastdll.h>

typedef struct
	{
	Word2	nbr_elem;
	Word2	max_elem;
	VoidP	*elems;
	} TListS, *TListP;

typedef struct
	{
	int	nbr_elem;
	VoidP	*cur_elem;
	} TList_IteratorS, *TList_IteratorP;

#define TList_First(tlist, iterP)				\
	((((iterP)->nbr_elem = (tlist)->nbr_elem) > 0)		\
		? *((iterP)->cur_elem = (tlist)->elems)		\
		: (VoidP)0)

#define TList_Stop(iterP)	((iterP)->nbr_elem <= 0)

#define TList_Next(iterP)					\
	(((--((iterP)->nbr_elem)) < 0) ? 0 :  *++((iterP)->cur_elem))

#define TList_DelIter(iterP)	0

#define TLIST_MAP(object, type, elem)			\
	{						\
	TList_IteratorS	_iter;				\
	for(elem = (type)TList_First(object, &_iter);	\
		! TList_Stop(&_iter);			\
		elem = (type)TList_Next(&_iter))	\
		{

#define TLIST_END_MAP()					\
		}					\
	TList_DelIter(&_iter);				\
	}


#define TList_Init(list)	(((list)->nbr_elem = (list)->max_elem = 0), \
					(list)->elems = 0)
#define TList_Reset(list)	((list)->nbr_elem = 0)
#define TList_Length(list)	((list)->nbr_elem)
#define TList_GetTable(list)	((list)->elems)
#define TList_GetElem(list, n)	((list)->elems[(n)])
#define TList_ReplaceElem(list, n, e)  ((list)->elems[(n)] = ((VoidP)(e)))
IMPORT 
void		TList_Destroy(TListP list);
IMPORT void		TList_Copy(TListP list, TListP old_list);
IMPORT void		TList_Append(TListP list, VoidP elem);
IMPORT void		TList_PackAppend(TListP list, VoidP elem);
IMPORT void		TList_Insert(TListP list, VoidP elem, int rank);
IMPORT void		TList_Remove(TListP list, int rank);
IMPORT void		TList_Pack(TListP list);


#define TList_Pop(stack)	((stack)->elems[--((stack)->nbr_elem)])
#define TList_PopN(l, n)	(((l)->nbr_elem) -= (n))
#define TList_Push(stack, elem)	\
				TList_Append(stack, elem)
#define TList_Top(stack)   	((stack)->elems[(stack)->nbr_elem - 1])

#endif /* TLIST_H */
