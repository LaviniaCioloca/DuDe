/*
**********
**********   Concerto Kernel Utilities
**********
**********
*
* Copyright Sema Group 1997
*
***********
* Michel Guyot - Janvier 1998
*  Ajout de CLex_Attach, CLex_Detach
*  documentation de CLex_GetNone
* Jean-Marc Letteron - Septembre 1997
*  Ajout de CLex_GetCurrentInput, CLex_CheckEnd et CLex_GetCComment
* Wael Kombar - 1997
*  Creation
***********
*
*/

#ifndef _CLEX_H_
#define _CLEX_H_


#include <stdcto.h>
#include <tlist.h>
#include <membuf.h>
#include <ctoio.h>

#undef FAST_H_DLL
#define FAST_H_DLL CTOKERNEL_LIB
#include <fastdll.h>

/********************************************************************************/
/** CTODOC GLOBAL_INFO
.pp
.=e "CLex"
is a package that provides a simple way to build lexical analyzers for small
languages. The first way to use this package is to create
a general context free lexical analyzer that parses the input flow into tokens.
The second way is to sequentially call specific functions to get a token of the type
expected at each call. The two ways can be mixed. That means that it is possible to
build a globally context free analyzer, and to call some specific functions from time
to time, to get a token depending on the context, or to accelerate the analysis if
the expected token type is known.
.pp
Here is a small example to give an idea of how to use the
.=q clex
package.
.pp
.<X

#include <clex.h>

/\&* Declare the separators of the language *\&/
#define SEPARATORS	" \\t\\n\\r(){}-+";

/\&* Declare the token types of the language *\&/
static CLex_GetFunc get_functions[] =
{
  CLex_GetSpaces,	/\&* spaces *\&/
  CLex_GetAdaComment,	/\&* comments *\&/
  CLex_CheckSeparator,	/\&* separators *\&/
  CLex_GetCSymbol,	/\&* symbols *\&/
  CLex_GetLString,	/\&* strings *\&/
  CLex_GetInteger,	/\&* integers *\&/
  CLex_GetFloat,	/\&* floats *\&/
  CLex_GetChar,		/\&* operators *\&/
  0
};

/\&* Some useful variable *\&/
CIO_InputP	input;
CLex_InputP	in;
int		ttype;
char		*str;

/\&* open an input flow on the file "/tmp/example" *\&/
input = FileInput_Open("/tmp/example");

/\&* open an analyzer on this input flow *\&/
in = CLex_Open(input, get_functions);

/\&* declare the separators of the language, for CLex_CheckSeparator *\&/
CLex_SetSeparators(in, SEPARATORS);

/\&* get the next token *\&/
ttype = CLex_GetToken(in);

/\&* find the type of the token got *\&/
switch(ttype)
{
case -1: printf("Unexpected end of file\&\n"); exit(1);
case 3: str = "this is a symbol"; break;
case 4: str = "this is a string"; break;
case 5: str = "this is an integer"; break;
case 6: str = "this is a float"; break;
case 7: str = "this is an operator"; break;
default: str = "Should not execute this line"; break;
}

/\&* print the token as it was read *\&/
printf("%s : %s\\n", str, in->token);

/\&* get the next token, that must be an integer *\&/
if(!CLex_Get(in, Integer))
{ printf("Error, an integer was expected\\n"); exit(1); }

/\&* print the value of this integer *\&/
printf("The integer is %d\\n", in->ivalue);

/\&* close the analyzer and the input flow *\&/
CLex_Close(in);
.>X
.pp
.<S "clex.h"
.=T "C INCLUDE"
.>S
.pp
Gives access to the programming interface provided by
.=q ctokernel
for building lexical analyzers.
**/



