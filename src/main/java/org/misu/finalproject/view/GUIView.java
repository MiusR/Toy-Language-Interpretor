package org.misu.finalproject.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.misu.finalproject.model.expresion.*;
import org.misu.finalproject.model.statement.*;

public class GUIView extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIView.class.getResource("/org/misu/finalproject/selector-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 480);
        stage.setTitle("Toy Example Selection");
        stage.setScene(scene);
        stage.show();

    }
}
