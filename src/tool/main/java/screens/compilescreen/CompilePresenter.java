package screens.compilescreen;

import com.airhacks.afterburner.injection.Injector;
import general.StageHistory;
import general.ViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import screens.compileerrorscreen.CompileErrorView;

import javax.inject.Inject;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CompilePresenter implements Initializable{

    @Inject
    private ViewModel viewModel;

    public ProgressBar progressBar;
    public Label progressBarLabel;
    public Thread compilerThread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StageHistory.getInstance().setCurrentStage(CompilePresenter.class.getSimpleName());

        compilerThread = new Thread(new Runnable() {
            @Override
            public void run() {

                /*
                String code = Model.getInstance().getCodePaneTextArea().getText();
                List<Term> visRules = RuleGenerator.generate(code);

                File graphFile = Model.getInstance().getGraphFileList().get(0);
                Graph graph = null;
                try {
                    graph = Importer.graphFromFile(graphFile);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
                List<Term> graphRules = new ArrayList<>();
                try {
                     //graphRules = AbstractSyntaxRuleConverter.convertToRules(graph);
                } catch (UnknownGraphTypeException e) {
                    e.printStackTrace();
                }

                List<Term> allTerms = new ArrayList<>();
                allTerms.addAll(visRules);
                allTerms.addAll(graphRules);

                TuProlog tuProlog = new TuProlog();
                try {
                    tuProlog = new TuProlog(allTerms);
                } catch (InvalidTheoryException e) {
                    e.printStackTrace();
                }

                Solver solver = new Solver(tuProlog);
                List<VisElem> visElemList = solver.solve();
                Document svg = SvgDocumentGenerator.generate(visElemList);
                Model.getInstance().setGeneratedSVG(svg);
                try {
                    SvgDocumentGenerator.writeDocument(svg, graphFile.getName().split(".")[0]+".svg");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */
            }
            /*
            @Override
            public void run() {
                try {
                    updateProgressBarLabel("Loading Graph");
                    sleep(2000);
                    updateProgressBar(0.10);

                    updateProgressBarLabel("Converting Graph");
                    sleep(2000);
                    updateProgressBar(0.20);

                    updateProgressBarLabel("Compiling GraafVis Script");
                    sleep(2000);
                    updateProgressBar(0.50);

                    updateProgressBarLabel("Solving Logical Rules");
                    sleep(2000);
                    updateProgressBar(0.70);

                    updateProgressBarLabel("Solving Constraints");
                    sleep(2000);
                    updateProgressBar(0.90);

                    updateProgressBarLabel("Generating SVG");
                    sleep(2000);
                    updateProgressBar(1);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            } */


        });
        compilerThread.start();
    }

    public void updateProgressBar(double progress){
        Platform.runLater(
                new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(progress);
                    }
                }
        );
    }
    
    public void updateProgressBarLabel(String message){
        Platform.runLater(
                new Runnable() {
                    @Override
                    public void run() {
                        progressBarLabel.setText(message);
                    }
                }
        );
    }

    public void errorButtonPushed(ActionEvent actionEvent) {
        compilerThread.interrupt();
        //CompileErrorView compileErrorView = new CompileErrorView();
        Map<Object, Object> context = new HashMap<>();
        context.put( "error", progressBarLabel.getText() );

        Injector.setConfigurationSource( context::get );

        CompileErrorView compileErrorView = new CompileErrorView();

        viewModel.setMainView(compileErrorView.getView());
    }
}