/********************************************************************************/
/** CTODOC GLOBAL_INFO
.pp
A lexical analyzer is represented by a structure
.=q CLex_InputS +
\&.
.<S "CLex_InputS"
.=T "C TYPE"
.=H "ctokernel" "clex.h"
.>S
.pp
Is the structure that holds all the data needed by a
.=q clex
lexical analyzer. This includes the input flow (represented as a
.=q "CIO_InputP" +
\&) and some information about spaces, comments and other specific functions.
This structure is not fully opaque, since it is used to return data related
to recognized tokens. Some fields need also to be known by people who
develop new types of tokens. The structure is defined as follow (only the
important fields are shown):
.<=
struct
{
  char		*token;	    /\&* the external form of the last token *\&/
  char		*svalue;    /\&* the internal form of the last token, if a string *\&/
  long		ivalue;     /\&* the value of the last token, if an integer *\&/
  double	fvalue;     /\&* the value of the last token, if a "real" number *\&/
  MemBufS	mbuf;       /\&* the buffer for the external form of the token *\&/
  MemBufS	smbuf;      /\&* the buffer for the internal form of the token *\&/
} CLex_InputS, *CLex_InputP;
.>=
.pp
The four first fields are needed when using a lexical analyzer, to get the values
corresponding to the last read token. THEY ARE VALID ONLY AFTER HAVING SUCCESSFULY
READ A TOKEN. THEY ARE INVALIDATED BY AN UNSUCCESSFUL ATTEMPT TO GET A TOKEN.
.pp
The two last fields are needed when developping new types of tokens.
.pp
.<S "CLex_InputP"
.=T "C TYPE"
.=H "ctokernel" "clex.h"
.>S
.pp
Represents a pointer on a
.=q CLex_InputS +
\&.
**/
typedef struct _CLex_Input  CLex_InputS, *CLex_InputP;

/********************************************************************************/
/** CTODOC GLOBAL_INFO
.pp
A lexical analyzer is based on a set of specific functions called
.=e "token type functions" +
: each function is able
to recognize tokens of a specific kind.
.<S "CLex_GetFunc"
.=T "C TYPE"
.=H "ctokernel" "clex.h"
.>S
Is the type of the functions that recognize tokens. It is defined as:
.<=
typedef int (*CLex_GetFunc)(CLex_InputP in);
.>=
.pp
Two types of token are treated in a special way: spaces and comments.
.<D "<CLex_GetSpaces>
.=A "input" "CIO_InputP"
.=R "int"
.=T "C FUNCTION"
.>D
This function is a
.=q CLex_GetFunc
function that recognizes sequences of spaces (as they are defined by
the language, i.e. tabs, newlines, ... may be considered as spaces).
This function has to be known by the analyzer in order to skip spaces,
instead of returning them as valid tokens.
.<D "<CLex_GetComment>
.=A "input" "CIO_InputP"
.=R "int"
.=T "C FUNCTION"
.>D
This function is a
.=q CLex_GetFunc
function that recognizes comments, as they are defined by
the language.
This function has to be known by the analyzer in order to skip comments,
instead of returning them as valid tokens.
.<D "<CLex_CheckSeparator>
.=A "input" "CIO_InputP"
.=R "int"
.=T "C FUNCTION"
.>D
This function is a
.=q CLex_GetFunc
function, but with a specific semantic. Its role is to tell if the
next character is a
.=e separator
or the end of the input flow. A token is not considered to be valid
unless it is immediatly followed by a separator (or the end of the input flow).
This separator may be the begining of the
next token, or a blank separator (like a space). A
.=q "<CLex_CheckSeparator>"
function should check the character and put it back in the input flow.
**/
typedef int (*CLex_GetFunc)(CLex_InputP in);

struct _CLex_Input
{
  TListS	input_stack;
  CIO_InputP	input;
  char		*separators;
  char		*token;
  char		*svalue;
  long		ivalue;
  double	fvalue;
  MemBufS	mbuf;
  MemBufS	smbuf;
  CLex_GetFunc	*get_functions;
};


/********************************************************************************/
/** CTODOC USE
.pp
.<D "CLex_Open"
.=A "input" "CIO_InputP"
.=A "get_functions" "CLex_GetFunc *"
.=R "CLex_InputP"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Creates a lexical analyzer. The input flow is defined by
.=q input +
\&. The "context free" token types recognized by the analyzer are specified
in the array
.=q "get_functions" +
\&. This array should contain the following items, in the order of their
description here:
.<L s
.=.
a
.=q "<CLex_GetSpaces>"
function that describes the spaces of the language;
.=.
a
.=q "<CLex_GetComment>"
function that describes the comments of the language;
.=.
a
.=q "<CLex_CheckSeparator>"
function that checks for a valid separator after a token;
.=.
a number of
.=q "CLex_GetFunc"
functions that describe the token types of the language;
.=.
a
.=q "NULL"
that indicates the end of the table.
.>L
.pp
If a token may belong to several token types, it will be considered to be of the
type that appears first in the
.=q get_functions
array. Note however that the
.=q get_functions
array is not taken into account when calling directly a specialized token type
function (for instance, the function
.=q "CLex_GetInteger"
checks for an integer in the input flow, without taking the array
.=q "get_functions"
into account).
.pp
The
.=q CLex
package proposes a set of predefined token type functions, for the most
common cases. It is always possible to define other functions if needed.
**/
IMPORT CLex_InputP
CLex_Open(CIO_InputP input, CLex_GetFunc *funcs);

