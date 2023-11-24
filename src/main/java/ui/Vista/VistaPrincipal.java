package ui.Vista;
import Main.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class VistaPrincipal {
    @FXML
    private ChoiceBox<String> opciones;
    private Stage stage;
    private Pane ventana;
    public VistaPrincipal() {
        stage = new Stage();
        var loader = new FXMLLoader(getClass().getResource("/ventanaPrincipal.fxml"));
        loader.setController(this);
        try {
            ventana = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene escena = new Scene(ventana);
        stage.setScene(escena);
        stage.setResizable(false);
        opciones.getItems().add("Nuevo Juego");
        opciones.setOnAction(event -> {
            opciones.getItems().remove(0);
            opciones.getItems().add("Nuevo Juego");
            VistaInicial vi = new VistaInicial(stage);
        });
    }

    public Stage getStage() {
        return stage;
    }

    public Pane getVentana() {
        return ventana;
    }
}
