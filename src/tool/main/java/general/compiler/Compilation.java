package general.compiler;

import alice.tuprolog.InvalidLibraryException;
import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import compiler.asrc.ASRCLibrary;
import compiler.graphloader.Importer;
import compiler.solver.Solver;
import compiler.solver.VisElem;
import compiler.svg.SvgDocumentGenerator;
import exceptions.UnknownGraphTypeException;
import graafvis.RuleGenerator;
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


    private Path scriptFile;
    private Path graphFile;

    private CompilationProgress maxProgress;
    private List<Term> scriptRules;
    private List<VisElem> generatedVisElems;
    private ASRCLibrary asrcLibrary;
    private Document generatedSVG;
    private Exception exception;

    public Compilation(Path scriptFile, Path graphFile){
        this.scriptFile = scriptFile;
        this.graphFile = graphFile;
        this.maxProgress = CompilationProgress.COMPILATIONFINISHED;
    }

    //To create debug compilations which stop at a certain progress
    public Compilation(Path scriptFile, Path graphFile, CompilationProgress maxProgress){
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
        scriptRules = RuleGenerator.generate(FileUtils.readFromFile(scriptFile.toFile()));
        setChanged();
        notifyObservers(CompilationProgress.GRAAFVISCOMPILED);
    }

    public void solve() throws InvalidTheoryException {
        List<Term> rules = new LinkedList<>();
        //rules.addAll(abstractSyntaxRules);
        rules.addAll(scriptRules);
        Solver solver = new Solver(rules);
        try {
            solver.addLibrary(asrcLibrary);
        } catch (InvalidLibraryException e) {
            e.printStackTrace();
        }
        generatedVisElems = solver.solve();
        setChanged();
        notifyObservers(CompilationProgress.SOLVED);
    }

    public void generateSVG() {
        generatedSVG = SvgDocumentGenerator.generate(generatedVisElems);
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

    public List<VisElem> getGeneratedVisElems(){
        return generatedVisElems;
    }
}
