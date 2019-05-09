#include <hash.h>

#undef FAST_H_DLL
#define FAST_H_DLL VTPKIT_LIB
#include <fastdll.h>

/**********	STRUCTURE TYPEDEFS	**********/

typedef struct _VTP_Phylum		VTP_PhylumS, *VTP_PhylumP;
typedef struct _VTP_AtomOperator	VTP_AtomOperatorS, *VTP_AtomOperatorP;
typedef struct _VTP_ListOperator	VTP_ListOperatorS, *VTP_ListOperatorP;
typedef struct _VTP_FixArityOperator	VTP_FixArityOperatorS,
					*VTP_FixArityOperatorP;
typedef struct _VTP_Operator		VTP_OperatorS, *VTP_OperatorP;
typedef struct _VTP_Frame		VTP_FrameS, *VTP_FrameP;
typedef struct _VTP_Formalism		VTP_FormalismS, *VTP_FormalismP;


/**********	STRUCTURE DEFINITIONS	**********/

struct _VTP_Phylum
	{
	char	*name;
	VTP_FormalismP	formalism;
	Word2	code;
	TListS	operators;
	};

struct _VTP_AtomOperator
	{
	char	*name;
	VTP_FormalismP	formalism;
	Word2	arity;
	Word2	code;
	VTP_AtomTypeP	atomType;
	};

struct _VTP_ListOperator
	{
	char	*name;
	VTP_FormalismP	formalism;
	Word2	arity;
	Word2	code;
	VTP_PhylumP	phylum;
	};

struct _VTP_FixArityOperator
	{
	char	*name;
	VTP_FormalismP	formalism;
	Word2	arity;
	Word2	code;
	VTP_PhylumP	phylums[1];
	};

struct _VTP_Operator
	{
	char	*name;
	VTP_FormalismP	formalism;
	Word2	arity;
	Word2	code;
	};

struct _VTP_Frame
	{
	char		*name;
	VTP_AtomTypeP	atomType;
	VTP_PhylumP	phylum;
	Word4		control;
	};

struct _VTP_Formalism
	{
	char	*name;
	int	version;
	TListS	operators;
	TListS	phylums;
	TListS	frames;
	Hash_TableP	phyl_op_table;
	};


/**********	FORMALISM INTERFACE	**********/

#define VTP_FORM_NAME(form)	((form)->name)

#define VTP_FORM_VERSION(form)	((form)->version)

#define VTP_FormalismPhylum_IteratorS		\
	TList_IteratorS

#define VTP_FormalismPhylum_IteratorP		\
	TList_IteratorP

#define VTP_FormalismPhylum_First(form, iter)	\
	(VTP_PhylumP)TList_First(&((form)->phylums), iter)

#define VTP_FormalismPhylum_Stop(iter)		\
	TList_Stop(iter)

#define VTP_FormalismPhylum_Next(iter)		\
	(VTP_PhylumP)TList_Next(iter)

#define VTP_FormalismPhylum_DelIter(iter)

#define VTP_FormalismFrame_IteratorS		\
	TList_IteratorS

#define VTP_FormalismFrame_IteratorP		\
	TList_IteratorP

#define VTP_FormalismFrame_First(form, iter)	\
	(VTP_FrameP)TList_First(&((form)->frames), iter)

#define VTP_FormalismFrame_Stop(iter)		\
	TList_Stop(iter)

#define VTP_FormalismFrame_Next(iter)		\
	(VTP_FrameP)TList_Next(iter)

#define VTP_FormalismFrame_DelIter(iter)

#define VTP_FormalismOperator_IteratorS		\
	TList_IteratorS

#define VTP_FormalismOperator_IteratorP		\
	TList_IteratorP

#define VTP_FormalismOperator_First(form, iter)	\
	(VTP_OperatorP)TList_First(&((form)->operators), iter)

#define VTP_FormalismOperator_Stop(iter)		\
	TList_Stop(iter)

#define VTP_FormalismOperator_Next(iter)		\
	(VTP_OperatorP)TList_Next(iter)

#define VTP_FormalismOperator_DelIter(iter)
IMPORT 
VTP_FormalismP	VTP_FormalismMake(
			char *name,
			int version);
IMPORT 
VTP_FormalismP	VTP_FormalismFind(
			char *name,
			int version);
IMPORT 
VTP_FormalismP	VTP_FormalismLoad(
			char *file);
IMPORT 
void		VTP_FormalismSave(
			VTP_FormalismP form,
			char *file);

/**********	PHYLUM INTERFACE	**********/

#define VTP_PHYLUM_NAME(phyl)		((phyl)->name)

#define VTP_PHYLUM_FORMALISM(phyl)	((phyl)->formalism)

#define VTP_PHYLUM_CODE(phyl)		((phyl)->code)

#define VTP_PhylumOperator_IteratorS		\
	TList_IteratorS

