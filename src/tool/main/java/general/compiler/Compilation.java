package general.compiler;

import alice.tuprolog.InvalidLibraryException;
import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import compiler.asrc.ASRCLibrary;
import compiler.graphloader.Importer;
import compiler.prolog.TuProlog;
import compiler.solver.Solver;
import compiler.solver.VisElem;
import compiler.solver.VisMap;
import compiler.svg.SvgDocumentGenerator;
import exceptions.UnknownGraphTypeException;
import graafvis.ErrorListener;
import graafvis.RuleGenerator;
import graafvis.RuleGeneratorProposal;
import graafvis.checkers.CheckerResult;
import graafvis.checkers.GraafvisChecker;
import graafvis.errors.VisError;
import graafvis.grammar.GraafvisLexer;
import graafvis.grammar.GraafvisParser;
import graafvis.warnings.Warning;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.dom4j.Document;
import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;
import utils.FileUtils;

import java.io.IOException;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class Compilation extends Observable{


    private Path scriptFile;
    private Path graphFile;

    private CompilationProgress maxProgress;
    private List<Term> scriptRules;
    private VisMap visMap;
    private ASRCLibrary asrcLibrary;
    private Document generatedSVG;
    private Exception exception;
    private List<VisError> errors;
    private List<Warning> warnings;

    public Compilation(Path scriptFile, Path graphFile){
        this.scriptFile = scriptFile;
        this.graphFile = graphFile;
        this.maxProgress = CompilationProgress.COMPILATIONFINISHED;
    }

    //To create debug compilations which stop at a certain progress
    protected Compilation(Path scriptFile, Path graphFile, CompilationProgress maxProgress){
        this.scriptFile = scriptFile;
        this.graphFile = graphFile;
        this.maxProgress = maxProgress;
    }

    public void addGraphRules() throws IOException, SAXException, UnknownGraphTypeException {
        Graph graph = Importer.graphFromFile(graphFile.toFile());
        asrcLibrary = new ASRCLibrary(graph);
        setChanged();
        notifyObservers(CompilationProgress.GRAPHCONVERTED);
    }

    public void compileGraafVis() throws IOException {
        errors = new ArrayList<>();
        warnings = new ArrayList<>();
        /* Get a string representation of the script */
        String script = FileUtils.readFromFile(scriptFile.toFile());
        /* Create a parser */
        Lexer lexer = new GraafvisLexer(new ANTLRInputStream(script));
        TokenStream tokens = new CommonTokenStream(lexer);
        GraafvisParser parser = new GraafvisParser(tokens);
        /* Add error listener so errors are captured */
        ErrorListener errorListener = new ErrorListener();
        lexer.removeErrorListeners();
        parser.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        parser.addErrorListener(errorListener);
        /* Parse the program */
        GraafvisParser.ProgramContext programContext = parser.program();
        /* Check for syntax errors */
        if (errorListener.hasErrors()) {
            /* Can't compile -- add errors to list */
            errors.addAll(errorListener.getErrors());
            throw new IOException();
        }
        /* Parsing successful, continue to checking phase */
        GraafvisChecker checker = new GraafvisChecker();
        CheckerResult checkerResult = checker.check(programContext);
        if (checkerResult.getErrors().size() > 0) {
            /* Checker found errors */
            errors.addAll(checkerResult.getErrors());
            warnings.addAll(checkerResult.getWarnings());
            throw new IOException();
        }
        warnings.addAll(checkerResult.getWarnings());
        /* Passed the checking phase, generate program */
        scriptRules = new RuleGeneratorProposal(programContext).getResult();
        setChanged();
        notifyObservers(CompilationProgress.GRAAFVISCOMPILED);
    }

    public void solve() throws InvalidTheoryException {
        List<Term> rules = new LinkedList<>();
        //rules.addAll(abstractSyntaxRules);
        rules.addAll(scriptRules);
        TuProlog prolog = new TuProlog(rules);
        try {
            prolog.loadLibrary(asrcLibrary);
        } catch (InvalidLibraryException e) {
            e.printStackTrace();
        }
        Solver solver = new Solver();
        System.out.println("DEBUGGING FOR PIM");
        System.out.println(prolog.toString());
        visMap = solver.solve(prolog);
        setChanged();
        notifyObservers(CompilationProgress.SOLVED);
    }

    public void generateSVG() {
        generatedSVG = SvgDocumentGenerator.generate(visMap.values());
        setChanged();
        notifyObservers(CompilationProgress.SVGGENERATED);
    }

    //This structure is done because maybe some errors can be resolved by the CompilerRunner and don't need to be passed to the observers.
    //Only when erros can't be solved the observers will be notified about them
    public void setException(Exception exception){
        this.exception = exception;
        setChanged();
        notifyObservers(CompilationProgress.ERROROCCURED);
    }

    public boolean isDebug(){
        return !maxProgress.equals(CompilationProgress.COMPILATIONFINISHED);
    }

    public CompilationProgress getMaxProgress(){
        return maxProgress;
    }

    public Exception getException(){
        return exception;
    }

    public Document getGeneratedSVG() {
        return generatedSVG;
    }

    public VisMap getVisMap(){
        return visMap;
    }

    public List<VisError> getErrors() {
        return errors;
    }

    public List<Warning> getWarnings() {
        return warnings;
    }

    public Path getGraphFile() {
        return graphFile;
    }

}
