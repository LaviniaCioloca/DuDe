/*********
**********   ENVIRONNEMENT CONCERTO  C
**********   Programme principal de l'analyseur C 
**********

Copyright Sema Group 1995 - 1995

**********
*  Jean-Marc Letteron - Octobre 1998
*   Librairies dynamiques
* Jean-Marc Letteron - Janvier 1998
*  ajout COMMENT_LINE_MACRO
* Jean-Marc Letteron - Janvier 1997
*  macro commentaires
*  PP directives as comments
*  sql macro type 
* Jean-Marc Letteron - Janvier 1996
*  Creation

*/

#ifndef C_CONF_H
#define C_CONF_H

#include <stdio.h>
#include <tlist.h>

#undef FAST_H_DLL
#define FAST_H_DLL  ENV_C_LIB
#include <fastdll.h>

/* Initialisation du systeme de configurations des parsers C
 *   Creation des configurations predefinies.
 *   La configuration vide (configuration predefinie
 *   de nom C_CONF_PREDEF_Empty) devient la configuration courante
 * Doit etre appelee avant toute utilisation d'un parser C */
IMPORT void
C_InitConf ();

/* Une configuration */
typedef struct _C_Conf		*C_ConfP;

/* ********************************************************************** */
/*                              LES TYPES                                 */
/* ********************************************************************** */

typedef enum {
  C_CONF_NOT_DEFINED			= 0,

  C_CONF_TYPE_StorageClass		= 1,
  C_CONF_TYPE_TypeSpecifier		= 2,
  C_CONF_TYPE_TypeQualifier		= 3,
  C_CONF_TYPE_TypeName			= 4,
  C_CONF_TYPE_Identifier		= 5,
  C_CONF_TYPE_AccessSpecifier		= 6,

  C_CONF_TYPE_StringMacro		= 7,
  C_CONF_TYPE_ExpressionMacro		= 8,
  C_CONF_TYPE_StatementMacro		= 9,
  C_CONF_TYPE_DeclarationMacro		= 10,
  C_CONF_TYPE_TypeMacro			= 11,
  C_CONF_TYPE_OpenBlockMacro		= 12,
  C_CONF_TYPE_CloseBlockMacro		= 13,
  C_CONF_TYPE_OpenLoopMacro		= 14,
  C_CONF_TYPE_CloseLoopMacro		= 15,

  C_CONF_TYPE_StringFuncMacro		= 16,
  C_CONF_TYPE_ExpressionFuncMacro	= 17,
  C_CONF_TYPE_StatementFuncMacro	= 18,
  C_CONF_TYPE_DeclarationFuncMacro	= 19,
  C_CONF_TYPE_TypeFuncMacro		= 20,
  C_CONF_TYPE_IdentifierFuncMacro	= 21,
  C_CONF_TYPE_OpenBlockFuncMacro	= 22,
  C_CONF_TYPE_CloseBlockFuncMacro	= 23,
  C_CONF_TYPE_OpenLoopFuncMacro		= 24,
  C_CONF_TYPE_CloseLoopFuncMacro	= 25,

  C_CONF_TYPE_CommentMacro              = 26,
  C_CONF_TYPE_CommentFuncMacro          = 27,
  C_CONF_TYPE_CommentLineMacro          = 28,
  C_CONF_TYPE_SQLMacroStart	        = 29,
  C_CONF_TYPE_SQLMacroType	        = 30,
  C_CONF_TYPE_PPasComment	        = 31

} C_ConfType ;

IMPORT GLOBALREF char		*C_ConfTypeNames[] ;

/* Acces a la representation externe d'un type */
#define C_CONF_TYPE_NAME(type) (C_ConfTypeNames[(type)])

/* ********************************************************************** */
/*                  LES CONFIGURATIONS PREDEFINIES                        */
/* ********************************************************************** */

/* Nom des configurations predefinies */
typedef enum {
  C_CONF_PREDEF_Empty		= 0,
  C_CONF_PREDEF_AnsiC		= 1,
  C_CONF_PREDEF_Cxx		= 2
} C_PredefConfName ;

IMPORT GLOBALREF C_ConfP	C_PredefConfs[] ;
IMPORT GLOBALREF char		*C_PredefConfsString[] ;

/* C_ConfP C_GetPredefConf (C_PredefConfName name);
 *   Acces a une configuration predefinie a partir de son nom  */
#define C_GetPredefConf(name)		(C_PredefConfs[(name)])

/* char *C_GetPredefConf (C_PredefConfName name);
 *   Acces a la representation externe d'une configuration predefinie
 *   a partir de son nom */
#define C_GetPredefConfString(name)	(C_PredefConfsString[(name)])

/* int C_ConfIsPredef(C_ConfP conf);
 *   Test si <conf> est une configuration predefinie. En particulier,
 *   les configurations predefinies ne peuvent etre ni modifiees ni
 *   detruites */
#define C_ConfIsPredef(conf)						\
  ((conf == C_GetPredefConf(C_CONF_PREDEF_Empty))			\
   || (conf == C_GetPredefConf(C_CONF_PREDEF_AnsiC))			\
   || (conf == C_GetPredefConf(C_CONF_PREDEF_Cxx)))

