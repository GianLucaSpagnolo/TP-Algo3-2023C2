package ui.Vista;

import Solitario.Carta;
import Solitario.Palos;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;


public class VistaCarta extends ImageView{
    private Carta carta;
    public VistaCarta(Carta carta) {
        super(obtenerRuta(carta));
    }

    private static String obtenerRuta(Carta carta) {
        if (carta.esVisible()) {
            return "Cartas/Medium/" + carta.getPalo().nombre + " " + carta.getNumero() + ".png";
        }
        return "Cartas/Medium/Dada Vuelta.png";
    }
}

