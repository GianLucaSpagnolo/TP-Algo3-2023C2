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
    private Stage stage;
    private Solitario modelo;

    public VistaSpider(Solitario modelo) {
        this.modelo = modelo;
        VistaPrincipal base = new VistaPrincipal();
        this.stage = base.getStage();
        stage.show();
    }
}
