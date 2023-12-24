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
    private final Stage stage;
    private Parent ventana;
    private final Stage escenaAbierta;

    public VistaInicial(Stage escenaAbierta) {
        this.escenaAbierta = escenaAbierta;
        stage = new Stage();
        var loader = new FXMLLoader(getClass().getResource("/ventanaInicial.fxml"));
        loader.setController(this);

        boolean error = false;
        try {
            ventana = loader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            error = true;
            stage.close();
        }

        if (!error) {
            Scene escena = new Scene(ventana);
            stage.setScene(escena);
            stage.setTitle("Menu inicial");
            cajaOpciones.getItems().addAll(NombresVariantes.getListaVariantes());
            cajaOpciones.setOnAction(this::cambiarEscena);
            botonCancelar.setOnAction(s -> {
                VistaPrincipal.actualizarContadorVentanaSeleccion();
                stage.close();
            });
            stage.setResizable(false);

            VistaPrincipal.setStageVentanaSeleccion(stage);
            stage.setOnCloseRequest(windowEvent -> VistaPrincipal.actualizarContadorVentanaSeleccion());
            stage.show();
        }
    }

    public void cambiarEscena(ActionEvent actionEvent) {
        String varianteElegida = cajaOpciones.getValue();
        if (escenaAbierta != null) {
            escenaAbierta.close();
        }
        VistaPrincipal.actualizarContadorVentanaSeleccion();
        stage.close();
        while (!cajaOpciones.getItems().isEmpty()) {
            cajaOpciones.getItems().remove(0);
        }
        cajaOpciones.getItems().addAll(NombresVariantes.getListaVariantes());
        Main.iniciarJuego(varianteElegida, null);
    }
}