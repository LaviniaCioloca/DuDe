
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
#ifndef UTILCTOALLOC_H
#define UTILCTOALLOC_H

#undef FAST_H_DLL
#define FAST_H_DLL CTOKERNEL_LIB
#include <fastdll.h>

typedef struct
	{
	char **blocks; /* block lists (1 entry per managed size) */
	VoidP *frees; /* free object lists (1 entry per managed size) */
	VoidP bigs; /* list of big objects allocated */
	} _Mem_Space, *Mem_Space;
IMPORT GLOBALREF Mem_Space _mem_space;

Exception_Declare(EX_ALLOC);
Exception_Declare(EX_NOMEM);

/********************************************************************************
	FUNCTION Mem_SpaceCreate : initialize the allocation system for the given space
*********************************************************************************
SYNTAX:
	void Mem_SpaceCreate(Mem_Space *space);
ARGUMENTS:
	space is a pointer on a Mem_Space variable.
RETURN:
	None.
ACTION:
	Mem_SpaceCreate(space) initializes the given memory space so the allocation
	system can work on it.
EXAMPLE:
	Mem_Space space;
	Mem_SpaceCreate(&space);
********************************************************************************/
IMPORT void Mem_SpaceCreate(Mem_Space *space);

/********************************************************************************
	FUNCTION
		Mem_Alloc : allocate memory for object of given type
		Mem_AllocSize : allocate memory for object of given size
		Mem_AllocSpace : allocate memory in a given memory space for object
			of the given type
		Mem_AllocSizeSpace : allocate memory in a given memory space for
			object of the given size
*********************************************************************************
SYNTAX:
	type *Mem_Alloc(type);
	VoidP Mem_AllocSize(int size);
	type *Mem_AllocSpace(type, Mem_Space space);
	VoidP Mem_AllocSizeSpace(int size, Mem_Space space);
ARGUMENTS:
	type is the name of a C type (it is highly recommended to use a symbol).
	size is the size of the object (unit is sizeof(char)).
	space is a memory space initialized by Mem_SpaceCreate().
RETURN:
	a pointer on the allocated object.
ACTION:
	These functions allocate enough memory to hold an object.
	The allocated memory is cleared.
EXAMPLE:
	typedef struct { float real,im; } complex;
	complex *ptr1, *ptr2, *ptr3, *ptr4;
	Mem_Space space;
	Mem_SpaceCreate(&space);
	ptr1 = Mem_Alloc(complex);
	ptr2 = (complex *)Mem_AllocSize(sizeof(complex));
	ptr3 = Mem_AllocSpace(complex, space);
	ptr4 = (complex *)Mem_AllocSizeSpace(sizeof(complex), space);
********************************************************************************/
#define Mem_Alloc(type)		\
	((type *)Mem_AllocSizeSpace(sizeof(type),_mem_space))
#define Mem_AllocSize(size)	\
	(Mem_AllocSizeSpace(size,_mem_space))
#define Mem_AllocSpace(type,space)	\
 	((type *)Mem_AllocSizeSpace(sizeof(type),space))
IMPORT VoidP Mem_AllocSizeSpace(int size, Mem_Space space);

