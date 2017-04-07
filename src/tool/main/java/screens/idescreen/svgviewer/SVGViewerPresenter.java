package screens.idescreen.svgviewer;

import general.ViewModel;
import general.files.DocumentModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class SVGViewerPresenter implements Initializable {

    @FXML private StackPane stackPane;
    @Inject ViewModel viewModel;
    private Node content;
    private Pane editPane;
    private StackPane graphics;
    private ImageView imageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        editPane = new Pane();
        graphics = new StackPane();

        editPane.getChildren().add(graphics);

        //stackPane = new StackPane();
        stackPane.getChildren().add(editPane);


        stackPane.setStyle("-fx-background-color: #7dff43;");
        stackPane.setAlignment(Pos.CENTER);
        editPane.setStyle("-fx-background-color: #ff4bf3;");

        editPane.prefWidthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                                Number newValue) {
                //adjustTransform();
                //System.out.println("W");
            }
        });

        editPane.prefHeightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                                Number newValue) {
                //adjustTransform();
                //System.out.println("H");
            }
        });

        //editPane.prefWidthProperty().bind( ((Pane) (viewModel.getMainView()).getParent()).widthProperty() );
        ((Pane) (viewModel.getMainView()).getParent()).widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("Test" + newValue);
                stackPane.setPrefWidth(newValue.doubleValue());
                editPane.setPrefWidth(newValue.doubleValue());
                imageView.prefWidth(newValue.doubleValue());
                //content.getBoundsInParent()
                //editPane.setMinWidth(newValue.doubleValue());
            }

        });

        ((Pane) (viewModel.getMainView()).getParent()).heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("Test" + newValue);
                //stackPane.setPrefWidth(newValue.doubleValue());
                //editPane.setPrefHeight(newValue.doubleValue());
                //imageView.prefWidth(newValue.doubleValue());
                //editPane.setMinHeight(newValue.doubleValue());
                //graphics.setPrefHeight(newValue.doubleValue() - 300);

            }
        });

        //bind();

    }

    public void bind(){
        //DoubleBinding prefWidth =  ((Pane) (viewModel.getMainView()).getParent()).widthProperty().subtract(65);
        //stackPane.prefWidthProperty().bind(pr);
        //stackPane.prefHeightProperty().bind( ((Pane) (viewModel.getMainView()).getParent()).heightProperty() );
    }

    public void loadContent(String key) {

        MyTranscoder transcoder = null;
        try {
            transcoder = new MyTranscoder(Files.newInputStream(DocumentModel.getInstance().getGeneratedSVG(key)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image javafxCompatibleImage = transcoder.getJavafxCompatibleImage(700, 500);

        imageView = new ImageView(javafxCompatibleImage);
        imageView.setImage(javafxCompatibleImage);
        Group content = new Group();
        content.getChildren().add(imageView);
        this.content = content;
        graphics.getChildren().add(content);
        adjustTransform();
    }

    private void adjustTransform() {
        graphics.getTransforms().clear();

        double cx = content.getBoundsInParent().getMinX();
        double cy = content.getBoundsInParent().getMinY();
        double cw = content.getBoundsInParent().getWidth();
        double ch = content.getBoundsInParent().getHeight();

        System.out.println("CONTENT:" + content.getBoundsInParent().getMinX() + " " + content.getBoundsInParent().getMinY() + " " +
                content.getBoundsInParent().getWidth() + " " +content.getBoundsInParent().getHeight());
        double ew = editPane.getWidth();
        double eh = editPane.getHeight();

        System.out.println("EDITPANE:" + editPane.getWidth() + editPane.getHeight());

        if (ew > 0.0 && eh > 0.0) {
            double scale = Math.min(ew / cw, eh / ch);

            // Offset to center content
            double sx = 0.5 * (ew - cw * scale);
            double sy = 0.5 * (eh - ch * scale);

            graphics.getTransforms().add(new Translate(sx, sy));
            graphics.getTransforms().add(new Translate(-cx, -cy));
            graphics.getTransforms().add(new Scale(scale, scale, cx, cy));
        }
    }
}
