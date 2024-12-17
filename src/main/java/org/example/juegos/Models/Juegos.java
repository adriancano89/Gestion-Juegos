package org.example.juegos.Models;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;
import javax.print.Doc;
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
            ConexionBD.getMongoClient().close();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
        return juegos;
    }

    public void registrarNuevoJuego(Juegos juego) {
        MongoDatabase database = ConexionBD.conexion();
        try {
            MongoCollection<Document> coleccionJuegos = database.getCollection("juegos");
            Document documento = new Document("titulo", juego.getTitulo()).append("genero", juego.getGenero()).append("precio", juego.getPrecio()).append("fecha_lanzamiento", juego.getFecha());
            System.out.println("Documento a insertar: " + documento);
            coleccionJuegos.insertOne(documento);
            ConexionBD.getMongoClient().close();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    public long comprobarTitulo(String titulo) {
        MongoDatabase database = ConexionBD.conexion();
        long numRegistros = 0;
        try {
            MongoCollection<Document> coleccionJuegos = database.getCollection("juegos");
            Document consulta = new Document("titulo", titulo);
            numRegistros = coleccionJuegos.countDocuments(consulta);
            ConexionBD.getMongoClient().close();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
        return numRegistros;
    }

    public void editarJuego(String titulo, Juegos juego) {
        MongoDatabase database = ConexionBD.conexion();
        try {
            MongoCollection<Document> coleccionJuegos = database.getCollection("juegos");
            Document consultaEditar = new Document("$set", new Document("titulo", juego.getTitulo()).append("genero", juego.getGenero()).append("precio", juego.getPrecio()).append("fecha_lanzamiento", juego.getFecha()));
            System.out.println(consultaEditar);
            Document filtro = new Document("titulo", titulo);
            coleccionJuegos.updateOne(filtro, consultaEditar);
            ConexionBD.getMongoClient().close();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    public void eliminarJuego(String titulo) {
        MongoDatabase database = ConexionBD.conexion();
        try {
            MongoCollection<Document> coleccionJuegos = database.getCollection("juegos");
            Document consultaEliminar = new Document("titulo", titulo);
            coleccionJuegos.deleteOne(consultaEliminar);
            ConexionBD.getMongoClient().close();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    public ArrayList<String> obtenerGeneros() {
        MongoDatabase database = ConexionBD.conexion();
        ArrayList<String> generos = new ArrayList<>();
        try {
            MongoCollection<Document> coleccionJuegos = database.getCollection("juegos");
            MongoIterable<String> valoresGeneros = coleccionJuegos.distinct("genero", String.class);
            for (String genero : valoresGeneros) {
                generos.add(genero);
            }
            ConexionBD.getMongoClient().close();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
        return generos;
    }

    public void eliminarPorGenero(String genero) {
        MongoDatabase database = ConexionBD.conexion();
        try {
            MongoCollection<Document> coleccionJuegos = database.getCollection("juegos");
            if (genero.equals("Sin género")) {
                genero = "";
            }
            Document consultaEliminar = new Document("genero", genero);
            coleccionJuegos.deleteMany(consultaEliminar);
            ConexionBD.getMongoClient().close();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

}
