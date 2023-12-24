package ui.Vista;

import Solitario.Carta;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;


public class VistaCarta extends ImageView {
    private final int indice;
    private final int indiceColumna;
    private final Carta carta;
    public VistaCarta(Carta carta, int indice, int indiceColumnaMesa, int tamanio) {
        super(obtenerRuta(carta, tamanio));
        this.carta = carta;
        this.indice = indice;
        this.indiceColumna = indiceColumnaMesa;
    }

    private static String obtenerRuta(Carta carta, int tamanio) {
        String direccion = "";
        if (tamanio == 1)
            direccion = "Cartas/Small/";
        else if (tamanio == 2)
            direccion = "Cartas/Medium/";

        if (carta == null) {
            return direccion + "transparente.png";
        }
        if (carta.esVisible()) {
            return direccion + carta.getPalo().nombre + " " + carta.getNumero() + ".png";
        }
        return direccion + "DadaVuelta.png";
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

