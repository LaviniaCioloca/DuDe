/*
  ********
  ********   FAST
  ********
  ********

  Copyright Sema Group - 1998

  ********

  Michel Guyot - Janvier 1998
  - creation

*/
#ifndef CTOACTION_H
#define CTOACTION_H
#include <clex.h>
#include <tlist.h>

#undef FAST_H_DLL
#define FAST_H_DLL CTOKERNEL_LIB
#include <fastdll.h>

/** CTODOC INTRODUCTION
.pp
.=e ctokernel
provides a set of utilities to create tool customization scripts. A script contains a list of
.=q actions
which are loaded when the tool is started. The syntax of an action is defined by the implementation of the action. Action loading can be conditional. Utilities are provided to define
.=q commands
and use
.=q "conditional expressions"
in actions. Strings in commands and boolean expressions can contain
.=q variables
which provide an access to values defined in the application (such as the current selection) or environment variables. 
**/

/** CTODOC PATTERN
.pp
Actions are useful to define tool customization files.
.pp
To use the action utilities in a tool, the following steps are required:
.<L s
.=.
define the syntax of actions (the
.=e viewcto
component defines actions that create menus and trigger commands when a menu entry is chosen),
.=.
define the syntax of commands (the
.=e ctokernel
component defines commands whose arguments are a list of strings),
.=.
determine if some values (called
.=e variables +
, such as the current selection) of the tool should be made accessible to the action scripts,
.=.
determine if some internal values (called
.=e "tool parameters" +
, such as the menubar widget) of the tool should be made accessible to command and action implementation functions,
.=.
create a
.=e "tool context"
.=.
associate callback functions with commands in the tool context (the
.=e ctokernel
component provides a function corresponding to a command that executes shell scripts),
.=.
associate callback functions with variables (the
.=e ctokernel
component provides a function corresponding to a variable which always returns the same value),
.=.
create an
.=e "evaluation context"
.=.
associate callback functions with tool parameters in the evaluation context (the
.=e ctokernel
component provides a function corresponding to a tool parameter which always returns the same value),
.=.
create an
.=e "action parser list"
.=.
add the action parsers to that list,
.=.
load the actions.
.>L
**/

/** CTODOC SYNTAX
.pp
The syntax of an action script is:
.<=
<action_script> ::= "TOOLACTION" "VERSION" "1" <action>*

<action> ::= <if_action> 
          | <action_name> <action_body>

<if_action> ::=  "IF" <bool_expr> <action>* <else_if>* [<else>]  "END" "IF"
<else_if> ::= "ELSIF" <bool_expr> <action>*
<else> ::= "ELSE" <action>*

<bool_expr> ::= <bool_or>
<bool_or> ::= <bool_and> "OR" <bool_or>
<bool_and> ::= <bool_simple> "AND" <bool_and>
<bool_simple> ::= "NOT" <bool_simple>
                | "(" <string> = <string> ")"
                | "(" <command> ")"
                | "(" <bool_expr> ")"
<command> ::= <command_name> <command_body>
<string> ::= "<text>"
.>=
.pp
The syntax of <action_body> (and <command_body>) depend on the action (respectively on the command).
.pp
A
.=q <string>
is a sequence of non-null characters (
.=q <text> +
) enclosed in double quotes (double quotes within
.=q <text>
must be doubled). The
.=q <text>
may contain variables, which are expanded when the expression (or command) in which they appear is evaluated.
.pp
The syntax of variables is:
.<=
<variable> ::= "@{"<name>"}"
             | "@{"<name>"(" <param> ["," <param>]* ")""}"
<param> ::= <text>
.>=
.pp
Since "@{" introduces a variable name, it must be written "@@{" if it is to be taken literally.
.pp
Within a parameter list, characters following a backslash "\\" are taken literally. The backslash character must be written "\\\\" if it is to appear literally. 
.pp
To appear literally, the following characters must be backslashed within
a parameter list: space, comma, closing parenthesis, literal backslash.
.pp
Note: spaces around and within <param> are ignored unless they are backslashed. All other spaces are significant (there must be no space around <name>).
.pp
Variables in the parameters are exanded as well. However, the expanded parameters are taken literally (there is no special character in variable values).
**/

