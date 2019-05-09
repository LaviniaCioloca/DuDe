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

/************************/
/* types et valeurs CQL */
/************************/

#ifndef CQLVALUE_H
#define CQLVALUE_H

#include <hash.h>

#undef FAST_H_DLL
#define FAST_H_DLL CQL_LIB
#include <fastdll.h>

/* Nom de type */
typedef char * CQL_TypeNameP;

typedef struct CQL_attributeTypeX * CQL_TypeP;

/* Fonctions associees au type */

/* La valeur peut etre une valeur du type, ou la valeur indefinie du type */
/* Dans ce cas, la fonction dump doit ecrire undef (sans guillemets) dans */
/* le fichier, et la fonction load doit retourner la valeur indefinie     */
/* si elle lit undef */

/* lit la valeur situee a la position courante du fichier
 * en supposant qu'elle a le type attendu
 * retourne une valeur du type approprie
 */
typedef CQL_Value (* CQL_TypeLoadValFP) (CQL_FileP);

/* ecrit la valeur dans le fichier */
typedef void (* CQL_TypeDumpValFP)(CQL_FileP, CQL_Value);

/* Permet d'obtenir une valeur a partir d'une chaine tapee par
 * l'utilisateur. La valeur indefinie peut etre retournee si la
 * chaine ne represente pas une valeur de ce type. Cette fonction
 * doit permettre de lire des valeurs simples.
 */
typedef CQL_Value (* CQL_TypeParseValFP)(CQL_StringP);

/* Retourne une chaine permettant d'afficher la valeur
 * Pour les valeurs simples, il est souhaitable que l'analyse de
 * cette chaine par la fonction parse (CQL_TypeParseValFP) du type
 * donne la valeur initiale.
 */
typedef CQL_StringP (* CQL_TypeStringValFP) (CQL_Value);

/* copie la valeur */
typedef CQL_Value (* CQL_TypeCopyValFP) (CQL_Value);

/* libere l'espace occupe par une valeur (s'il y a lieu) */
typedef void (* CQL_TypeDeleteValFP) (CQL_Value);

/* retourne la valeur est indefinie du type */
typedef CQL_Value (* CQL_TypeGetUndefFP) ();

/* teste si la valeur est indefinie. Retourne 1 si oui, 0 si non */
typedef int (* CQL_TypeCheckUndefFP) (CQL_Value);

/* Type de cle d'index = Cle de table de hachage */
typedef Hash_KeyClass *CQL_IndexKeyP;

/* Definition d'un type
 * - name est la chaine qui servira a declarer les variables du type,
 *   et qui sera ecrit dans les fichiers comme type des colonnes.
 * - id est le nom qui servira a ecrire le nom du type dans une table pour 
 *   typer les elements de liste et de tagged (doit etre court, si possible
 *   1 seul caractere.
 * - load est la fonction de lecture de la valeur sur un support d'entree.
 *   Si la valeur n'a pas de representation sur un tel support, cette
 *   fonction doit retourner la valeur indefinie du type. Si la valeur
 *   ne peut pas etre lue (erreur de format), il faut appeler CQL_Abort.
 * - dump est la fonction d'ecriture pour les tables. La positionner
 *   Si les valeurs de ce type ne peuvent pas etre ecrites, cette fonction
 *   doit ecrire undef sur le support de sortie.
 * - string retourne une chaine non partagee, allouee avec Mem_NewString 
 *   ou equivalent.
 * - copy copie la valeur. La valeur de retour peut etre une copie physique
 *   ou peut etre la meme valeur avec un compteur d'utilisations incremente.
 * - destroy detruit la valeur. Si la fonction de copie incremente un compteur,
 *   la destruction decremente le compteur et detruit physiquement la valeur
 *   lorsqu'il arrive a 0.
 * - deepcopy copie la valeur "en profondeur", c'est a dire retourne une
 *   valeur physiquement independante. Si la valeur est composee
 *   d'autres valeurs, celles-ci doivent aussi etre copiees en profondeur.
 *   Si copy retourne une valeur jamais partagee, deepcopy est equivalent.
 */
