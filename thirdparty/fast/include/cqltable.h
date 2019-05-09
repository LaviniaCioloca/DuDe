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

/**************/
/* tables CQL */
/**************/

#ifndef CQLTABLE_H
#define CQLTABLE_H

#include <stdio.h>

#undef FAST_H_DLL
#define FAST_H_DLL CQL_LIB
#include <fastdll.h>

/**************************************/
/* types concernant les nom de colonne */
/**************************************/

/* type "nom de colonne" */
typedef char * CQL_ColumnNameP;

/* type "nom d'attribut" */
typedef char *CQL_TableAttributeNameP;

/* permet d'acceder rapidement a une colonne (sans parcourir la liste */
/* des colonnes */
typedef VoidP CQL_ColumnAccessP;

/********************************/
/* types concernant les indexes */
/********************************/

/*******************************/
/* types concernant les tables */
/*******************************/

typedef char * CQL_TableNameP;

typedef struct CQL_indexIteratorX  *CQL_IndexIteratorP;

typedef struct CQL_columnIteratorX  *CQL_ColumnIteratorP;

typedef struct CQL_rowIteratorX  *CQL_RowIteratorP;

typedef struct CQL_tableX *CQL_TableP;

/*----------------------*/
/* fonctions des tables */
/*----------------------*/

/* retourne un nouveau pointeur sur la table (la table en deduit qu'elle
* a un utilisateur en plus) */
typedef CQL_TableP (* CQL_TableDuplicateFP) (CQL_TableP);

/* indique a la table que l'utilisateur qui avait demande le pointeur
 * passe en parametre n'a plus l'intention de s'en servir */
typedef CQL_ReturnStatus (* CQL_TableReleaseFP) (CQL_TableP);

/* detruit la table meme si tous les release n'ont pas ete faits
 */
typedef CQL_ReturnStatus (* CQL_TableDeleteFP) (CQL_TableP);

/* Retourne le nom de la table. C'est une valeur partagee.
 */
typedef CQL_TableNameP (* CQL_TableGetNameFP) (CQL_TableP);

/* Retourne un pointeur vers le type de la colonne dont le nom est passe en 
 * parametre, ou NULL si aucune colonne ne porte ce nom.
 */
typedef CQL_TypeP (* CQL_TableGetColumnTypeFP) (CQL_TableP, CQL_ColumnNameP );

/* Retourne un pointeur vers le type de l'attribut dont le nom est passe en
 * parametre, ou NULL si aucun attribut ne porte ce nom.
 * positionne la valeur dont l'adresse est passee en parametre
 */
typedef CQL_TypeP (* CQL_TableGetAttributeFP) (CQL_TableP, CQL_TableAttributeNameP,
		CQL_ValueP );

/* Positionne l'attribut dont le nom est passe en parametre
 * Si la type passe est NULL, l'attribut est supprime
 * Sinon le type et la valeur sont modifies
 * Si la modification est acceptee par la table, la valeur retournee est 1
 * Sinon aucune modification n'est faite et la valeur retournee est 0
 */
typedef int (* CQL_TableSetAttributeFP) (CQL_TableP, CQL_TableAttributeNameP,
		CQL_TypeP, CQL_Value );

/* la requete ne peut pas etre traitee */
#define CQL_MAP_IMPOSSIBLE -1
/* la table est vide */
#define CQL_MAP_EMPTY 0
/* la table est en memoire, peu d'elements */
#define CQL_MAP_VERY_FAST 1
/* la table est en memoire, nbre d'elements moyen */
/* ou la table est sur disque, avec un seul element */
#define CQL_MAP_FAST 10
/* la table est en memoire, nbre d'elements important */
/* ou la table est sur disque, avec peu d'elements, */
/* ou avec un nombre moyen d'elements lus sur disque en une seule fois */
#define CQL_MAP_SLOW 100        
/* la table est sur disque, avec un petit nombre d'elements lus un a un, ou */
/* un nombre moyen d'elements lus en plusieurs fois, ou */
/* un nombre important d'elements lus sur disque en une seule  fois */
#define CQL_MAP_VERY_SLOW 1000
/* la table est sur disque, avec un nombre moyen d'elements lus un a un, ou */
/* un nombre important d'elements lus en plusieurs fois (ou pire, un a un) */
#define CQL_MAP_SLOWEST 10000

