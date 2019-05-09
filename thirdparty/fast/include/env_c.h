/*********
**********   ENVIRONNEMENT CONCERTO  C++
**********   Programme principal de l'analyseur C++
**********
 
Copyright Sema Group 1995 - 1997
 
***********
*
*  Jean-Marc Letteron - Octobre 1998
*   Librairies dynamiques
*  Thomas Schneider - Mai 1997 - Juin 1997
*   Ajout C_IsCstPointer + modif init
* 
*/
 
#ifndef ENV_C_H
#define ENV_C_H

#include <stdcto.h>
#include <ctoio.h>
#include "c_parser.h"
#include "c_conf.h"
#include "c_typetable.h"
#include "c_dependency.h"

#undef FAST_H_DLL
#define FAST_H_DLL  ENV_C_LIB
#include <fastdll.h>

IMPORT void
C_Init(void);

#define C_CheckOper(tree, oper) (VTP_TREE_OPERATOR(tree) == C_1_op_##oper)
#define C_EqOper(op, oper) (op == C_1_op_##oper)
					       
typedef enum {DATA, CONSTANT, TYPEDEF, STRUCT, UNION, CLASS, FUNCTION, FNDEF} C_DeclType;
 
/* call back functions for mapping of C stmts
 * return 1 to go down in the current tree, level is incremented
 * return 0 to jump to next tree */
typedef int (*C_MapFunctionP)(VTP_TreeP tree, int level, void *param);
typedef int (*C_MapFunction2P)(VTP_TreeP tree, C_DeclType type, void *param);

/* Map all declarations */
IMPORT void
C_MapDeclarations(VTP_TreeP tree, C_MapFunctionP func, void *param);

/* Map all functions and sub-functions declared inside functions
 * return 1 to go down in the current function, level is incremented
 * return 0 to jump to next function */
IMPORT void
C_MapFunctions(VTP_TreeP tree, C_MapFunctionP func, void *param);

/* Map all classes and class templates declared inside classes and templates
 * return 1 to go down in the current class, level is incremented
 * return 0 to jump to next class */
IMPORT void
C_MapClassDeclarations(VTP_TreeP tree, C_MapFunctionP func, void *param) ;

/* Map all declarations and statements
 * return 1 to go down in the current tree, level is incremented
 * return 0 to jump to next tree */
IMPORT void
C_MapStatements(VTP_TreeP tree, C_MapFunctionP func, void *param);

/* Map all declarator in a declaration
 * level is 0 for type specifier
 * level is 1 for declarators
/* tree if one of extdecl,decl, struct_decl, member_decl */
IMPORT void
C_MapDeclarators(VTP_TreeP tree, C_MapFunctionP func, void *param);
/* passes the type of declaration to function */
IMPORT void
C_MapDeclaratorsWithDeclarationType(VTP_TreeP tree, C_MapFunction2P func, void *param);

/* return 1 if tree is a function declarator, else 0 */
/* tree belongs to phylum ANY_DECLARATOR */
IMPORT int
C_IsFunction(VTP_TreeP tree);

/* return 1 if tree is an array declarator,
 *   else 0 */
/* tree belongs to phylum ANY_DECLARATOR */
IMPORT int
C_IsArray(VTP_TreeP tree);

/* return 1 if tree is a pointer declarator,
 *        2 if tree is a reference declarator,
 *        3 if tree is a pointer to member declarator,
 *   else 0 */
/* tree belongs to phylum ANY_DECLARATOR */
IMPORT int
C_IsPointer(VTP_TreeP tree);

/* return 1 if tree is a constant pointer,
 *          like type * const var;
 *   else 0 
 */
/* tree belongs to phylum ANY_DECLARATOR */
IMPORT int
C_IsCstPointer(VTP_TreeP tree);


/* return 1 if tree is a constant pointer to member,
 *          like type C::* const var;
 *   else 0 
 */
/* tree belongs to phylum ANY_DECLARATOR */
IMPORT int
C_IsCstPtrToMember(VTP_TreeP tree);


/* return the parameters of a function declaration */
/* tree if one of fndef, subfndef, ANY_DECLARATOR */
IMPORT VTP_TreeP
C_FunctionParameters(VTP_TreeP tree);

/* returns the name of the tree, 0 if the tree cannot be named */
IMPORT char *
C_TreeName(VTP_TreeP tree);

/* returns the complete name (with the scope) of the class tree, 
   0 if the tree cannot be named */
IMPORT char *
C_ClassFullName(VTP_TreeP tree);

/* returns the tree corresponding to name of the tree, 0 if the tree cannot be named */
IMPORT VTP_TreeP
C_TreeId(VTP_TreeP tree);

/* Tests if the declaration has the given storage class */
/* tree if one of extdecl,decl, struct_decl, member_decl, param_decl,
 *      fndef, subfndef, declaration_specifiers */
IMPORT int
C_HasStorageClass(VTP_TreeP tree, char *value);

/* Tests if the declaration has the given storage class */
/* tree if one of extdecl,decl, struct_decl, member_decl, param_decl,
 *      fndef, subfndef, declaration_specifiers, type_specifiers, type_qualifiers */
IMPORT int
C_HasQualifier(VTP_TreeP tree, char *value);
/* returns the types of a declaration */
/* returned value is the number of types, types are VTP_TreeP's in the TList */
/* the TList shall be initialized */
/* tree if one of extdecl,decl, struct_decl, member_decl, param_decl,
 *      fndef, subfndef, declaration_specifiers, type_specifiers */
IMPORT int
C_GetTypes(VTP_TreeP tree, TListP types);

/* returns a string corresponding to the name of the operator */
/* tree belongs to OPER phylum */
IMPORT char *
C_OperPP(VTP_TreeP oper);

/* returns a string with C escape sequences */
/* the return string shall be used immediately */
IMPORT char *
C_CString(char *);
 
IMPORT void
C_DeclNameType(VTP_TreeP, char **, char **);

#endif /* ENV_C_H */
