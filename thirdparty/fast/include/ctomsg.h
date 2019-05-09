/*
  ********
  ********   FAST
  ********
  ********

  Copyright Sema Group - 1996

  ********
*/
#ifndef _CTO_MSG_H
#define _CTO_MSG_H

#undef FAST_H_DLL
#define FAST_H_DLL CTOKERNEL_LIB
#include <fastdll.h>

/***************************************************************/
/** CTODOC GLOBAL_INFO
.pp
.<X
 /\&* Choose the English language *\&/
 if French_User()
      { Msg_SetLang("fr"); }
 else
      { Msg_SetLang("en"); }
 /\&* Get access to the translations *\&/
 Dic_Load(MSG_LANG,"xxx/xxx1");
 /\&* Get access to the  messages *\&/
 Msg_MessagesLoad(MSG_LANG, "xxx/xxx1");
 \&...
 /\&* Get a translation *\&/
 Dic_LGet(MSG_LANG, "xxx_losange");
 /\&* Get a message with no field *\&/
 Msg_Get(MSG_LANG, "XXX_USAGE", (char *)0, 0);
.>X
.pp
This example shows how to get texts in the language previously chosen by
the user
(for instance with an option on the command line). The tool loads a
dictionary and a
compiled message file, which makes the translations and messages for
that language available for retrieval.
.<S "ctomsg.h"
.=T "C INCLUDE"
.>S
.pp
Gives access to the programming interface provided by
.=q ctokernel
for handling messages and dictionaries, by declaring the public elements of
this interface (C types, functions, macros, ...).
**/



/***************************************************************/
/** CTODOC GLOBAL_INFO
.<V MSG_LANG
.=V "char *"
.=T "C VARIABLE"
.=H "ctokernel" "ctomsg.h"
.=B "libctokernel"
.>V
.pp
Denotes the current language.
**/
#define MSG_LANG    ((char *)0)



/***************************************************************/
/** CTODOC DIC
.<E EX_DIC
.=V VoidP
.=T EXCEPTION
.=M EX_ANY
.>E
.pp
Is the exception raised when a problem with a dictionary is encountered.
**/
Exception_Declare(EX_DIC);


/***************************************************************/
/** CTODOC MSG
.<E EX_MSG
.=V VoidP
.=T EXCEPTION
.=M EX_ANY
.>E
.pp
Is the exception raised when a problem with a message or with a message file is
encountered.
**/
Exception_Declare(EX_MSG);


/***************************************************************/
/** CTODOC NONE
**/
Exception_Declare(EX_MSG_BAD_LANG);
Exception_Declare(EX_MSG_BAD_CATALOG);
Exception_Declare(EX_MSG_BAD_MSG);
Exception_Declare(EX_MSG_BAD_FORMAT);
Exception_Declare(EX_MSG_BAD_PARAM);


/***************************************************************/
/** CTODOC GLOBAL_INFO
.=I "ctomsg.h" "Msg_GetLang"
.<D "Msg_GetLang"
.=R "char *"
.=T "C FUNCTION"
.=H "ctokernel" "ctomsg.h"
.=B "libctokernel"
.>D
.pp
Returns the language of the running application.
This is the language specified explicitly by
.=q Msg_SetLang
or the language defined by the environment variable
.=q CTO_LANG
(example: en) or the language defined by the environment variable
.=q LANG
(example: en_US.ISO8859-1).
**/
IMPORT char *
Msg_GetLang(void);


/***************************************************************/
/** CTODOC GLOBAL_INFO
.=I "ctomsg.h" "Msg_SetLang"
.<D "Msg_SetLang"
.=A "lang" "char *"
.=R "void"
.=T "C FUNCTION"
.=H "ctokernel" "ctomsg.h"
.=B "libctokernel"
.>D
.pp
Declares the language specified by
.=q "lang"
as the language of the running application.
**/
IMPORT void
Msg_SetLang(char *lang);



/***************************************************************/
/** CTODOC MSG
.=I "ctokernel" "Msg_MessagesLoad"
.<D "Msg_MessagesLoad"
.=A "lang" "char *"
.=A "msg_file" "char *"
.=R "void"
.=T "C FUNCTION"
.=H "ctokernel" "ctomsg.h"
.=B "libctokernel"
.>D
.pp
Loads the message map from the file identified by
.=q "msg_file"
(filename without the
.=q ".map"
suffix)
and the language pointed to by
.=q "lang" +
\&. If this map is already loaded, no re-loading is done.
**/
IMPORT void
Msg_MessagesLoad(char *lang, char *file);



