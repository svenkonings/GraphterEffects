lexer grammar GraafvisVocab;

fragment LETTER_LO: [a-z];
fragment LETTER_HI: [A-Z];
fragment DIGIT: [0-9];

ARROW: '->';
COLON: ':';

EOL: '.';

PAR_OPEN: '(';
PAR_CLOSE: ')';
BRACE_OPEN: '{';
BRACE_CLOSE: '}';
BRACKET_OPEN: '[';
BRACKET_CLOSE: ']';

VBAR: '|';

/* Eq operators */
EQ: '==';
NQ: '!=';
GT: '>';
LT: '<';
GE: '>=';
LE: '<=';

/* Booloperators */

COMMA: ',';
SEMICOLON: ';';
OR: 'or';
AND: 'and';

NOT: 'not';

/* Num operators */

PLUS: '+';
MINUS: '-';
MULT: '*';
DIV: '/';
POW: '^';
MOD: '%';

UNDERSCORE: '_';

TRUE: 'true';
FALSE: 'false';

IMPORT_TOKEN: 'consult';
NODE_LABEL_TOKEN: 'node labels';
EDGE_LABEL_TOKEN: 'edge labels';
RENAME_TOKEN: 'as';

STRING: '"' (~'"')* '"';
NUMBER: DIGIT+ | MINUS NUMBER;

ID: LETTER_LO (LETTER_LO | LETTER_HI | DIGIT | UNDERSCORE)*;
HID: LETTER_HI (LETTER_LO | LETTER_HI | DIGIT | UNDERSCORE)*;

WS:             [ \t\r\n]+              -> skip;
BLOCKCOMMENT:   '/*' .*? '*/'           -> skip;
LINECOMMENT:    '//' ~[\r\n]*           -> skip; // Double // otherwise only a /* is also deemed as a correct comment even though that is an unfinished block comment