//package graafvis.checkers;
//
//import graafvis.errors.ImportError;
//import graafvis.errors.VisError;
//import graafvis.grammar.GraafvisBaseVisitor;
//import graafvis.grammar.GraafvisParser;
//
//import java.io.File;
//import java.util.ArrayList;
//
///**
// * Checks if the imported files actually exist
// */
//public class ImportCheck extends GraafvisBaseVisitor<Void> {
//
//    /** List of errors obtained during the checking phase */
//    private final ArrayList<VisError> errors;
//
//    /** Create a new import checker */
//    public ImportCheck() {
//        errors = new ArrayList<>();
//    }
//
//    /*
//     * Visitor methods
//     */
//
//    /** Just visit import statements */
//    @Override
//    public Void visitProgram(GraafvisParser.ProgramContext ctx) {
//        for (GraafvisParser.Import_visContext imp : ctx.import_vis()) {
//            visitImport_vis(imp);
//        }
//        return null;
//    }
//
//    /** Check if the file exists */
//    @Override
//    public Void visitImport_vis(GraafvisParser.Import_visContext ctx) {
//        String importString = ctx.STRING().getText();
//        String filename = importString.substring(1, importString.length() - 1);
//
//        File dir = new File("src\\main\\test\\java\\graafvis\\checkers");
//        File file = new File(dir, filename + ".vis");
//
//        System.out.println(file.getAbsolutePath());
//        System.out.println(file.exists());
//        if (!file.exists() || file.isDirectory()) {
//            int line = ctx.STRING().getSymbol().getLine();
//            int column = ctx.STRING().getSymbol().getCharPositionInLine();
//            errors.add(new ImportError(line, column, filename));
//        }
//        return null;
//    }
//
//    /*
//     * Getters
//     */
//
//    public ArrayList<VisError> getErrors() {
//        return errors;
//    }
//
//}
