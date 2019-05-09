#include <stdcto.h>
#undef FAST_H_DLL
#define FAST_H_DLL ENV_C_LIB
#include <fastdll.h>
void C_1_Init();

GLOBALREF VTP_FormalismP C_1_formalism;

IMPORT GLOBALREF VTP_OperatorP C_1_op_meta;
IMPORT GLOBALREF VTP_OperatorP C_1_op_errortree;
IMPORT GLOBALREF VTP_OperatorP C_1_op_edited_text;
IMPORT GLOBALREF VTP_OperatorP C_1_op_comment;
IMPORT GLOBALREF VTP_OperatorP C_1_op_comment_s;
IMPORT GLOBALREF VTP_OperatorP C_1_op_program;
IMPORT GLOBALREF VTP_OperatorP C_1_op_extdefs;
IMPORT GLOBALREF VTP_OperatorP C_1_op_asm;
IMPORT GLOBALREF VTP_OperatorP C_1_op_extern_def;
IMPORT GLOBALREF VTP_OperatorP C_1_op_empty_extdecl;
IMPORT GLOBALREF VTP_OperatorP C_1_op_extdecl;
IMPORT GLOBALREF VTP_OperatorP C_1_op_decl;
IMPORT GLOBALREF VTP_OperatorP C_1_op_initdecls;
IMPORT GLOBALREF VTP_OperatorP C_1_op_dcltr_noinit;
IMPORT GLOBALREF VTP_OperatorP C_1_op_dcltr_affinit;
IMPORT GLOBALREF VTP_OperatorP C_1_op_dcltr_callinit;
IMPORT GLOBALREF VTP_OperatorP C_1_op_initializer_list;
IMPORT GLOBALREF VTP_OperatorP C_1_op_absdcltr;
IMPORT GLOBALREF VTP_OperatorP C_1_op_parenth_dcltr;
IMPORT GLOBALREF VTP_OperatorP C_1_op_func_dcltr;
IMPORT GLOBALREF VTP_OperatorP C_1_op_array_dcltr;
IMPORT GLOBALREF VTP_OperatorP C_1_op_ptr_dcltr;
IMPORT GLOBALREF VTP_OperatorP C_1_op_ref_dcltr;
IMPORT GLOBALREF VTP_OperatorP C_1_op_memptr_dcltr;
IMPORT GLOBALREF VTP_OperatorP C_1_op_struct_decls;
IMPORT GLOBALREF VTP_OperatorP C_1_op_struct_decl;
IMPORT GLOBALREF VTP_OperatorP C_1_op_members;
IMPORT GLOBALREF VTP_OperatorP C_1_op_bit_field;
IMPORT GLOBALREF VTP_OperatorP C_1_op_parmlist;
IMPORT GLOBALREF VTP_OperatorP C_1_op_var_parmlist;
IMPORT GLOBALREF VTP_OperatorP C_1_op_parm_decl;
IMPORT GLOBALREF VTP_OperatorP C_1_op_identifiers;
IMPORT GLOBALREF VTP_OperatorP C_1_op_fndef;
IMPORT GLOBALREF VTP_OperatorP C_1_op_var_kr_arglist;
IMPORT GLOBALREF VTP_OperatorP C_1_op_kr_arglist;
IMPORT GLOBALREF VTP_OperatorP C_1_op_subfndef;
IMPORT GLOBALREF VTP_OperatorP C_1_op_declaration_specifiers;
IMPORT GLOBALREF VTP_OperatorP C_1_op_scope;
IMPORT GLOBALREF VTP_OperatorP C_1_op_global_scope;
IMPORT GLOBALREF VTP_OperatorP C_1_op_typeof;
IMPORT GLOBALREF VTP_OperatorP C_1_op_type_specifiers;
IMPORT GLOBALREF VTP_OperatorP C_1_op_typespec;
IMPORT GLOBALREF VTP_OperatorP C_1_op_storage_class;
IMPORT GLOBALREF VTP_OperatorP C_1_op_type_qualifier;
IMPORT GLOBALREF VTP_OperatorP C_1_op_struct;
IMPORT GLOBALREF VTP_OperatorP C_1_op_union;
IMPORT GLOBALREF VTP_OperatorP C_1_op_enum;
IMPORT GLOBALREF VTP_OperatorP C_1_op_class;
IMPORT GLOBALREF VTP_OperatorP C_1_op_typename_id;
IMPORT GLOBALREF VTP_OperatorP C_1_op_enumlist;
IMPORT GLOBALREF VTP_OperatorP C_1_op_enumerator;
IMPORT GLOBALREF VTP_OperatorP C_1_op_type_id;
IMPORT GLOBALREF VTP_OperatorP C_1_op_type_qualifiers;
IMPORT GLOBALREF VTP_OperatorP C_1_op_parenth_type;
IMPORT GLOBALREF VTP_OperatorP C_1_op_new_gnu_type;
IMPORT GLOBALREF VTP_OperatorP C_1_op_type_list;
IMPORT GLOBALREF VTP_OperatorP C_1_op_class_head;
IMPORT GLOBALREF VTP_OperatorP C_1_op_class_kw;
IMPORT GLOBALREF VTP_OperatorP C_1_op_struct_kw;
IMPORT GLOBALREF VTP_OperatorP C_1_op_union_kw;
IMPORT GLOBALREF VTP_OperatorP C_1_op_base_classes;
IMPORT GLOBALREF VTP_OperatorP C_1_op_base_class;
IMPORT GLOBALREF VTP_OperatorP C_1_op_access_list;
IMPORT GLOBALREF VTP_OperatorP C_1_op_access_specifier;
IMPORT GLOBALREF VTP_OperatorP C_1_op_class_decls;
IMPORT GLOBALREF VTP_OperatorP C_1_op_class_decls_section;
IMPORT GLOBALREF VTP_OperatorP C_1_op_component_decl_list;
IMPORT GLOBALREF VTP_OperatorP C_1_op_member_decl;
IMPORT GLOBALREF VTP_OperatorP C_1_op_exprlist;
IMPORT GLOBALREF VTP_OperatorP C_1_op_expr;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pmap;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pmpp;
IMPORT GLOBALREF VTP_OperatorP C_1_op_plus;
IMPORT GLOBALREF VTP_OperatorP C_1_op_minus;
IMPORT GLOBALREF VTP_OperatorP C_1_op_mul;
IMPORT GLOBALREF VTP_OperatorP C_1_op_div;
IMPORT GLOBALREF VTP_OperatorP C_1_op_rem;
IMPORT GLOBALREF VTP_OperatorP C_1_op_lsh;
IMPORT GLOBALREF VTP_OperatorP C_1_op_rsh;
IMPORT GLOBALREF VTP_OperatorP C_1_op_lt;
IMPORT GLOBALREF VTP_OperatorP C_1_op_gt;
IMPORT GLOBALREF VTP_OperatorP C_1_op_ge;
IMPORT GLOBALREF VTP_OperatorP C_1_op_le;
IMPORT GLOBALREF VTP_OperatorP C_1_op_eq;
IMPORT GLOBALREF VTP_OperatorP C_1_op_neq;
IMPORT GLOBALREF VTP_OperatorP C_1_op_bwand;
IMPORT GLOBALREF VTP_OperatorP C_1_op_bwor;
IMPORT GLOBALREF VTP_OperatorP C_1_op_bwxor;
IMPORT GLOBALREF VTP_OperatorP C_1_op_and;
IMPORT GLOBALREF VTP_OperatorP C_1_op_or;
IMPORT GLOBALREF VTP_OperatorP C_1_op_cond;
IMPORT GLOBALREF VTP_OperatorP C_1_op_ass;
IMPORT GLOBALREF VTP_OperatorP C_1_op_plus_ass;
IMPORT GLOBALREF VTP_OperatorP C_1_op_minus_ass;
IMPORT GLOBALREF VTP_OperatorP C_1_op_mul_ass;
IMPORT GLOBALREF VTP_OperatorP C_1_op_div_ass;
IMPORT GLOBALREF VTP_OperatorP C_1_op_rem_ass;
IMPORT GLOBALREF VTP_OperatorP C_1_op_bwand_ass;
IMPORT GLOBALREF VTP_OperatorP C_1_op_bwxor_ass;
IMPORT GLOBALREF VTP_OperatorP C_1_op_bwor_ass;
IMPORT GLOBALREF VTP_OperatorP C_1_op_lsh_ass;
IMPORT GLOBALREF VTP_OperatorP C_1_op_rsh_ass;
IMPORT GLOBALREF VTP_OperatorP C_1_op_throw;
IMPORT GLOBALREF VTP_OperatorP C_1_op_cast;
IMPORT GLOBALREF VTP_OperatorP C_1_op_deref;
IMPORT GLOBALREF VTP_OperatorP C_1_op_addr;
IMPORT GLOBALREF VTP_OperatorP C_1_op_uminus;
IMPORT GLOBALREF VTP_OperatorP C_1_op_uplus;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pre_incr;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pre_decr;
IMPORT GLOBALREF VTP_OperatorP C_1_op_bwnot;
IMPORT GLOBALREF VTP_OperatorP C_1_op_not;
IMPORT GLOBALREF VTP_OperatorP C_1_op_sizeof;
IMPORT GLOBALREF VTP_OperatorP C_1_op_new;
IMPORT GLOBALREF VTP_OperatorP C_1_op_global_scope_new;
IMPORT GLOBALREF VTP_OperatorP C_1_op_delete;
IMPORT GLOBALREF VTP_OperatorP C_1_op_global_scope_delete;
IMPORT GLOBALREF VTP_OperatorP C_1_op_array_delete;
IMPORT GLOBALREF VTP_OperatorP C_1_op_global_scope_array_delete;
IMPORT GLOBALREF VTP_OperatorP C_1_op_integer;
IMPORT GLOBALREF VTP_OperatorP C_1_op_float;
IMPORT GLOBALREF VTP_OperatorP C_1_op_character;
IMPORT GLOBALREF VTP_OperatorP C_1_op_strings;
IMPORT GLOBALREF VTP_OperatorP C_1_op_string;
IMPORT GLOBALREF VTP_OperatorP C_1_op_true;
IMPORT GLOBALREF VTP_OperatorP C_1_op_false;
IMPORT GLOBALREF VTP_OperatorP C_1_op_this;
IMPORT GLOBALREF VTP_OperatorP C_1_op_parenth_expr;
IMPORT GLOBALREF VTP_OperatorP C_1_op_call;
IMPORT GLOBALREF VTP_OperatorP C_1_op_index;
IMPORT GLOBALREF VTP_OperatorP C_1_op_dot;
IMPORT GLOBALREF VTP_OperatorP C_1_op_arrow;
IMPORT GLOBALREF VTP_OperatorP C_1_op_post_incr;
IMPORT GLOBALREF VTP_OperatorP C_1_op_post_decr;
IMPORT GLOBALREF VTP_OperatorP C_1_op_functional_cast;
IMPORT GLOBALREF VTP_OperatorP C_1_op_dynamic_cast;
IMPORT GLOBALREF VTP_OperatorP C_1_op_static_cast;
IMPORT GLOBALREF VTP_OperatorP C_1_op_reinterpret_cast;
IMPORT GLOBALREF VTP_OperatorP C_1_op_const_cast;
IMPORT GLOBALREF VTP_OperatorP C_1_op_typeid;
IMPORT GLOBALREF VTP_OperatorP C_1_op_stmts;
IMPORT GLOBALREF VTP_OperatorP C_1_op_compound;
IMPORT GLOBALREF VTP_OperatorP C_1_op_block;
IMPORT GLOBALREF VTP_OperatorP C_1_op_loop;
IMPORT GLOBALREF VTP_OperatorP C_1_op_expr_stmt;
IMPORT GLOBALREF VTP_OperatorP C_1_op_if;
IMPORT GLOBALREF VTP_OperatorP C_1_op_while;
IMPORT GLOBALREF VTP_OperatorP C_1_op_do;
IMPORT GLOBALREF VTP_OperatorP C_1_op_for;
IMPORT GLOBALREF VTP_OperatorP C_1_op_switch;
IMPORT GLOBALREF VTP_OperatorP C_1_op_break;
IMPORT GLOBALREF VTP_OperatorP C_1_op_continue;
IMPORT GLOBALREF VTP_OperatorP C_1_op_return;
IMPORT GLOBALREF VTP_OperatorP C_1_op_goto;
IMPORT GLOBALREF VTP_OperatorP C_1_op_empty_stmt;
IMPORT GLOBALREF VTP_OperatorP C_1_op_case;
IMPORT GLOBALREF VTP_OperatorP C_1_op_default;
IMPORT GLOBALREF VTP_OperatorP C_1_op_label;
IMPORT GLOBALREF VTP_OperatorP C_1_op_destructor;
IMPORT GLOBALREF VTP_OperatorP C_1_op_operator;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_mult;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_div;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_mod;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_plus;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_minus;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_bwand;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_bwor;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_bwxor;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_bwnot;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_comma;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_eq;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_less;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_greater;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_leq;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_geq;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_neq;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_ass_plus;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_ass_minus;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_ass_mult;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_ass_div;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_ass_mod;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_ass_bwxor;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_ass_bwand;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_ass_bwor;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_ass_lshift;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_ass_rshift;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_ass;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_lshift;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_rshift;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_incr;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_decr;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_and;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_or;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_not;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_cond;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_arrow;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_pmap;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_parenth;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_croch;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_new;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_new_array;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_delete;
IMPORT GLOBALREF VTP_OperatorP C_1_op_op_delete_array;
IMPORT GLOBALREF VTP_OperatorP C_1_op_ctor_initializer;
IMPORT GLOBALREF VTP_OperatorP C_1_op_member_init_list;
IMPORT GLOBALREF VTP_OperatorP C_1_op_member_init;
IMPORT GLOBALREF VTP_OperatorP C_1_op_template_def;
IMPORT GLOBALREF VTP_OperatorP C_1_op_export;
IMPORT GLOBALREF VTP_OperatorP C_1_op_template_parms;
IMPORT GLOBALREF VTP_OperatorP C_1_op_type_parameter;
IMPORT GLOBALREF VTP_OperatorP C_1_op_template_parameter;
IMPORT GLOBALREF VTP_OperatorP C_1_op_template_name;
IMPORT GLOBALREF VTP_OperatorP C_1_op_template_arg_list;
IMPORT GLOBALREF VTP_OperatorP C_1_op_template_id;
IMPORT GLOBALREF VTP_OperatorP C_1_op_explicit_instanciation;
IMPORT GLOBALREF VTP_OperatorP C_1_op_explicit_specialization;
IMPORT GLOBALREF VTP_OperatorP C_1_op_namespace_def;
IMPORT GLOBALREF VTP_OperatorP C_1_op_using_declaration;
IMPORT GLOBALREF VTP_OperatorP C_1_op_using_directive;
IMPORT GLOBALREF VTP_OperatorP C_1_op_exception_spec;
IMPORT GLOBALREF VTP_OperatorP C_1_op_try_block;
IMPORT GLOBALREF VTP_OperatorP C_1_op_try_handlers;
IMPORT GLOBALREF VTP_OperatorP C_1_op_handler;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_define;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_undef;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_macro_args;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_conditional;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_elif_parts;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_elif_part;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_else_part;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_if;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_ifdef;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_ifndef;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_elif;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_else;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_endif;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_line;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_error;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_pragma;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_none;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_include;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_external_file;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_local_file;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_text;
IMPORT GLOBALREF VTP_OperatorP C_1_op_pp_text_line;
IMPORT GLOBALREF VTP_OperatorP C_1_op_macro_call;
IMPORT GLOBALREF VTP_OperatorP C_1_op_sql_stmt;
IMPORT GLOBALREF VTP_OperatorP C_1_op_sql_lines;
IMPORT GLOBALREF VTP_OperatorP C_1_op_sql_line;
IMPORT GLOBALREF VTP_OperatorP C_1_op_identifier;
IMPORT GLOBALREF VTP_OperatorP C_1_op_none;
IMPORT GLOBALREF VTP_OperatorP C_1_op_qualified_id;

