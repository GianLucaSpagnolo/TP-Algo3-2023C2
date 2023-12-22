package ui.Vista;

import Solitario.Carta;
import Solitario.Columna;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class VistaColumnaMesa extends VBox implements VistaColumna {
    private int tamanioColumna;
    private final int indice;

    public VistaColumnaMesa(Columna columnaMesa, int indiceMesa, int tamanio)  {
        super((-45) - (23 * (tamanio - 1)));
        indice = indiceMesa;
        ArrayList<Carta> listaCartas = columnaMesa.getCartas();
        tamanioColumna = listaCartas.size();
        VistaCarta base = new VistaCarta(null, 0, indice, tamanio);
        this.getChildren().add(base);
        for (int i = columnaMesa.size()-1; i >= 0; i--) {
            Carta carta = listaCartas.get(i);
            VistaCarta vc = new VistaCarta(carta, i, indiceMesa, tamanio);
            this.getChildren().add(vc);
        }

        if (tamanioColumna >= 22) {
            this.setSpacing((-45) - (23 * (tamanio - 1)) - 3.6 - (3 * (tamanio - 1)));
        }
        if (tamanioColumna >= 32) {
            this.setSpacing((-45) - (23 * (tamanio - 1)) - 7 - (3 * (tamanio - 1)));
        }
    }

    public int size() {
        return tamanioColumna;
    }

    public void pintar(int indice) {
        int indiceEnColumna = tamanioColumna-indice; //indice adaptado al VBox
        for (int i = tamanioColumna; i >= indiceEnColumna; i--) {
            ((VistaCarta)this.getChildren().get(i)).pintarCarta();
        }
    }

    public void despintar() {
        for (int i = tamanioColumna; i >= 0; i--) {
            ((VistaCarta)this.getChildren().get(i)).despintarCarta();
        }
    }

    public int getIndice() {
        return indice;
    }

    public void actualizar(Columna columna, int tamanio) {
        if (columna.size() >= 22) {
            this.setSpacing((-45) - (23 * (tamanio - 1)) - 3.6 - (3 * (tamanio - 1)));
        }
        if (columna.size() >= 32) {
            this.setSpacing((-45) - (23 * (tamanio - 1)) - 7 - (3 * (tamanio - 1)));
        }
        if (columna.size() < 22) {
            this.setSpacing((-45) - (23 * (tamanio - 1)));
        }
        this.getChildren().clear();
        VistaCarta base = new VistaCarta(null, 0, indice, tamanio);
        this.getChildren().add(base);
        ArrayList<Carta> listaCartas = columna.getCartas();
        tamanioColumna = listaCartas.size();
        for (int i = columna.size()-1; i >= 0; i--) {
            Carta carta = listaCartas.get(i);
            VistaCarta vc = new VistaCarta(carta, i, indice, tamanio);
            this.getChildren().add(vc);
        }
    }

}
