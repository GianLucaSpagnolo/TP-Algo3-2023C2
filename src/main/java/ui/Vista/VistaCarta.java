package ui.Vista;

import Solitario.Carta;
import Solitario.Palos;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;


public class VistaCarta {
    public static Canvas CanvasCarta(Carta modelo) {
        Canvas canvas = new Canvas(71, 94);
        var gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(50,70,120,160);
        if (modelo.getPalo() == Palos.PICAS) {
            gc.setFill(Color.BLACK);
            gc.fillText(String.valueOf(modelo.getNumero()), 50, 80);
            gc.setFill(Color.BLACK);
            gc.fillText("♠", 60, 80);
        } else if (modelo.getPalo() == Palos.DIAMANTES) {
            gc.setFill(Color.RED);
            gc.fillText(String.valueOf(modelo.getNumero()), 50, 80);
            gc.setFill(Color.RED);
            gc.fillText("♦", 60, 80);
        } else if (modelo.getPalo() == Palos.CORAZONES) {
            gc.setFill(Color.RED);
            gc.fillText(String.valueOf(modelo.getNumero()), 50, 80);
            gc.setFill(Color.RED);
            gc.fillText("♥", 60, 80);
        } else if (modelo.getPalo() == Palos.TREBOLES) {
            gc.setFill(Color.BLACK);
            gc.fillText(String.valueOf(modelo.getNumero()), 50, 80);
            gc.setFill(Color.BLACK);
            gc.fillText("♣", 60, 80);
        }
        return canvas;
    }
}
