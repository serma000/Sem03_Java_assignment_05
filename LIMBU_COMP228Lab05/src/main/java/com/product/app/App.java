package com.product.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // CrudOperation crudOperation = new CrudOperation();
        // crudOperation.setConnection();
        UI ui = new UI();
        ui.initialize_UI(stage);
    }
    public static void main(String[]args)
    {
        launch(args);
    }
}
