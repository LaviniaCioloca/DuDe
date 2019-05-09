#include <stdcto.h>
#undef FAST_H_DLL
#define FAST_H_DLL VTPKIT_LIB
#include <fastdll.h>

/**********	TYPEDEF DEFINITIONS		**********/

typedef struct _VTP_Node	VTP_NodeS, *VTP_NodeP;
typedef VTP_NodeS		VTP_TreeS, *VTP_TreeP;
typedef struct _VTP_Annot	VTP_AnnotS, *VTP_AnnotP;


/**********	STRUCTURE DEFINITIONS		**********/

struct _VTP_Node
{
  VTP_OperatorP	oper;
  VTP_NodeP	parent;
  TListS		annots;
};

typedef struct
{
  VTP_OperatorP	oper;
  VTP_NodeP	parent;
  TListS		annots;
  VTP_NodeP	children[1];
} VTP_FixArityNodeS, *VTP_FixArityNodeP;

typedef struct
{
  VTP_OperatorP	oper;
  VTP_NodeP	parent;
  TListS		annots;
  TListS		children;
} VTP_ListNodeS, *VTP_ListNodeP;

typedef struct
{
  VTP_OperatorP	oper;
  VTP_NodeP	parent;
  TListS		annots;
  VTP_AtomP	atom;
} VTP_AtomNodeS, *VTP_AtomNodeP;

typedef struct
{
  VTP_TreeP	*childP;
  int		valid;
  int		nbrElem;
} VTP_TreeChild_IteratorS, *VTP_TreeChild_IteratorP;

typedef VTP_TreeChild_IteratorS
VTP_TreeRChild_IteratorS, *VTP_TreeRChild_IteratorP;

struct _VTP_Annot
{
  VTP_FrameP	frame;
  VTP_AtomP	atom;
};


/**********	NAVIGATION INTERFACE		**********/

#define VTP_TreeChild_Stop(iter) (! ((iter)->valid))
#define VTP_TreeRChild_Stop(iter) (! ((iter)->valid))

IMPORT VTP_TreeP
VTP_TreeChild_First(VTP_TreeP tree, VTP_TreeChild_IteratorP iter);

IMPORT VTP_TreeP
VTP_TreeChild_Next(VTP_TreeChild_IteratorP iter);

IMPORT VTP_TreeP
VTP_TreeRChild_First(VTP_TreeP tree, VTP_TreeChild_IteratorP iter);

IMPORT VTP_TreeP
VTP_TreeRChild_Next(VTP_TreeChild_IteratorP iter);

#define		VTP_TreeChild_DelIter(iter)
#define		VTP_TreeRChild_DelIter(iter)

#define VTP_TreeAnnot_IteratorS		\
	TList_IteratorS

#define VTP_TreeAnnot_IteratorP		\
	TList_IteratorP

#define VTP_TreeAnnot_First(tree, iter)	\
	(VTP_AnnotP)TList_First(&((tree)->annots), iter)

#define VTP_TreeAnnot_Stop(iter)		\
	TList_Stop(iter)

#define VTP_TreeAnnot_Next(iter)		\
	(VTP_AnnotP)TList_Next(iter)

#define VTP_TreeAnnot_DelIter(iter)
IMPORT 
VTP_TreeP	VTP_TreeUp(
			VTP_TreeP tree);
IMPORT 
VTP_TreeP	VTP_TreeRoot(
			VTP_TreeP tree);
IMPORT 
VTP_TreeP	VTP_TreeDown(
			VTP_TreeP tree,
			int rank);
IMPORT 
VTP_TreeP	VTP_TreeRight(
			VTP_TreeP tree);
IMPORT 
VTP_TreeP	VTP_TreeLeft(
			VTP_TreeP tree);


/**********	CREATION INTERFACE		**********/
IMPORT 
VTP_TreeP	VTP_TreeMake(
			VTP_OperatorP op);


/**********	DESTRUCTION INTERFACE		**********/
IMPORT 
void		VTP_TreeDestroy(
			VTP_TreeP tree);


/**********	CONSULTATION INTERFACE		**********/

#define VTP_TREE_OPERATOR(tree)	((tree)->oper)

