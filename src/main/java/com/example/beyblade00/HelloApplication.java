package com.example.beyblade00;

import com.example.beyblade00.Dao.BeybladeDao;
import com.example.beyblade00.Model.BeybladeModel;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.binding.BooleanBinding;
import org.controlsfx.control.Notifications;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.util.ArrayList;


public class HelloApplication extends Application {
    private final BeybladeDao dao = new BeybladeDao();
    private final ObservableList<BeybladeModel> BeybladeObservableList = FXCollections.observableArrayList();

    private Stage escenarioPrincipal;
    private Scene escenaLogin;
    private Scene escenaPrincipal;


    @Override
    public void start(Stage primaryStage) {
        this.escenarioPrincipal = primaryStage;
        primaryStage.setTitle("Registro Beyblade X");
        BeybladeObservableList.addAll(dao.cargar());

        crearEscenaLogin();
        crearEscenaPrincipal();

        escenarioPrincipal.setScene(escenaLogin);
        escenarioPrincipal.show();
    }

        private void crearEscenaLogin(){
            Label titulo = new Label("Acceso al sistema");
            titulo.getStyleClass().addAll("h3", "text-primary", "b");

            TextField campoUsuario = new TextField();
            campoUsuario.setPromptText("Ingrese un nombre de usuario");
            campoUsuario.getStyleClass().add("form-control");

            PasswordField campoPassword = new PasswordField();
            campoPassword.setPromptText("Ingrese una contraseña");
            campoPassword.getStyleClass().add("form-control");

            Button botonIngresar = new Button("Iniciar Sesion");
            botonIngresar.getStyleClass().addAll("btn", "btn-success");

            BooleanBinding loginIncompleto = campoUsuario.textProperty().isEmpty()
                    .or(campoPassword.textProperty().isEmpty());
            botonIngresar.disableProperty().bind(loginIncompleto);

            botonIngresar.setOnAction(e -> {
                escenarioPrincipal.setScene(escenaPrincipal);
            });

            VBox contenedor = new VBox(15, titulo, new Label("Usuario: "), campoUsuario,
                    new Label ("Contraseña: "), campoPassword, botonIngresar);
            contenedor.setPadding(new Insets(30));
            contenedor.setMaxHeight(320);
            contenedor.setAlignment(Pos.CENTER);

            BorderPane raiz = new BorderPane(contenedor);
            escenaLogin = new Scene(raiz, 720, 440);
            escenaLogin.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        }



        private void crearEscenaPrincipal(){

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

            botonRegistrar.disableProperty().bind(
                    campoNombre.textProperty().isEmpty()
                    .or(campoContrasena.textProperty().isEmpty()));

            Button botonCerrarSesion = new Button("Cerrar sesion");
            botonCerrarSesion.getStyleClass().addAll("btn", "danger");

            botonCerrarSesion.setOnAction(e-> escenarioPrincipal.setScene(escenaLogin));


            TableView<BeybladeModel> tablaBeyblades = new TableView<>();


            TableColumn<BeybladeModel, String> colSistema = new TableColumn<>("Sistema");
            colSistema.setCellValueFactory(new PropertyValueFactory<>("sistema"));
            colSistema.setPrefWidth(120);

            TableColumn<BeybladeModel, String> colNombre = new TableColumn<>("Nombre Beyblade");
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colNombre.setPrefWidth(200);

            tablaBeyblades.getColumns().addAll(colSistema, colNombre);
            tablaBeyblades.setItems(BeybladeObservableList);


            botonRegistrar.setOnAction(e-> {
                String nombre = campoNombre.getText().trim();
                String sistema = comboSistema.getValue();
                String pass =  campoContrasena.getText().trim();

                BeybladeModel nuevoBey = new BeybladeModel(nombre, sistema, pass);
                BeybladeObservableList.add(nuevoBey);
                try {
                    dao.guardar(new ArrayList<>(BeybladeObservableList));

                    Notifications.create()
                            .title("registro Exitoso")
                            .text("Se guardo " + nombre + " correctamente")
                            .showInformation();
                    campoNombre.clear();
                    campoContrasena.clear();
                } catch (IOException ex){
                    Notifications.create()
                            .title("Error de Guardado")
                            .text("No se pudo escribir en el disco.")
                            .showError();
                    ex.printStackTrace();
                }
            });

            GridPane formulario = new GridPane();
            formulario.setHgap(10);
            formulario.setVgap(10);
            formulario.add(new Label("Nombre: "), 0, 0);
            formulario.add(campoNombre, 1, 0);
            formulario.add(new Label("Sistema: "), 0, 1);
            formulario.add(comboSistema, 1, 1);
            formulario.add(new Label("contraseña : "), 0, 2);
            formulario.add(campoContrasena, 1, 2);

            VBox panelIzquierdo = new VBox (15, titulo, formulario, botonRegistrar, botonCerrarSesion);
            panelIzquierdo.setPadding(new Insets(15));

            VBox panelDerecho = new VBox(10, new Label("Beyblades Registrados: "), tablaBeyblades);
            panelDerecho.setPadding(new Insets(15));

            HBox raiz = new HBox(20, panelIzquierdo, panelDerecho);
            raiz.setPadding(new Insets(15));
            raiz.setAlignment(Pos.CENTER);

            escenaPrincipal = new Scene (raiz, 700, 400);
            escenaPrincipal.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        }


    public static void main(String[] args){
        launch(args);
    }
}
