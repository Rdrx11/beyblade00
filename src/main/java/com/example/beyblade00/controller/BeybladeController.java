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
import java.util.List;

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
    private Button botonEleminar;

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
        BeybladeObservableList.addAll(dao.cargar(BeybladeDao.Archivo_Bx));
        BeybladeObservableList.addAll(dao.cargar(BeybladeDao.Archivo_Ux));
        BeybladeObservableList.addAll(dao.cargar(BeybladeDao.Archivo_Cx));

        colSistema.setCellValueFactory(new PropertyValueFactory<>("Sistema"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tablaBeyblades.setItems(BeybladeObservableList);

        comboSistema.getItems().addAll("Bx (basic line)", "Ux (Unique line)", "Cx Custom line");
        comboSistema.getSelectionModel().selectFirst();

        botonRegistrar.disableProperty().bind(
                campoNombre.textProperty().isEmpty()
                        .or(campoContrasena.textProperty().isEmpty())
        );

        botonEleminar.disableProperty().bind(
                tablaBeyblades.getSelectionModel().selectedItemProperty().isNull()
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
            guardarSistema();
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
    @FXML
    private void onEleminarClick(){
        BeybladeModel seleccionado = tablaBeyblades.getSelectionModel().getSelectedItem();
        if(seleccionado != null){
            BeybladeObservableList.remove(seleccionado);
            try {
                guardarSistema();
                Notifications.create()
                        .title("Eleminado")
                        .text("Se elemino " + seleccionado.getNombre() + " correctamente")
                        .show();
                } catch (IOException e) {
                    Notifications.create()
                            .title("ERROR")
                            .text("no se pudo actualizar el guardado")
                            .showError();
                    e.printStackTrace();
            }
        }
    }
    private void guardarSistema() throws IOException {
        List<BeybladeModel> listaBx = new ArrayList<>();
        List<BeybladeModel> listaUx = new ArrayList<>();
        List<BeybladeModel> listaCx = new ArrayList<>();


        for (BeybladeModel bey : BeybladeObservableList) {
            String sistema = bey.getSistema().toUpperCase();
            if (sistema.startsWith("BX")) {
                listaBx.add(bey);
            } else if (sistema.startsWith("UX")) {
                listaUx.add(bey);
            } else if (sistema.startsWith("CX")) {
                listaCx.add(bey);
            }
        }
        dao.guardar(BeybladeDao.Archivo_Bx, listaBx);
        dao.guardar(BeybladeDao.Archivo_Ux, listaUx);
        dao.guardar(BeybladeDao.Archivo_Cx, listaCx);
    }
}