#define VTP_TREE_PARENT(tree)	((tree)->parent)

#define VTP_TREE_FORMALISM(tree)	\
	VTP_OP_FORMALISM(VTP_TREE_OPERATOR(tree))

#define VTP_TREE_PHYLUM(tree)	VTP_TreePhylum(tree)
IMPORT 
VTP_FormalismP	VTP_TreeFormalism(
			VTP_TreeP tree);
IMPORT 
VTP_OperatorP	VTP_TreeOperator(
			VTP_TreeP tree);
IMPORT 
VTP_PhylumP	VTP_TreePhylum(
			VTP_TreeP tree);
IMPORT 
VTP_AtomP	VTP_TreeGetAnnotValue(
			VTP_TreeP tree,
			VTP_FrameP frame);
IMPORT 
VTP_AnnotP	VTP_TreeGetAnnot(
			VTP_TreeP tree,
			VTP_FrameP frame);
IMPORT 
int		VTP_TreeLength(
			VTP_TreeP tree);
IMPORT 
int		VTP_TreeRank(
			VTP_TreeP tree);
IMPORT 
VTP_AtomTypeP	VTP_TreeAtomType(
			VTP_TreeP tree);
IMPORT 
VTP_AtomP	VTP_TreeAtomValue(
			VTP_TreeP tree);
IMPORT 
int		VTP_TreeEqual(
			VTP_TreeP tree1,
			VTP_TreeP tree2);
IMPORT 
VTP_TreeP	VTP_TreeCopy(
			VTP_TreeP tree);
IMPORT 
int		VTP_TreeIsParent(
				 VTP_TreeP parent,
				 VTP_TreeP son);

/**********	MODIFICATION INTERFACE		**********/
IMPORT 
void		VTP_TreeReplace(
			VTP_TreeP oldTree,
			VTP_TreeP newTree);
IMPORT 
void		VTP_TreeSetChild(
			VTP_TreeP tree,
			VTP_TreeP child,
			int rank);
IMPORT 
void		VTP_TreeSetAtomValue(
			VTP_TreeP tree,
			VTP_AtomP atom);
IMPORT 
void		VTP_TreeDestroySetChild(
			VTP_TreeP tree,
			VTP_TreeP child,
			int rank);
IMPORT 
void		VTP_TreeAdopt(
			VTP_TreeP tree,
			VTP_TreeP child,
			int rank);
IMPORT 
VTP_TreeP	VTP_TreeDisown(
			VTP_TreeP tree,
			int rank);
IMPORT 
void		VTP_TreeDestroyChild(
			VTP_TreeP tree,
			int rank);
IMPORT 
void		VTP_NodePack(
			     VTP_TreeP tree);
IMPORT 
void		VTP_TreePack(
			     VTP_TreeP tree);

#define VTP_ANNOT_FRAME(annot)	((annot)->frame)

#define VTP_ANNOT_VALUE(annot)	((annot)->atom)

#define VTP_ANNOT_TYPE(annot)	\
	VTP_FRAME_ATOM_TYPE(VTP_ANNOT_FRAME(annot))

#define VTP_TREE_ADD_ANNOT(tree, annot)	\
	TList_Append(&((VTP_TreeP)tree)->annots), annot)
IMPORT 
void		VTP_TreeAddAnnot(
			VTP_TreeP tree,
			VTP_AnnotP annot);
IMPORT 
VTP_AnnotP	VTP_TreeRemoveAnnot(
			VTP_TreeP tree,
			VTP_FrameP rank);
IMPORT 
VTP_AnnotP	VTP_AnnotMake(
			VTP_FrameP	frame,
			VTP_AtomP	atom);
IMPORT 
void		VTP_AnnotDestroy(
			VTP_AnnotP	annot);

IMPORT int
VTP_TreeGetCoord(VTP_TreeP tree,
  CIO_PositionP	startPos, CIO_PositionP endPos);

IMPORT void
VTP_TreeSetCoord(VTP_TreeP tree,
  CIO_PositionP startPos, CIO_PositionP endPos);

/**********	DEBUG UTILITIES			**********/
IMPORT 
void		VTP_TreeWrite(
			VTP_TreeP	tree,
			FILE		*fp);