typedef struct CQL_attributeTypeX {
  CQL_TypeNameP        name;        /* nom type pour l'utilisateur  */
  CQL_TypeNameP        id;          /* nom type court               */
  CQL_TypeLoadValFP    load;        /* lecture valeur sur fichier   */
  CQL_TypeDumpValFP    dump;        /* ecriture valeur sur fichier  */
  CQL_TypeParseValFP   parse;       /* lecture d'une chaine         */
  CQL_TypeStringValFP  string;      /* affichage valeur             */
  CQL_TypeCopyValFP    copy;        /* copie valeur                 */
  CQL_TypeCopyValFP    deepcopy;    /* copie valeur en profondeur   */
  CQL_TypeDeleteValFP  destroy;      /* destruction valeur           */
  CQL_TypeGetUndefFP   getUndef;    /* retourne la valeur indefinie */
  CQL_TypeCheckUndefFP isUndef;     /* test valeur indefinie        */
  CQL_FunctionP        equal;       /* test egalite 2 valeurs       */
  CQL_FunctionP        lessThan;    /* comparaison ("<") 2 valeurs  */
}
CQL_TypeS, **CQL_TypePP;

/* valeur typee */
typedef struct {
  CQL_TypeP     type;
  CQL_Value     value;
}
CQL_TypedValueS, *CQL_TypedValueP;

/* types d'attributs predefinis par CQL (pointeurs) */

/* types atomiques */
IMPORT GLOBALREF CQL_TypeP CQL_booleanType;  /* booleens */
IMPORT GLOBALREF CQL_TypeP CQL_numberType;   /* nombres */
IMPORT GLOBALREF CQL_TypeP CQL_integerType;  /* entiers */
IMPORT GLOBALREF CQL_TypeP CQL_stringType;   /* chaine */

/* types composes, geres par un compteur d'utilisation */
IMPORT GLOBALREF CQL_TypeP CQL_listType;     /* listes */
IMPORT GLOBALREF CQL_TypeP CQL_taggedType;   /* listes */
IMPORT GLOBALREF CQL_TypeP CQL_tableType;    /* tables */
IMPORT GLOBALREF CQL_TypeP CQL_cursorType;   /* tables */
IMPORT GLOBALREF CQL_TypeP CQL_columnType;   /* colonne (select col from tab) */
IMPORT GLOBALREF CQL_TypeP CQL_sequenceType; /* sequence d'entiers */
IMPORT GLOBALREF CQL_TypeP CQL_fileType;     /* file descriptor */

/* type ne contenant que la valeur indefinie, ne peut pas etre en parametre */
/* d'une fonction (donc ne peut pas etre insere dansd une liste ou un tagged */
/* ni etre le type d'une colonne d'une table */
IMPORT GLOBALREF CQL_TypeP CQL_voidType;     /* void (type sans valeur) */

/* declaration et acces aux types */
IMPORT 
CQL_TypeP CQL_GetTypeByName (CQL_TypeNameP name);
IMPORT CQL_TypeP CQL_GetTypeById (CQL_TypeNameP id);
IMPORT 
void CQL_DeclareType (CQL_TypeP type);

#define CQL_ARRAY_NAME_SUFFIX "_array"
#define CQL_ARRAY_NAME_SUFFIX_LEN 6
#define CQL_ARRAY_ID_SUFFIX "_a"
#define CQL_ARRAY_ID_SUFFIX_LEN 2
IMPORT 
CQL_TypeP CQL_CreateArrayType(CQL_TypeP elem_type);

/* declaration et acces aux variables globales (cf variable.c) */
IMPORT int
CQL_DeclareGlobalConstant(CQL_StringP name, CQL_TypeP  type, CQL_Value init);
 
IMPORT int
CQL_DeclareGlobalVariable(CQL_StringP name, CQL_TypeP  type, CQL_Value init);
 
IMPORT int
CQL_SetGlobalVariable(CQL_StringP name, CQL_TypeP  type, CQL_Value value);
 
IMPORT CQL_TypeP
CQL_GetGlobalVariable(CQL_StringP name, CQL_ValueP  value_p);
 

/* declaration et acces aux fonctions (cf interface.c) */
typedef struct CQL_FuncInfoX  *CQL_FunctionHandlerP;

/* recherche, parmi les fonctions declarees, une fonction dont :
 * - le nom est name
 * - l'arite est arity
 * - les types des arguments sont les types figurant dans <types>
 * <types> est un tableau de pointeurs de type (c'est a dire un pointeur
 * vers un pointeur P vers le type du premier argument, les pointeurs vers
 * les types fes arguments suivants etant immediatement apres P)
 * Attention, on ne compare pas les noms des types, mais les pointeurs vers
 * ces types.
 * retourne le type du resultat et place un pointeur vers la fonction
 * dans func_pp
 */
