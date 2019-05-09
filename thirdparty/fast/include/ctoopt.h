/*
  ********
  ********   FAST
  ********
  ********

  Copyright Sema Group - 1996

  ********
*/
#ifndef _CTOOPT_H
#define _CTOOPT_H

#undef FAST_H_DLL
#define FAST_H_DLL CTOKERNEL_LIB
#include <fastdll.h>

/* Where is the installation directory */
IMPORT GLOBALREF char *_fast_instal_dir;

Exception_Declare(EX_FAST_INSTAL_VAR);

/** CTODOC REGISTRY_INTRODUCTION
.pp
Windows only.
.pp
This set of utilities allows manipulation of Concerto registry keys. 
Windows manages a key database that store values on keys. Keys belong to the local machine branch
or the current user branch. Only keys from the user branch can be modified from within
an application
.pp
ctokernel provides a fast_key, which is the father of all other keys. The default fast_key
is Software\Sema Group\Concerto 2\. It can be changed with 
.=q Fast_RegistryKey
**/

/* read the sub-key under the fast key */
/* private to this component */
#ifdef WIN32PC

#include <windows.h>

/** CTODOC REGISTRY_FUNCTION
.<D ReadFastSubkey
.=A key char *
.=A value char *
.=A value_length int *
.=R BOOL
.=T "C FUNCTION TYPE"
.=H ctokernel "ctoopt.h"
.>D
.pp
This function reads the default value of the key under the fast_key. The key from the current
user is used first. if it does not exist, the key from the local machine branch is used.
value is the string buffer filled with the key value. value_length indicates the length of the
value buffer. On return, its value is the actual length of the key value.
This function returns TRUE if it succeeds, FALSE if not.
**/

BOOL ReadFastSubkey(char * key,char * value, int * value_length);

/** CTODOC REGISTRY_FUNCTION
.<D Fast_RegistryKey
.=A key char *
.=R void
.=T "C FUNCTION TYPE"
.=H ctokernel "ctoopt.h"
.>D
.pp
Define the registry key that contains program informations.
Default is Software\Sema Group\Concerto 2\
**/
EXPORT void
Fast_RegistryKey(char *key);

/** CTODOC REGISTRY_FUNCTION
.<D Fast_WriteFastSubkey
.=A key char *
.=A value char *
.=R BOOL
.=T "C FUNCTION TYPE"
.=H ctokernel "ctoopt.h"
.>D
.pp
Write the key value on the current user branch. If the key does not exist, it is created.
**/
EXPORT BOOL 
Fast_WriteFastSubkey(char * key, char * value);
#endif

/****
  Define the environment variable that tells where is
  the installation directory

  Should be called at the beginning of any program
  ****/
IMPORT void
Fast_InstalDirVar(
		  char		*var);
IMPORT 
char *
Fast_GetInstalDir(void);
IMPORT 
char *
Fast_GetInstalSubDir(char *subdir);

#define Fast_GetInstalFile(file)	Fast_GetInstalSubDir(file)

IMPORT void
Fast_SetAppName(char *name);
IMPORT 
char *
Fast_GetAppName(void);

IMPORT void
Fast_SetStringOption(char *var, char *val);
IMPORT 
char *
Fast_GetStringOption(char *var, char *def);

IMPORT int
Fast_GetIntOption(char *var, int def);

#ifdef TO_COMPLETE
IMPORT char *
Fast_GetStringOption(
		     char	*name,
		     char	*cmdline,
		     char	*prefix);

IMPORT int
Fast_GetIntOption(
		     char	*name,
		     char	*cmdline,
		     char	*prefix);

IMPORT int
Fast_GetBoolOption(
		     char	*name,
		     char	*cmdline,
		     char	*prefix);
#endif /* TO_COMPLETE */

#endif /* _CTOOPT_H */
