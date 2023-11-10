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
                String datos = (String) modelo.getNumero() + "♠";
                gc.fillText("%d ♠", modelo.getNumero(), 10, 10);
        }
    }
}
