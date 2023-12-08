package ui.Vista;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class VistaPrincipal {
    @FXML
    private ChoiceBox<String> opciones;
    private final Stage stage;
    private Pane ventana;
    private final Integer variante;

    private final double[] medidasAncho = {503, 635};
    private final double[] medidasAlto = {570, 630};

    public VistaPrincipal(Integer variante) {
        stage = new Stage();
        this.variante = variante;
        var loader = new FXMLLoader(getClass().getResource("/ventanaPrincipal.fxml"));
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
            setMedidas();
            stage.setResizable(false);
            stage.setTitle("Solitario " + NombresVariantes.getListaVariantes()[variante]);
            opciones.getItems().add("Nuevo Juego");
            opciones.setOnAction(event -> {
                opciones.getItems().remove(0);
                opciones.getItems().add("Nuevo Juego");
                VistaInicial vi = new VistaInicial(stage);
            });
        }
    }

    public Stage getStage() {
        return stage;
    }

    public Pane getVentana() {
        return ventana;
    }


    private void setMedidas() {
        stage.setWidth(medidasAncho[variante]);
        stage.setHeight(medidasAlto[variante]);
    }
}