/* cette fonction est reservee a un usage interne,
 * Elle permet de tester l'existence sans recuperer le handler
 */
IMPORT CQL_TypeP CQL_CheckFunction (CQL_FunctionNameP name,
				    int arity, 
				    CQL_TypePP types);

/* recherche la fonction et retourne un descripteur de fonction
 * le type est mis dans ret_type (si ret_type est non nul)
 */
IMPORT CQL_FunctionHandlerP CQL_GetFunctionHandler (CQL_FunctionNameP name,
				    CQL_TypePP ret_type, 
				    int arity, 
				    CQL_TypePP params);

/* declare une fonction d'interface
 */
IMPORT void
CQL_DeclareInterfaceFunction (CQL_FunctionP       func_p,
                          CQL_FunctionNameP   name,
                          CQL_TypeP  ret_type,
                          int                 arity,
                          CQL_TypePP types);

/* recherche les fonctions d'interface
 * l'usage de cette fonction en dehors du composant CQL est autorise
 */
IMPORT CQL_TypeP CQL_GetInterfaceFunction (CQL_FunctionPP func_pp, 
				    CQL_FunctionNameP name,
				    int arity, 
				    CQL_TypePP types);

/* permet d'executer une function retournee par CQL_GetFunctionHandler
 * aucun controle des parametre n'est effectue
 * l'appelant doit s'assure que les parametres passes corresponde
 * a la signature de la function */
IMPORT CQL_Value CQL_EvalFunction (CQL_FunctionHandlerP func_hdl,
				    CQL_ValueP val);

/* identique a la precedente, mais execute dans un environnement separe */
/* peut etre appelee en dehors de l'evaluateur et l'exception EX_CQL_EVAL
 *  peut etre recupere */
IMPORT CQL_Value CQL_EvalSeparateFunction (CQL_FunctionHandlerP func_hdl,
				    CQL_ValueP val);

/* permet d'executer une fonction a partir de son nom
 */
IMPORT int CQL_ApplyFunction (CQL_FunctionNameP name,
				    CQL_ValueP val, 
				    int arity, 
				    CQL_EvalNodePP params);

/* retourne l'arite d'une CQL_FunctionHandler */
IMPORT int
CQL_FunctionArity(CQL_FunctionHandlerP f);
#define CQL_FUNCTION_ARITY(f)	CQL_FunctionArity(f)

/* retourne le type de retour d'une CQL_FunctionHandler */
IMPORT CQL_TypeP
CQL_FunctionReturnType(CQL_FunctionHandlerP f);
#define CQL_FUNCTION_RETURNTYPE(f)	CQL_FunctionReturnType(f)

/* retourne le type du nieme parametre d'une CQL_FunctionHandler */
IMPORT CQL_TypeP
CQL_FunctionParamType(CQL_FunctionHandlerP f, int n);
#define CQL_FUNCTION_PARAMTYPE(f,n)	CQL_FunctionParamType(f, n)
IMPORT 
void CQL_DeclareFunction (CQL_FunctionP func, 
			  CQL_FunctionNameP name,
			  CQL_TypeP ret_type, 
			  int arity, 
			  CQL_TypePP params);
IMPORT CQL_FunctionHandlerP
CQL_DeclareFunctionWithData (CQL_FunctionP func, 
			  CQL_FunctionNameP name,
			  CQL_TypeP ret_type, 
			  int arity, 
			  CQL_TypePP params,
			  VoidP data,
                          void (*delete_data)(VoidP));

/* supprime la fonction CQL correspondant au handler */
IMPORT void
CQL_DeleteFunction(CQL_FunctionHandlerP func);
IMPORT 
CQL_TypeP CQL_GetGroupFunction (CQL_FunctionPP f1, 
					 CQL_FunctionPP f2, 
					 CQL_FunctionPP f3, 
					 CQL_FunctionNameP name,
					 int arity, 
					 CQL_TypePP params);
IMPORT 
void CQL_DeclareGroupFunction (CQL_FunctionP f1, 
			       CQL_FunctionP f2, 
			       CQL_FunctionP f3, 
			       CQL_FunctionNameP name,
			       CQL_TypeP ret_type, 
			       int arity, 
			       CQL_TypePP params);
