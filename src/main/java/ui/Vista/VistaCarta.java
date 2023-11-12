package ui.Vista;

import Solitario.Carta;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;


public class VistaCarta {
    public static Canvas CanvasCarta(Carta modelo) {
        Canvas canvas = new Canvas(71, 94);
        var gc = canvas.getGraphicsContext2D();
        switch (modelo.getPalo()) {
            case PICAS:
                gc.setFill(Color.BLACK);
                gc.fillText(String.valueOf(modelo.getNumero()), 10, 10);
                gc.fillText("♠", 10, 20);
            case DIAMANTES:
                gc.setFill(Color.RED);
                gc.fillText(String.valueOf(modelo.getNumero()), 10, 10);
                gc.fillText("♦", 10, 20);
            case CORAZONES:
                gc.setFill(Color.RED);
                gc.fillText(String.valueOf(modelo.getNumero()), 10, 10);
                gc.fillText("♥", 10, 20);
            case TREBOLES:
                gc.setFill(Color.BLACK);
                gc.fillText(String.valueOf(modelo.getNumero()), 10, 10);
                gc.fillText("♣", 10, 20);
        }
        return canvas;
    }
}
