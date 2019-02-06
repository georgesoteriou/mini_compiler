parser grammar WaccParser;

options {
  tokenVocab=WaccLexer;
}

expr: array_elem
| expr binaryOperPres1 expr
| expr binaryOperPres2 expr
| expr binaryOperPres3 expr
| expr binaryOperPres4 expr
| expr binaryOperPres5 expr
| expr binaryOperPres6 expr
| unaryOper expr
| OPEN_PARENTHESES expr CLOSE_PARENTHESES
| INT_SIGN? INT_LITER
| BOOL_LITER
| CHAR_LITER
| STR_LITER
| PAIR_LITER
| IDENT
;

binaryOperPres1: MULT | DIV | MOD;
binaryOperPres2: PLUS | MINUS;
binaryOperPres3: GT | GTE | LT | LTE;
binaryOperPres4: EQ | NOTEQ;
binaryOperPres5: AND;
binaryOperPres6: OR;
unaryOper: NOT | MINUS | LEN | ORD | CHR ;

assign_lhs: IDENT
| array_elem
| pair_elem
;

assign_rhs: expr
| array_liter
| NEWPAIR OPEN_PARENTHESES expr COMMA expr CLOSE_PARENTHESES
| pair_elem
| CALL IDENT OPEN_PARENTHESES arg_list? CLOSE_PARENTHESES
;

arg_list: expr (COMMA expr)* ;

param: type IDENT ;

param_list: param (COMMA param)* ;

stat: SKIP_S
| type IDENT ASSIGN assign_rhs
| assign_lhs ASSIGN assign_rhs
| READ_S assign_lhs
| FREE_S expr
| RETURN_S expr
| EXIT_S expr
| PRINT_S expr
| PRINTLN_S expr
| IF_S expr THEN_S stat ELSE_S stat FI_S
| WHILE_S expr DO_S stat DONE_S
| BEGIN_S stat END_S
| stat SEMICOL stat
;

//types

base_type: INT_T
| BOOL_T
| CHAR_T
| STRING_T
;

type: base_type
| array_type
| pair_type
;

array_elem: IDENT (OPEN_SQ_BRACKETS expr CLOSE_SQ_BRACKETS)+ ;

array_type: base_type OPEN_SQ_BRACKETS CLOSE_SQ_BRACKETS
| array_type OPEN_SQ_BRACKETS CLOSE_SQ_BRACKETS
| pair_type OPEN_SQ_BRACKETS CLOSE_SQ_BRACKETS
;

array_liter: OPEN_SQ_BRACKETS (expr (COMMA expr)*)? CLOSE_SQ_BRACKETS;

pair_elem_type: base_type
| array_type
| PAIR
;

pair_type: PAIR OPEN_PARENTHESES pair_elem_type COMMA pair_elem_type CLOSE_PARENTHESES ;

pair_elem: FST expr
| SND expr
;

func: type IDENT OPEN_PARENTHESES param_list? CLOSE_PARENTHESES IS_S stat END_S ;

prog: BEGIN_S (func)* stat END_S ;
