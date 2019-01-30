lexer grammar BasicLexer;

//skippable
WHITESPACE: (' ' | '\n' | '\t')+ -> skip ;
COMMENT: '#' (~('\n'))* '\n' -> skip ;

//binary operators
PLUS: '+' ;
MINUS: '-' ;
MULT: '*' ;
DIV: '/' ;
MOD: '%' ;
GT: '>' ;
GTE: '>=' ;
LT: '<' ;
LTE: '<=' ;
EQ: '==' ;
NOTEQ: '!=' ;
AND: '&&' ;
OR: '||' ;


// unary operators:
NOT: '!' ;
NEG: '-' ;
LEN: 'len' ;
ORD: 'ord' ;
CHR: 'chr' ;

//brackets
OPEN_PARENTHESES: '(' ;
CLOSE_PARENTHESES: ')' ;
OPEN_SQ_BRACKETS: '[' ;
CLOSE_SQ_BRACKETS: ']' ;

//numbers
fragment DIGIT: '0'..'9' ;
INT_SIGN: '+' | '-' ;

//letters
fragment UPPERCASE: 'A' .. 'Z' ;
fragment LOWERCASE: 'a' .. 'z' ;
CHARACTER: ~('\b' | '\t' | '\n' | 'f' | '\r' | '"' | '\'' | '\\') ;

//statements
BEGIN_S: 'begin' ;
END_S: 'end' ;
IS_S: 'is' ;
SKIP_S: 'skip' ;
FREE_S: 'free' ;
READ_S: 'read' ;
RETURN_S: 'return' ;
EXIT_S: 'exit' ;
PRINT_S: 'print' ;
PRINTLN_S: 'println' ;
IF_S: 'if' ;
THEN_S: 'then' ;
ELSE_S: 'else' ;
FI_S: 'fi' ;
WHILE_S: 'while' ;
DO_S: 'do' ;
DONE_S: 'done' ;

//base-type
INT_T: 'int' ;
BOOL_T: 'bool' ;
CHAR_T: 'char' ;
STRING_T: 'string' ;

//various symbols
SEMICOL: ';' ;
COMMA: ',' ;
ASSIGN: '=' ;
FST: 'fst' ;
SND: 'snd' ;
PAIR: 'pair' ;
NEWPAIR: 'newpair' ;
CALL: 'call' ;


//literals
INT_LITER: INT_SIGN? DIGIT+ ;
BOOL_LITER: 'true' | 'false' ;
CHAR_LITER: '\'' CHARACTER '\'' ;
STR_LITER: '"' (CHARACTER)* '"' ;
PAIR_LITER: 'null' ;

IDENT: ('_' | UPPERCASE | LOWERCASE) ('_' | UPPERCASE | LOWERCASE | DIGIT)* ;





