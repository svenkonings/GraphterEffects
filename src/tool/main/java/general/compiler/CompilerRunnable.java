package general.compiler;

import general.files.DocumentModel;
import org.dom4j.Document;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CompilerRunnable implements Runnable {

    private Path scriptFile;
    private Path graphFile;
    private Compilation compilation;
    private CompilationProgress maxCompilationProgress;

    public CompilerRunnable(Path scriptFile, Path graphFile){
        this.scriptFile = scriptFile;
        this.graphFile = graphFile;
        maxCompilationProgress = CompilationProgress.COMPILATIONFINISHED;
    }

    public CompilerRunnable(Path scriptFile, Path graphFile, CompilationProgress maxCompilationProgress){
        this.scriptFile = scriptFile;
        this.graphFile = graphFile;
        this.maxCompilationProgress = maxCompilationProgress;

    }

    @Override
    public synchronized void run() {
        compilation = new Compilation(scriptFile, graphFile, maxCompilationProgress);
        CompilationModel.getInstance().setCompilation(compilation);
        try {
            while (CompilationModel.getInstance().countObservers() != compilation.countObservers()) {
                CompilationModel.getInstance().lock.lock();
                CompilationModel.getInstance().allObserversAdded.await();
                CompilationModel.getInstance().lock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //TODO: error resolving
        try {
            if (maxCompilationProgress == CompilationProgress.COMPILATIONFINISHED) {
                compilation.compileGraafVis();
                compilation.addGraphRules();
                compilation.solve();
                compilation.generateSVG();
                saveGeneratedSVG(compilation.getGeneratedSVG());
            } else {
                if (maxCompilationProgress.ordinal() >= CompilationProgress.GRAAFVISCOMPILED.ordinal()){
                    compilation.compileGraafVis();
                }
                if (maxCompilationProgress.ordinal() >= CompilationProgress.GRAPHCONVERTED.ordinal()){
                    compilation.addGraphRules();
                }
                if (maxCompilationProgress.ordinal() >= CompilationProgress.SOLVED.ordinal()){
                    compilation.solve();
                }
                if (maxCompilationProgress.ordinal() >= CompilationProgress.SVGGENERATED.ordinal()){
                    compilation.generateSVG();
                }
            }
        } catch (Exception e){
            compilation.setException(e);
            e.printStackTrace();
        }


    }

    private void saveGeneratedSVG(Document document){
        //Sets the name of this SVG to the name of the dot.
        String svgFileName = graphFile.getFileName().toString().split("\\.")[0];
        int counter = DocumentModel.getInstance().generateSVGCounter(svgFileName);
        if (counter != 0){
            svgFileName += "(" + counter + ")";
        }
        document.setName(svgFileName);

        String svgxml = document.asXML();
        List<String> svgxmltext = new ArrayList<>();
        svgxmltext.add(svgxml);

        new File("temp/compiled/").mkdirs();

        Path file = Paths.get("temp/compiled/",document.getName() + ".svg");
        try {
            Files.write(file, svgxmltext, Charset.forName("UTF-8"));
            DocumentModel.getInstance().addGeneratedSVG(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