/***************************************************************/
/** CTODOC MSG
.=I "ctokernel" "Msg_GetContents"
.<D "Msg_GetContents"
.=A "lang" "char *"
.=A "symbol" "char *"
.=R "char *"
.=T "C FUNCTION"
.=H "ctokernel" "ctomsg.h"
.=B "libctokernel"
.>D
.pp
Retrieves the message identified by
.=q "symbol"
for the language specified by
.=q "lang" +
, and returns a pointer to the resulting message.
No particular work is done for fields.
.pp
The corresponding map file must have been loaded beforehand.
**/
IMPORT char *
Msg_GetContents(char *lang, char *name);



/***************************************************************/
/** CTODOC MSG
.=I "ctokernel" "Msg_LGetContents"
.<D "Msg_LGetContents"
.=A "lang" "char *"
.=A "msg_file" "char *"
.=A "symbol" "char *"
.=R "char *"
.=T "C MACRO"
.=H "ctokernel" "ctomsg.h"
.=B "libctokernel"
.>D
.pp
Loads the message map from the file identified by
.=q "msg_file"
(filename without the
.=q ".map"
suffix)
and by the language pointed to by
.=q "lang"
\(em unless this map is already loaded \(em, retrieves the message identified by
.=q "symbol" +
, and returns a pointer to the resulting message.
**/
#define Msg_LGetContents(lang, file, name)  \
    	(Msg_MessagesLoad(lang, file), Msg_GetContents(lang, name))



/***************************************************************/
/** CTODOC MSG
.=I "ctokernel" "Msg_Get"
.<D "Msg_Get"
.=A "lang" "char *"
.=A "symbol" "char *"
.=A "params" "char **[]"
.=A "nb_params" "int"
.=R "char *"
.=T "C FUNCTION"
.=H "ctokernel" ""
.=B "libctokernel"
.>D
.pp
Retrieves the message identified by
.=q "symbol"
for the language specified by
.=q "lang" +
, replaces the fields by the strings pointed to by the
.=q "nb_params"
elements of the table pointed to by
.=q "params" +
, and returns a pointer to the resulting message.
.pp
The corresponding message map must have been loaded beforehand.
**/
IMPORT char *
Msg_Get(char *lang, char *name, char **params, int nbrParam);


/* << PAn */
/***************************************************************/
/** CTODOC MSG
.=I "ctokernel" "Msg_VGet"
.<D "Msg_VGet"
.=A "symbol" "char *"
.=A "nb_params" "int"
.=A "param..." "char *"
.=R "char *"
.=T "C FUNCTION"
.=H "ctokernel" ""
.=B "libctokernel"
.>D
.pp
Retrieves in the current language the message identified by
.=q "symbol" +
, replaces the fields by the strings pointed to by the
.=q "nb_params"
parameters pointed to by
.=q "param..." +
, and returns a pointer to the resulting message.
.pp
The corresponding message map must have been loaded beforehand.
**/
IMPORT char *
Msg_VGet(char *name, int nbrParam, ...);
/* >> PAn */


/***************************************************************/
/** CTODOC MSG
.=I "ctomsg.h" "Msg_LGet"
.<D "Msg_LGet"
.=A "lang" "char *"
.=A "file" "char *"
.=A "symbol" "char *"
.=A "params" "char *"
.=A "nb_params" "int"
.=R "char *"
.=T "C MACRO"
.=H "ctokernel" "ctomsg.h"
.=B "libctokernel"
.>D
.pp
Loads the message map from the file identified by
.=q "msg_file"
(filename without the 
.=q ".map"
suffix) and by the language pointed to by
.=q "lang" 
\(em unless this map is already loaded \(em, retrieves the message identified by
.=q "symbol" +
, replaces the fields by the strings pointed to by the
.=q "nb_params"
elements of the table pointed to by
.=q "params" +
, and returns a pointer to the resulting message.
**/
#define Msg_LGet(lang, file, name, params, nbrParam) \
    	(Msg_MessagesLoad(lang, file), Msg_Get(lang, name, params, nbrParam))


/***************************************************************/
/** CTODOC DIC
.=I "ctomsg.h" "Dic_Load"
.<D "Dic_Load"
.=A "lang" "char *"
.=A "dic_file" "char *"
.=R "void"
.=T "C FUNCTION"
.=H "ctokernel" "ctomsg.h"
.=B "libctokernel"
.>D
.pp
Loads the translations in the language pointed to by
.=q "lang"
from the dictionary file with the pathname pointed to by
.=q "dic_file" +
\&.
**/
IMPORT void
Dic_Load(char *lang, char *file);