IMPORT 
char * CQL_FunctionString(CQL_FunctionNameP name, int arity, CQL_TypePP params);
IMPORT char * CQL_NamedFunctionString(CQL_FunctionNameP oper_name);
IMPORT void CQL_ListFunctions(void);

/* type de la valeur qui sera retournee par l'evaluation du noeud */
IMPORT CQL_TypeP CQL_ExprGetType (CQL_EvalNodeP n);

/* type de valeur retourne par la fonction courante (plus precisement, la
 * derniere fonction appelee
 */
IMPORT CQL_TypeP CQL_EvalFuncType(void);

/* Fonction d'evaluation d'un noeud */
IMPORT CQL_Value CQL_ExprEval (CQL_EvalNodeP n);

/* Libere la valeur, qui doit etre le resultat de l'application de 
 * CQL_ExprEval sur le noeud
 */
IMPORT void CQL_ExprFreeValue(CQL_EvalNodeP n, CQL_Value v);

/* Si node est une variable, libere la valeur associee et affecte la valeur
 * indefinie a la variable. Cette fonction permet d'implanter la fonction
 * "free (<valeur>) -> void" pour un nouveau type.
 * La valeur retournee est du type void (pas de liberation necessaire)
 */
IMPORT CQL_Value CQL_ExprFreeVariable(CQL_EvalNodeP node);

/* Permet de retourner directement une valeur recue en parametre. La valeur
 * est copiee (au sens de type->copy) si necessaire.
 * v doit etre le resultat de l'appel CQL_ExprEval(n).
 * Il ne faut pas appeler CQL_ExprFreeValue ensuite : il faut soit retourner
 * cette valeur (eventuellement indirectement, par exemple en l'inserant dans
 * la valeur de retour),
 * soit la detruire avec node_p->type->destroy
 */ 
  
IMPORT CQL_Value
CQL_ExprMakeReturnValue(CQL_EvalNodeP n, CQL_Value v);

/* Retourne une copie en profondeur de la valeur
 * Alors que CQL_ExprMakeReturnValue peut partager des valeurs lorsque
 * les types sont geres par des compteurs d'utilisation, ce n'est pas le
 * cas ici.
 */
IMPORT CQL_Value
CQL_ExprCopyValue(CQL_EvalNodeP n, CQL_Value v);

/* Interromt l'execution. Seule une fonction retournant CQL_voidType
 * peut appeler cette fonction proprement, car si elle est appelee au
 * cours de l'evaluation d'un argument d'une autre fonction, les valeurs
 * deja calculees par celle-ci ne peuvent pas etre liberees.
 * En cas d'execution impossible, une fonction retournant une valeur doit
 * afficher (eventuellement) un message et retourner la valeur indefinie
 * du type de retour.
 */
IMPORT void CQL_Abort(void);

/* Donne la position de l'instruction CQL en cours d'evaluation */
/* Les parametres ont la meme signification que pour VTP_TreeGetCoord */
IMPORT int CQL_GetCurrentCoord(CIO_PositionP startPos, CIO_PositionP endPos);

/* Macros permettant de manipuler des valeurs, en connaissant leur
 * format de stockage (entier, flottant, pointeur), mais sans se
 * preoccuper du stockage lui-meme
 *
 * Par exemple, si on definit un nouveau type, et que l'on considere que
 * ses valeurs peuvent etre stockees dans un flottant, on utilisera
 * CQL_VALUE_FROM_FLOAT pour retourner une valeur et CQL_VALUE_TO_FLOAT
 * pour convertir un argument en nombre flottant.
 *
 * CQL_VALUE_FROM_XXX signifie "stocker une valeur sous format XXX
 * CQL_VALUE_TO_XXX signifie "recuperer une valeur stockee sous format XXX
 */

/* Booleens */

/* Varible privee (utilisee par les macros ci-dessous) */
IMPORT GLOBALREF CQL_Value cql_valueUnion;

/* Pour obtenir un booleen a partir d'un entier et reciproquement */
/*           (0 <-> CQL_TRUE, 1 <-> CQL_FALSE)    */
#define CQL_VALUE_FROM_INT(num) (cql_valueUnion.i=(num),cql_valueUnion)
#define CQL_VALUE_TO_INT(num) ((num).i)

#define CQL_BOOLEAN_TRUE (cql_valueUnion.i=CQL_TRUE,cql_valueUnion)
#define CQL_BOOLEAN_FALSE (cql_valueUnion.i=CQL_FALSE,cql_valueUnion)