#define CQL_SIZE_EMPTY 0        /* table vide */
#define CQL_SIZE_ONE 1          /* table a un element */
#define CQL_SIZE_SMALL 10       /* table a quelques dizaines d'elements */
#define CQL_SIZE_BIG 100        /* table a quelques centaines d'elements */
#define CQL_SIZE_VERY_BIG 1000  /* table a quelques milliers d'elements */
#define CQL_SIZE_BIGGEST 10000  /* tailles au-dessus */

/* tables internes (contenu gere par CQL) */
#define CQL_INTERNAL 0
/* tables externes (contenu gere ailleurs, CQL a un proxy) */
#define CQL_EXTERNAL 1

/* determine la traitabilite de la requete (cf fonction RequestSchedule)
 * En entree :
 * parametre 1 : pointeur sur la table
 * parametre 2 : TListP de CQL_TableExprP (expressions a verifier)
 * En sortie :
 * parametre 3 : pointeur sur TListP de CQL_TableExprP (expressions evaluables)
 * parametre 4 : idem (expressions non evaluables)
 * parametre 5 : nombre estime de lignes a parcourir (apres filtrage par les
 *               expressions evaluables) cf CQL_SIZE_* ci-dessus.
 * En retour :
 *  -1 si la requete n'est pas traitable, ou une estimation du temps qu'il
 *  faut pour parcourir la table (en tenant compte du temps necessaire au
 *  filtrage par les expressions evaluables). Cf CQL_MAP_* ci-dessus.
 */
typedef int (* CQL_TreatabilityFP) (CQL_TableP, TListP, TListP*, TListP*, int*);

/* retourne un pointeur vers un iterateur, auquel on demandera
 * successivement les noms des fonctions d'index
 */
typedef CQL_IndexIteratorP 
(* CQL_TableGetIndexIteratorFP) (CQL_TableP);

/* retourne un pointeur vers un iterateur, auquel on demandera
 * successivement les noms (ou les types) des colonnes de la table
 */
typedef CQL_ColumnIteratorP 
(* CQL_TableGetColumnIteratorFP) (CQL_TableP);

/* retourne un pointeur vers un iterateur, auquel on demandera
 * successivement les lignes de la table
 * On lui passe la liste des expressions que la table sait evaluer (determinee
 * a l'aide de la fonction de traitabilite), et la liste des noms
 * des colonnes de cette table auxquelles on va acceder dans la requete
 * (dans le select et dans le where). Une valeur NULL indique que l'on veut
 * pouvoir acceder a toutes les colonnes.
 */
typedef CQL_RowIteratorP 
(* CQL_TableGetRowIteratorFP) (CQL_TableP, TListP, TListP);

/* Retourne un moyen (a fournir a l'iterateur) d'acceder rapidement a une */
/* colonne dans la rangee courante. Ce peut etre un numero, un pointeur de */
/* fonction, ... */
typedef CQL_ColumnAccessP 
(* CQL_TableGetColumnAccessFP) (CQL_TableP, CQL_ColumnNameP);

/* Permet de detruire la valeur retournee par la fonction ci-dessus */
typedef void
(* CQL_TableDelColumnAccessFP) (CQL_ColumnAccessP);

/* Ajout d'une ligne a la table. La table copiera la ligne.
 * CQL_ValueP est un tableau de valeurs correspondant aux colonnes
 * de la tables parcourues a l'aide de l'iterateur des colonnes.
 * La ligne peut etre inseree n'inporte ou dans la table.
 * Si la table ne sait pas ajouter des lignes, retourne 0, sinon 
 * retourne 1.
 */
typedef int
(* CQL_TableAddRowFP) (CQL_TableP, CQL_ValueP);

/*------------------------------------*/
/* fonctions des iterateurs de lignes */
/*------------------------------------*/

/* ACCES ET ITERATION SUR LES LIGNES D'UNE TABLE */

/* Pointeur vers une fonction de filtrage des lignes, qui prend en */
/* argument une liste de noms de colonnes */
/* L'iterateur est obtenu par une fonction de la table */
/* positionne l'iterateur sur la premiere ligne */
typedef void
  (* CQL_MapRowsFirstFP) (CQL_RowIteratorP);

/* positionne l'iterateur sur la ligne suivante*/
typedef void
  (* CQL_MapRowsNextFP) (CQL_RowIteratorP);

/* teste la fin de l'iteration (plus de lignes a retourner) */
typedef CQL_BooleanS
  (* CQL_MapRowsStopFP) (CQL_RowIteratorP);

/* copie de l'iterateur (retourne un iterateur pointant au meme endroit,
 * independant du premier)
 */
