grammar Graafvis;

import GraafvisVocab;

/* A Graafvis script consists out of a list of imports, label predicate generation and a list of clauses */
program: import_vis*
         node_label_gen?
         edge_label_gen?
         clause*
         EOF;

/** Import another vis file. The .vis is implied. */
import_vis: IMPORT_TOKEN STRING EOL;

// TO DO check if this generates only predicates or not
/* Specify which labels should have generated identifiers for predicates and constants */
node_label_gen: NODE_LABEL_TOKEN COLON label (COMMA label)* EOL;
edge_label_gen: EDGE_LABEL_TOKEN COLON label (COMMA label)* EOL;

/* Define and rename a label */
// TO DO in tree walker kijken of de "as identifier" nodig was vanwege complexe string
label: STRING (RENAME_TOKEN predicate)?;

/* Implicative clauses */
clause: (antecedent ARROW)? consequence EOL;

antecedent: propositional_formula;

propositional_formula: NOT propositional_formula
                     | propositional_formula bool_op propositional_formula
                     | PAR_OPEN propositional_formula PAR_CLOSE
                     | literal
                     ;

consequence: literal (COMMA literal)*;

/* Literals are atomic formulas or boolean expressions*/
literal: atom                   #atomLiteral
       | multi_atom             #multiAtomLiteral
       | bool_expr              #boolLiteral
       ;

/* Atoms are predicates applied to a tuple of terms */
atom: predicate PAR_OPEN (term (COMMA term)*)? PAR_CLOSE;

/* Language feature to apply one predicate to multiple tuples of terms */
multi_atom: predicate BRACE_OPEN (term (COMMA term)*)? BRACE_CLOSE;

/* Predicates start with lowercase letter */
predicate: NAME_LO;

/* Terms are either ground terms, free variables, underscores or a tuple of more terms */
term: ground_term
    | variable
    | UNDERSCORE
    | PAR_OPEN term (COMMA term)* PAR_CLOSE
    ;

/* Ground terms contain no free variables */
ground_term: STRING
           | NUMBER
           | NAME_LO
           ;

/* Variables start with uppercase letter */
variable: NAME_HI;

bool_expr: NOT bool_expr
         | num_expr eq_op num_expr
         | bool_expr bool_op bool_expr
         | PAR_OPEN bool_expr PAR_CLOSE
         | TRUE
         | FALSE
         ;

num_expr: num_expr pow_op num_expr
        | num_expr mult_op num_expr
        | num_expr plus_op num_expr
        | PAR_OPEN num_expr PAR_CLOSE
        | NUMBER
        ;

bool_op: OR | AND | COMMA;
eq_op: EQ | NQ | GT | LT | GE | LE;

pow_op: POW;
mult_op: MULT | DIV | MOD;
plus_op: PLUS | MINUS;





