package Main;

import Reglas.Klondike;
import Reglas.Solitario;
import Reglas.Spider;
import Solitario.*;
import Solitario.Mesa;
import javafx.application.Application;
import javafx.stage.Stage;
import ui.Controlador.ControladorKlondike;
import ui.Controlador.ControladorSpider;
import ui.Vista.NombresVariantes;
import ui.Vista.VistaInicial;
import ui.Vista.VistaKlondike;
import ui.Vista.VistaSpider;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class Main extends Application {
    private static Stage stage;
    private final String rutaArchivoGuardado = "src/main/resources/estadoJuego.txt";
    private static Solitario modelo;
    private static final String[] variantes = NombresVariantes.getListaVariantes();

    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        Main.stage = stage;
        if (!ControladorArchivos.archivoEstaVacio(rutaArchivoGuardado)) {
            FileInputStream fileIn = new FileInputStream(rutaArchivoGuardado);
            Mesa estadoJuego = ControladorArchivos.deserializarMesa(fileIn);
            iniciarJuego(variantes[estadoJuego.getTipoMesa()], estadoJuego);
        } else {
            VistaInicial vi = new VistaInicial(null);
        }
    }

    public static void iniciarJuego(String varianteElegida, Mesa estadoJuego) {
        if (varianteElegida != null) {
            if (varianteElegida.equals(variantes[0])) {
                Klondike modeloKlondike = new Klondike(null, estadoJuego);
                if (estadoJuego == null) {
                    modeloKlondike.repartirCartasInicio();
                }
                VistaKlondike vistaKlondike = new VistaKlondike(modeloKlondike);
                ControladorKlondike controladorKlondike = new ControladorKlondike(modeloKlondike, vistaKlondike);
                modelo = modeloKlondike;
                controladorKlondike.iniciar();
            } else if (varianteElegida.equals(variantes[1])) {
                Spider modeloSpider = new Spider(null, estadoJuego);
                if (estadoJuego == null) {
                    modeloSpider.repartirCartasInicio();
                }
                VistaSpider vistaSpider = new VistaSpider(modeloSpider);
                ControladorSpider controladorSpider = new ControladorSpider(modeloSpider, vistaSpider);
                modelo = modeloSpider;
                controladorSpider.iniciar();
            }
        }
    }

    @Override
    public void stop() throws Exception {
        if (modelo != null) {
            if (modelo.estaGanado()) {
                ControladorArchivos.vaciarArchivo(rutaArchivoGuardado);
            } else {
                Mesa estadoJuego = modelo.getEstadoMesa();
                FileOutputStream fileOut = new FileOutputStream(rutaArchivoGuardado);
                ControladorArchivos.serializarMesa(fileOut, estadoJuego);
            }
        }
        super.stop();
    }
}