/********************************************************************************/
/** CTODOC USE
.pp
.<D "CLex_Open"
.=A "input" "CIO_InputP"
.=A "get_functions" "CLex_GetFunc *"
.=R "CLex_InputP"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Associates a lexical analyzer with an input flow. This function can be
called while reading the input flow, to use another analyzer.
.pp
If an analyzer was already attached (or opened) on the input flow, it can be
detached first (using CLex_Detach). If two analyzers are used simultaneously,
the CLex_Unput... functions of an analyzer must be used with care (unput
buffers become obsolete after a call to the other analyzer).
**/
#define CLex_Attach(i,f) CLex_Open((i),(f))

/********************************************************************************/
/** CTODOC USE
.pp
.<D "CLex_Include"
.=A "in" "CLex_InputP"
.=A "input" "CIO_InputP"
.=R "void"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Inserts all the characters of the flow
.=q "input"
at the current position of the input flow of the analyzer. This is done by
considering
.=q "input"
as the current input flow, until its end is reached, and then by getting back
to the previous input flow.
.pp
When its end will be reached,
.=q "input"
will be closed. It will also be closed if the analyzer itself is closed or
detached from its input.
**/
IMPORT void
CLex_Include(CLex_InputP in, CIO_InputP input);


/********************************************************************************/
/** CTODOC USE
.pp
.<D "CLex_GetCurrentInput"
.=A "in" "CLex_InputP"
.=R "CIO_InputP"
.=T "C MACRO"
.=H "ctokernel" "clex.h"
.>D
.pp
returns the current input flow.
.pp
This allow to get information on the current position of the lexical
annalysis.
**/
#define CLex_GetCurrentInput(i) ((i)->input)

/********************************************************************************/
/** CTODOC USE
.pp
.<D "CLex_Close"
.=A "in" "CLex_InputP"
.=R "void"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Closes the input flow and frees all the resources used by the lexical analyzer.
**/
IMPORT void
CLex_Close(CLex_InputP in);

/********************************************************************************/
/** CTODOC USE
.pp
.<D "CLex_Detach"
.=A "in" "CLex_InputP"
.=R "void"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Frees all the resources used by the lexical analyzer. The input flow is
not closed.
**/
IMPORT void
CLex_Detach(CLex_InputP in);

/********************************************************************************/
/** CTODOC USE
.pp
.<D "CLex_GetToken"
.=A "in" "CLex_InputP"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Parses the next token in the input flow, after skipping spaces and comments.
The return value indicates whether a token has been found or not, and, if any,
what token has been found.
.pp
.<L s
.=.
0 means that no valid token has been found. The position in the input flow is
set just before the first character of the invalid token.
.=.
-1 means that the end of the input flow has been encountered.
.=.
A positive value means that a valid token has been found. The returned value is
the rank of the token type function that has recongnized the token, in the array
of token type functions given to
.=q "CLex_Open" +
\&. This value is always strictly greater than 2, since 2 is the rank of the function
that checks for a valid separator.
.>L
.pp
If a valid token has been found, the position in the input flow is moved after
this token. Note that a token is not considered to be valid if it is not
followed by a
.=q separator
character, or by the end of the input flow.
.pp
If a valid token has been found, the characters that compose the token are
stored in the field
.=q token
of the analyzer structure. If this token has a corresponding value, this value
may be accessed in the field whose type is appropriate (
.=q svalue +
\&,
.=q ivalue
or
.=q fvalue +
\&).
**/
IMPORT int
CLex_GetToken(CLex_InputP in);