/* ********************************************************************** */
/*                      EDITION DE CONFIGURATIONS                         */
/* ********************************************************************** */

/* Creation d'une configuration vide. */
IMPORT C_ConfP
C_CreateConf ();

/* Creation d'une configuration par copie de la configuration from. */
IMPORT C_ConfP
C_CopyConf (C_ConfP from);

/* Positionnement du type de l'identificateur <name> a <type> dans
 * la configuration <conf>.
 *   Si <type> vaut C_CONF_NOT_DEFINED, cela revient a supprimer
 *   la definition de <name> dans <conf>, c'est-a-dire que les
 *   parsers utilises avec <conf> traiteront <name> comme un identificateur. */
IMPORT void
C_ConfSetType (C_ConfP conf, char *name, C_ConfType type);

/* Suppression de toutes les definitions contenues dans <conf> */
IMPORT void
C_ConfEmpty(C_ConfP conf);

/* Liberation de la configuration <conf>.
 *   Si <conf> est la configuration courante, alors la configuration
 *   courante devient la configuration predefinie de nom C_CONF_PREDEF_Empty */
IMPORT void
C_ConfDestroy (C_ConfP conf);

/* ********************************************************************** */
/*                 INTERROGATION DE CONFIGURATIONS                        */
/* ********************************************************************** */

/* Obtention du type de <name> dans la configuration <conf>.
 *   Une valeur de retout egale a C_CONF_NOT_DEFINED signifie
 *   que <name> n'est pas defini dans <conf>, c'est-a-dire que les
 *   parsers utilises avec <conf> traiteront <name> comme un identificateur. */
IMPORT C_ConfType
C_ConfGetType (C_ConfP conf, char *name);

/* Structure utilisee lors du parcours d'une configuration */
typedef struct _C_ConfDef {
  char		*name;
  C_ConfType	type;
} C_ConfDefS, *C_ConfDefP;

/* Iterateur permettant de parcourir
 * toutes les definitions d'une configuration */
typedef struct {
  C_ConfP		conf;
  int			nbrSlot;
  TList_IteratorS	slotIter;
} C_Conf_IteratorS, *C_Conf_IteratorP ;

IMPORT C_ConfDefP
C_Conf_First(C_ConfP conf, C_Conf_IteratorP iter);

#define C_Conf_Stop(iter)		((iter)->nbrSlot <= 0)

IMPORT C_ConfDefP
C_Conf_Next(C_Conf_IteratorP iter);

#define C_Conf_DelIter(iter)						\
  (((iter)->nbrSlot <= 0) ? 0 : TList_DelIter(&((iter)->slotIter)))

/* ********************************************************************** */
/*             GESTION DE LA CONFIGURATION COURANTE                       */
/* ********************************************************************** */

/* Positionnement de <conf> comme configuration courante, c'est-a-dire
 * que <conf> est la configuration utilisee par les parsers C. */
IMPORT void
C_SetCurrentConf (C_ConfP conf);

/* Lecture et Positionnement de <name> comme configuration courante,
 * <name> peut etre un nom de fichier accessible depuis le repertoire courant,
 * ou le nom d'un fichier de configuration dans <instalDir>/config/env_c/ */
IMPORT C_ConfP
C_OpenConf (char *name);

/* Obtention de la configuration courante */
IMPORT C_ConfP
C_GetCurrentConf ();

/* Obtention du type de <name> dans la configuration courante.
 *   Fonctionnellement equivalent a :
 *      C_ConfGetType(C_GetCurrentConf(),name) */
IMPORT C_ConfType
C_CurrentConfGetType (char *name);

/* Exception levee en cas d'erreur lors de l'analyse d'un fichier de
 * configuration. La valeur est une chaine */
Exception_Declare(EX_C_CONF_PARSE);

/* ********************************************************************** */
/*                         ENTREES/SORTIES                                */
/* ********************************************************************** */

/* Modification de la configuration <conf> par lecture d'un stream devant
 * correspondre a la syntaxe suivante :
 * conf
 * 	: '%C_CONFIGURATION head defs %END_C_CONFIGURATION
 *	;
 * head
 * 	: "=" init ";"
 *	;
 * init
 *	: 'copy" predefined
 *      | 'add' predefined
 *      ;
 * predefined
 *	: 'EMPTY'
 *	| 'ANSI_C'
 *	... (cf. C_PredefConfs et C_PredefConfsString)
 *	;
 * defs
 *	:
 *	| defs def
 *	;
 * def
 *	: type ":" idents ";"
 *	;
 * type
 *	: '%IDENTIFIER'
 *	| '%STORAGE_CLASS'
 *	| '%TYPE_SPECIFIER'
 *	| '%TYPE_QUALIFIER'
 *	... (cf. C_ConfType et C_ConfTypeNames)
 *	;
 * idents
 *	:
 * 	| idents ident
 *	;
 */
IMPORT void
C_ConfRead (C_ConfP conf, CIO_InputP stream); 

/* Ecriture dans le descripteur <fp> de toutes les definitions contenus
 * dans la configuration <conf> sous une forme parsable par C_ConfRead */
IMPORT void
C_ConfWrite(C_ConfP conf, FILE *fp);

#endif /* C_CONF_H */
