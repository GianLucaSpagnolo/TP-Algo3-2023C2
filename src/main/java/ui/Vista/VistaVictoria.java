package ui.Vista;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class VistaVictoria {
    @FXML
    private Button botonCerrar;
    @FXML
    private Button botonNuevoJuego;

    private final Stage stage;
    private Parent ventana;
    private final Stage escenaAbierta;

    public VistaVictoria(Stage escenaAbierta) {
        this.escenaAbierta = escenaAbierta;
        stage = new Stage();
        var loader = new FXMLLoader(getClass().getResource("/ventanaVictoria.fxml"));
        loader.setController(this);
        try {
            ventana = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene escena = new Scene(ventana);
        stage.setScene(escena);
        botonCerrar.setOnAction(event -> {
            escenaAbierta.close();
            stage.close();
        });

        botonNuevoJuego.setOnAction(event -> {
            escenaAbierta.close();
            VistaInicial vi = new VistaInicial(stage);
        });

        stage.setOnCloseRequest(windowEvent -> escenaAbierta.close());

        stage.setResizable(false);
        stage.show();
    }
}
