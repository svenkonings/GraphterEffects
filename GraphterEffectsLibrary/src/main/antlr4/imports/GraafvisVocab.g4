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

COMMA: ',';
SEMICOLON: ';';

NOT: 'not';

MINUS: '-';

UNDERSCORE: '_';

IMPORT_TOKEN: 'consult';
NODE_LABEL_TOKEN: 'node labels';
EDGE_LABEL_TOKEN: 'edge labels';
RENAME_TOKEN: 'as';

STRING: '"' ~'"'* '"';
NUMBER: MINUS? DIGIT+ ('.' DIGIT+)?;

ID: LETTER_LO (LETTER_LO | LETTER_HI | DIGIT | UNDERSCORE)*;
HID: LETTER_HI (LETTER_LO | LETTER_HI | DIGIT | UNDERSCORE)*;

INFIX_ID: '`' ~'`'+ '`';

WS:             [ \t\r\n]+              -> skip;
BLOCKCOMMENT:   '/*' .*? '*/'           -> skip;
LINECOMMENT:    '//' ~[\r\n]*           -> skip;