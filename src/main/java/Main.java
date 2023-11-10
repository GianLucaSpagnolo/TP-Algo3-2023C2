import Reglas.Klondike;
import Reglas.Spider;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ui.Controlador.ControladorKlondike;
import ui.Controlador.ControladorSpider;
import ui.Vista.VistaKlondike;
import ui.Vista.VistaSpider;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Main extends Application implements Initializable {
    @FXML
    private ChoiceBox<String> cajaOpciones;
    private String[] variantes = {"Klondike", "Spider"};
    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane ventana = FXMLLoader.load((getClass().getResource("ventanaInicial.fxml")));
        Scene escena = new Scene(ventana);
        stage.setScene(escena);
        stage.setTitle("Solitario");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cajaOpciones.getItems().addAll(variantes);
        cajaOpciones.setOnAction(this::cambiarEscena);
    }

    public void cambiarEscena(ActionEvent actionEvent) {
        Stage stage = null;
        String varianteElegida = cajaOpciones.getValue();
        try {
            AnchorPane ventana = FXMLLoader.load((Objects.requireNonNull(getClass().getClassLoader().getResource("ventanaJuego.fxml"))));
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(ventana);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (varianteElegida) {
            case "Klondike":
                Klondike modeloKlondike = new Klondike(null, null);
                VistaKlondike vistaKlondike = new VistaKlondike(stage, modeloKlondike);
                ControladorKlondike controladorKlondike = new ControladorKlondike(modeloKlondike, vistaKlondike);
                controladorKlondike.iniciar();
            case "Spider":
                Spider modeloSpider = new Spider(null, null);
                VistaSpider vistaSpider = new VistaSpider(stage, modeloSpider);
                ControladorSpider controladorSpider = new ControladorSpider(modeloSpider, vistaSpider);
                controladorSpider.iniciar();
        }
    }
}
