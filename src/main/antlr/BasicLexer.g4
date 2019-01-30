lexer grammar BasicLexer;

//binary operators
PLUS: '+' ;
MINUS: '-' ;
MULT: '*';
DIV: '/';
MOD: '%';
GT: '>';
GTE: '>=';
LT: '<';
LTE: '<=';
EQ: '==';
NOTEQ: '!=';
AND: '&&';
OR: '||'; 

//brackets
OPEN_PARENTHESES: '(' ;
CLOSE_PARENTHESES: ')' ;

//numbers
fragment DIGIT: '0'..'9' ; 

INTEGER: DIGIT+ ;





