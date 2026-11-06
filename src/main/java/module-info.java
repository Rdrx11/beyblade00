module com.example.beyblade {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    exports com.example.beyblade00;
    opens com.example.beyblade00.Model to javafx.base, javafx.fxml, com.google.gson;
    opens com.example.beyblade00.controller to javafx.fxml;
}