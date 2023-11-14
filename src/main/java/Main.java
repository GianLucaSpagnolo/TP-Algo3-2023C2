import Reglas.Klondike;
import Reglas.Solitario;
import Reglas.Spider;
import Solitario.ControladorArchivos;
import Solitario.Mesa;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import ui.Controlador.ControladorKlondike;
import ui.Controlador.ControladorSpider;
import ui.Vista.VistaInicial;
import ui.Vista.VistaKlondike;
import ui.Vista.VistaSpider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Main extends Application {
    private static Stage stage;
    @FXML
    private Button botonNuevoJuego;
    private final String rutaArchivoGuardado = "src/main/resources/estadoJuego.txt";
    private static final String[] variantes = {"Klondike", "Spider"};
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
            VistaInicial.mostrarVentanaInicial(variantes, stage);
        }
    }

    public static void main(String[] args) {
        launch();
    }


    public void iniciarJuego(String varianteElegida, Mesa estadoJuego) {
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
