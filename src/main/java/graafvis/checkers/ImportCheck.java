package graafvis.checkers;

import graafvis.grammar.GraafvisBaseVisitor;
import graafvis.grammar.GraafvisParser;

/**
 *
 */
public class ImportCheck extends GraafvisBaseVisitor<String> {

    @Override
    public String visitProgram(GraafvisParser.ProgramContext ctx) {
        return super.visitProgram(ctx); // TODO -- just visit import
    }

    @Override
    public String visitImport_vis(GraafvisParser.Import_visContext ctx) {
        return super.visitImport_vis(ctx); // TODO -- check import
    }


}
