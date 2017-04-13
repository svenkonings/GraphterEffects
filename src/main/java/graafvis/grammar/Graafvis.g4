grammar Graafvis;

import GraafvisVocab;

/* A Graafvis script consists out of a list of imports, label predicate generation and a list of clauses */
program: importVis*
         nodeLabelGen?
         edgeLabelGen?
         clause*
         EOF;

// TODO? java predicates import

/* Import another vis file. The .vis is implied. */
importVis: IMPORT_TOKEN STRING EOL;

/* Specify which labels should have generated identifiers for predicates and constants */
nodeLabelGen: NODE_LABEL_TOKEN COLON labels+=label (COMMA labels+=label)* EOL;
edgeLabelGen: EDGE_LABEL_TOKEN COLON labels+=label (COMMA labels+=label)* EOL;

/* Define and rename a label */
label: STRING (RENAME_TOKEN ID)?;

/* Implicative clauses */
clause: (antecedent=aTerm ARROW)? consequence=cTerm EOL;

/* Antecedent */
aTerm: aTerm andOp aTerm                                                                                                #andAntecedent
     | aTerm orOp aTerm                                                                                                 #orAntecedent
     | NOT aTerm                                                                                                        #notAntecedent
     | predicate (PAR_OPEN aTermSeries? PAR_CLOSE)?                                                                     #atomAntecedent
     | predicate BRACE_OPEN terms+=aMultiTerm (andOp terms+=aMultiTerm)* BRACE_CLOSE                                    #multiAndAtomAntecedent
     | predicate BRACE_OPEN terms+=aMultiTerm (orOp terms+=aMultiTerm)* BRACE_CLOSE                                     #multiOrAtomAntecedent
     | BRACKET_OPEN (aTermSeries (VBAR BRACKET_OPEN aTerm? BRACKET_CLOSE)?)? BRACKET_CLOSE                              #listAntecedent
     | variable=HID                                                                                                     #variableAntecedent
     | wildcard=UNDERSCORE                                                                                              #wildcardAntecedent
     | PAR_OPEN aTerm PAR_CLOSE                                                                                         #parAntecedent
     | STRING                                                                                                           #stringAntecedent
     | NUMBER                                                                                                           #numberAntecedent
     ;

aTermSeries: terms+=aTerm (COMMA terms+=aTerm)*;

aMultiTerm: aTerm
          | PAR_OPEN aTermSeries? PAR_CLOSE
          ;

// TODO wordt bij listregel wel echt gebruik gemaakt van de COMMA in cTermSeries? Of wordt daar andConseq gebruikt?
// TODO par moet weg
/* Consequence */
cTerm: cTerm andOp cTerm                                                                                                #andConsequence
     | predicate (PAR_OPEN cTermSeries? PAR_CLOSE)?                                                                     #atomConsequence
     | predicate BRACE_OPEN terms+=cMultiTerm (andOp terms+=cMultiTerm)* BRACE_CLOSE                                    #multiAtomConsequence
     | BRACKET_OPEN (cTermSeries (VBAR BRACKET_OPEN cTerm? BRACKET_CLOSE)?)? BRACKET_CLOSE                              #listConsequence
     | variable=HID                                                                                                     #variableConsequence
     | PAR_OPEN cTerm PAR_CLOSE                                                                                         #parConsequence
     | STRING                                                                                                           #stringConsequence
     | NUMBER                                                                                                           #numberConsequence
     ;

cTermSeries: terms+=cTerm (COMMA terms+=cTerm)*;

cMultiTerm: cTerm
          | PAR_OPEN cTermSeries? PAR_CLOSE
          ;

/* Predicates */
predicate: ID                                                                                                           #idPredicate
         | INFIX (~INFIX)+ INFIX                                                                                        #infixPredicate
         ;

/* Operators */
andOp: COMMA | AND;
orOp: SEMICOLON | OR;
