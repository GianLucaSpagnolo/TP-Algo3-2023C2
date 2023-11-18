package ui.Vista;

import Reglas.Solitario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Solitario.*;

import java.io.IOException;
import java.util.Objects;

public class VistaKlondike implements VistaJuego {
    private Stage stage;
    private Solitario modelo;

    public VistaKlondike(Solitario modelo) {
        VistaPrincipal base = new VistaPrincipal();
        this.stage = base.getStage();
        stage.show();
    }

}
