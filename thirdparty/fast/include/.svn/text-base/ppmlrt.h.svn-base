/*
********
********   CONCERTO PPML
********
********

Copyright SEMA GROUP 1995 - 1995

********

Pascal Andrieu - Janvier 1995
 Creation

*/
#ifndef _PPMLRT_H_
#define _PPMLRT_H_

#include <ppstream.h>

#undef FAST_H_DLL
#define FAST_H_DLL VTPKIT_LIB
#include <fastdll.h>

typedef PP_VoidFunc *PP_DispatchP;

#define PP

typedef struct {
  VTP_TreeP parent;
  int first;
  int last;
} PP_ListS, PP_ListP;

typedef struct {
  PP_StreamP	out;
  PP_DispatchP	dispatch;
  PP_Context	context;
  PP_ContextDataP context_data;
  int		holo;
} PP_PrettyContextS, *PP_PrettyContextP;


#define PP_PutString(out, string, context, cdata) \
     (((PP_StreamP)(out))->type->putstring)(out, string, context, cdata)

#define PP_PutChar(out, char, context, cdata) \
     (((PP_StreamP)(out))->type->putchar)(out, char, context, cdata)

#define PP_StartBox(out, sep, context, cdata) \
     (((PP_StreamP)(out))->type->startbox)(out, sep, context, cdata)

#define PP_EndBox(out) (((PP_StreamP)(out))->type->endbox)(out)

#define PP_PutSep(out, sep, context, cdata) \
     (((PP_StreamP)(out))->type->putsep)(out, sep, context, cdata)

#define PP_Reccall(out, pp, tree, cont, holo) \
  (*pp[(VTP_TreeOperator(tree))->code])(out, pp, tree, cont, holo)

IMPORT void 
PP_Terminal(PP_StreamP out, char *string,
	    PP_Context cont, PP_ContextDataP cdata);

IMPORT void
PP_Default(PP_StreamP out, PP_DispatchP pp, VTP_TreeP tree,
           PP_Context cont, int holo);

IMPORT void
PP_PutNLString(PP_StreamP out, char *str,
	       PP_Context cont, PP_ContextDataP cdata);

#define PP_CheckOper(tree, op) \
  (op == VTP_TREE_OPERATOR(tree))

#define PP_Down(parent, rank, child) \
  (child = VTP_TreeDown(parent, rank))

#define PP_Sublist(tr, r1, r2, list) \
(((list.first = r1) + 1) && \
 (list.last = r2) && \
 (list.parent = tr))

#define PP_SublistMap(list) \
{ \
  VTP_TreeP parent = list.parent; \
  int first = list.first; \
  int last = VTP_TreeLength(parent) + list.last; \
  VTP_TreeP list; \
  for (; first <= last; first++) { \
    list = VTP_TreeDown(parent, first)
				      
#define PP_SublistEndMap() \
}}
					
#define PP_AtomValue(tree, atom) \
  ((atom = VTP_TreeAtomValue(tree)) || 1)
					  
#define PP_CheckAtomValue(tree, atom, type) \
  VTP_ATOM_EQUAL(type, atom, VTP_TreeAtomValue(tree))

#define PP_AnnotValue(tree, frame, atom) \
  (VTP_TreeGetAnnot(tree, frame) && \
   ((atom = VTP_TreeGetAnnotValue(tree, frame)) || 1))

#define PP_CheckAnnot(tree, frame) \
  VTP_TreeGetAnnot(tree, frame)

#define PP_CheckAnnotValue(tree, frame, atom, type) \
  (VTP_TreeGetAnnot(tree, frame) && \
   VTP_ATOM_EQUAL(type, atom, VTP_TreeGetAnnotValue(tree, frame)))


#endif /* _PPMLRT_H_ */
