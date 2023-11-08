import Reglas.Klondike;
import Reglas.Solitario;
import Reglas.Spider;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import ui.Vista.VistaJuego;
import ui.Vista.VistaKlondike;
import ui.Vista.VistaSpider;

public class Main extends Application {
    private Solitario modelo;
    @FXML
    Button botonKlondike;

    @FXML
    Button botonSpider;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        var loaderVentanaInicial = new FXMLLoader(getClass().getResource("ventanaInicial.fxml"));
        loaderVentanaInicial.setController(this);
        FlowPane ventana = loaderVentanaInicial.load();

        var loaderMesa = new FXMLLoader(getClass().getResource("Mesa.fxml"));
        TitledPane mesa = loaderMesa.load();

        botonKlondike.setOnAction(actionEvent -> {
            modelo = new Klondike(null, null);
            loaderMesa.setController(this);
            var scene = new Scene(mesa, 600,600);
            stage.setScene(scene);
            stage.show();
            VistaKlondike vista = new VistaKlondike(stage, modelo);
        });

        botonSpider.setOnAction(actionEvent -> {
            modelo = new Spider(null, null);
            VistaSpider vista = new VistaSpider(stage, modelo);
        });

        var scene = new Scene(ventana, 500, 300);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
