/*
**********
**********   CQL Kernel
**********
**********
* 
* Copyright Sema Group 1995 - 1997
* 
**********
* Michel Guyot - Septembre 1995
*  Creation
**********
*
*/

/****************************/
/* divers types de base CQL */
/****************************/


#ifndef CQLTYPES_H
#define CQLTYPES_H

#undef FAST_H_DLL
#define FAST_H_DLL CQL_LIB
#include <fastdll.h>

/* Retour des fonctions permettant de savoir si celle-ci s'est bien */
/* deroulee */
#define CQL_OK 1
#define CQL_NOK -1

typedef int CQL_ReturnStatus;

/* pointeur vers une chaine */
typedef char* CQL_StringP;
typedef CQL_StringP* CQL_StringPP;


/* booleen C (a ne pas melanger avec les booleens CQL) */
#define CQL_FALSE 0
#define CQL_TRUE 1

typedef int CQL_BooleanS, *CQL_BooleanP;

/* pointeur vers un fichier */
typedef FILE * CQL_FileP;

/* valeur d'une table */
/* ATTENTION: il faut que toutes les alternatives tiennent dans VoidP */
typedef union {
  float f;
  long  l;
  int   i;
  VoidP p;
} CQL_Value, *CQL_ValueP;

/* Une ligne d'un tableau est un tableau de valeurs. Les valeurs
 * correspondent aux colonnes qui peuvent etre parcourues a l'aide de
 * l'iterateur sur les colonnes
 */ 

/* Le nom d'une fonction */
typedef char * CQL_FunctionNameP;

/* Les fonctions effectuant l'evaluation des expressions CQL */
/* (signature incomplete : il peut y avoir 0, 1 ou 2 arguments) */
typedef CQL_Value (* CQL_FunctionP)();

/* pointeur de pointeur de fonction */
typedef CQL_FunctionP *CQL_FunctionPP;

/* pour les fonctions, qui manipulent des CQL_EvalNodeP sans en connaitre
 * le contenu.
 */
typedef struct CQL_EvalNodeX *CQL_EvalNodeP;

typedef CQL_EvalNodeP *CQL_EvalNodePP;

#endif /* CQLTYPES_H */
