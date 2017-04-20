grammar TestGrammar;

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
clause: (antecedent=aTermExpr ARROW)? consequence=cTermSeries EOL;

/* Antecedent */
aTerm: NOT aTerm                                                                                                        #notAntecedent
     | functor (PAR_OPEN arguments=aTermSeries? PAR_CLOSE)?                                                             #compoundAntecedent
     | functor BRACE_OPEN (args+=aMultiArg COMMA)* args+=aMultiArg BRACE_CLOSE                                          #multiAndCompoundAntecedent
     | functor BRACE_OPEN (args+=aMultiArg SEMICOLON)* args+=aMultiArg BRACE_CLOSE                                      #multiOrCompoundAntecedent
     | BRACKET_OPEN (aTermSeries (VBAR BRACKET_OPEN aTerm? BRACKET_CLOSE)?)? BRACKET_CLOSE                              #listAntecedent
     | PAR_OPEN aTermExpr PAR_CLOSE                                                                                     #parAntecedent
     | variable=HID                                                                                                     #variableAntecedent
     | wildcard=UNDERSCORE                                                                                              #wildcardAntecedent
     | STRING                                                                                                           #stringAntecedent
     | NUMBER                                                                                                           #numberAntecedent
     ;

aTermSeries: aTermSeries SEMICOLON aTermSeries                                                                          #orSeriesAntecedent
           | aTermSeries COMMA aTermSeries                                                                              #andSeriesAntecedent
           | aTerm                                                                                                      #termSeriesAntecedent
           ;

aTermExpr: aTermExpr COMMA aTermExpr                                                                                    #andExpressionAntecedent
         | aTermExpr SEMICOLON aTermExpr                                                                                #orExpressionAntecedent
         | PAR_OPEN aTermExpr PAR_CLOSE                                                                                 #parExpressionAntecedent
         | aTerm                                                                                                        #termExpressionAntecedent
         ;

aMultiArg: PAR_OPEN aTermSeries? PAR_CLOSE
         | aTerm
         ;

/* Consequence */
cTerm: functor (PAR_OPEN arguments=cTermSeries? PAR_CLOSE)?                                                             #compoundConsequence
     | functor BRACE_OPEN (args+=cMultiArg COMMA)* args+=cMultiArg BRACE_CLOSE                                          #multiCompoundConsequence
     | BRACKET_OPEN (cTermSeries (VBAR BRACKET_OPEN cTerm? BRACKET_CLOSE)?)? BRACKET_CLOSE                              #listConsequence
     | variable=HID                                                                                                     #variableConsequence
     | STRING                                                                                                           #stringConsequence
     | NUMBER                                                                                                           #numberConsequence
     ;

cMultiArg : PAR_OPEN cTermSeries? PAR_CLOSE
          | cTerm
          ;

cTermSeries: (terms+=cTerm COMMA)* terms+=cTerm;

/* Functors */
functor: ID                                                                                                             #idFunctor
       | INFIX_ID                                                                                                       #infixFunctor
       ;
