package com.example.beyblade00.controller;

import com.example.beyblade00.Dao.BeybladeDao;
import com.example.beyblade00.Model.BeybladeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.util.ArrayList;

public class BeybladeController {
    @FXML
    private TextField campoNombre;
    @FXML
    private ComboBox<String> comboSistema;
    @FXML
    private PasswordField campoContrasena;
    @FXML
    private Button botonRegistrar;

    @FXML
    private TableView<BeybladeModel> tablaBeyblades;
    @FXML
    private TableColumn<BeybladeModel, String> colSistema;
    @FXML
    private TableColumn<BeybladeModel, String> colNombre;

    private final BeybladeDao dao = new BeybladeDao();
    private final ObservableList<BeybladeModel> BeybladeObservableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        BeybladeObservableList.addAll(dao.cargar());


        colSistema.setCellValueFactory(new PropertyValueFactory<>("Sistema"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tablaBeyblades.setItems(BeybladeObservableList);

        comboSistema.getItems().addAll("Bx (basic line)", "Ux (Unique line)", "Cx Custom line");
        comboSistema.getSelectionModel().selectFirst();

        botonRegistrar.disableProperty().bind(
                campoNombre.textProperty().isEmpty()
                        .or(campoContrasena.textProperty().isEmpty())
        );
    }
    @FXML
    private void onRegistrarClick(){
        String nombre = campoNombre.getText().trim();
        String sistema = comboSistema.getValue();
        String pass = campoContrasena.getText();

        BeybladeModel nuevoBey = new BeybladeModel(nombre, sistema, pass);
        BeybladeObservableList.add(nuevoBey);

        try{
            dao.guardar(new ArrayList<>(BeybladeObservableList));
            Notifications.create()
                    .title("Registro Exitoso")
                    .text("Se guardo " + nombre + "correctamente")
                    .showInformation();
            campoNombre.clear();
            campoContrasena.clear();
        } catch (IOException ex){
            Notifications.create()
                    .title("Error de guardado")
                    .text("No se pudo escribir en el disco")
                    .showError();
            ex.printStackTrace();
        }
    }
}
