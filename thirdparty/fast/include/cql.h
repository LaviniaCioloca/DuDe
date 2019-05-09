/*********
**********   ENVIRONNEMENT CONCERTO  CQL
**********   Programme principal de l'analyseur CQL
**********

Copyright Sema Group 1995 - 1995

**********

*/

#ifndef CQL_H
#define CQL_H

#include <ctoio.h>
#include <stdio.h>
#include <stdcto.h>
#include <ctoaction.h>
#include <hash.h>
#include <vtp.h>
#include <cql_f.h>

#include <bytestring.h>

#include <cqltypes.h>
#include <cqlvalue.h>
#include <cqltable.h>

#undef FAST_H_DLL
#define FAST_H_DLL CQL_LIB
#include <fastdll.h>

typedef void(*CQL_ErrorHandler)(int, CIO_PositionP);

IMPORT VTP_TreeP
CQL_Parse(CIO_InputP streamP);

Exception_Declare (EX_CQL_EVAL);   /* valeur = arbre errone */

/* un outil utilisant cql comme noyau doit soit utiliser cql batch 
 * (cf cqlbatch.a), soit definir les fonctions CQL :
 * error ( string ) -> void  affiche un message et appelle CQL_Abort
 * message ( string ) -> void  affiche un message 
 * synchronize ( ) -> void  synchronize l'affichage avec l'etat courant
 * input ( string, list ) -> void demande des valeurs a l'utilisateur (ne fait
 *  rien dans le cas de cql batch)
 */
#define CQL_INT_ERROR_FUNCTION "_cql_error_"
#define CQL_ERROR_FUNCTION "error"
#define CQL_MESSAGE_FUNCTION "message"
#define CQL_SYNCHRO_FUNCTION "synchronize"
#define CQL_INPUT_FUNCTION "input"
#define CQL_IMPORT_FUNCTION "import"

/* Un outil interactif peut definir cette fonction afin de permettre la saisie
 * interactive des parametres d'un programme
 * Cette saisie est activee automatiquement si le programme a des parametres alors
 * qu'aucun parametre n'est passe a CQL_Eval.
 */
#define CQL_INPUT_PARAMETERS_FUNCTION "input_parameters"

/* initialisation de l'evaluateur */
IMPORT void CQL_Init (void);

/* appel de l'evaluateur
 * tree est l'arbre CQL a evaluer
 * names est le tableau des noms des parametres du programme CQL
 * values est le tableau des valeurs correspondantes (chaines)
 * varc est le nomble d'elements de ces tableaux
 * Retourne -1 si l'evaluation a produit des erreurs.
 */
IMPORT int CQL_Eval(CIO_InputP stream, int varc, char** names, char** values);

/* appel de l'evaluateur pour importer une libairie
 * name est le nom de la librairie a importee
 * dans le cas standard (la fonction import n'existe pas)
 * evalue le fichier de nom "name"
 * Leve une exception si l'evaluation a produit des erreurs.
 */
IMPORT int CQL_ImportLibrary(char *name);


/* context d'evaluation au sens des actions
 * positionne et recupere le contexte d'evaluation pouvant etre utilise
 * lors de l'evaluation des fonctions CQL implementees en C
 * CQL_SetEvalContext fait une copie du contexte passe en parametre
 * CQL_GetEvalContext retourne un pointeur sur le contexte qui est donc partage
 *   on a pas le droit de le detruire, si on le modifie ca modifie le contexte
 *   pour CQL
 */
IMPORT void CQL_SetEvalContext(Action_EvalContextP eval_ctx);
IMPORT Action_EvalContextP CQL_GetEvalContext(void);

IMPORT void CQL_SetExitHandler(void (*func)(VoidP), VoidP data);

#endif /* CQL_H */
