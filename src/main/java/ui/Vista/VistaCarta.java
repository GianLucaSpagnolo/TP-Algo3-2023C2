package ui.Vista;

import Solitario.Carta;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;


public class VistaCarta extends ImageView {
    private final int indice;
    private final int indiceColumna;
    public VistaCarta(Carta carta, int indice, int indiceColumnaMesa) {
        super(obtenerRuta(carta));
        this.indice = indice;
        this.indiceColumna = indiceColumnaMesa;
    }

    private static String obtenerRuta(Carta carta) {
        if (carta == null) {
            return "Cartas/Medium/transparente.png";
        }
        if (carta.esVisible()) {
            return "Cartas/Medium/" + carta.getPalo().nombre + " " + carta.getNumero() + ".png";
        }
        return "Cartas/Medium/Dada Vuelta.png";
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
}

