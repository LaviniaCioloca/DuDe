#include <stdcto.h>
#undef FAST_H_DLL
#define FAST_H_DLL CQL_LIB
#include <fastdll.h>
void CQL_1_Init();

GLOBALREF VTP_FormalismP CQL_1_formalism;

IMPORT GLOBALREF VTP_OperatorP CQL_1_op_meta;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_errortree;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_edited_text;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_comment;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_comment_s;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_cqlmodule;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_cqllibrary;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_module_dcl_list;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_import_stmt;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_program;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_parameters;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_function;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_record;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_member_list;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_library_dcl_list;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_block;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_instructions;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_assign;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_cursor;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_declarations;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_dcl;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_persistent;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_create;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_names;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_create_index;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_create_unique_index;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_insert;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_select_into;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_ifstmt;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_elsifs;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_elsif;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_while;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_for;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_select;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_where;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_group;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_order;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_star;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_distinct;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_from;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_table_name;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_args;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_exprs;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_list;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_funcall;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_plus;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_concat;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_minus;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_mul;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_pow;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_div;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_u_minus;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_eq;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_ne;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_gt;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_lt;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_ge;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_le;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_isundef;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_isnotundef;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_in;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_notin;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_subselect;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_and;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_or;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_not;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_index;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_at;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_outer;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_attrvar;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_string;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_number;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_integer;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_name;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_void;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_type;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_true;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_false;
IMPORT GLOBALREF VTP_OperatorP CQL_1_op_undef;

IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_COMMENT;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_EVERY;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_MODULE_DECLARATIONS;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_MODULE_DECLARATION;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_PARAMS;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_PROGRAM;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_FUNCTION;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_RECORD;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_MEMBER_LIST;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_MEMBER_DECLARATION;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_LIBRARY;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_LIBRARY_DECLARATION;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_LIB_DCL;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_BLOCK;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_INSTRUCTIONS;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_INSTRUCTION;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_ASSIGN;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_DECLARATIONS;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_VAR_INIT;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_DCL;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_STORAGE;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_INIT;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_COLUMNS;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_INSERT_EXPR;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_INSERT;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_NAMES;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_SELECT;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_ELSIFS;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_ELSIF;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_DISTINCT;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_SELECTION;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_FROM;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_TABLE_NAME;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_ALIAS;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_WHERE;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_GROUP;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_ORDER;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_ARGS;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_EXPRS;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_EXPR;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_ATTRVAR;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_AT;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_QUALIFIER;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_OUTER;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_TYPE;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_NAME;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_STR;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_NUMBER;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_INTEGER;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_CONSTANT;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_TOPOP;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_VOID;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_NOSELECT;
IMPORT GLOBALREF VTP_PhylumP CQL_1_ph_NOEDIT;

IMPORT GLOBALREF VTP_FrameP CQL_1_fr_prefix;
IMPORT GLOBALREF VTP_FrameP CQL_1_fr_postfix;
IMPORT GLOBALREF VTP_FrameP CQL_1_fr_focus;
