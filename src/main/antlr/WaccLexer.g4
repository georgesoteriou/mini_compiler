lexer grammar WaccLexer;


INCLUDE : 'include';

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
LEN: 'len' ;
ORD: 'ord' ;
CHR: 'chr' ;

// side-effecting operators
PLUS_EQ: '+=';
MINUS_EQ: '-=';
MULT_EQ: '*=';
MOD_EQ: '%=';
DIV_EQ: '/=';
AND_EQ: '&&=';
OR_EQ: '||=';

//brackets
OPEN_PARENTHESES: '(' ;
CLOSE_PARENTHESES: ')' ;
OPEN_SQ_BRACKETS: '[' ;
CLOSE_SQ_BRACKETS: ']' ;


//numbers and chars
fragment DIGIT: '0'..'9' ;
fragment ESC_CHAR: '0' | 'b' | 't' | 'n' | 'f' | 'r' | '\'' | '\\' | '"' ;
fragment CHAR: ~( '\\' | '\'' |  '"') | ('\\' ESC_CHAR);

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
FOR_S: 'for';
DO_S: 'do' ;
DONE_S: 'done' ;
WHEN_S: 'when' ;

//base-returnType
INT_T: 'int' ;
BOOL_T: 'bool' ;
CHAR_T: 'char' ;
STRING_T: 'string' ;

//various symbols
SEMICOL: ';' ;
COL: ':' ;
COMMA: ',' ;
ASSIGN: '=' ;
FST: 'fst' ;
SND: 'snd' ;
PAIR: 'pair' ;
NEWPAIR: 'newpair' ;
CALL: 'call' ;


//literals, -2^31 <= int < 2^31
INT_LITER: DIGIT+ {
  Long n = Long.parseLong(getText());
  if (!(n <= ((long) Integer.MAX_VALUE) + 1)) {
    throw new ParseCancellationException("Integer overflow while parsing");
  }
};
BOOL_LITER: 'true' | 'false' ;
CHAR_LITER: '\'' CHAR '\'' ;
STR_LITER: '"' CHAR* '"' ;
PAIR_LITER: 'null' ;

IDENT: ('_' |[a-zA-Z]) ('_' | [a-zA-Z] | DIGIT)* ;
FILENAME: [a-zA-Z0-9:\\_/.]+('.wacc') ;