typedef CQL_RowIteratorP
  (* CQL_MapRowsCopyIterFP) (CQL_RowIteratorP);

/* fin d'iteration (liberation des ressources */
typedef void
  (* CQL_MapRowsDelIterFP) (CQL_RowIteratorP);

/* acces a la rangee contenune dans l'iterateur *
 * La rangee retournee est valide jusqu'au prochain appel a l'operation
 * next ou destroy de l'iterateur. Il ne faut pas la liberer.
 */
typedef CQL_ValueP
  (* CQL_MapRowsCurrentFP) (CQL_RowIteratorP);

/* retourne l'identificateur de la rangee courante */
/* Il est interdit de retourner NULL (reserve pour outer join) */
typedef ByteStringP
  (* CQL_MapRowsCurrentIdFP) (CQL_RowIteratorP);

/* acces a la valeur de colonne dont l'acces est passe en parametre,
 * La valeur retournee est valide jusqu'au prochain appel a l'operation
 * next ou destroy de l'iterateur. Il ne faut pas la liberer.
 */
typedef CQL_Value
  (* CQL_ColumnGetValueFP) (CQL_RowIteratorP, CQL_ColumnAccessP);

/* Modifie la valeur de colonne dont l'acces est passe en parametre,
 * La table prend une copie de la valeur en parametre.
 * retourne 0 si la modification est interdite, 1 si OK
 */
typedef int
  (* CQL_ColumnSetValueFP) (CQL_RowIteratorP, CQL_ColumnAccessP, CQL_Value);

/* pour lire le contenu d'une rangee obtenue avec getRow */
typedef CQL_Value
  (* CQL_ColumnGetValueInRowFP) (CQL_ValueP, CQL_ColumnAccessP);

/*--------------------------------------*/
/* fonctions des iterateurs de colonnes */
/*--------------------------------------*/

/* ACCES ET ITERATION SUR LES COLONNES D'UNE TABLE */

/* Positionne l'iterateur sur la premiere colonne */
typedef void
  (* CQL_MapColFirstFP) (CQL_ColumnIteratorP);

/* Positionne l'iterateur sur la colonne suivante */
typedef void
  (* CQL_MapColNextFP) (CQL_ColumnIteratorP);

/* teste la fin de l'iteration (plus de lignes a retourner) */
typedef CQL_BooleanS
  (* CQL_MapColStopFP) (CQL_ColumnIteratorP);

/* fin d'iteration (liberation des ressources */
typedef void
  (* CQL_MapColDelIterFP) (CQL_ColumnIteratorP);

/* acces au nom de la colonne courante */
typedef CQL_ColumnNameP
  (* CQL_MapColGetNameFP) (CQL_ColumnIteratorP);

/* acces au type de la colonne courante */
typedef CQL_TypeP
  (* CQL_MapColGetTypeFP) (CQL_ColumnIteratorP);

/*--------------------------------------*/
/* fonctions des iterateurs d'index     */
/*--------------------------------------*/

/* ACCES ET ITERATION SUR LES INDEXS D'UNE TABLE */

/* Positionne l'iterateur sur le premier index */
typedef void
  (* CQL_MapIndFirstFP) (CQL_IndexIteratorP);

/* Positionne l'iterateur sur l'index suivant */
typedef void
  (* CQL_MapIndNextFP) (CQL_IndexIteratorP);

/* teste la fin de l'iteration (plus de lignes a retourner) */
typedef CQL_BooleanS
  (* CQL_MapIndStopFP) (CQL_IndexIteratorP);

/* fin d'iteration (liberation des ressources */
typedef void
  (* CQL_MapIndDelIterFP) (CQL_IndexIteratorP);

/* acces au nom de la fonction d'index courante */
typedef CQL_StringP
  (* CQL_MapIndGetOperFP) (CQL_IndexIteratorP);

/* acces au nom de la colonne sur laquelle porte l'indexation */
typedef CQL_StringP
  (* CQL_MapIndGetColFP) (CQL_IndexIteratorP);

/* acces au nom de la fonction negation */
typedef CQL_StringP
  (* CQL_MapIndGetNegFP) (CQL_IndexIteratorP);

/* Iterateur sur les lignes */
/****************************/

