package org.example.juegos.Controllers;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.juegos.Models.ConexionBD;
import org.example.juegos.Models.Juegos;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TablaController implements Initializable {
    private Juegos modeloJuegos = new Juegos();
    @FXML private TableView<Juegos> tablaJuegos;
    @FXML private TableColumn<Juegos, String> columnaTitulo;
    @FXML private TableColumn<Juegos, String> columnaGenero;
    @FXML private TableColumn<Juegos, Double> columnaPrecio;
    @FXML private TableColumn<Juegos, String> columnaFecha;

    public void mostrarJuegos() {
        ArrayList<Juegos> juegos = modeloJuegos.obtenerJuegos();
        ObservableList<Juegos> datosJuegos = FXCollections.observableList(juegos);

        columnaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        columnaGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        tablaJuegos.setItems(datosJuegos);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (tablaJuegos != null) {
            mostrarJuegos();
        }
    }
}
