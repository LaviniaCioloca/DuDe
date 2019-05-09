/*
-- Martin Gaschignard - 26 juin 1997
--  Ajout des types d'atome temporaires.
*/
#include <stdcto.h>
#undef FAST_H_DLL
#define FAST_H_DLL VTPKIT_LIB
#include <fastdll.h>

typedef struct _VTP_AtomType	VTP_AtomTypeS, *VTP_AtomTypeP;

typedef VoidP VTP_AtomP;

typedef void (*_VTP_VoidFunc)();
typedef VTP_AtomP (*_VTP_AtomFunc)();
typedef int (*_VTP_IntFunc)();
typedef char *(*_VTP_StringFunc)();

struct _VTP_AtomType
	{
	char		*name;
	_VTP_AtomFunc	copy;
	_VTP_VoidFunc	destroy;
	_VTP_IntFunc	equal;
	_VTP_AtomFunc	fromString;
	_VTP_StringFunc	toString;
	};

#define VTP_ATOM_TYPE_NAME(atomType)		((atomType)->name)

#define VTP_ATOM_COPY(atom, atomType)		(atomType)->copy(atom)
#define VTP_ATOM_DESTROY(atom, atomType)	(atomType)->destroy(atom)
#define VTP_ATOM_EQUAL(atom, atomType, atom2)	(atomType)->equal(atom, atom2)

#define VTP_ATOM_FROM_STRING(atomType, str)	(atomType)->fromString(str)
#define VTP_ATOM_TO_STRING(atom, atomType)	(atomType)->toString(atom)

typedef struct
	{
	char	*name;
	int	nbrRef;
	} VTP_NameS;

typedef struct {
  int	startLine, endLine;
  int	startPos, endPos;
  short	startCol, endCol;
} VTP_CoordS, *VTP_CoordP;

#define VTP_INTEGER_VALUE(atom)	(int)(atom)
#define VTP_STRING_VALUE(atom)	(char *)(atom)
#define VTP_NAME_STRING(atom)	(((VTP_NameS *)(atom))->name)
#define VTP_NAME_VALUE(atom)	(((VTP_NameS *)(atom))->name)
#define VTP_COORD_VALUE(atom)	((VTP_CoordP)atom)

#define VTP_VOID_MAKE()		VTP_ATOM_FROM_STRING(vtp_at_void, "")
#define VTP_INTEGER_MAKE(value)	(VTP_AtomP)(value)
#define VTP_STRING_MAKE(value)	(VTP_AtomP)(Mem_NewString(value))
#define VTP_NAME_MAKE(value)	VTP_ATOM_FROM_STRING(vtp_at_name, value)

IMPORT GLOBALREF VTP_AtomTypeP vtp_at_void;
IMPORT GLOBALREF VTP_AtomTypeP vtp_at_integer;
IMPORT GLOBALREF VTP_AtomTypeP vtp_at_string;
IMPORT GLOBALREF VTP_AtomTypeP vtp_at_name;
IMPORT GLOBALREF VTP_AtomTypeP vtp_at_coord;
IMPORT GLOBALREF VTP_AtomTypeP vtp_at_tree;
IMPORT 
void		VTP_AtomTypeDeclare(
			VTP_AtomTypeP atomType);
IMPORT 
VTP_AtomTypeP	VTP_AtomTypeByName(
			char *name);
IMPORT 
VTP_AtomTypeP   VTP_AtomTypeMakeTmp(
			char *name);
IMPORT 
void _VTP_AtomTypeInit(void);