/* Nombres */

/* Pour fabriquer un nombre a partir d'un float */

#define CQL_VALUE_FROM_FLOAT(num) (cql_valueUnion.f=(num),cql_valueUnion)

/* Pour obtenir un float a partir d'un contenu de nombre */

#define CQL_VALUE_TO_FLOAT(num) ((num).f)

/* Entiers */

/* Pour fabriquer un integer cql a partir d'un long */

#define CQL_VALUE_FROM_LONG(num) (cql_valueUnion.l=(num),cql_valueUnion)

/* Pour obtenir un float a partir d'un contenu d'integer */

#define CQL_VALUE_TO_LONG(num) ((num).l)

/* Chaines */

/* Permet de retourner une valeur correspondant a une chaine */
#define CQL_VALUE_FROM_STRING(str) (cql_valueUnion.p=(str),cql_valueUnion)

/* pour obtenir un pointeur a partir d'une valeur pointeur */
#define CQL_VALUE_TO_STRING(str) ((char*)((str).p))

/* Type void */
/* Une fonction retournant void doit retourner cette valeur */
#define CQL_VOID CQL_VALUE_FROM_POINTER(NULL)

/* Type sequence */

/* Permet de cree une sequence */
IMPORT CQL_Value
CQL_SequenceMake(int start, int incr);

/* Permet d'obtenir la valeur suivante d'une sequence */
/* retourne un integer */
IMPORT CQL_Value
CQL_SequenceNext(CQL_Value val);

/* Permet d'obtenir la valeur courante d'une sequence */
/* retourne un integer */
IMPORT CQL_Value
CQL_SequenceCurrent(CQL_Value seq);

/* Permet de reinitialiser une sequence avec les valeurs d'origine */
IMPORT void
CQL_SequenceReset(CQL_Value seq);

/* Type tagged */

/* Declare les fonctions permettant de traiter une valeur en tant que tagged */
/* <type> ( tagged ) */
IMPORT void CQL_TaggedValueTypeDeclare(CQL_TypeP type);

/* pour permettre aux autres types de construire des valeurs taggees
 * type : obtenu par CQL_ExprGetType(<node>)
 * val : resultat de l'evaluation du <node> ci-dessus
 * Cette fonction ne gere pas la copie de la valeur <val>. Pour retourner
 * la valeur tagged, il faut que <val> soit obtenu par CQL_EvalMakeReturnValue
 * ou bien par CQL_EvalCopyValue (dans ce cas, ne pas oublier CQL_ExprFreeValue
 * pour liberer la valeur copiee). Si la valeur a referencer a ete obtenue par
 * un de ces moyens au cours d'une operation anterieure (creation d'une valeur
 * composite par exemple), <val> peut etre obtenu soit type->copy ou
 * type->deepcopy appliquee a cette valeur.
 */
IMPORT CQL_Value CQL_TaggedMake(CQL_TypeP type, CQL_Value val);

/* pour permettre aux autres types de manipuler des valeurs taggees
 * Ne pas appliquer a une valeur indefinie
 */
IMPORT CQL_TypeP CQL_TaggedGetType(CQL_Value tagged);

/* retourne une valeur qui ne sera pas affectee par la destruction de <tagged>
 * (peut etre retournee par une fonction), mais peut etre partagee.
 * Ne pas appliquer a une valeur indefinie
 */
IMPORT CQL_Value CQL_TaggedGetValue(CQL_Value tagged);

/* retourne une valeur qui sera invalide si <tagged> est detruit
 * (valeur en consultation)
 * Ne pas appliquer a une valeur indefinie
*/
IMPORT CQL_Value CQL_TaggedGetValueRef(CQL_Value tagged);

/* retourne une copie en profondeur de la valeur (non partagee)
 * Ne pas appliquer a une valeur indefinie
 */
IMPORT CQL_Value CQL_TaggedGetValueCopy(CQL_Value tagged);

/* Pour permettre aux autres types de creer et manipuler des listes */

/* Pour creer un nouveau type pouvant etre insere dans une liste,
 * il faut declarer les fonctions append, add, replace a l'aide de
 * cette fonction.
 */
IMPORT void CQL_ListElementTypeDeclare(CQL_TypeP type);

