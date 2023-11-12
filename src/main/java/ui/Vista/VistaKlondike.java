package ui.Vista;

import Reglas.Solitario;
import com.sun.tools.javac.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import Solitario.*;
import javafx.stage.Window;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class VistaKlondike implements VistaJuego {
    private AnchorPane ventana;
    private Scene escena;

    public VistaKlondike(Stage stage, Solitario modelo) {
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
