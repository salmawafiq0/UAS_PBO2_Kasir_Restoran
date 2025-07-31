package com.mycompany.kasir_restoran;


import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        MenuController controller = new MenuController();
        stage.setScene(controller.getScene(stage));
        stage.setTitle("Aplikasi Kasir Restoran");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
