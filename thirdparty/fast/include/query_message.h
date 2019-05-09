/*
**********
**********   QUERY Environment
**********
**********
* 
* Copyright Sema Group 1996 - 1996
* 
***********
* Pascal Andrieu - Sep 1996
*  Creation
*********** 
*/
#ifndef _QUERY_MESSAGE_H
#define _QUERY_MESSAGE_H

#undef FAST_H_DLL
#define FAST_H_DLL QUERY_LIB
#include <fastdll.h>

IMPORT void
QueryMessage_Add(char* str);

IMPORT void
QueryMessage_Show(void);
 
IMPORT void
QueryMessage_ShowError(void);

IMPORT int
QueryMessage_SetMode(int mode);

IMPORT void
QueryMessage_Reset(void);

IMPORT void
Query_DisplayError(char *mss);
 
IMPORT void
Query_DisplayMessage(char *mss);
 
IMPORT void
Query_CQLError(int level, char *mss);

#endif /* _QUERY_MESSAGE_H */