/* Alloue une liste CQL
 * La liste retournee a son compteur d'utilisation a 1
 * Elle peut etre manipulee comme une TList, sauf pour la destruction
 * qui doit etre faite par CQL_stringType->destroy
 */
IMPORT TListP CQL_ListMake(void);

/* Construit un element de liste
 * Ne copie pas la valeur (a faire avant si on veut retourner la liste)
 * La valeur doit vaoir le type indique
 * Liberation avec CQL_ListElementDelete
 * acces au type par CQL_LIST_ELEMENT_TYPE
 * acces a la valeur par CQL_LIST_ELEMENT_VALUE
 */
IMPORT CQL_TypedValueP CQL_ListElementMake(CQL_TypeP type, CQL_Value val);

/* libere la valeur et detruit l'element */
IMPORT void CQL_ListElementDelete(CQL_TypedValueP elem);

/* retourne un CQL_TypeP */
#define CQL_LIST_ELEMENT_TYPE(elem) \
    ((elem)->type)

/* retourne une CQL_Value */
#define CQL_LIST_ELEMENT_VALUE(elem) \
    ((elem)->value)

/* obtient la TList correspondant a une valeur */
/* Les elements de la TList sont des CQL_TypedValueP */
#define CQL_VALUE_TO_LIST(val) \
   ((TListP)CQL_VALUE_TO_POINTER(val))

/* retourne une valeur de type liste. Attention, cette valeur doit
 * etre une liste CQL, obtenue par CQL_ListMake ou par CQL_VALUE_TO_LIST
 */
#define CQL_VALUE_FROM_LIST(lst) \
     CQL_VALUE_FROM_POINTER(lst)

/* Tableaux */

/* Declare la fonction qui permet de rechercher des valeurs dans une
 * colonne de type <type> (implantation du in/not in) */
IMPORT void CQL_SubselectTypeDeclare(CQL_TypeP type);

/* Declare la fonction qui permet de positionner un attribut de tableau
 * de type <type> (implantation du set_attribute) */
IMPORT void CQL_TableAttributeTypeDeclare(CQL_TypeP type);

IMPORT void CQL_GroupTypeDeclare ( CQL_TypeP type);

/* Declare une fonction de declaration de fonction manipulant des tableaux
 * La fonction pren en parametre le type tableau et le type element
 */
IMPORT void CQL_DeclareArrayFunctionDeclarator(void (*func)(CQL_TypeP, CQL_TypeP));

/* Alloue un tableau CQL
 * La liste retournee a son compteur d'utilisation a 1
 * Elle peut etre manipulee comme une TList, sauf pour la destruction
 * qui doit etre faite par la fonction destroy du type tableau.
 */
IMPORT TListP CQL_ArrayMake(CQL_TypeP elem_type);

/* vrai si type est un type tableau de quelque chose */
IMPORT int CQL_TypeIsArray(CQL_TypeP type);

/* type des elements des tableaux de ce type (array_type doit
 * etre un type tableau, a verifier avec CQL_TypeIsArray par exemple)
 */
IMPORT CQL_TypeP CQL_ArrayTypeElementType(CQL_TypeP array_type);

/* obtient la TList correspondant a un tableau */
/* Les elements de la TList sont des CQL_TypedValueP */
#define CQL_VALUE_TO_ARRAY(val) \
   ((TListP)CQL_VALUE_TO_POINTER(val))

/* retourne une valeur de type liste. Attention, cette valeur doit
 * etre une liste CQL, obtenue par CQL_ArrayMake ou par CQL_VALUE_TO_ARRAY
 */
#define CQL_VALUE_FROM_ARRAY(arr) \
     CQL_VALUE_FROM_POINTER(arr)

/* retourne un element de tableau correspondant a` une valeur.
 * La valeur retournee peut etre placee dans la liste d'un tableau
 */
#define CQL_VALUE_TO_ARRAY_ELEM(val) \
     ((VoidP)(val).p)

/* retourne une valeur CQL a partir d'un element de la liste d'un tableau
 */
#define CQL_VALUE_FROM_ARRAY_ELEM(valp) \
     CQL_VALUE_FROM_POINTER(valp)

/* retourne un entier correspondant a un element de tableau */
#define CQL_ARRAY_ELEM_TO_INT(valp) \
     ((valp).i)

/* retourne un long correspondant a un element de tableau */
#define CQL_ARRAY_ELEM_TO_LONG(valp) \
     ((valp).l)

