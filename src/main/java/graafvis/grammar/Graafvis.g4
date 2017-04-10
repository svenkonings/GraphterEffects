grammar Graafvis;

import GraafvisVocab;

/* A Graafvis script consists out of a list of imports, label predicate generation and a list of clauses */
program: importVis*
         nodeLabelGen?
         edgeLabelGen?
         clause*
         EOF;

// TODO? java predicates import

/** Import another vis file. The .vis is implied. */
importVis: IMPORT_TOKEN STRING EOL;            // TODO

/* Specify which labels should have generated identifiers for predicates and constants */
nodeLabelGen: NODE_LABEL_TOKEN COLON labels+=label (COMMA labels+=label)* EOL;
edgeLabelGen: EDGE_LABEL_TOKEN COLON labels+=label (COMMA labels+=label)* EOL;

/* Define and rename a label */
label: STRING (RENAME_TOKEN ID)?;

/** Implicative clauses */
clause: (antecedent=aTerm ARROW)? consequence=cTerm EOL;

// TODO ()
/** Antecedent */
aTerm: aTerm andOp aTerm                                                                    #andAntecedent
     | aTerm orOp aTerm                                                                     #orAntecedent
     | NOT aTerm                                                                            #notAntecedent
     | predicate=ID (PAR_OPEN aTermSeries? PAR_CLOSE)?                                      #atomAntecedent
     | predicate=ID BRACE_OPEN terms+=aMultiTerm (andOp terms+=aMultiTerm)* BRACE_CLOSE     #multiAndAtomAntecedent
     | predicate=ID BRACE_OPEN terms+=aMultiTerm (orOp terms+=aMultiTerm)* BRACE_CLOSE      #multiOrAtomAntecedent
     | BRACKET_OPEN (aTermSeries (VBAR aTerm)?)? BRACKET_CLOSE                              #listAntecedent // TODO There can be only one | in a list, and no commas after it. @Ron check my update'. [|tail] kan niet toch?
     | variable=HID                                                                         #variableAntecedent
     | wildcard=UNDERSCORE                                                                  #wildcardAntecedent
     | STRING                                                                               #stringAntecedent
     | NUMBER                                                                               #numberAntecedent
     ;

aTermSeries: terms+=aTerm (COMMA terms+=aTerm)*;

aMultiTerm: aTerm
          | PAR_OPEN aTermSeries? PAR_CLOSE
          ;

/** Consequence */
cTerm: cTerm andOp cTerm                                                                    #andConsequence
     | predicate=ID (PAR_OPEN cTermSeries? PAR_CLOSE)?                                      #atomConsequence
     | predicate=ID BRACE_OPEN terms+=cMultiTerm (andOp terms+=cMultiTerm)* BRACE_CLOSE     #multiAtomConsequence   // Check for not predicate
     | BRACKET_OPEN (cTermSeries (VBAR cTermSeries)?)? BRACKET_CLOSE                        #listConsequence
     | variable=HID                                                                         #variableConsequence
     | STRING                                                                               #stringConsequence
     | NUMBER                                                                               #numberConsequence
     ;

cTermSeries: terms+=cTerm (COMMA terms+=cTerm)*;

cMultiTerm: cTerm
          | PAR_OPEN cTermSeries? PAR_CLOSE
          ;

/* Operators */
andOp: COMMA | AND;
orOp: SEMICOLON | OR;
