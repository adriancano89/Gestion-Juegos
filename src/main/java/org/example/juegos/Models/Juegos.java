package org.example.juegos.Models;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
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

    public ArrayList<Juegos> obtenerJuegos() {
        ArrayList<Juegos> juegos = new ArrayList<>();
        MongoDatabase database = ConexionBD.conexion();
        try {
            MongoCollection<Document> coleccionJuegos = database.getCollection("juegos");
            MongoCursor<Document> cursor = coleccionJuegos.find().iterator();
            while (cursor.hasNext()) {
                Document documento = cursor.next();
                System.out.println(documento.toJson());
                String titulo = documento.getString("titulo");
                String genero = documento.getString("genero");
                Double precio = documento.getDouble("precio");
                String fecha = documento.getString("fecha_lanzamiento");
                juegos.add(new Juegos(titulo, genero, precio, fecha));
            }

        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
        return juegos;
    }

}