/* retourne un float correspondant a un element de tableau */
#define CQL_ARRAY_ELEM_TO_FLOAT(valp) \
     ((valp).f)

/* retourne un pointeur correspondant a un element de tableau */
#define CQL_ARRAY_ELEM_TO_POINTER(valp) \
     ((valp).p)

/* Structure */

/* Declaration d'un type record
 * member_names et member_types doivent etre des tableau de dimension
 * au moins egale a size
 */
IMPORT CQL_TypeP
CQL_DeclareRecordType(char *name, int size, CQL_StringP *member_names,
		CQL_TypeP *member_types);

/* Alloue une structure CQL
 * le tableau retournee a son compteur d'utilisation a 1
 * Il peut etre manipule normalement, sauf pour la destruction
 * qui doit etre faite par la fonction destroy du type tableau.
 */
IMPORT CQL_ValueP CQL_RecordMake(CQL_TypeP record_type);

/* vrai si type est un type structure */
IMPORT int CQL_TypeIsRecord(CQL_TypeP type);

/* obtient tableau de CQL_Values correspondant a une structure */
/* Les elements du tableau sont des CQL_Values */
#define CQL_VALUE_TO_RECORD(val) \
   ((CQL_ValueP)CQL_VALUE_TO_POINTER(val))

/* retourne une valeur de type record. Attention, cette valeur doit
 * etre une structure CQL, obtenue par CQL_RecordMake ou par CQL_VALUE_TO_RECORD
 */
#define CQL_VALUE_FROM_RECORD(arr) \
     CQL_VALUE_FROM_POINTER(arr)

/* interface reservee a un usage interne dans l'evaluateur */
typedef struct {
  CQL_TypeS basic_type;
 
  /* useful data */
  int size;
  CQL_TypeP *memberTypes;
  char * *memberNames;
}
CQL_RecordTypeS, *CQL_RecordTypeP;

IMPORT void
CQL_RecordTypeDelete(CQL_TypeP type);

IMPORT int
CQL_RecordTypeMemberAccess(CQL_TypeP record_type, char *member_name);

#define CQL_RECORD_SIZE(typ) \
   (((CQL_RecordTypeP)(typ))->size)
#define CQL_RECORD_MEMBER_TYPE(typ, acc) \
   (((CQL_RecordTypeP)(typ))->memberTypes[acc])
#define CQL_RECORD_MEMBER_NAME(typ, acc) \
   (((CQL_RecordTypeP)(typ))->memberNames[acc])
#define CQL_RECORD_MEMBER_VALUE(rec, acc) \
   (CQL_VALUE_TO_RECORD(rec)[acc])


/* Pour permettre d'expoiter des curseurs */
/* Le type sous-jacent des curseurs n'est pas documente */

/* pour permettre aux autres types de construire des curseurs
 * cree un curseur sur la table (celle-ci est "utilisee" par le curseur :
 * elle ne peut pas etre liberee tant que le curseur n'est pas libere
 * L'iterateur n'est pas cree.
 * curv : CQL_Value (resultat: contiendra la curseur)
 * tab : CQL_Value (la table)
 */
IMPORT CQL_Value CQL_CursorMake(CQL_Value table);

/* parcourt la table associee au curseur en executant la liste d'instructions
 * pour chaque ligne. <cursor> ne doit pas etre indefini.
 */
IMPORT void CQL_CursorMapInstructions(CQL_Value cursor, VTP_TreeP instrs);

/** Type file */

/* Cree une valeur de type file
 * si close_on_destroy est vrai, la liberation provoquera la fermeture de <fd>
 */
IMPORT CQL_Value CQL_FileMake (FILE * fd, int close_on_destroy) ;

/* Retourne de file descriptor associe a une valueur de type file */
IMPORT FILE * CQL_FileGetDescriptor (CQL_Value val) ;


/* Colonnes (pour exploiter un argument issu d'un sous-select) */
/* VOIR CQLTABLE.H */

/* Pointeurs Quelconques */

/* Permet de retourner une valeur correspondant a un pointeur quelconque */
#define CQL_VALUE_FROM_POINTER(ptr) (cql_valueUnion.p=(ptr),cql_valueUnion)

/* pour obtenir un pointeur a partir d'une valeur pointeur */
#define CQL_VALUE_TO_POINTER(ptr) ((ptr).p)

#endif /* CQLVALUE_H */