IMPORT GLOBALREF VTP_PhylumP C_1_ph_COMMENT;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_EVERY;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_EXTDEFS;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_EXTDEF;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_EXTERN_DEFS;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_STRING;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_INITDECLS_OPT;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_INITDCL;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_INITIALIZER;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_DECLARATOR;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_ABSDCLTR;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_ANY_DECLARATOR;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_STRUCT_DECLS_OPT;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_STRUCT_DECL;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_MEMBERS;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_MEMBER;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_DECLARATOR_OPT;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_FUNC_PARAMETERS;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_PARMLIST;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_FIXED_PARMLIST;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_PARM_DECL;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_PARAM_DCLTR;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_KR_DECLS;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_KR_DECL;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_FIXED_KR_DECLS;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_FNBODY;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_COMPOUND;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_DECLARATION_SPECIFIERS;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_DECLARATION_SPECIFIER;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_TYPE_SPECIFIER;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_QUALIFIED_TYPE_OPT;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_TYPE_NAME;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_STRUCT_SPECIFIER;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_NESTED_NAME_SPECIFIER;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_TYPEOF_ARG;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_TYPE_SPECIFIERS;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_IDENTIFIER_OPT;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_SIMPLE_EXPR_OPT;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_ENUMLIST_OPT;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_ENUMERATOR;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_TYPENAME;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_TYPE_QUALIFIERS;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_TYPE_QUALIFIER;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_CLASS_HEAD;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_AGGR;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_BASE_CLASS_OPT;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_BASE_CLASS;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_ACCESS_LIST;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_BASE_SPECIFIER;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_ACCESS_SPECIFIER_OPT;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_ACCESS_SPECIFIER;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_CLASS_DECLS_OPT;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_CLASS_DECLS;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_CLASS_DECL_SECTION;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_COMPONENT_DECLS;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_COMPONENT_DECL;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_EXPRLIST;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_EXPR;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_SIMPLE_EXPR;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_CAST_EXPR;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_UNARY_EXPR;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_SIZEOF_ARG;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_NEW_TYPE_ID;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_PRIMARY;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_STRING1;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_STMTS;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_STMT;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_OPEN_BLOCK;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_CLOSE_BLOCK;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_STMTS_OPT;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_OPERATOR;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_OPER;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_MEMBER_INIT_LIST;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_MEMBER_INIT;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_MEMBER_NAME;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_EXPORT_OPT;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_TEMPLATE_HEADER;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_TEMPLATE_PARM;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_TYPE_PARM_DEF;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_TEMPLATE_PARM_DEF;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_TEMPLATE_ARG_LIST;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_TEMPLATE_ARG;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_TYPE_PARM_INIT;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_NAMESPACE_DEF;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_EXCEPTION_SPECIFICATION;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_RAISE_IDENTIFIERS;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_TRY_STMTS;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_HANDLER_SEQ;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_TRY_HANDLER;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_PREPROCESSOR;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_PP_MACRO_NAME;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_PP_MACRO_ARGS;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_PP_IF;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_PP_ELIF_PARTS;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_PP_ELIF_PART;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_PP_ELIF;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_PP_ELSE_PART;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_PP_ELSE;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_PP_ENDIF;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_PP_IFDEF_IDENT;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_PP_FILENAME;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_PP_TEXT;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_PP_TEXT_LINE;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_SQL_STMT;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_SQL_LINES;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_SQL_LINE;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_SQL_TYPE;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_EXPR_OPT;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_IDENTIFIER;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_QUALIFIED_ID;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_UNQUALIFIED_ID;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_VOID;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_NOEDIT;
IMPORT GLOBALREF VTP_PhylumP C_1_ph_TOPOP;

IMPORT GLOBALREF VTP_FrameP C_1_fr_prefix;
IMPORT GLOBALREF VTP_FrameP C_1_fr_postfix;
IMPORT GLOBALREF VTP_FrameP C_1_fr_focus;

