/*
  ********
  ********   FAST
  ********
  ********

  Copyright Sema Group - 1996

  ********

  Wael Kombar - 16 novembre 1993
  - correction de sgb/33

*/
#ifndef _CTO_HASH_H
#define _CTO_HASH_H

#include <stdcto.h>

#undef FAST_H_DLL
#define FAST_H_DLL CTOKERNEL_LIB
#include <fastdll.h>

/* Flags to explain what to do if the key already exist */
/*  in the table, when trying to put an element */
#define HASH_KEY_UNIQUE		0	/* Generate an exception */
#define HASH_KEY_REPLACE	1	/* Replace the old value by the new one */
#define HASH_KEY_PUSH		2	/* The old value is masked until the new */
								/*  one is removed */
#define HASH_KEY_CONFLICT_FLAG 3

/* Flags to tell if value and key have to be copied */
/*  when put in the hash table */
#define HASH_KEY_NO_COPY	0
#define HASH_KEY_COPY		4
#define HASH_KEY_COPY_FLAG	4

#define HASH_VALUE_NO_COPY	0
#define HASH_VALUE_COPY		8
#define HASH_VALUE_COPY_FLAG 8

typedef struct
	{
	VoidP (*copy)(); /* Copy the key when an element is put in */
	void (*destroy)(); /* Delete the key when an element is removed */
	int (*compare)(); /* Compare two keys */
	int (*hash_function)(); /* The hash function: key -> int */
	int nbr_slots; /* Default number of slots in the table */
	} Hash_KeyClass;

IMPORT GLOBALREF Hash_KeyClass hash_KeyString;
IMPORT GLOBALREF Hash_KeyClass hash_KeyInt;
IMPORT GLOBALREF Hash_KeyClass hash_KeyPtr;

typedef struct
	{
	VoidP (*copy)(); /* Copy the value when an element is put in */
	void (*destroy)(); /* Delete the key when an element is removed */
	} Hash_ValueClass;

IMPORT GLOBALREF Hash_ValueClass hash_ValueString;
IMPORT GLOBALREF Hash_ValueClass hash_ValueInt;
IMPORT GLOBALREF Hash_ValueClass hash_ValuePtr;

typedef struct _Hash_Element
	{
	VoidP key;
	VoidP value;
	struct _Hash_Element *next;
	} Hash_ElementS, *Hash_ElementP;

typedef struct
	{
	Hash_KeyClass	*key_class;
	Hash_ValueClass	*value_class;
	int		flags;
	int		nbr_slots;
	Hash_ElementP	*slots;
	int		nbr_elem;
	int		nbr_per_slot;
	} Hash_TableS, *Hash_TableP;

typedef struct _Hash_TableElem
	{
	Hash_TableP	table;
	Hash_ElementP	*slot;
	Hash_ElementP	elem;
	Hash_ElementP	last;
	} Hash_IteratorS, *Hash_IteratorP;

Exception_Declare(EX_HASH);
Exception_Declare(EX_HASH_NOT_FOUND);
Exception_Declare(EX_HASH_EXIST);

#define HASH_NBR_ELEM(table)	((table)->nbr_elem)
#define HASH_NBR_SLOT(table)	((table)->nbr_slots)
IMPORT 
Hash_TableP Hash_TableCreate(
	Hash_KeyClass *key_class, Hash_ValueClass *value_class,
	int nbr_slots, int flags);
IMPORT 
void Hash_TableDestroy(Hash_TableP table);
IMPORT 
void Hash_TableReset(Hash_TableP table);
IMPORT 
void Hash_TableResize(Hash_TableP table, int nbr_slots);
IMPORT 
void Hash_Put(VoidP key, VoidP value, Hash_TableP table);
IMPORT 
VoidP Hash_Get(VoidP key, Hash_TableP table);
IMPORT 
VoidP Hash_GetDefault(VoidP key, Hash_TableP table, VoidP def);
IMPORT 
void Hash_Delete(VoidP key, Hash_TableP table);
IMPORT 
Hash_ElementP Hash_First(Hash_TableP table, Hash_IteratorP iter);

#define Hash_Stop(iter)	(!((iter)->last))

#define Hash_DelIter(iter)
IMPORT 
Hash_ElementP Hash_Next( Hash_IteratorP iter);

#endif /* _CTO_HASH_H */
