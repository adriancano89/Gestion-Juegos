package org.example.juegos.Controllers;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.juegos.Models.ConexionBD;
import org.example.juegos.Models.Juegos;

import java.net.URL;
import java.util.ResourceBundle;

public class TablaController implements Initializable {
    private Juegos modeloJuegos = new Juegos();
    @FXML private TableView<Juegos> tablaJuegos;
    @FXML private TableColumn<Juegos, String> columnaTitulo;
    @FXML private TableColumn<Juegos, String> columnaGenero;
    @FXML private TableColumn<Juegos, Double> columnaPrecio;
    @FXML private TableColumn<Juegos, String> columnaFecha;

    public void mostrarJuegos() {
        modeloJuegos.obtenerJuegos();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (tablaJuegos != null) {
            mostrarJuegos();
        }
    }
}
