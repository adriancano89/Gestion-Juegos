module org.example.juegos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.xml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;

    opens org.example.juegos to javafx.fxml;
    opens org.example.juegos.Controllers to javafx.fxml;
    opens org.example.juegos.Models to javafx.base;
    exports org.example.juegos;
}