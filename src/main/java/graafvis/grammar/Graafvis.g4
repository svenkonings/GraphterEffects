grammar Graafvis;

import GraafvisVocab;

/* A Graafvis script consists out of a list of imports, label functor generation and a list of clauses */
program: importVis*
         nodeLabelGen?
         edgeLabelGen?
         clause*
         EOF;

/* Import another vis file. The .vis is implied. */
importVis: IMPORT_TOKEN STRING EOL;

/* Specify which labels should have generated identifiers for predicates and constants */
nodeLabelGen: NODE_LABEL_TOKEN COLON labels+=label (COMMA labels+=label)* EOL;
edgeLabelGen: EDGE_LABEL_TOKEN COLON labels+=label (COMMA labels+=label)* EOL;

/* Define and rename a label */
label: STRING (RENAME_TOKEN ID)?;

/* Implicative clauses */
clause: (antecedent=aTermSeries ARROW)? consequence=cTermSeries EOL;

/* Antecedent */
aTerm: aTerm SEMICOLON aTerm                                                                                            #orAntecedent
     | NOT aTerm                                                                                                        #notAntecedent
     | functor (PAR_OPEN aTermSeries? PAR_CLOSE)?                                                                       #compoundAntecedent
     | functor BRACE_OPEN (terms+=aMultiTerm COMMA)* terms+=aMultiTerm BRACE_CLOSE                                      #multiAndCompoundAntecedent
     | functor BRACE_OPEN (terms+=aMultiTerm SEMICOLON)* terms+=aMultiTerm BRACE_CLOSE                                  #multiOrCompoundAntecedent
     | BRACKET_OPEN (aTermSeries (VBAR BRACKET_OPEN aTerm? BRACKET_CLOSE)?)? BRACKET_CLOSE                              #listAntecedent
     | variable=HID                                                                                                     #variableAntecedent
     | wildcard=UNDERSCORE                                                                                              #wildcardAntecedent
     | PAR_OPEN aTermSeries PAR_CLOSE                                                                                   #parAntecedent
     | STRING                                                                                                           #stringAntecedent
     | NUMBER                                                                                                           #numberAntecedent
     ;

aTermSeries: (terms+=aTerm COMMA)* terms+=aTerm;

aMultiTerm: aTerm
          | PAR_OPEN PAR_CLOSE
          ;

/* Consequence */
cTerm: functor (PAR_OPEN cTermSeries? PAR_CLOSE)?                                                                       #compoundConsequence
     | functor BRACE_OPEN (terms+=cMultiTerm COMMA)* terms+=cMultiTerm BRACE_CLOSE                                      #multiCompoundConsequence
     | BRACKET_OPEN (cTermSeries (VBAR BRACKET_OPEN cTerm? BRACKET_CLOSE)?)? BRACKET_CLOSE                              #listConsequence
     | variable=HID                                                                                                     #variableConsequence
     | STRING                                                                                                           #stringConsequence
     | NUMBER                                                                                                           #numberConsequence
     ;

cTermSeries: (terms+=cTerm COMMA)* terms+=cTerm;

cMultiTerm: cTerm
          | PAR_OPEN cTermSeries? PAR_CLOSE
          ;

/* Functors */
functor: ID                                                                                                             #idFunctor
       | INFIX_ID                                                                                                       #infixFunctor
       ;
