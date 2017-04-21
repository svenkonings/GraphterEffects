package graafvis.checkers;

import graafvis.grammar.GraafvisBaseVisitor;
import graafvis.grammar.GraafvisParser;

/**
 * The checking phase of Graafvis compilation
 */
public class GraafvisChecker extends GraafvisBaseVisitor<Void> {

    private final ConsequenceBlacklist consequenceBlacklist = new ConsequenceBlacklist();
    private final LabelGenerationCheck labelGenerationCheck = new LabelGenerationCheck();
    private final VariableUsageCheck variableUsageCheck = new VariableUsageCheck();
    private final SingletonVariablesCheck singletonVariablesCheck = new SingletonVariablesCheck();

    /**
     * Run all checkers on the parsed Graafvis code
     *
     * @param program   A parsed Graafvis program
     * @return          The result obtained by all checkers
     */
    public CheckerResult check(GraafvisParser.ProgramContext program) {
        /* Reset checkers for next usage */
        reset();
        /* Initialize result */
        CheckerResult result = new CheckerResult();
        /* Perform checks */
        labelGenerationCheck.visitProgram(program);
        consequenceBlacklist.visitProgram(program);
        variableUsageCheck.visitProgram(program);
        singletonVariablesCheck.visitProgram(program);
        /* Add errors and warnings to result */
        result.addErrors(labelGenerationCheck.getErrors());
        result.addErrors(consequenceBlacklist.getErrors());
        result.addErrors(variableUsageCheck.getErrors());
        result.addWarnings(singletonVariablesCheck.getWarnings());
        return result;
    }

    /** Reset the checkers for next usage */
    private void reset() {
        consequenceBlacklist.reset();
        labelGenerationCheck.reset();
        variableUsageCheck.reset();
        singletonVariablesCheck.reset();
    }


}
