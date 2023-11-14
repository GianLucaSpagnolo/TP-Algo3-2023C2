package ui.Vista;

import Reglas.Solitario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Solitario.*;

import java.io.IOException;
import java.util.Objects;

public class VistaSpider implements VistaJuego{
    private AnchorPane ventana;
    private Scene escena;

    public VistaSpider(Stage stage, Solitario modelo) {
        try {
            ventana = FXMLLoader.load((Objects.requireNonNull(getClass().getClassLoader().getResource("ventanaJuego.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Carta ejemplo = new Carta(5, Palos.PICAS);
        ejemplo.darVuelta();
        Canvas carta = VistaCarta.CanvasCarta(ejemplo);
        ventana.getChildren().add(carta);
        escena = new Scene(ventana);
        stage.setScene(escena);
        stage.show();
    }
}