/** CTODOC TOOL
.<S Action_ToolContextP
.=T "C TYPE"
.=H ctokernel "ctoaction.h"
.>S
.pp
This is a pointer to a tool context, which contains the commands and variables provided by the tool.
**/
/** CTODOC EXAMPLE
.pp
The example below shows how to create a menu action (see the
.=e viewcto
utilities), with shell commands and a
.=q selection
variable.
.<X
static void
ParseError (CLex_InputP lex_p, char* msg)
{
  CIO_InputP input;
  CIO_PositionS pos;
  input = CLex_GetCurrentInput(lex_p);
  CIO_InputTell(input, &pos);
  fprintf(stderr, "Error in %s at line %d column %d (expected %s)\\n",
                  CIO_InputGetName(input), pos.line, pos.col, msg);
}

static char*
GetSelection(Action_EvalContextP eval_ctx, TListP args, VoidP data)
{
  return "String in a static buffer";
}

static void
LoadMenu(Widget menu_bar, char *file)
{
  Action_EvalContextP eval_ctx = Action_EvalContextCreate();
  Action_ToolContextP tool_ctx = Action_ToolContextCreate();
  Action_ParserListP parser_list = Action_ParserListCreate();

  Action_ToolContextAddCommand(tool_ctx, "SHELL", 
			       Action_CommandStringListParse, 
			       Action_ShellCommand,
 			       Action_CommandStringListDestroy,
			       0, 0);

  Action_EvalContextAddParam(eval_ctx, "XMenu_MenuContext", 
			  Action_EvalContextConstantParam, 0, menu_bar);

  Action_ParserListAddAction(parser_list, "MENU", 
			     XMenu_ParseMenuDescription, view);

  Action_ToolContextAddVariable(tool_ctx, "selection", GetSelection, 0);
  
  Exception_Begin();
  if(Exception_Catch()) {
    stream_p = 0;
  }
  else {
    stream_p = FileInput_Open(file);
  }
  Exception_End();

  if (stream_p) {
     Action_LoadActions(tool_ctx, eval_ctx, parser_list, stream_p, ParseError);
  }
}
.>X
**/

/** CTODOC EVAL
.<S Action_EvalContextP
.=T "C TYPE"
.=H ctokernel "ctoaction.h"
.>S
.pp
This is a pointer to an evaluation context, which contains the parameters provided by the tool.
**/

/** CTODOC COMMAND
.<S Action_CommandP
.=T "C TYPE"
.=H ctokernel "ctoaction.h"
.>S
.pp
This is a pointer to a command.
**/

/** CTODOC BOOL
.<S Action_BooleanExprP
.=T "C TYPE"
.=H ctokernel "ctoaction.h"
.>S
.pp
This is a pointer to a boolean expression.
**/
/** CTODOC PARAM
.<D Action_EvalContextAddParam
.=A eval_ctx Action_EvalContextP
.=A name char*
.=A action Action_ParamProc
.=A free_data Action_ParamFreeProc
.=A data VoidP
.=R void
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function adds a tool parameter to
.=q eval_ctx +
\&. The parameter is called
.=q name
(which must be unique within the evaluation context), its value is obtained by a call to
.=q action +
\&. The
.=q data
value will be passed to
.=q action +
, and will be freed using
.=q free_data +
(or will not be freed if
.=q free_data
is a null pointer).
**/
/** CTODOC PARAM
.<D <Action_ParamProc>
.=A args TListP
.=A data VoidP
.=R VoidP
.=T "C FUNCTION TYPE"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function will be called to obtain the value of a tool parameter. It must be declared to the current evaluation context using
.=q Action_EvalContextAddParam +
\&. 
.pp
.=q args
is a list of arguments of type VoidP. The list may be a null pointer if there are no arguments. The actual argument types and the return value type are implementation dependent.
.=q data
is the data pointer that was passed to
.=q Action_EvalContextAddParam
when the tool parameter was declared.
.pp
The caller will not free the return value, so it must be a pointer to a static value which may be freed the next time this function is called.
**/

