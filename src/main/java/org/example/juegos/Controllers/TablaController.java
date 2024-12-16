package org.example.juegos.Controllers;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.juegos.Models.ConexionBD;
import org.example.juegos.Models.Juegos;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TablaController implements Initializable {
    private Juegos modeloJuegos = new Juegos();
    private static Juegos juegoSeleccionado;
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
    @FXML private ChoiceBox<String> selectGenero;
    @FXML private Button btnEliminarGenero;
    @FXML private Button btnRegistrar;
    @FXML private Button btnAnadir;
    @FXML private Button btnGuardar;
    @FXML private Button btnEliminar;
    @FXML private Text mensajeError;

    public static Juegos getJuegoSeleccionado() {
        return juegoSeleccionado;
    }

    public static void setJuegoSeleccionado(Juegos juegoSeleccionado) {
        TablaController.juegoSeleccionado = juegoSeleccionado;
    }

    public void mostrarJuegos() {
        ArrayList<Juegos> juegos = modeloJuegos.obtenerJuegos();
        ObservableList<Juegos> datosJuegos = FXCollections.observableList(juegos);

        columnaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        columnaGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        tablaJuegos.setItems(datosJuegos);
    }

    public void registrarJuego(Juegos juegoNuevo) {
        modeloJuegos.registrarNuevoJuego(juegoNuevo);
    }

    public void editarJuego(String titulo, Juegos juegoEditar) {
        modeloJuegos.editarJuego(titulo, juegoEditar);
    }

    public void eliminarJuego(String titulo) {
        modeloJuegos.eliminarJuego(titulo);
    }

    public void mostrarOpcionesSelect() {
        selectGenero.setValue("Eliminar por género");
        btnEliminarGenero.setVisible(false);
        ArrayList<String> generos = modeloJuegos.obtenerGeneros();
        ObservableList<String> camposSelect = FXCollections.observableList(generos);
        selectGenero.setItems(camposSelect);
    }

    public void eliminarPorGenero(String genero) {
        modeloJuegos.eliminarPorGenero(genero);
    }

    public void vaciarInputs() {
        inputTitulo.setText("");
        inputGenero.setText("");
        inputPrecio.setText("");
        inputFecha.setText("");
    }

    public void mostrarFormRegistrar() {
        btnRegistrar.setVisible(false);
        tituloFormulario.setText("REGISTRAR JUEGO");
        vaciarInputs();
        btnGuardar.setVisible(false);
        btnEliminar.setVisible(false);
        btnAnadir.setVisible(true);
    }

    public void cargarTablaySelect() {
        mostrarJuegos();
        mostrarOpcionesSelect();
    }

    public boolean mostrarAlertaEliminar(String mensaje) {
        Alert alertaEliminar = new Alert(Alert.AlertType.CONFIRMATION);
        alertaEliminar.setTitle("Eliminación");
        alertaEliminar.setHeaderText("ELIMINACIÓN");
        alertaEliminar.setContentText(mensaje);
        alertaEliminar.showAndWait();
        ButtonType botonSeleccionado = alertaEliminar.getResult();
        boolean confirmado = botonSeleccionado.getText().equals("Aceptar");
        return  confirmado;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (tablaJuegos != null) {
            cargarTablaySelect();
            selectGenero.setOnAction(event -> {
                btnEliminarGenero.setVisible(true);
            });
            btnEliminarGenero.setOnAction(event -> {
                String mensaje;
                if (selectGenero.getValue().isBlank()) {
                    mensaje = "¿Deseas eliminar todos los juegos que no tienen género?";
                }
                else {
                    mensaje = "¿Deseas eliminar todos los juegos del género " + selectGenero.getValue() + "?";
                }
                boolean confirmacion = mostrarAlertaEliminar(mensaje);
                if (confirmacion) {
                    eliminarPorGenero(selectGenero.getValue());
                    cargarTablaySelect();
                }
            });
            btnAnadir.setOnAction(event -> {
                mensajeError.setVisible(false);
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
                        Double precio = (!inputPrecio.getText().isBlank()) ? Double.parseDouble(inputPrecio.getText()) : 0;
                        registrarJuego(new Juegos(inputTitulo.getText(), inputGenero.getText(), precio, inputFecha.getText()));
                        vaciarInputs();
                        cargarTablaySelect();
                    }
                }
            });
            tablaJuegos.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    setJuegoSeleccionado(newSelection);
                    System.out.println(getJuegoSeleccionado().getTitulo());
                    tituloFormulario.setText("EDITAR JUEGO");
                    inputTitulo.setText(getJuegoSeleccionado().getTitulo());
                    inputGenero.setText(getJuegoSeleccionado().getGenero());
                    inputPrecio.setText(String.valueOf(getJuegoSeleccionado().getPrecio()));
                    inputFecha.setText(getJuegoSeleccionado().getFecha());
                    btnRegistrar.setVisible(true);
                    btnAnadir.setVisible(false);
                    btnGuardar.setVisible(true);
                    btnEliminar.setVisible(true);
                }
            });
            btnGuardar.setOnAction(event -> {
                mensajeError.setVisible(false);
                if (inputTitulo.getText().isBlank()) {
                    mensajeError.setText("El título no puede estar vacío");
                    mensajeError.setVisible(true);
                }
                else {
                    editarJuego(getJuegoSeleccionado().getTitulo(), new Juegos(inputTitulo.getText(), inputGenero.getText(), Double.parseDouble(inputPrecio.getText()), inputFecha.getText()));
                    cargarTablaySelect();
                    mostrarFormRegistrar();
                }
            });
            btnEliminar.setOnAction(event -> {
                boolean confirmacion = mostrarAlertaEliminar("¿Deseas eliminar el juego " + getJuegoSeleccionado().getTitulo() + "?");
                if (confirmacion) {
                    eliminarJuego(getJuegoSeleccionado().getTitulo());
                    mostrarFormRegistrar();
                    vaciarInputs();
                    cargarTablaySelect();
                }
            });
            btnRegistrar.setOnAction(event -> {
                mostrarFormRegistrar();
            });
        }
    }
}