/***************************************************************/
/** CTODOC DIC
.=I "ctomsg.h" "Dic_Get"
.<D "Dic_Get"
.=A "lang" "char *"
.=A "symbol" "char *"
.=R "char *"
.=T "C FUNCTION"
.=H "ctokernel" "ctomsg.h"
.=B "libctokernel"
.>D
.pp
Returns a pointer to the translation of the sentence identified by
.=q symbol
in the language pointed to by
.=q "lang" +
\&.
.pp
The dictionary file that contains the translations for that symbol must
have been loaded beforehand.
.pp
Raises EX_DIC exception if
.=q symbol
is not found
**/
IMPORT char *
Dic_Get(char *lang, char *word);

/* << PAn */
/***************************************************************/
/** CTODOC DIC
.=I "ctomsg.h" "Dic_VGet"
.<D "Dic_VGet"
.=A "symbol" "char *"
.=R "char *"
.=T "C FUNCTION"
.=H "ctokernel" "ctomsg.h"
.=B "libctokernel"
.>D
.pp
Returns a pointer to the translation of the sentence identified by
.=q symbol
in the current language.
.pp
The dictionary file that contains the translations for that symbol must
have been loaded beforehand.
.pp
Raises EX_DIC exception if
.=q symbol
is not found
**/
#define Dic_VGet(word) Dic_Get(MSG_LANG, (word))
/* >> PAn */


/***************************************************************/
/** CTODOC DIC
.=I "ctomsg.h" "Dic_LGet"
.<D "Dic_LGet"
.=A "lang" "char *"
.=A "file" "char *"
.=A "symbol" "char *"
.=R "char *"
.=T "C MACRO"
.=H "ctokernel" "ctomsg.h"
.=B "libctokernel"
.>D
.pp
Loads the translations in the language pointed to by
.=q "lang"
from the dictionary file with the pathname pointed to by
.=q "file" +
, and returns the translation identified by
.=q "symbol" +
\&.
.pp
Raises EX_DIC exception if
.=q symbol
is not found
**/
#define Dic_LGet(lang, file, word)	\
	(Dic_Load(lang, file), Dic_Get(lang, word))

/* << PAn */
/* ********************************************************************** */
/** CTODOC TYPES
.<S Msg_OutputS
.=T "C TYPE"
.=H "ctokernel" ""
.>S
Represents a message output handler.
The structure is defined as follow:
.<=
typedef struct {
    void	(*display)(Msg_OutputP output, char *msg);
} Msg_OutputS;
.>=
.<S Msg_OutputP
.=T "C TYPE"
.=H "ctokernel" ""
.>S
Represents a pointer to a message output handler.
**/
typedef struct _Msg_Output *Msg_OutputP;

typedef struct _Msg_Output {
    void	(*display)(Msg_OutputP output, char *msg);
} Msg_OutputS;


/***************************************************************/
/** CTODOC MSG
.=I "ctokernel" "Msg_SetOuput"
.<D "Msg_SetOuput"
.=A "output" "Msg_OutputP"
.=R "void"
.=T "C FUNCTION"
.=H "ctokernel" ""
.=B "libctokernel"
.>D
.pp
Sets the message output handler identified by
.=q "output"
as the current one.
**/
IMPORT void
Msg_SetOutput(Msg_OutputP output);

/***************************************************************/
/** CTODOC MSG
.=I "ctokernel" "Msg_GetOuput"
.<D "Msg_GetOuput"
.=R "Msg_OutputP"
.=T "C FUNCTION"
.=H "ctokernel" ""
.=B "libctokernel"
.>D
.pp
Gets the current message output handler.
**/
IMPORT Msg_OutputP
Msg_GetOutput(void);

/***************************************************************/
/** CTODOC MSG
.=I "ctokernel" "Msg_Display"
.<D "Msg_Display"
.=A "msg" "char*"
.=R "void"
.=T "C FUNCTION"
.=H "ctokernel" ""
.=B "libctokernel"
.>D
.pp
Displays the message with the text value
.=q "msg"
on the current message output handler.
**/
IMPORT void
Msg_Display(char *msg);

/* >> PAn */

#endif /* _CTO_MSG_H */