/** CTODOC PARAM
.<D <Action_ParamFreeProc>
.=A data VoidP
.=R void
.=T "C FUNCTION TYPE"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function frees
.=q data +
\&.
It is associated to
.=q data
by a call to
.=q Action_EvalContextAddParam +
\&. It will be called to free the data if the parameter is removed from
the evaluation context, or if the context is deleted.
**/

/** CTODOC VARIABLE
.<D Action_ToolContextAddVariable
.=A tool_ctx Action_ToolContextP
.=A name char*
.=A action Action_VariableProc
.=A data VoidP
.=R void
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function adds a variable to
.=q tool_ctx +
The variable is called
.=q name
(which must be unique within the tool context), its value is obtained by a call to
.=q action +
\&. The
.=q data
value will be passed to
.=q action +
\&.
**/
/** CTODOC VARIABLE
.<D <Action_VariableProc>
.=A eval_context Action_EvalContextP
.=A params TListP
.=A data VoidP
.=R char*
.=T "C FUNCTION TYPE"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function will be called to obtain the value of a variable. It must be declared to the current tool context using
.=q Action_ToolContextAddVariable +
\&. 
.pp
.=q eval_context
is the evaluation context in which the command may obtain tool parameters,
.=q params
are the user-provided parameters of the variable. It is a list of strings (char*).
.=q data
is the data pointer that was passed to
.=q Action_ToolContextAddVariable
when the variable was declared.
.pp
The caller will not free the return value, so it must be a pointer to a static buffer which may be freed the next time this function is called.
**/

/** CTODOC COMMAND
.<D Action_ToolContextAddCommand
.=A tool_ctx Action_ToolContextP
.=A name char*
.=A parse Action_CommandParseProc
.=A action Action_CommandEvalProc
.=A destroy Action_CommandFreeProc
.=A parse_data VoidP
.=A eval_data VoidP
.=R void
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function adds a command to
.=q tool_ctx +
\&.
The command is called
.=q name
(which must be unique within the tool context). The command is parsed by a call to the
.=q parse
function, and its evaluation is obtained by a call to
.=q action
function.
.=q destroy
Will be called to free the value returned by the
.=q parse
function,
.pp
.=q parse_data
will be the 
.=q data
parameter in calls to the
.=q parse
function, and
.=q eval_data
.=q data
parameter in calls to the
.=q action
function.
**/

/** CTODOC COMMAND
.<D <Action_ErrorProc>
.=A stream CLex_InputP
.=A msg char*
.=R void
.=T "C FUNCTION TYPE"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function will be called when an error occurs while reading an action, a command or a boolean expression.
.=q stream
is the input stream, at the position where the error was detected.
.=q msg
is the token (or a syntactic rule) that was expected at this position.
**/

/** CTODOC COMMAND
.<D <Action_CommandParseProc>
.=A tool_ctx Action_ToolContextP
.=A eval_ctx Action_EvalContextP
.=A error_func Action_ErrorProc
.=A stream CLex_InputP
.=A end_keywords TListP
.=A data VoidP
.=R VoidP
.=T "C FUNCTION TYPE"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function is called to parse a command. 
.=q tool_ctx
and
.=q eval_ctx
are the tool and evaluation contexts,
.=q error_func
must be called if a parse error is detected, 
.=q stream
is the input stream, positioned at the start of the command (ready to read the name of the command).
.pp
.=q end_keywords
is the
.=q end_keywords
parameter that was passed to the
.=q Action_CommandParse
function. If not null, it can be used to detect the end of the command. The actual contents of the list and its meaning are not specified here.
.pp
.=q data
is the
.=q parse_data
pointer that was passed to
.=q Action_ToolContextAddCommand
when the command was declared.
.pp
The function must read the input stream and return a pointer to a data structure containing the command parameters (possibly pre-compiled, but variables should not be expanded yet). The input stream must be positioned at the end of the command.
.pp
If there is a parse error in the command, this function must return a null pointer. Conversely, a null pointer is interpreted by the caller as a parse error.
.pp 
If the command syntax does not define a mandatory end marker, then the end of the command is reached in the following cases:
.<L s
.=.
at the end of the stream,
.=.
by reaching an unexpected closing parenthesis, which means the command is part of a boolean expression (the syntax of the command should be unambiguous in this case),
.=.
on a condition determined by
.=q end_keywords +
\&. For instance, see how it is used in the
.=q Action_CommandStringListParse +
function.
.>L
**/

