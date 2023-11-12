package ui.Vista;

import Reglas.Solitario;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import Solitario.*;

public class VistaKlondike implements VistaJuego {
    private Scene escena;

    public VistaKlondike(Stage stage, Solitario modelo) {
        Carta ejemplo = new Carta(5, Palos.PICAS);
        Canvas carta = VistaCarta.CanvasCarta(ejemplo);
        stage.getChildren().
    }
}
