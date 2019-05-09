/*
********
********   CONCERTO PPML
********
********

Copyright SEMA GROUP 1995 - 1995

********

Wael Kombar - mars 1996
 Creation

*/
#ifndef _PPTREE_H_
#define _PPTREE_H_

#include <vtp.h>

#undef FAST_H_DLL
#define FAST_H_DLL VTPKIT_LIB
#include <fastdll.h>

typedef struct _PP_TreeCoord *PP_TreeCoordP;

typedef struct
{
  int		length;
  VTP_TreeP	nodes[1];
} PP_TreeCoordPathS, *PP_TreeCoordPathP;

IMPORT void
PP_TreeCoordPush(PP_TreeCoordP dp, VTP_TreeP node, int crank);

IMPORT void
PP_TreeCoordPop(PP_TreeCoordP dp, int crank);

IMPORT PP_TreeCoordP
PP_TreeCoordCreate(void);

IMPORT void
PP_TreeCoordStartStack(PP_TreeCoordP dp, int crank);

IMPORT void
PP_TreeCoordEndStack(PP_TreeCoordP dp);

IMPORT void
PP_TreeCoordDestroy(PP_TreeCoordP dp);

IMPORT void
PP_TreeCoordPathDestroy(PP_TreeCoordPathP path);

IMPORT PP_TreeCoordPathP
PP_TreeCoordPathCreate(int length);

IMPORT PP_TreeCoordPathP
PP_TreeCoordPathFromPos(PP_TreeCoordP dp, int firstChar, int nchar);

IMPORT int
PP_TreeCoordPathToPos(PP_TreeCoordP dp, PP_TreeCoordPathP path,
		      int *firstp, int *lenp);


#endif /* _PPTREE_H_ */