/** CTODOC COMMAND
.<D <Action_CommandEvalProc>
.=A tool_ctx Action_ToolContextP
.=A eval_context Action_EvalContextP
.=A params VoidP
.=A data VoidP
.=R int
.=T "C FUNCTION TYPE"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function is called to execute a command. 
.=q tool_ctx
and
.=q eval_context
are the tool and evaluation contexts,
.=q params
are the parameters returned by the parsing function (
.=q Action_CommandParseProc +
),
.=q data
is the
.=q eval_data
pointer that was passed to
.=q Action_ToolContextAddCommand
when the command was declared.
.pp
The function must call
.=q Action_ToolContextDecodeVariables
on string parameters to replace variables with their current value, and execute the command.
.pp
The return value must be 0 if the command was executed successfully, another value otherwise.
**/

/** CTODOC COMMAND
.<D <Action_CommandFreeProc>
.=A params VoidP
.=R void
.=T "C FUNCTION TYPE"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function is called to free the
.=q params
value, which was returned by
.=q Action_CommandParseProc +
\&.
**/

/** CTODOC PARSE 
.<S Action_ParserListP
.=T "C TYPE"
.=H ctokernel "ctoaction.h"
.>S
.pp
This is a list of parsers. A tool can use different parser lists to analyze different kinds of action files.
**/
/** CTODOC EVAL
.<D Action_EvalContextCreate
.=R Action_EvalContextP
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function creates an evaluation context.
**/

/** CTODOC PARAM
.<D Action_EvalContextGetParam
.=A eval_ctx Action_EvalContextP
.=A name char*
.=A args TListP
.=R VoidP
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function returns the value of the tool parameter called
.=q name
in the context
.=q eval_ctx +
\&. Parameters can be passed to the implementation function by means of the 
.=q args
parameter, which is passed to the
.=q args
parameter of the implementation function (see type
.=q Action_ParamProc +
).
.pp
The null pointer is returned if the context does not contain the parameter.
**/

/** CTODOC PARAM
.<D Action_EvalContextExistsParam
.=A ctx Action_EvalContextP
.=A name char*
.=R int
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function returns 1 if there is a tool parameter called
.=q name
in the context
.=q eval_ctx +
, 0 otherwise.
**/

/** CTODOC PARAM
.<D Action_EvalContextDeleteParam
.=A ctx Action_EvalContextP
.=A name char*
.=R void
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function removes the tool parameter called
.=q name
from the context
.=q eval_ctx +
, and frees the associated data if it is no used in another evaluation context (which may happen if
.=q Action_EvalContextCopy
is used.
.pp
This function has no effect if the parameter does not exist.
**/

/** CTODOC PREDEF
.<D Action_EvalContextConstantParam
.=A args TListP
.=A data VoidP
.=R VoidP
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This is a function of type
.=q Action_ParamProc +
, which can be used to implement a tool parameter which always returns the
.=q data
value and takes no argument.
**/

/** CTODOC EVAL
.<D Action_EvalContextCopy
.=A ctx Action_EvalContextP
.=R Action_EvalContextP
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function returns a context which is a copy of 
.=q ctx +
\&. Parameters can be added to or removed from both contexts independently. However, if a parameter has associated data, the data is shared between the context and its copies, and it will be freed when all instances of the parameter are deleted.
**/

/** CTODOC EVAL
.<D Action_EvalContextDestroy
.=A ctx Action_EvalContextP
.=R void
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function frees
.=q ctx
and the parameters it contains.
**/

/** CTODOC TOOL
.<D Action_ToolContextCreate
.=R Action_ToolContextP
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function creates a tool context.
**/

/** CTODOC VARIABLE
.<D Action_ToolContextDecodeVariables
.=A tool_ctx Action_ToolContextP
.=A eval_ctx Action_EvalContextP
.=A string char*
.=R char*
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function returns a copy of
.=q string
where variables are replaced by their current value.
.=q tool_ctx
and
.=q eval_ctx
are the tool and evaluation contexts,
The return value is only valid until the next call to
.=q Action_ToolContextDecodeVariables +
s.
**/

