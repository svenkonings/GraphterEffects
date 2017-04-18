package screens.idescreen.tab.ruleviewer;

import alice.tuprolog.InvalidLibraryException;
import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import prolog.TuProlog;
import general.compiler.CompilationModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class RuleViewerPresenter implements Initializable {

    public StackPane visElemPane;
    public TitledPane titledPane;
    public TextField queryArgumentsTextField;
    public Button queryButton;
    public ListView rulesListView;
    public TableView<TableTerm> rulesTable;
    public TableColumn<TableTerm, String> tailColumn;
    public TableColumn<TableTerm, String> headColumn;
    public StackPane queryResultPane;
    public TextArea queryResultTextArea;

    private TuProlog tuProlog;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        titledPane.prefWidthProperty().bind(visElemPane.widthProperty());
        titledPane.prefHeightProperty().bind(visElemPane.heightProperty());
        titledPane.maxWidthProperty().bind(visElemPane.widthProperty());

        queryResultPane.minHeightProperty().set(30);
        queryResultPane.prefHeightProperty().set(30);
        queryResultPane.maxHeightProperty().set(30);

        rulesTable.prefWidthProperty().bind(visElemPane.prefWidthProperty());

        queryArgumentsTextField.prefWidthProperty().setValue(300);

        //queryArgumentsTextField.set

        headColumn.setCellValueFactory(cellData -> cellData.getValue().head);
        headColumn.setPrefWidth(200);
        tailColumn.setCellValueFactory(cellData -> cellData.getValue().tail);
        tailColumn.prefWidthProperty().bind(rulesTable.widthProperty().subtract(headColumn.widthProperty()));
        try {
            loadContent(CompilationModel.getInstance().getCompilation().getProlog());
        } catch (InvalidTheoryException e) {
            e.printStackTrace();
        } catch (InvalidLibraryException e) {
            e.printStackTrace();
        }
    }

    public void loadContent(TuProlog tuProlog) {
        this.tuProlog = tuProlog;
        for (Iterator<? extends Term> it = tuProlog.getProlog().getTheory().iterator(tuProlog.getProlog()); it.hasNext(); ) {
            Term term = it.next();
            term.getTerm();
            String head = ((Struct) term).getArg(0).toString();
            String tail = "";
            if (((Struct) term).getArity() - 1 <= 1) {
                tail += ((Struct) term).getArg(1);
            }
            for (int i = 2; i <= ((Struct) term).getArity() - 1; i++) {
                tail += ((Struct) term).getArg(i);
            }

            TableTerm tableTerm = new TableTerm(head,tail);
            rulesTable.getItems().add(tableTerm);

        }
    }

    public void queryButtonPressed(ActionEvent actionEvent) {
        queryResultTextArea.setText("");
        if (!queryArgumentsTextField.getText().matches("\\s*")) {
            queryResultPane.minHeightProperty().bind(visElemPane.heightProperty().multiply(0.3));
            queryResultPane.prefHeightProperty().bind(visElemPane.heightProperty().multiply(0.3));
            queryResultPane.maxHeightProperty().bind(visElemPane.heightProperty());
            List<Map<String, Term>> result = tuProlog.solve(queryArgumentsTextField.getText());
            int counter = 1;
            if (result.isEmpty()){
                queryResultTextArea.appendText("False");
            }
            for (Map<String, Term> varResults : result) {
                queryResultTextArea.appendText(counter + ":" + varResults + "\n");
                counter++;
            }
        } else {
            queryResultPane.minHeightProperty().set(30);
            queryResultPane.prefHeightProperty().set(30);
            queryResultPane.maxHeightProperty().set(30);
        }
    }

    private class TableTerm {

        private final SimpleStringProperty head;
        private final SimpleStringProperty tail;

        public TableTerm(String head, String tail) {
            this.head = new SimpleStringProperty(head);
            this.tail = new SimpleStringProperty(tail);
        }

        public String getHead(){
            return head.get();
        }

        public String getTail(){
            return tail.get();
        }

    }
}
