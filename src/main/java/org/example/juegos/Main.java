package org.example.juegos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.juegos.Controllers.TablaController;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("FXML/TablaJuegos.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        TablaController tabla = fxmlLoader.getController();
        stage.setTitle("Juegos");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}