/** CTODOC PREDEF
.<D Action_ToolContextConstantValue
.=A eval_ctx Action_EvalContextP
.=A args TListP
.=A data VoidP
.=R char*
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This is a function of type
.=q Action_VariableProc +
, which can be used to implement a variable which always returns the
.=q data
value and takes no argument.
**/

/** CTODOC COMMAND
.<D Action_CommandParse
.=A tool_ctx Action_ToolContextP
.=A eval_ctx Action_EvalContextP
.=A error_func Action_ErrorProc
.=A lex_p CLex_InputP
.=A end_keywords TListP
.=R Action_CommandP
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function parses a command starting at the current position of 
.=q lex_p +
\&.
.=q tool_ctx
and
.=q eval_ctx
are the tool and evaluation contexts.
.=q error_func
will be called if the command parsing function detects a parse error.
.=q end_keywords
is a parameter which, if it is not a null pointer, may be used by the parsing function to detect the end of the command. Typically, it can be a list of keywords that will appear after the command (but are not part of the command). The precise meaning is specified by the parsing function.
**/

/** CTODOC COMMAND
.<D Action_CommandEval
.=A tool_ctx Action_ToolContextP
.=A eval_ctx Action_EvalContextP
.=A command Action_CommandP
.=R int
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
Executes
.=q command
in the
.=q tool_ctx
and
.=q eval_ctx
contexts.
The return value is the value returned by the command evaluation function
(parameter
.=q action
of the
.=q Action_ToolContextAddCommand
function): it is 0 if the command was successful, another value otherwise.
**/

/** CTODOC COMMAND
.<D Action_CommandExecute
.=A tool_ctx Action_ToolContextP
.=A eval_ctx Action_EvalContextP
.=A error_func Action_ErrorProc
.=A string char*
.=R int
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function executes the command described by 
.=q string
It is equivalent to a call to
.=q Action_CommandParse
followed by
.=q Action_CommandEval
and
.=q Action_CommandDestroy +
\&. The return value is the command evaluation return value.
**/

/** CTODOC COMMAND
.<D Action_CommandDestroy
.=A command Action_CommandP
.=R void
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function frees the
.=q command
that was returned by 
.=q Action_CommandParse +
\&. The command parameters are freed as well (by calling the function that was
passed in as the
.=q destroy
argument of the
.=q Action_ToolContextAddCommand
function.
**/

/** CTODOC PREDEF
.<D Action_CommandStringListParse
.=A tool_ctx Action_ToolContextP
.=A eval_ctx Action_EvalContextP
.=A error_func Action_ErrorProc
.=A lex_p CLex_InputP
.=A end_keywords TListP
.=A data VoidP
.=R VoidP
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This is a function of type
.=q  Action_CommandParseProc +
, which can be used to implement the parsing of a command whose parameters are either keywords, double-quoted strings, or INPUT blocks.
.pp
.=q tool_ctx
and
.=q eval_ctx
are the tool and evaluation contexts.
.=q error_func
will be called if the command parser detects a parse error.
.=q lex_p
is the input stream, positioned before the command name,
.pp
If
.=q end_keywords
is not the null pointer, it is a list of keywords which immediately follow a command (but are not part of the command). The command parser detects the end of the command if a sequence of keywords exactly match the
.=q end_keywords
list (i.e. the keywords appear in the same order and same number as in
.=q end_keywords +
).
.pp
A command also ends when reaching the end of the input or when reading an unexpected character (such as ")" outside a double-quoted string or INPUT).
.pp
.=q data
is not used. The actual type of the return value is
.=q TListP +
\&. The command implementation function (
.=q Action_CommandEvalProc +
) will be able to cast its
.=q params
parameter to
.=q TListP +
\&. This list will contain the parameters of the command.
**/

/** CTODOC PREDEF
.<D Action_CommandStringListDestroy
.=A params VoidP
.=R void
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This is a function of type
.=q  Action_CommandFreeProc +
which can be used to free the value returned by
.=q Action_CommandStringListParse +
\&.
**/

