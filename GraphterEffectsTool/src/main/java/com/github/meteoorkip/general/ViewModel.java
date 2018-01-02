package com.github.meteoorkip.general;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

public class ViewModel {

    private final ObjectProperty<Node> mainView = new SimpleObjectProperty(this, "mainView", null);

    public final Node getMainView() {
        return mainView.get();
    }
    public final void setMainView(Node mainView) {
        this.mainView.set(mainView);
    }

    public ReadOnlyDoubleProperty sceneWidthProperty(){
        return getMainView().getScene().widthProperty();
    }

    public ReadOnlyDoubleProperty sceneHeigthProperty(){
        return getMainView().getScene().heightProperty();
    }
}
