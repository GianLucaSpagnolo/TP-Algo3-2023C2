package ui.Vista;

import Solitario.Carta;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;


public class VistaCarta extends ImageView {
    private final int indice;
    private final int indiceColumna;
    private final Carta carta;
    public VistaCarta(Carta carta, int indice, int indiceColumnaMesa) {
        super(obtenerRuta(carta));
        this.carta = carta;
        this.indice = indice;
        this.indiceColumna = indiceColumnaMesa;
    }

    private static String obtenerRuta(Carta carta) {
        if (carta == null) {
            return "Cartas/Small/transparente.png";
        }
        if (carta.esVisible()) {
            return "Cartas/Small/" + carta.getPalo().nombre + " " + carta.getNumero() + ".png";
        }
        return "Cartas/Small/Dada Vuelta.png";
    }

    public int getIndiceColumna() {
        return indiceColumna;
    }

    public int getIndice() {
        return indice;
    }

    public void pintarCarta() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.3);
        this.setEffect(colorAdjust);
    }

    public void despintarCarta() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0);
        this.setEffect(colorAdjust);
    }

    public Carta getCarta() {
        return carta;
    }
}