/** CTODOC PREDEF
.<D Action_CommandInputParse
.=A error_func Action_ErrorProc
.=A lex_p CLex_InputP
.=R char*
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function can be called in the implementation of an 
.=q Action_CommandParseProc
function to parse an INPUT parameter.
.pp
The syntax of an INPUT parameter is
.<=
<input> ::= "INPUT" <tag> <text> "END" <tag>
.>=
.pp
The
.=q <tag>
is a keyword which is used to detect the end of the input. Its actual value must be chosen so 
.=q <text>
does not contain
.=q "END <tag>"
.pp
The return value is a string (allocated with Mem_Alloc) which contains
.=q <text> +
\&.
**/

/** CTODOC ACTION
.<D Action_LoadActions
.=A tool_ctx Action_ToolContextP
.=A eval_ctx Action_EvalContextP
.=A parser_list Action_ParserListP
.=A stream_p CIO_InputP
.=A error_func Action_ErrorProc
.=R int
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function must be called to parse an action script.
.=q tool_ctx
and
.=q eval_ctx
are the tool and evaluation contexts.
The
.=q parser_list
contains parsers for every type of action that may appear in the script,
.=q stream_p
is the input stream,
.=q error_func
will be called if a parse error occurs while reading the script.
The return value is 1 if there is no error, 0 otherwise. The input stream is closed before returning.
**/

/** CTODOC ACTION
.<D Action_OpenActionStream
.=A stream_p CIO_InputP
.=R CLex_InputP
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function associates a lexical analyzer with
.=q stream_p +
, and returns a pointer to the analyzer.
.pp
The anaylser expects a C-like syntax, where:
.<L s
.=.
keywords contain letters (upper or lower case), digits and underscores, beginning with a letter or an underscore,
.=.
strings are enclosed in double quotes (double quotes within strings must be doubled),
.=.
comments are within
.=e /\&*
and
.=e * +
.=e / +
,
.=.
separators are spaces and =-+/|*(){}[]<>:;,.\\a\\b\\t\\v\\n\\r\\f
.>L
.pp
A parser can temporarily use another lexical analyzer, for instance to parse shell scripts, where some character sequences may be erroneously interpreted as C-style comments. This can be done as follows:

.<L s
.=.
.=q CLex_Attach
to attach a new analyzer,
.=.
.=q CLex_SetSeparators
to set the separators of the new analyzer,
.=.
use the new analyzer,
.=.
.=q CLex_Detach
to remove the new analyzer,
.=.
resume parsing with the default analyzer. Be aware that the buffers of the default analyzer have not been updated while the new analyzer was in use.
.>L
**/

/** CTODOC BOOL
.<D Action_BooleanExprParse
.=A tool_ctx Action_ToolContextP
.=A eval_ctx Action_EvalContextP
.=A error_func Action_ErrorProc
.=A lex_p CLex_InputP
.=R Action_BooleanExprP
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function reads a boolean expression from
.=q lex_p +
\&.
.=q error_func
will be called if a parse error occurs while reading the expression.
.pp
The returned value will be evaluated using
.=q Action_BooleanExprEval
and freed using
.=q Action_BooleanExprDestroy +
\&.
**/

/** CTODOC BOOL
.<D Action_BooleanExprEval
.=A tool_ctx Action_ToolContextP
.=A eval_ctx Action_EvalContextP
.=A expr Action_BooleanExprP
.=R int
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function evaluates
.=q expr
and returns 1 if the expression is true, or 0 if it is false.
.pp
Variables are decoded and commands are evaluated. The evaluation of a command is considered true if it is successful (i.e the command returns 0), and false otherwise.
**/

/** CTODOC BOOL
.<D Action_BooleanExprDestroy
.=A expr Action_BooleanExprP
.=R void
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function frees
.=q expr +
\&.
**/

