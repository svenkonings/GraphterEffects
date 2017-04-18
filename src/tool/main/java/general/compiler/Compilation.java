package general.compiler;

import alice.tuprolog.InvalidLibraryException;
import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import compiler.asrc.ASRCLibrary;
import compiler.graphloader.Importer;
import compiler.prolog.TuProlog;
import compiler.solver.Solver;
import compiler.solver.VisMap;
import compiler.svg.SvgDocumentGenerator;
import graafvis.GraafvisCompiler;
import graafvis.errors.VisError;
import graafvis.warnings.Warning;
import org.dom4j.Document;
import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;
import utils.FileUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class Compilation extends Observable{

    /**
     * Compilation Object.
     *
     * <P>This is a data object for a compilation process.
     * It contains all the methods necessary to advance compilation as well as
     * all the data generated during the compilation process.
     */

    private Path scriptFile;
    private Path graphFile;

    private CompilationProgress maxProgress;
    private List<Term> scriptRules;
    private VisMap visMap;
    private ASRCLibrary asrcLibrary;
    private Document generatedSVG;
    private Exception exception;
    private GraafvisCompiler compiler;

    /**
     * Constructor for a normal compilation.
     *
     * @param scriptFile The path of where the Graafvis Script is stored
     * @param graphFile The path of where the Abstract Syntax Graph is stored
     *                  See //TODO{@link } which fileformats are suported.
     */

    public Compilation(Path scriptFile, Path graphFile){
        this.scriptFile = scriptFile;
        this.graphFile = graphFile;
        this.maxProgress = CompilationProgress.COMPILATIONFINISHED;
        this.compiler = new GraafvisCompiler();
    }

    /**
     * Constructor for a debug compilation.
     *
     * @param scriptFile The path of where the Graafvis Script is stored
     * @param graphFile The path of where the Abstract Syntax Graph is stored
     *                  See //TODO{@link } which fileformats are suported.
     * @param maxProgress The {@link CompilationProgress} until which the compilation is supposed to
     *                    continue.
     */
    public Compilation(Path scriptFile, Path graphFile, CompilationProgress maxProgress){
        this.scriptFile = scriptFile;
        this.graphFile = graphFile;
        this.maxProgress = maxProgress;
        this.compiler = new GraafvisCompiler();
    }

    /**
     * Adds the ASCR Library to the Compilation
     * @throws IOException Thrown when an the {@link Graph} could not be loaded from disk.
     * @throws SAXException Thrown when the {@link Graph} file contains syntax errors.
     */
    public void addASCRLibrary() throws IOException, SAXException {
        Graph graph = Importer.graphFromFile(graphFile.toFile());
        asrcLibrary = new ASRCLibrary(graph);
        setChanged();
        notifyObservers(CompilationProgress.GRAPHCONVERTED);
    }

    public void compileGraafVis() throws IOException, GraafvisCompiler.SyntaxException, GraafvisCompiler.CheckerException {
        /* Get a string representation of the script */
        String script = FileUtils.readFromFile(scriptFile.toFile());
        scriptRules = compiler.compile(script);
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
            e.printStackTrace(); //TODO: Throw this
        }

        Solver solver = new Solver();
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
        return compiler.getErrors();
    }

    public List<Warning> getWarnings() {
        return compiler.getWarnings();
    }

    public Path getGraphFile() {
        return graphFile;
    }

    public TuProlog getProlog() throws InvalidTheoryException, InvalidLibraryException {
        TuProlog tuProlog = new TuProlog(scriptRules);
        tuProlog.loadLibrary(asrcLibrary);
        return tuProlog;


    }

}