/********************************************************************************/
/** CTODOC USE
.pp
.<D "CLex_Get"
.=A "in" "CLex_InputP"
.=A "function" "symbol"
.=R "int"
.=T "C MACRO"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Tries to get the next token in the input flow, which should be of the type
.=q function +
\&. This type corresponds to a token type function of the name
.=q "CLex_Get<function>" +
\&. For instance,
.=q "CLex_Get(in, Integer)"
will try to get the next token by using the function
.=q CLex_GetInteger +
\&.
.pp
If the next token is of the expected type, a positive value is returned, and
the fields of the analyzer (\c
.=q "token" +
,
.=q "svalue" +
\&,...) are updated consequently. Otherwise, a null value is returned, and
the position in the input flow is set just before the next token.
Note that a token is not considered to be valid if it is not followed by a
.=q separator
character, or by the end of the input flow.
.pp
The only difference between
.=q "CLex_Get(in, Integer)"
and
.=q "CLex_GetInteger(in)"
is that
.=q "CLex_GetInteger(in)"
does not skip spaces and comments before looking for a valid token.
**/
#define CLex_Get(in, func)	(CLex_SkipNullTokens(in), CLex_Get##func(in))

/********************************************************************************/
/** CTODOC USE
.pp
.<D "CLex_CheckOperator"
.=A "in" "CLex_InputP"
.=A "string" "const char *"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.<D "CLex_CheckIOperator"
.=A "in" "CLex_InputP"
.=A "string" "const char *"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.<D "CLex_CheckKeyword"
.=A "in" "CLex_InputP"
.=A "string" "const char *"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.<D "CLex_CheckIKeyword"
.=A "in" "CLex_InputP"
.=A "string" "const char *"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
These four functions are used to check for a given sequence of characters (\c
.=q "string" +
\&) in the input flow. They all skip spaces and comments first.
.pp
The functions
.=q "CLex_CheckOperator"
and
.=q "CLex_CheckIOperator"
do not test that the token is followed by a
.=q separator
or by the end of the input flow. The other functions do.
.pp
The functions
.=q "CLex_CheckOperator"
and
.=q "CLex_CheckKeyword"
are case sensitive. The other functions are not.
.pp
In all cases, if a valid token is found, a positive value
is returned, and the field
.=q "token"
of the analyzer is updated. Otherwise, a null value is returned.
**/
IMPORT int
CLex_CheckOperator(CLex_InputP in, const char *keyword);

IMPORT int
CLex_CheckIOperator(CLex_InputP in, const char *keyword);

IMPORT int
CLex_CheckKeyword(CLex_InputP in, const char *keyword);

IMPORT int
CLex_CheckIKeyword(CLex_InputP in, const char *keyword);

/********************************************************************************/
/** CTODOC USE
.pp
.<D "CLex_UnputToken"
.=A "in" "CLex_InputP"
.=R "void"
.=T "C MACRO"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Puts back the characters composing the last read token in the input flow.
This function may be used only to put back one token.
**/
#define CLex_UnputToken CLex_UnputBuf

/********************************************************************************/
/** CTODOC FUNCTIONS
.pp
The
.=q "CLex"
package offers a set of common token type functions described here. These
functions can be used to define "context-free" analyzers, or to get a
specific token in a specific context. All these functions update the field
.=q "token"
of the analyzer, and any other relevant field.
**/

/********************************************************************************/
/** CTODOC FUNCTIONS
.pp
.<D "CLex_GetSpaces"
.=A "in" "CLex_InputP"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Skips any space in the input flow. The position in the input flow is set
just before the next non-space character. The function returns a positive
value if it could find spaces. These spaces are put in the field
.=q "token"
of the analyzer.
.pp
This function is usually used as the first element of the array of functions
passed to
.=q "CLex_Open" +
\&, to define what should be considered as spaces by the analyzer.
.pp
In this function, space characters are defined by the function
.=q "isspace" +
\&.
**/
IMPORT int
CLex_GetSpaces(CLex_InputP in);

/********************************************************************************/
/** CTODOC FUNCTIONS
.pp
.<D "CLex_CheckSeparator"
.=A "in" "CLex_InputP"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Tells if the next character of the input flow is a valid separator, or if this
is the end of the input flow. A non-null value is returned if one of these conditions
is true. This function is usually used as the third element of the array of functions
passed to
.=q "CLex_Open" +
\&, to define what should be considered as valid separators.
.pp
For this function, the valid separator characters are those specified by calling
the macro
.=q CLex_SetSeparators
\&.
**/
IMPORT int
CLex_CheckSeparator(CLex_InputP in);