/** CTODOC PARSE
.<D Action_ParserListCreate
.=R Action_ParserListP
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function creates a parser list. A parser list corresponds to the list of action that can be encountered in an action script.
**/
/** CTODOC PARSE
.<D Action_ParserListAddAction
.=A action_list Action_ParserListP
.=A name char*
.=A parser Action_ParseProc
.=A data VoidP
.=R void
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function adds an action called 
.=q name
to
.=q action_list +
\&. The action will be parsed by calling
.=q parser
when the action name is found in the action script.
.=q data
will be passed to
.=q parser
.pp
Action names should be unique within an action list.
**/

/** CTODOC PARSE
.<D <Action_ParseProc>
.=A tool_ctx Action_ToolContextP
.=A eval_ctx Action_EvalContextP
.=A error_func Action_ErrorProc
.=A stream CLex_InputP
.=A data VoidP
.=A eval int
.=R int
.=T "C FUNCTION TYPE"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function will be called to parse an action. It is declared in an action parser list using
.=q Action_ParserListAddAction +
\&.
.pp
.=q tool_ctx
and
.=q eval_ctx
are the tool and evaluation contexts,
.=q error_func
must be called if a parse error is detected, 
.=q stream
is the input stream, positioned at the start of the action (ready to read the name of the action).
.pp
.=q data
is the data pointer that was passed to
.=q Action_ParserListAddAction
when the action was declared.
.=q eval
is 1 if the action has to be parsed and evaluated, or 0 if must not be evaluated (in which case the function simply checks that the action is parsable).
.pp
The return value is 1 if the parsing is successful, 0 otherwise. The input stream must be positioned at the end of the action before returning successfully.
**/

/** CTODOC PARSE
.<D Action_ParserListAddList
.=A action_list Action_ParserListP
.=A add_list Action_ParserListP
.=R void
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This function adds the actions defined in
.=q add_list
to
.=q action_list +
\&. If
.=q action_list
and
.=q add_list
contain action with identical names, the action from
.=q action_list
is kept.
**/

/** CTODOC PREDEF
.<D Action_ShellCommand
.=A tool_ctx Action_ToolContextP
.=A eval_ctx Action_EvalContextP
.=A params VoidP
.=A data VoidP
.=R int
.=T "C FUNCTION"
.=H ctokernel "ctoaction.h"
.>D
.pp
This is a function of type
.=q  Action_CommandEvalProc +
, which can be used to implement a shell command.
.pp
The actual type of
.=q params
is
.=q TListP
(which allows 
.=q Action_ShellCommand
to be used in conjunction with
.=q Action_CommandStringListParse +
). This list contains a single element, which is the shell script. If the script does not contain newlines, it is evaluated using the
.=q system
C function, otherwise it is written into a file which is evaluated in the same way.
.=q data
is not used.
.pp
Here is the syntax of shell commands:
.<=
<command_name> ::= "SHELL"
<command_body> ::= <string>
                 | <input>
.>=
**/

typedef struct Action_ToolContextStruct* Action_ToolContextP;
typedef struct Action_EvalContextStruct* Action_EvalContextP;
typedef struct Action_CommandStruct* Action_CommandP;
typedef struct Action_BooleanExprStruct* Action_BooleanExprP;

typedef VoidP (* Action_ParamProc)(TListP args, VoidP data);

typedef void (* Action_ParamFreeProc)(VoidP data);

typedef char* (* Action_VariableProc)(Action_EvalContextP eval_context,
				      TListP params, VoidP data);

typedef void (* Action_ErrorProc)(CLex_InputP stream, char* msg);

typedef int (* Action_ParseProc)(Action_ToolContextP tool_ctx,
				 Action_EvalContextP eval_ctx, 
				 Action_ErrorProc error_func, 
				 CLex_InputP stream, VoidP data, int eval);

typedef VoidP (* Action_CommandParseProc)(Action_ToolContextP tool_ctx,
					  Action_EvalContextP eval_ctx, 
					  Action_ErrorProc error_func, 
					  CLex_InputP stream,
					  TListP end_keywords, VoidP data);

typedef int (* Action_CommandEvalProc)(Action_ToolContextP tool_ctx, 
				       Action_EvalContextP eval_context,
				       VoidP params, VoidP data);

typedef void (* Action_CommandFreeProc)(VoidP params);

typedef TListP Action_ParserListP;

IMPORT Action_EvalContextP
Action_EvalContextCreate(void);

