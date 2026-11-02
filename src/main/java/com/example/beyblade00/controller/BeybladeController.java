package com.example.beyblade00.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BeybladeController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