/********************************************************************************
	FUNCTION
		Mem_Free : free memory used by the given object
		Mem_FreeSize : free memory of given size
		Mem_FreeSpace : free memory used bu given object, in given memory space
		Mem_FreeSizeSpace : free memory of given size, in given memory space
*********************************************************************************
SYNTAX:
	void Mem_Free(any_object);
	void Mem_FreeSize(VoidP object, int size);
	void Mem_FreeSpace(any_object, Mem_Space space);
	void Mem_FreeSizeSpace(VoidP object, int size, Mem_Space space);
ARGUMENTS:
	any_object is a pointer on a C object (no matter for the type).
	object is a pointer on memory allocated by Mem_Alloc...
	size is the exact size of the memory allocated.
	space is the space where the memory was allocated
RETURN:
	None
ACTION:
	These function free the memory used at the location pointed by object or
	any_object.
CAUTION:
	Memory should have been allocated by Mem_alloc or Mem_allocSize.
	When the size is not given, it is computed from the type of object. In this
	case, sizeof(*any_object) should return the exact allocated size
EXAMPLE:
	Mem_Space space;
	Mem_SpaceCreate(&space);
	complex *ptr1=Mem_Alloc(complex);
	complex *ptr2=Mem_Alloc(complex);
	complex *ptr3=Mem_AllocSpace(complex, space);
	complex *ptr4=Mem_AllocSpace(complex, space);
	Mem_Free(ptr1);
	Mem_FreeSize(ptr2, sizeof(complex));
	Mem_FreeSpace(ptr3, space);
	Mem_FreeSizeSpace(ptr4, sizeof(complex), space);
********************************************************************************/
#define Mem_Free(p)		(Mem_FreeSizeSpace(p,sizeof(*(p)),_mem_space))
#define Mem_FreeSize(p,s)	(Mem_FreeSizeSpace(p,s,_mem_space))
#define Mem_FreeSpace(p,space)	(Mem_FreeSizeSpace(p,sizeof(*(p)),space))
#ifndef Mem_FreeSizeSpace
IMPORT void Mem_FreeSizeSpace(VoidP p, int size, Mem_Space space);
#endif

/********************************************************************************
	FUNCTION
		Mem_Realloc : modify the size of allocated memory
		Mem_ReallocSpace : modify the size of allocated memory, in given memory space
*********************************************************************************
SYNTAX:
	VoidP Mem_Realloc(VoidP object, int old, int new);
	VoidP Mem_ReallocSpace(VoidP object, int old, int new, Mem_Space space);
ARGUMENTS:
	object is a pointer on allocated memory.
	old is the size of allocated memory before calling the function.
	new is the required new size.
RETURN:
	The returned value is a pointer on a new place where the object were moved.
ACTION:
	These functions allocate enough memory to hold the new size of object, copy
	the old object into it and free its old place.
CAUTION:
	Memory should have been allocated by Mem_alloc or Mem_allocs.
EXAMPLE:
	Mem_Space space;
        Mem_SpaceCreate(&space);
	char *p1=(char *)Mem_AllocSize(10);
	char *p2=(char *)Mem_AllocSizeSpace(10,space);
	p1=Mem_Realloc(p1,10,20);
	p2=Mem_Realloc(p2,10,5);
********************************************************************************/
#define Mem_Realloc(p,s1,s2)	Mem_ReallocSpace(p,s1,s2,_mem_space)
#ifndef Mem_ReallocSpace
IMPORT VoidP Mem_ReallocSpace(VoidP p, int old_size, int new_size, Mem_Space space);
#endif

/********************************************************************************
	FUNCTION Mem_SpaceDestroy : free all memory of a given memory space
*********************************************************************************
SYNTAX:
	void Mem_SpaceDestroy(Mem_Space *space);
ARGUMENTS:
	space is a memory space initialized by Mem_SpaceCreate
RETURN:
	None
ACTION:
	Mem_SpaceDestroy frees all the memory allocated thru Mem_Alloc system in
	the given memory space.
	The memory is returned to the system.
EXAMPLE:
	Mem_Space space;
	Mem_SpaceCreate(&space);
	Mem_SpaceDestroy(&space);
********************************************************************************/
IMPORT void Mem_SpaceDestroy(Mem_Space *space);


#define Mem_NewString(str)	\
	(str ?			\
	    (char *)strcpy((char *)Mem_AllocSize(strlen(str)+1), str)	\
	    : (char *)0)

#define Mem_FreeString(str)	\
	do { if(str) Mem_FreeSize(str, strlen(str)+1); } while(0)

#define Mem_NewStringSpace(str, space)	\
	(str ?	\
		(char *)strcpy((char *)Mem_AllocSizeSpace(strlen(str)+1, space), str) : 0)
#define Mem_FreeStringSpace(str, space)	\
	do { if(str) Mem_FreeSizeSpace(str, strlen(str)+1, space); } while(0)

#endif /* UTILCTOALLOC_H */