/********************************************************************************/
/** CTODOC FUNCTIONS
.pp
.<D "CLex_SetSeparators"
.=A "in" "CLex_InputP"
.=A "separators" "const char *"
.=R "void"
.=T "C MACRO"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Defines which characters are to be considered as valid separators by
.=q CLex_CheckSeparator +
\&. It should be called just after opening the analyzer.
**/
#define CLex_SetSeparators(in, sep) ((in)->separators = Mem_NewString(sep))

/********************************************************************************/
/** CTODOC FUNCTIONS
.pp
.<D "CLex_GetChar"
.=A "in" "CLex_InputP"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Considers the next character to be a valid token, and returns it.
**/
IMPORT int
CLex_GetChar(CLex_InputP in);

/********************************************************************************/
/** CTODOC FUNCTIONS
.pp
.<D "CLex_GetNone"
.=A "in" "CLex_InputP"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Does not read the input flow and returns 0. This function can replace
the CLex_GetCComment function in the token type functions array if the language
does not have comments).
**/
IMPORT int
CLex_GetNone(CLex_InputP in);

/********************************************************************************/
/** CTODOC FUNCTIONS
.pp
.<D "CLex_GetCSymbol"
.=A "in" "CLex_InputP"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Defines the type of tokens corresponding to C symbols. This function updates
the
.=q svalue
field of the analyzer.
**/
IMPORT int
CLex_GetCSymbol(CLex_InputP in);

/********************************************************************************/
/** CTODOC FUNCTIONS
.pp
.<D "CLex_GetCChar"
.=A "in" "CLex_InputP"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Defines the type of tokens corresponding to C characters (between quotes).
This function updates the
.=q ivalue
field of the analyzer.
**/
IMPORT int
CLex_GetCChar(CLex_InputP in);

/********************************************************************************/
/** CTODOC FUNCTIONS
.pp
.<D "CLex_GetQString"
.=A "in" "CLex_InputP"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Defines the type of tokens corresponding to a sequence of characters
delimited by simple quotes. A single quote is represented by doubling
it. This function updates the
.=q svalue
field of the analyzer.
**/
IMPORT int
CLex_GetQString(CLex_InputP in);

/********************************************************************************/
/** CTODOC FUNCTIONS
.pp
.<D "CLex_GetDQString"
.=A "in" "CLex_InputP"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Defines the type of tokens corresponding to a sequence of characters
delimited by double quotes. A double quote is represented by doubling
it. This function updates the
.=q svalue
field of the analyzer.
**/
IMPORT int
CLex_GetDQString(CLex_InputP in);

/********************************************************************************/
/** CTODOC FUNCTIONS
.pp
.<D "CLex_GetInteger"
.=A "in" "CLex_InputP"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Defines the type of tokens corresponding to simple decimal integers (composed
of decimal digits, possibly preceded by a sign). This function updates the
.=q ivalue
field of the analyzer.
**/
IMPORT int
CLex_GetInteger(CLex_InputP in);

/********************************************************************************/
/** CTODOC FUNCTIONS
.pp
.<D "CLex_GetFloat"
.=A "in" "CLex_InputP"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Defines the type of tokens corresponding to simple real numbers. The lex
definition of a real number is
.=q "[+-]?[0-9]+(.[0-9]?)?([eE][+-]?[0-9]+)?" +
\&. This function updates the
.=q "fvalue"
field of the analyzer.
**/
IMPORT int
CLex_GetFloat(CLex_InputP in);

/********************************************************************************/
/** CTODOC FUNCTIONS
.pp
.<D "CLex_GetAdaComment"
.=A "in" "CLex_InputP"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Defines the type of tokens corresponding to an Ada comment, started
by '--' and ended by the end of the line (or end of input).
The end of the line is not part of the token.
This function updates the
.=q "svalue"
field of the analyzer.
**/
IMPORT int
CLex_GetAdaComment(CLex_InputP in);