typedef struct CQL_rowIteratorX {

  /* parcours des lignes : prendre la premiere ligne */
  CQL_MapRowsFirstFP         first ;

  /* ligne suivante */
  CQL_MapRowsNextFP          next ;
  
  /* vrai s'il n'y a plus de lignes */
  CQL_MapRowsStopFP          stop ;

  /* copie de l'iterateur */
  CQL_MapRowsCopyIterFP      copy;

  /* destruction de l'iterateur */
  CQL_MapRowsDelIterFP       destroy;

  /* acces a la rangee */
  CQL_MapRowsCurrentFP       getRow;

  /* identificateur de la rangee courante */
  CQL_MapRowsCurrentIdFP     getId;

  /* acces a une colonne dans la rangee courante */
  CQL_ColumnGetValueFP       getValue;

  /* acces a une colonne dans la rangee courante */
  CQL_ColumnSetValueFP       setValue;

  /* acces a une colonne dans une rangee */
  CQL_ColumnGetValueInRowFP  getValueInRow;

} CQL_RowIteratorS;

/****************************************************************/
/* Pour permettre l'utilisation de ITERATOR_MAP sur les lignes */
/****************************************************************/

/* exemple : parcours d'une table en demandant la valeur de l'attribut
 *             dont l'accesseur est access (de type CQL_ColumnAccessP)
 * CQL_TableP table;
 * CQL_RowIteratorP iter;
 *
 * ITERATOR_MAP(CQL_Row, table, iter);
 *   ... = (iter->getValue)(iter, access);
 * ITERATOR_END_MAP(CQL_Row);
*/

/* Dans le schema standard CQL, c'est la table qui cree ses iterateurs
 * ITERATOR_MAP veut les creer lui-meme. On le laisse faire, mais c'est
 * en fait CQL_Row_First qui va effectivement le creer
 */
typedef CQL_RowIteratorP CQL_Row_IteratorS, *CQL_Row_IteratorP;
IMPORT 
CQL_RowIteratorP CQL_Row_First   (CQL_TableP t, CQL_Row_IteratorP r);

/* Signature des macros ci-dessous :
 * CQL_RowIteratorP CQL_Row_Next    (CQL_Row_IteratorP);
 * int              CQL_Row_Stop    (CQL_Row_IteratorP);
 * void             CQL_Row_DelIter (CQL_Row_IteratorP);
 */

#define CQL_Row_Next(iterP) \
  (((*(iterP))->next)(*(iterP)),*(iterP))

/* le cas *(iterP) == NULL correspond a une table qui ne peut pas etre */
/* parcourue */
#define CQL_Row_Stop(iterP) \
  ((*(iterP) == NULL) || ((*(iterP))->stop)(*(iterP)) == CQL_TRUE)

#define CQL_Row_DelIter(iterP) \
  if (*(iterP) != NULL) ((*(iterP))->destroy)(*(iterP))

/******************************/
/* Iterateur sur les colonnes */
/******************************/

typedef struct CQL_columnIteratorX {

  /* parcours des colonnes : prendre la premiere colonnes */
  CQL_MapColFirstFP         first ;

  /* colonne suivante */
  CQL_MapColNextFP          next ;
  
  /* vrai s'il n'y a plus de colonne */
  CQL_MapColStopFP          stop ;

  /* destruction de l'iterateur */
  CQL_MapColDelIterFP       destroy;

  /* acces au nom de la colonne */
  CQL_MapColGetNameFP       getName;

  /* acces au type de la colonne */
  CQL_MapColGetTypeFP       getType;

} CQL_ColumnIteratorS;


/*****************************************************************/
/* Pour permettre l'utilisation de ITERATOR_MAP sur les colonnes */
/*****************************************************************/

/* exemple : obtenir le nom de chaque colonne
 * CQL_TableP table;
 * CQL_ColumnIteratorP iter;
 *
 * ITERATOR_MAP(CQL_Column, table, iter);
 *   ... = (iter->getName)(iter);
 * ITERATOR_END_MAP(CQL_Column);
*/

/* Dans le schema standard CQL, c'est la table qui cree ses iterateurs
 * ITERATOR_MAP veut les creer lui-meme. On le laisse faire, mais c'est
 * en fait CQL_Column_First qui va effectivement le creer
 */
typedef CQL_ColumnIteratorP CQL_Column_IteratorS, *CQL_Column_IteratorP;
IMPORT 
CQL_ColumnIteratorP CQL_Column_First   (CQL_TableP t, CQL_Column_IteratorP c);

/* Signature des macros ci-dessous :
 * CQL_ColumnIteratorP CQL_Column_Next    (CQL_Column_IteratorP c);
 * int                 CQL_Column_Stop    (CQL_Column_IteratorP c);
 * void                CQL_Column_DelIter (CQL_Column_IteratorP c);
 */