IMPORT void
Action_EvalContextAddParam(Action_EvalContextP ctx, char* name, 
			   Action_ParamProc action,
			   Action_ParamFreeProc free_data, VoidP data);

IMPORT VoidP
Action_EvalContextGetParam(Action_EvalContextP ctx, char* name, TListP args);

IMPORT int
Action_EvalContextExistsParam(Action_EvalContextP ctx, char* name);

IMPORT void
Action_EvalContextDeleteParam(Action_EvalContextP ctx, char* name);

IMPORT VoidP
Action_EvalContextConstantParam(TListP args, VoidP data);

IMPORT Action_EvalContextP
Action_EvalContextCopy(Action_EvalContextP ctx);

IMPORT void 
Action_EvalContextDestroy(Action_EvalContextP ctx);

IMPORT Action_ToolContextP
Action_ToolContextCreate(void);

IMPORT void
Action_ToolContextAddVariable(Action_ToolContextP tool_ctx, char* name,
			      Action_VariableProc action, VoidP data);
IMPORT 
char*
Action_ToolContextDecodeVariables(Action_ToolContextP tool_ctx,
				  Action_EvalContextP eval_ctx, char* string);
IMPORT 
char*
Action_ToolContextConstantValue(Action_EvalContextP eval_ctx, TListP args, VoidP data);

IMPORT void
Action_ToolContextAddCommand(Action_ToolContextP tool_ctx, char* name,
			     Action_CommandParseProc parse,
			     Action_CommandEvalProc action,
			     Action_CommandFreeProc destroy,
			     VoidP parse_data, VoidP eval_data);

IMPORT Action_CommandP
Action_CommandParse(Action_ToolContextP tool_ctx,
		    Action_EvalContextP eval_ctx,
		    Action_ErrorProc error_func,
		    CLex_InputP lex_p, TListP end_keywords);

IMPORT int
Action_CommandEval(Action_ToolContextP tool_ctx, Action_EvalContextP eval_ctx,
		   Action_CommandP command);

IMPORT int
Action_CommandExecute(Action_ToolContextP tool_ctx,
		      Action_EvalContextP eval_ctx,
		      Action_ErrorProc error_func,
		      char* string);

IMPORT void
Action_CommandDestroy(Action_CommandP command);
IMPORT 
char*
Action_CommandInputParse(Action_ErrorProc error_func, 
			 CLex_InputP lex_p);

IMPORT VoidP
Action_CommandStringListParse(Action_ToolContextP tool_ctx,
			      Action_EvalContextP eval_ctx,
			      Action_ErrorProc error_func, 
			      CLex_InputP lex_p, TListP end_keywords,
			      VoidP data);

IMPORT void
Action_CommandStringListDestroy(VoidP params);

IMPORT int
Action_LoadActions(Action_ToolContextP tool_ctx, 
		   Action_EvalContextP eval_ctx,
		   Action_ParserListP parser_list, 
		   CIO_InputP stream_p, 
		   Action_ErrorProc error_func);

IMPORT CLex_InputP
Action_OpenActionStream(CIO_InputP stream_p);


IMPORT Action_BooleanExprP
Action_BooleanExprParse(Action_ToolContextP tool_ctx,
			Action_EvalContextP eval_ctx,
 			Action_ErrorProc error_func,
			CLex_InputP lex_p);

IMPORT int
Action_BooleanExprEval(Action_ToolContextP tool_ctx,
		       Action_EvalContextP eval_ctx,
		       Action_BooleanExprP expr);

IMPORT void
Action_BooleanExprDestroy(Action_BooleanExprP expr);

IMPORT Action_ParserListP
Action_ParserListCreate(void);

IMPORT void
Action_ParserListAddAction(Action_ParserListP action_list,
			   char* name, Action_ParseProc parser, VoidP data);

IMPORT void
Action_ParserListAddList(Action_ParserListP action_list,
			 Action_ParserListP add_list);


IMPORT int
Action_ShellCommand(Action_ToolContextP tool_ctx,
		    Action_EvalContextP eval_ctx,
		    VoidP params, VoidP data);

#endif /* CTOACTION_H */
