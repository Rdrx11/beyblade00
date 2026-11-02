package com.example.beyblade00;

import com.example.beyblade00.Model.BeybladeModel;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import org.controlsfx.control.Notifications;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    private final List<BeybladeModel> listaBeyblade = new ArrayList<>();
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Registro Beyblade X");
        Label titulo = new Label("SISTEMA BEYBLADE X");
        titulo.getStyleClass().addAll("h3", "text-primary", "b");

        TextField campoNombre = new TextField();
        campoNombre.setPromptText("Ej: Dran Sword 3-60F");
        campoNombre.getStyleClass().add("form-control");

        ComboBox<String> comboSistema = new ComboBox<>();
        comboSistema.getItems().addAll("Bx (basic line)", "Ux (Unique line)", "Cx (Custom line");
        comboSistema.getSelectionModel().selectFirst();

        PasswordField campoContrasena = new PasswordField();
        campoContrasena.setPromptText("Crear contraseña para este bey");
        campoContrasena.getStyleClass().add("form-control");

        Button botonRegistrar = new Button("Registrar Beyblade");
        botonRegistrar.getStyleClass().addAll("btn", "btn-primary");

        ListView<String> listaVisual = new ListView<>();

        botonRegistrar.setOnAction(e-> {
            String nombre = campoNombre.getText().trim();
            String sistema = comboSistema.getValue();
            String pass =  campoContrasena.getText().trim();
            if (nombre.isEmpty() || pass.isEmpty()){
                Notifications.create()
                        .title("campos Icompletos")
                        .text("Debes ingresar un nombre y una contraseña")
                        .showWarning();
                return;
            }
            BeybladeModel nuevoBey = new BeybladeModel(nombre, sistema, pass);
            listaBeyblade.add(nuevoBey);

            listaVisual.getItems().add(nuevoBey.toString());
            Notifications.create()
                    .title("registro Exitoso")
                    .title("Se guardo " + nombre + " correctamente")
                    .showInformation();
            campoNombre.clear();
            campoContrasena.clear();
        });

        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(10);
        formulario.add(new Label("Nombre: "), 0, 0);
        formulario.add(campoNombre, 1, 0);
        formulario.add(new Label("Sistema: "), 0, 1);
        formulario.add(comboSistema, 1, 1);
        formulario.add(new Label("contraseña: "), 0, 2);
        formulario.add(campoContrasena, 1, 2);

        VBox panelIzquierdo = new VBox (15, titulo, formulario, botonRegistrar);
        panelIzquierdo.setPadding(new Insets(15));

        VBox panelDerecho = new VBox(10, new Label("Beyblades Registrados: "), listaVisual);
        panelDerecho.setPadding(new Insets(15));

        HBox raiz = new HBox(20, panelIzquierdo, panelDerecho);
        raiz.setPadding(new Insets(15));
        raiz.setAlignment(Pos.CENTER);

        Scene scene = new Scene(raiz, 700, 400);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