#define VTP_PhylumOperator_IteratorP		\
	TList_IteratorP

#define VTP_PhylumOperator_First(phyl, iter)	\
	(VTP_OperatorP)TList_First(&((phyl)->operators), iter)

#define VTP_PhylumOperator_Stop(iter)		\
	TList_Stop(iter)

#define VTP_PhylumOperator_Next(iter)		\
	(VTP_OperatorP)TList_Next(iter)

#define VTP_PhylumOperator_DelIter(iter)
IMPORT 
VTP_PhylumP	VTP_PhylumMake(
			char *name,
			VTP_FormalismP form);
IMPORT 
VTP_PhylumP	VTP_PhylumByName(
			char *name,
			VTP_FormalismP form);
IMPORT 
int		VTP_PhylumHasOperator(
			VTP_PhylumP phyl,
			VTP_OperatorP op);
IMPORT 
void		VTP_PhylumAddOperator(
			VTP_PhylumP phyl,
			VTP_OperatorP op);


/**********	OPERATOR INTERFACE	**********/

#define VTP_ATOM_ARITY		0
#define VTP_LIST0_ARITY		0xfffe
#define VTP_LIST1_ARITY		0xffff

#define VTP_OP_NAME(op)		((op)->name)
#define VTP_OP_FORMALISM(op)	((op)->formalism)
#define VTP_OP_CODE(op)		((op)->code)
#define VTP_OP_ARITY(op)	((op)->arity)
#define VTP_OP_IS_ATOM(op)	((op)->arity == 0)
#define VTP_OP_IS_LIST0(op)	((op)->arity == VTP_LIST0_ARITY)
#define VTP_OP_IS_LIST1(op)	((op)->arity == VTP_LIST1_ARITY)
#define VTP_OP_IS_LIST(op)	((op)->arity >= VTP_LIST0_ARITY)
#define VTP_OP_IS_FIX_ARITY(op)	(((op)->arity < VTP_LIST0_ARITY) && (op)->arity)
#define VTP_OP_ATOM_TYPE(op)	\
	(((VTP_AtomOperatorP)(op))->atomType)
#define VTP_OP_SONS_PHYLUM(op)	\
	(((VTP_ListOperatorP)(op))->phylum)
#define VTP_OP_NTH_PHYLUM(op, n) \
	((VTP_PhylumP)((((VTP_FixArityOperatorP)(op))->phylums[n])))
IMPORT 
VTP_OperatorP	VTP_OperatorByName(
			char *name,
			VTP_FormalismP form);
IMPORT 
VTP_AtomOperatorP VTP_OperatorMakeAtom(
			char *name,
			VTP_FormalismP form);
IMPORT 
void		VTP_OperatorSetAtomType(
			VTP_AtomOperatorP op,
			VTP_AtomTypeP atomType);
IMPORT 
VTP_ListOperatorP VTP_OperatorMakeList(
			char *name,
			VTP_FormalismP form,
			int arity);
IMPORT 
void		VTP_OperatorSetPhylum(
			VTP_ListOperatorP op,
			VTP_PhylumP phyl);
IMPORT 
VTP_FixArityOperatorP VTP_OperatorMakeFixArity(
			char *name,
			VTP_FormalismP form,
			int arity);
IMPORT 
void		VTP_OperatorSetNthPhylum(
			VTP_FixArityOperatorP op,
			VTP_PhylumP phyl,
			int rank);
IMPORT 
VTP_OperatorP	VTP_OperatorMake(
			char *name,
			VTP_FormalismP form,
			int arity);


/**********	FRAME INTERFACE	**********/

#define VTP_FRAME_NAME(frame)		((frame)->name)
#define VTP_FRAME_ATOM_TYPE(frame)	((frame)->atomType)
#define VTP_FRAME_PHYLUM(frame)		((frame)->phylum)
#define VTP_FRAME_CONTROL(frame)	((frame)->control)

#define VTP_CONTROL_COPY	0x01
#define VTP_CONTROL_SAVE	0x02
#define VTP_CONTROL_EQUAL	0x04

#define VTP_FRAME_CHECK_CONTROL(frame, ctrl)	\
	((frame)->control & (ctrl))
IMPORT 
VTP_FrameP	VTP_FrameMake(
			char *name,
			VTP_FormalismP form);
IMPORT 
VTP_FrameP	VTP_FrameByName(
			char *name,
			VTP_FormalismP form);
IMPORT 
void		VTP_FrameSetAtomType(
			VTP_FrameP frame,
			VTP_AtomTypeP atomType);
IMPORT 
void		VTP_FrameSetControls(
			VTP_FrameP frame,
			Word4 controls);
IMPORT 
void		VTP_FrameAddControl(
			VTP_FrameP frame,
			Word4 control);
IMPORT 
void		VTP_FrameRemoveControl(
			VTP_FrameP frame,
			Word4 control);