/********************************************************************************/
/** CTODOC FUNCTIONS
.pp
.<D "CLex_GetCComment"
.=A "in" "CLex_InputP"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Defines the type of tokens corresponding to an C comment, started
by '/\&*' and ended by '*\&/'.
'/\&*' and '*\&/' are part of the comment.
This function updates the
.=q "svalue"
field of the analyzer.
**/
IMPORT int
CLex_GetCComment(CLex_InputP in);

/********************************************************************************/
/** CTODOC FUNCTIONS
.pp
.<D "CLex_GetDate"
.=A "in" "CLex_InputP"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Defines the type of tokens corresponding to simple dates. The lex
definition of a date is
.=q "[0-9]+/[0-9]+/[0-9]+" +
\&. This function updates the
.=q "svalue"
field of the analyzer.
**/
IMPORT int
CLex_GetDate(CLex_InputP in);

/********************************************************************************/
/** CTODOC IMPLEMENT
.pp
The functions described here can be used to define new token type functions.
A token type function must:
.<L s
.=.
set the position of the
.=q "mbuf"
field of the analyzer to 0;
.=.
read characters of the input flow to find if they correspond to a valid token
\&(without skipping spaces or comments);
.=.
put back any read character and return a null value if the next characters do
not correspond to a valid token of the type defined by the function;
.=.
check that there is a valid separator or the end of the input flow after the
token - if this is not the case, the token is not valid and the
characters should be put back in the input flow;
.=.
update the
.=q "token"
field of the analyzer, and any other relevant field, and return a positive
value if the characters correspond to a valid token - the
.=q "smbuf"
field of the analyzer may be used to store a string value.
.>L
**/

/********************************************************************************/
/** CTODOC IMPLEMENT
.pp
.<D "CLex_Input"
.=A "in" "CLex_InputP"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Returns the next character of the input flow, or a null value if the end
of the input flow is reached. The returned character is appended to the
characters of the
.=q "mbuf"
field of the analyzer.
**/
IMPORT int
CLex_Input(CLex_InputP in);

/********************************************************************************/
/** CTODOC IMPLEMENT
.pp
.<D "CLex_Unput"
.=A "in" "CLex_InputP"
.=A "c" "int"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Puts back a character in the input flow, and updates the
.=q "mbuf"
field of the analyzer.
**/
IMPORT void
CLex_Unput(CLex_InputP in, int c);

/********************************************************************************/
/** CTODOC IMPLEMENT
.pp
.<D "CLex_UnputBuf"
.=A "in" "CLex_InputP"
.=R "void"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Puts back the characters stored in the
.=q "mbuf"
field of the analyzer.
**/
IMPORT void
CLex_UnputBuf(CLex_InputP in);

/********************************************************************************/
/** CTODOC IMPLEMENT
.pp
.<D "CLex_SkipSpaces"
.=A "in" "CLex_InputP"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
This function is a convenience function to call the token type function that
defines spaces for the analyzer.
**/
IMPORT int
CLex_SkipSpaces(CLex_InputP in);

/********************************************************************************/
/** CTODOC IMPLEMENT
.pp
.<D "CLex_SkipNullTokens"
.=A "in" "CLex_InputP"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
This function is a convenience function to skip spaces and comments. It returns
a non-null value if characters were really skipped.
**/
IMPORT void
CLex_SkipNullTokens(CLex_InputP in);

/********************************************************************************/
/** CTODOC IMPLEMENT
.pp
.<D "CLEX_CHECK_SEPARATOR"
.=A "in" "CLex_InputP"
.=R "int"
.=T "C MACRO"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
This macro checks that the next character of
the input flow is a valid separator (or the end of the input flow). It returns
a non-null value if this is the case.
**/
#define CLEX_CHECK_SEPARATOR(in) ((((in)->get_functions)[2])(in))


/********************************************************************************/
/** CTODOC IMPLEMENT
.pp
.<D "CLex_CheckEnd"
.=A "in" "CLex_InputP"
.=R "int"
.=T "C FUNCTION"
.=H "ctokernel" "clex.h"
.=B "libctokernel"
.>D
.pp
Checks that this is 
the end of the input flow.
A non-null value is returned if this is the case.
**/
IMPORT int
CLex_CheckEnd(CLex_InputP in);




#endif /* _CLEX_H_ */
