grammar Graafvis;

import GraafvisVocab;

/* A Graafvis script consists out of a list of imports, label predicate generation and a list of clauses */
program: importVis*
         nodeLabelGen?
         edgeLabelGen?
         clause*
         EOF;

/** Import another vis file. The .vis is implied. */
importVis: IMPORT_TOKEN STRING EOL;            // TODO? java predicates import

/* Specify which labels should have generated identifiers for predicates and constants */
nodeLabelGen: NODE_LABEL_TOKEN COLON label (COMMA label)* EOL;
edgeLabelGen: EDGE_LABEL_TOKEN COLON label (COMMA label)* EOL;

/* Define and rename a label */
label: STRING (RENAME_TOKEN ID)?;

/* Implicative clauses */
clause: (antecedent ARROW)? consequence EOL;

antecedent: propositionalFormula;

propositionalFormula: NOT propositionalFormula                           # pfNot
                    | propositionalFormula andOp propositionalFormula    # pfAnd
                    | propositionalFormula orOp propositionalFormula     # pfOr
                    | PAR_OPEN propositionalFormula PAR_CLOSE            # pfNest
                    | literal                                            # pfLit
                    ;

consequence: literal (andOp literal)*;

/* Literals are atomic formulas or boolean expressions*/
literal: atom                                   #atomLiteral
       | multiAtom                              #multiAtomLiteral
       ;

/* Atoms are predicates applied to a tuple of terms */
atom: predicate termTuple;

/* Language feature to apply one predicate to multiple tuples of terms */
multiAtom: predicate BRACE_OPEN (term | termTuple) (COMMA (term | termTuple))* BRACE_CLOSE;

/* A tuple of terms */
termTuple: PAR_OPEN (term (COMMA term)*)? PAR_CLOSE;

/* Predicates start with lowercase letter */
predicate: ID;

/* Terms are either ground terms, free variables, underscores or a list of more terms */
term: variable                                                                                  # termVar
    | atom                                                                                      # termAtom
    | UNDERSCORE                                                                                # termWildcard
    | STRING                                                                                    # termString
    | NUMBER                                                                                    # termNumber
    | ID                                                                                        # termID
    | BRACKET_OPEN (term (COMMA term)* (VBAR term (COMMA term)*)?)? BRACKET_CLOSE               # termList
    ;

/* Variables start with uppercase letter */
variable: HID;

/* Operators */
andOp: COMMA | AND;
orOp: SEMICOLON | OR;
