package org.example.juegos.Models;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

public class Juegos {
    String titulo;
    String genero;
    Double precio;
    String fecha;

    public Juegos(String titulo, String genero, Double precio, String fecha) {
        this.titulo = titulo;
        this.genero = genero;
        this.precio = precio;
        this.fecha = fecha;
    }

    public Juegos() {

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void obtenerJuegos() {
        MongoDatabase database = ConexionBD.conexion();
        try {
            MongoCollection<Document> coleccionJuegos = database.getCollection("juegos");
            FindIterable<Document> documentos = coleccionJuegos.find();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

}
