package screens.idescreen.ruleviewer;

import alice.tuprolog.InvalidLibraryException;
import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import compiler.prolog.TuProlog;
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
    public TextField filterArgumentsTextField;
    public Button filterButton;
    public TextField queryResultTextField;
    public ListView rulesListView;
    public TableView<TableTerm> rulesTable;
    public TableColumn<TableTerm, String> tailColumn;
    public TableColumn<TableTerm, String> headColumn;
    public StackPane queryResultPane;

    private TuProlog tuProlog;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        titledPane.prefWidthProperty().bind(visElemPane.widthProperty());
        titledPane.prefHeightProperty().bind(visElemPane.heightProperty());
        titledPane.maxWidthProperty().bind(visElemPane.widthProperty());
        queryResultPane.maxHeightProperty().set(30);

        rulesTable.prefWidthProperty().bind(visElemPane.prefWidthProperty());
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
        List<Map<String, Term>> awnsers = tuProlog.solve(filterArgumentsTextField.getText());
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
