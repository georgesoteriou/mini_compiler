lexer grammar BasicLexer;

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

//numbers
fragment DIGIT: '0'..'9' ; 

//letters
fragment UPPERCASE: 'A' .. 'Z' ;
fragment LOWERCASE: 'a' .. 'z' ;

INTEGER: DIGIT+ ;

ID: ('_' | UPPERCASE | LOWERCASE) ('_' | UPPERCASE | LOWERCASE | DIGIT)+ ;





