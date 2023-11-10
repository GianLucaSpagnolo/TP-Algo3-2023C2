package ui.Controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControladorInicial implements Initializable {
    @FXML
    private ChoiceBox<String> cajaOpciones;
    private String[] variantes = {"Klondike", "Spider"};
    private Stage stage;
    private Scene scene;
    private Parent ventana;

    public void cambiarEscena(ActionEvent actionEventevent) {
        String varianteElegida = cajaOpciones.getValue();
        try {
            ventana = FXMLLoader.load((Objects.requireNonNull(getClass().getClassLoader().getResource("ventanaJuego.fxml"))));
            stage = (Stage) ((Node) actionEventevent.getSource()).getScene().getWindow();
            scene = new Scene(ventana);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cajaOpciones.getItems().addAll(variantes);
        cajaOpciones.setOnAction(this::cambiarEscena);
    }

}
