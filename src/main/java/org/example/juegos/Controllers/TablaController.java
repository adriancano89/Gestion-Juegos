package org.example.juegos.Controllers;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
    @FXML private VBox formulario;
    @FXML private Text tituloFormulario;
    @FXML private TextField inputTitulo;
    @FXML private TextField inputGenero;
    @FXML private TextField inputPrecio;
    @FXML private TextField inputFecha;
    @FXML private Button btnRegistrar;
    @FXML private Button btnAnadir;
    @FXML private Button btnGuardar;
    @FXML private Text mensajeError;


    public void mostrarJuegos() {
        ArrayList<Juegos> juegos = modeloJuegos.obtenerJuegos();
        ObservableList<Juegos> datosJuegos = FXCollections.observableList(juegos);

        columnaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        columnaGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        tablaJuegos.setItems(datosJuegos);
    }

    public void registrarJuego() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (tablaJuegos != null) {
            mostrarJuegos();
            btnAnadir.setOnAction(event -> {
                if (tituloFormulario.getText().equals("REGISTRAR JUEGO")) {
                    if (inputTitulo.getText().isBlank()) {
                        mensajeError.setText("El título no puede estar vacío");
                        mensajeError.setVisible(true);
                    }
                    else {
                        long numCoincidencias = modeloJuegos.comprobarTitulo(inputTitulo.getText());
                        if (numCoincidencias > 0) {
                            mensajeError.setText("No puede haber dos juegos con el mismo título.");
                            mensajeError.setVisible(true);
                        }
                        else {
                            modeloJuegos.registrarNuevoJuego(new Juegos(inputTitulo.getText(), inputGenero.getText(), Double.parseDouble(inputPrecio.getText()), inputFecha.getText()));
                            mostrarJuegos();
                        }
                    }
                }
            });
            tablaJuegos.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    Juegos juegoSeleccionado = newSelection;
                    System.out.println(juegoSeleccionado.getTitulo());
                    tituloFormulario.setText("EDITAR JUEGO");
                    inputTitulo.setText(juegoSeleccionado.getTitulo());
                    inputGenero.setText(juegoSeleccionado.getGenero());
                    inputPrecio.setText(String.valueOf(juegoSeleccionado.getPrecio()));
                    inputFecha.setText(juegoSeleccionado.getFecha());
                    btnRegistrar.setVisible(true);
                    btnAnadir.setVisible(false);
                    btnGuardar.setVisible(true);
                }
            });
            btnRegistrar.setOnAction(event -> {
                tituloFormulario.setText("REGISTRAR JUEGO");
                inputTitulo.setText("");
                inputGenero.setText("");
                inputPrecio.setText("");
                inputFecha.setText("");
                btnGuardar.setVisible(false);
                btnAnadir.setVisible(true);
            });
        }
    }
}
