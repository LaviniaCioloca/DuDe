/*********
**********   ENVIRONNEMENT CONCERTO  C
**********   Gestion de la table des types
**********
*
* Copyright Sema Group 1995 - 1995
*
*  Jean-Marc Letteron - Octobre 1998
*   Librairies dynamiques
*
**********

*/

#ifndef C_TYPETABLE_H
#define C_TYPETABLE_H

#include <stdio.h>
#include <tlist.h>
#include <vtp.h>

#undef FAST_H_DLL
#define FAST_H_DLL  ENV_C_LIB
#include <fastdll.h>

#define NO_USE            0
#define INCLUDED_MODULES  1
#define INCLUDING_MODULES 2

IMPORT void
C_ParserSetTypeTableUseLevel(int level);

IMPORT int
C_ParserGetTypeTableUseLevel();

/* Type de la fonction de callback appelee lorsqu'on se trouve en presence
 * d'une table des types non a jour.
 */
typedef int (*CallBackP)(char *filename);

/* Mise a jour de le fonction de callback appelee lors d'une faute de
 * page pour la table des types.
 */
IMPORT void
C_ParserSetUpdateCallback(CallBackP func);

/* Renvoie le pointeur sur la fonction de callback de faute de page.
 */
IMPORT CallBackP
C_ParserGetUpdateCallback();

/* Charge dans la table des types les modules contenus dans la liste
 * 'context'. Ces modules ne sont pas inclus.
 */
IMPORT void
C_TypeTableSetContext(TListP context);


/* Teste l'existence dans la table des types courante l'existence d'une
 * entree correspondant a 'name' */
IMPORT int
C_IsTypeName(char *name);

/* Cree une nouvelle table de hachage, initialise la pile, empile une
 * nouvelle TListP destinee a contenir les types declares dans le scope
 * courant */
IMPORT void
C_TypeTableCreate();

/* Sauvegarde dans le fichier 'filename' de la liste des tables des types
 * calculees. */
IMPORT void
C_ApplicationTypeTablesDump(char *filename);

/* Chargement a partir du fichier 'filename' de la liste des tables des types
 * de l'application. */
IMPORT void
C_ApplicationTypeTablesLoad(char *filename);

/* Liberation des tables calculees;
 */
IMPORT void
C_ApplicationTypeTablesFree();

/* Sauvegarde la table des types courantes dans la liste des tables des
 * types calculees */
IMPORT void
C_TypeTableSave();

/* Empile une nouvelle TListP destinee a contenir les types declares
 * dans le scope courant (on ne conserve pas le nom du scope)*/
void
C_TypeTablePushAuto();

/* Empile une nouvelle TListP destinee a contenir les types declares
 * dans le scope courant (on conserve le nom du scope)*/
void
C_TypeTablePushClass();

/* Depile la derniere TListP contenant les types declares dans le scope
 * scope courant (a appeler a la sortie du scope) */
void
C_TypeTablePopAuto();

/* Depile la derniere TListP contenant les types declares dans le scope
 * scope courant (a appeler a la sortie du scope)
 * les types declares sont transferes dans le scope englobant avec un
 * nom qualifie */
void
C_TypeTablePopClass();

void
C_Add_Using_Directive();

void
C_Add_Type_Name();

void
C_Add_Typedef_Name();

void
C_Add_Namespace_Name();

void
C_Type_Name_Push();

void
C_Template_Arg_Pop();

void
C_Template_Args_List_Push();

void
C_Template_Args_List_Pop();

void
C_Current_Scope_Push();

void
C_Reset_Current_Scope();

IMPORT void
C_ParserSetModuleName(char *name);

IMPORT char *
C_ParserGetApplicationDirectory(void);

IMPORT void
C_ParserSetApplicationDirectory(char *name);

void
C_LoadTypeTable(char *file_name, int islocal);

#endif /* C_TYPETABLE_H */
