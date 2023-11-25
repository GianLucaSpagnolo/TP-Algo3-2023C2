package ui.Vista;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import Main.Main;

import java.io.IOException;

public class VistaInicial {
    @FXML
    private ChoiceBox<String> cajaOpciones;
    @FXML
    private Button botonCancelar;
    private static final String[] variantes = {"Klondike", "Spider"};
    private final Stage stage;
    private Parent ventana;
    private final Stage escenaAbierta;

    public VistaInicial(Stage escenaAbierta) {
        this.escenaAbierta = escenaAbierta;
        stage = new Stage();
        var loader = new FXMLLoader(getClass().getResource("/ventanaInicial.fxml"));
        loader.setController(this);
        try {
            ventana = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene escena = new Scene(ventana);
        stage.setScene(escena);
        cajaOpciones.getItems().addAll(variantes);
        cajaOpciones.setOnAction(this::cambiarEscena);
        botonCancelar.setOnAction(s -> stage.close());
        stage.setResizable(false);
        stage.show();
    }

    public void cambiarEscena(ActionEvent actionEvent) {
        String varianteElegida = cajaOpciones.getValue();
        if (escenaAbierta != null) {
            escenaAbierta.close();
        }
        stage.close();
        while (!cajaOpciones.getItems().isEmpty()) {
            cajaOpciones.getItems().remove(0);
        }
        cajaOpciones.getItems().addAll(variantes);
        Main.iniciarJuego(varianteElegida, null);
    }

    public static String[] getVariantes() {
        return variantes;
    }
}