#define CQL_Column_Next(iterP) \
  (((*(iterP))->next)(*(iterP)),*(iterP))

#define CQL_Column_Stop(iterP) \
  (((*(iterP))->stop)(*(iterP)) == CQL_TRUE)

#define CQL_Column_DelIter(iterP) \
  ((*(iterP))->destroy)(*(iterP))

/***************************************/
/* Iterateur sur les fonctions d'index */
/***************************************/

typedef struct CQL_indexIteratorX {

  /* parcours des index : 1er index */
  CQL_MapIndFirstFP         first ;

  /* Indonne suivante */
  CQL_MapIndNextFP          next ;
  
  /* vrai s'il n'y a plus d'index */
  CQL_MapIndStopFP          stop ;

  /* destruction de l'iterateur */
  CQL_MapIndDelIterFP       destroy;

  /* acces au nom de la fonction */
  CQL_MapIndGetOperFP       getOper;

  /* acces au nom de la colonne */
  CQL_MapIndGetColFP        getColumn;

  /* acces au nom de la fonction negation (ou NULL) */
  CQL_MapIndGetNegFP        getNegation;
  
  /* Eventuellement, on ajoutera plus tard le traitement des types */

} CQL_IndexIteratorS;


/*****************************************************************/
/* Pour permettre l'utilisation de ITERATOR_MAP sur les indexs   */
/*****************************************************************/

/* exemple : obtenir le nom de chaque fonction d'index et sa colonne
 * CQL_TableP table;
 * CQL_IndexIteratorP iter;
 *
 * ITERATOR_MAP(CQL_Index, table, iter);
 *   ... = (iter->getOper)(iter);
 *   ... = (iter->getColumn)(iter);
 * ITERATOR_END_MAP(CQL_Index);
*/

/* Dans le schema standard CQL, c'est la table qui cree ses iterateurs
 * ITERATOR_MAP veut les creer lui-meme. On le laisse faire, mais c'est
 * en fait CQL_Index_First qui va effectivement le creer
 */
typedef CQL_IndexIteratorP CQL_Index_IteratorS, *CQL_Index_IteratorP;
IMPORT 
CQL_IndexIteratorP CQL_Index_First   (CQL_TableP t, CQL_Index_IteratorP i);

/* Signature des macros ci-dessous :
 * CQL_IndexIteratorP CQL_Index_Next    (CQL_Index_IteratorP);
 * int                 CQL_Index_Stop    (CQL_Index_IteratorP);
 * void                CQL_Index_DelIter (CQL_Index_IteratorP);
 */

#define CQL_Index_Next(iterP) \
  (((*(iterP))->next)(*(iterP)),*(iterP))

#define CQL_Index_Stop(iterP) \
  (((*(iterP))->stop)(*(iterP)) == CQL_TRUE)

#define CQL_Index_DelIter(iterP) \
  ((*(iterP))->destroy)(*(iterP))


/**************/
/* type table */
/**************/

/* Attention, tous les acces aux tables se font par des fonctions */

typedef struct CQL_tableX {

  /* CQL_EXTERNAL ou CQL_INTERNAL */
  int                       category;

  /* un pointeur de plus */
  CQL_TableDuplicateFP      duplicate;

  /* un pointeur de moins */
  CQL_TableReleaseFP        release ;

  /* destruction de la table */
  CQL_TableDeleteFP         destroy ;

  /* nom de la table (copie) */
  CQL_TableGetNameFP        tableName;

  /* nom du type d'une colonne (copie) */
  CQL_TableGetColumnTypeFP  columnType;

  /* valeur et type d'un attribut */
  CQL_TableGetAttributeFP  getAttribute;

  /* poisitionne valeur et type d'un attribut */
  CQL_TableSetAttributeFP  setAttribute;

  /* Demande d'iterateur sur les index */
  CQL_TableGetIndexIteratorFP getIndexIterator;

  /* faisabilite de la requete */
  CQL_TreatabilityFP        checkTreatability ;

  /* Demande d'iterateur sur les lignes */
  CQL_TableGetRowIteratorFP    getRowIterator;
  
  /* Demande d'iterateur sur les colonnes */
  CQL_TableGetColumnIteratorFP getColumnIterator;

  /* demande un moyen d'acceder a une colonne */
  CQL_TableGetColumnAccessFP  getColumnAccess;

  /* fonction de destruction de la valeur retournee par getColumnAccess */
  CQL_TableDelColumnAccessFP  deleteAccess;

  /* ajout d'une ligne a la table */
  CQL_TableAddRowFP           addRow;

} CQL_TableS ;

