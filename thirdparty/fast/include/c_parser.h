/*********
**********   ENVIRONNEMENT CONCERTO  C
**********   Programme principal de l'analyseur C 
**********
* 
* Copyright Sema Group 1995 - 1995
* 
**********
*
* Jean-Marc Letteron - Octobre 1998
*   Librairies dynamiques
*
**********
*/

#ifndef C_PARSER_H
#define C_PARSER_H

#include <ctoio.h>
#include <vtp.h>
#include <vtpparser.h>

#undef FAST_H_DLL
#define FAST_H_DLL  ENV_C_LIB
#include <fastdll.h>

IMPORT VTP_TreeP
C_Parse(CIO_InputP streamP, TListP contexts);

IMPORT void
C_SetErrorHandler(Parser_ErrorCallbackFunc clbk);

IMPORT VTP_TreeP
Cxx_Parse(CIO_InputP streamP, TListP contexts);

IMPORT void
Cxx_SetErrorHandler(Parser_ErrorCallbackFunc clbk);

#endif /* C_PARSER_H */
