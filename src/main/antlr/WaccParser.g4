parser grammar WaccParser;

options {
  tokenVocab=WaccLexer;
}

expr: array_elem                            #arrayElem
| (MINUS | PLUS)? INT_LITER                 #intLit
| unaryOper expr                            #unaryOp
| expr (MULT | DIV | MOD) expr              #binaryOp1
| expr (PLUS | MINUS) expr                  #binaryOp2
| expr (GT | GTE | LT | LTE )expr           #binaryOp3
| expr (EQ | NOTEQ) expr                    #binaryOp4
| expr (AND) expr                           #binaryOp5
| expr (OR) expr                            #binaryOp6
| OPEN_PARENTHESES expr CLOSE_PARENTHESES   #parenth
| BOOL_LITER                                #boolLit
| CHAR_LITER                                #charLit
| STR_LITER                                 #strLit
| PAIR_LITER                                #pairLit
| IDENT                                     #ident
;

unaryOper: NOT | MINUS | LEN | ORD | CHR ;

assign_lhs: IDENT    #lhsIdent
| array_elem         #lhsArray
| pair_elem          #lhsPair
;

assign_rhs: expr                                                #expression
| array_liter                                                   #arrayLit
| NEWPAIR OPEN_PARENTHESES expr COMMA expr CLOSE_PARENTHESES    #newPair
| pair_elem                                                     #pairElem
| CALL IDENT OPEN_PARENTHESES arg_list? CLOSE_PARENTHESES       #callFunc
;

arg_list: expr (COMMA expr)* ;

param: type IDENT ;

param_list: param (COMMA param)* ;

stat_list: stat (SEMICOL stat)* ;

stat: SKIP_S                                        #skip
| type IDENT ASSIGN assign_rhs                      #declare
| assign_lhs ASSIGN assign_rhs                      #assign
| READ_S assign_lhs                                 #read
| FREE_S expr                                       #free
| RETURN_S expr                                     #return
| EXIT_S expr                                       #exit
| PRINT_S expr                                      #print
| PRINTLN_S expr                                    #println
| IF_S expr THEN_S stat_list ELSE_S stat_list FI_S  #if
| WHILE_S expr DO_S stat_list DONE_S                #while
| BEGIN_S stat_list END_S                           #begin
;

//types

base_type: INT_T    #int
| BOOL_T            #bool
| CHAR_T            #char
| STRING_T          #string
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

pair_elem: FST expr      #fst
| SND expr               #snd
;

func: type IDENT OPEN_PARENTHESES param_list? CLOSE_PARENTHESES IS_S stat_list END_S ;

prog: BEGIN_S (func)* stat_list END_S EOF ;