/* structure correspondant a une expression pouvant etre evaluee */
/* par une table. Seules les expressions a 2 arguments sont prevues */
typedef struct {

  /* nom de l'operateur (eq, lt,...) */
  CQL_StringP    oper;
  
  /* nom de l'attribut */
  CQL_StringP    column;

  /* rang de l'attribut dans les argument (1 = 1er, 2 = 2eme) */
  int            columnPos;

  /* valeur courante pour une demande d'evaluation */
  CQL_Value      value;

  /* Type de la valeur */
  CQL_TypeP      valueType;

  /* donnees privees CQL */
  /* Expression permettant a CQL d'evaluer la valeur */
  CQL_EvalNodeP  valueNode;

  /* arbre CQL correspondant a la valeur */
  VTP_TreeP      valueTree;

  /* arbre CQL correspondant a l'expression complete */
  VTP_TreeP      exprTree;
}
CQL_TableExprS, *CQL_TableExprP;



/* Fonctions de gestion des tables et des vues */

/* recherche des tables et vues par nom */
CQL_TableP       CQL_GetTableByName(CQL_TableNameP n);

/* comme CQL_GetTableByName si la table de has est NULL, sinon
 * le nom est celui de la table si elle n'a pas d'alias, ou l'alias
 * si la table a un alias
 */
CQL_TableP       CQL_GetTableByAlias(CQL_TableNameP n, Hash_TableP h);

/* Pointeurs vers des tables */
#define CQL_VALUE_TO_TABLE(val) \
   ((CQL_TableP)CQL_VALUE_TO_POINTER(val))
#define CQL_VALUE_FROM_TABLE(ptr) \
     CQL_VALUE_FROM_POINTER(ptr)

/* Acces aux informations des valeurs de type CQL_columnType */
/* creation d'une valeur colonne */
IMPORT CQL_Value
CQL_ColumnValueMake(CQL_StringP col, CQL_TableP tab);

/* acces a la colonne */
IMPORT CQL_TableP CQL_ColumnValueTable(CQL_Value column);

/* nom de la colonne */
IMPORT CQL_StringP CQL_ColumnValueName(CQL_Value column);

/* acces a la colonne */
IMPORT CQL_ColumnAccessP CQL_ColumnValueAccess(CQL_Value column);

/* type de la colonne */
IMPORT CQL_TypeP CQL_ColumnValueType(CQL_Value column);

/* Fonctions permettant de considerer une valeur simple de type "array" ou
 * "colonne" comme un ensemble de valeurs de meme type, les autres valeurs
 * etant traitees comme des singletons.
 * En particulier, on peut utiliser ITERATOR_MAP
 */

/* exemple : parcourir les elements contenus dans une valeur
 * CQL_TableExprP simple_node;
 * CQL_Value value;
 *
 * ITERATOR_MAP(CQL_TableExpr, simple_node, value);
 *   i
 * ITERATOR_END_MAP(CQL_Index);
*/
#define CQL_SET_SINGLE 0
#define CQL_SET_ARRAY 1
#define CQL_SET_COLUMN 2

typedef struct {
  int setType;
  int done;
  CQL_Value value;
  TListP array;       /* for array */
  int    rank;        /* for array */
  CQL_Row_IteratorS rowIter;   /* for column */
  CQL_TableP        table;     /* for column */
  CQL_ColumnAccessP access;    /* for column */
}
CQL_TableExpr_IteratorS, *CQL_TableExpr_IteratorP;
IMPORT 
CQL_Value CQL_TableExpr_First(CQL_TableExprP simple_node,
			      CQL_TableExpr_IteratorP iterp);

/* iterp is a CQL_TableExpr_IteratorP */
#define CQL_TableExpr_Stop(iterp) ((iterp)->done)
IMPORT 
CQL_Value CQL_TableExpr_Next(CQL_TableExpr_IteratorP iterp);
IMPORT 
void CQL_TableExpr_DelIter(CQL_TableExpr_IteratorP iterp);

IMPORT CQL_TableP
CQL_InternalTableCreate(int nb_cols, char **col_names, CQL_TypeP *col_types);

IMPORT int
CQL_InternalTableCreateIndex(CQL_TableP table, char *name, int unique,
   int nb_indexed_columns, char **column_names);

IMPORT CQL_TableP
CQL_InternalTableLoad(FILE *fp, char *file_name);

#endif /* CQLTABLE_H */
