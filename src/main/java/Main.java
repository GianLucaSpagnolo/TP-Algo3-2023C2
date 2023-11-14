import Reglas.Klondike;
import Reglas.Solitario;
import Reglas.Spider;
import Solitario.ControladorArchivos;
import Solitario.Mesa;

import javafx.fxml.FXML;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import ui.Controlador.ControladorKlondike;
import ui.Controlador.ControladorSpider;
import ui.Vista.VistaKlondike;
import ui.Vista.VistaSpider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application implements Initializable {
    private static Stage stage;
    @FXML
    private ChoiceBox<String> cajaOpciones;
    private final String rutaArchivoGuardado = "src/main/resources/estadoJuego.txt";
    private final String[] variantes = {"Klondike", "Spider"};
    private static Solitario modelo;

    @Override
    public void start(Stage stage) throws IOException {
        Main.stage = stage;

        if (!ControladorArchivos.archivoEstaVacio(rutaArchivoGuardado)) {
            Mesa estadoJuego = null;
            try {
                FileInputStream fileIn = new FileInputStream(rutaArchivoGuardado);
                estadoJuego = ControladorArchivos.deserializarMesa(fileIn);
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            iniciarJuego(variantes[estadoJuego.getTipoMesa()], estadoJuego);
        } else {
            AnchorPane ventana = FXMLLoader.load((getClass().getResource("ventanaInicial.fxml")));
            Scene escena = new Scene(ventana);
            stage.setScene(escena);
            stage.setTitle("Solitario");
            stage.setResizable(false);
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cajaOpciones.getItems().addAll(variantes);
        cajaOpciones.setOnAction(this::cambiarEscena);
    }

    private void cambiarEscena(ActionEvent actionEvent) {
        String varianteElegida = cajaOpciones.getValue();
        iniciarJuego(varianteElegida, null);
    }

    private void iniciarJuego(String varianteElegida, Mesa estadoJuego) {
        if (varianteElegida.equals(variantes[0])) {
            Klondike modeloKlondike = new Klondike(null, estadoJuego);
            VistaKlondike vistaKlondike = new VistaKlondike(stage, modeloKlondike);
            ControladorKlondike controladorKlondike = new ControladorKlondike(modeloKlondike, vistaKlondike);
            modelo = modeloKlondike;
            controladorKlondike.iniciar();
        } else if (varianteElegida.equals(variantes[1])) {
            Spider modeloSpider = new Spider(null, estadoJuego);
            VistaSpider vistaSpider = new VistaSpider(stage, modeloSpider);
            ControladorSpider controladorSpider = new ControladorSpider(modeloSpider, vistaSpider);
            modelo = modeloSpider;
            controladorSpider.iniciar();
        }
    }

    @Override
    public void stop() throws Exception {
        if (modelo != null) {
            if (modelo.estaGanado()) {
                try {
                    ControladorArchivos.vaciarArchivo(rutaArchivoGuardado);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            } else {
                Mesa estadoJuego = modelo.getEstadoMesa();
                try {
                    FileOutputStream fileOut = new FileOutputStream(rutaArchivoGuardado);
                    ControladorArchivos.serializarMesa(fileOut, estadoJuego);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        // LINEAS A BORRAR CUANDO ANDE EL RESTO
        try {
            ControladorArchivos.vaciarArchivo(rutaArchivoGuardado);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        /////////

        super.stop();
    }
}
