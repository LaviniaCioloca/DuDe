/*
**********
**********   AUDIT Environment
**********
**********
* 
* Copyright Sema Group 1995 - 1997
* 
***********
* Thomas Schneider
*  custom -> on enleve l'include X11/Xlib.h - juin 1997 
*  Separation Query - aout 1997
* Michel Guyot - Dec 1996
*  Ajout de AuditValue_MakeInteger, AuditValue_MakeString,
*     AuditValue_MakeNumber
* Pascal Andrieu - Jul 1995
*  Creation
*********** 
*/
#ifndef _AUDIT_COMMON_H
#define _AUDIT_COMMON_H

#include <stdio.h>

#include <stdcto.h>
#include <tlist.h>

#undef FAST_H_DLL
#define FAST_H_DLL AUDIT_LIB
#include <fastdll.h>

#if defined(WIN32PC)
#define AUDIT_DIR_SEP "\\"
#define AUDIT_DIR_SEP_CHR '\\'
#define AUDIT_DIRLST_SEP ";"
#else
#define AUDIT_DIR_SEP "/"
#define AUDIT_DIR_SEP_CHR '/'
#define AUDIT_DIRLST_SEP ":"
#endif

typedef struct AuditCtrlGraphX *AuditCtrlGraphP;

typedef struct {
  void (*init)();
  char *(*levelName)();
  void (*loadApplication)();
  void (*unloadApplication)();
  char *(*getApplication)();
  TListP (*applicationFiles)();
  void (*loadModule)();
  void (*unloadModule)();
  char *(*getModule)();
  TListP (*levelMeasures)();
  TListP (*standardViolations)();
  char * (*measureString)();
  CQL_Value (*measureFilePos)();
  int (*measureLine)();
  int (*computeCtrl)();
  AuditCtrlGraphP (*getCtrlGraph)();
  char *name;
  int nbrLevels;
} AuditS, *AuditP;

IMPORT GLOBALREF AuditP audit_Environment;

#ifdef _WDRAW_HAS_WIN32
GLOBALREF HINSTANCE audit_instance;
#endif

/* Audit */

IMPORT void
Audit_Init(AuditP audit);

IMPORT char*
Audit_TimeString (int time);

EXPORT void
Audit_TableFiles (TListP files, char *directory, char *table_file,
		  char *ref_lang);

EXPORT void
Audit_FilterFiles (TListP files, char *directory, char *filters);

#define Audit_NbrLevels() \
  (audit_Environment->nbrLevels)

#define Audit_LoadApplication(application, data, filter, table, config) \
  (audit_Environment->loadApplication((application), (data), \
				      (filter), (table), (config)))

#define Audit_UnloadApplication() \
  (audit_Environment->unloadApplication())

#define Audit_GetApplication() \
  (audit_Environment->getApplication())

#define Audit_ApplicationFiles() \
  (audit_Environment->applicationFiles())

#define Audit_LoadModule(file, ctx) \
  (audit_Environment->loadModule(file, ctx))

#define Audit_UnloadModule() \
  (audit_Environment->unloadModule())

#define Audit_GetModule() \
  (audit_Environment->getModule())

#define Audit_CtrlExists() \
  (audit_Environment->computeCtrl != NULL)

#define Audit_ComputeCtrl() \
  (audit_Environment->computeCtrl())

#define Audit_GetCtrlGraph(start, end) \
  (audit_Environment->getCtrlGraph(start, end))

#endif /* _AUDIT_COMMON_H */
