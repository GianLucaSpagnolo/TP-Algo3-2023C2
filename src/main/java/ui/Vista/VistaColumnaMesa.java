package ui.Vista;

import Solitario.Carta;
import Solitario.Columna;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class VistaColumnaMesa extends VBox {
    private int tamanio;
    private final int indice;

    public VistaColumnaMesa(Columna columnaMesa, int indiceMesa)  {
        super(-70);
        indice = indiceMesa;
        ArrayList<Carta> listaCartas = columnaMesa.getCartas();
        tamanio = listaCartas.size();
        VistaCarta base = new VistaCarta(null, 0, indice);
        this.getChildren().add(base);
        for (int i = columnaMesa.size()-1; i >= 0; i--) {
            Carta carta = listaCartas.get(i);
            VistaCarta vc = new VistaCarta(carta, i, indiceMesa);
            this.getChildren().add(vc);
        }
    }

    public int size() {
        return tamanio;
    }

    public void pintarCartas(int indice) {
        int indiceEnColumna = tamanio-indice; //indice adaptado al VBox
        for (int i = tamanio; i >= indiceEnColumna; i--) {
            ((VistaCarta)this.getChildren().get(i)).pintarCarta();
        }
    }

    public void despintarCartas() {
        for (int i = tamanio; i >= 0; i--) {
            ((VistaCarta)this.getChildren().get(i)).despintarCarta();
        }
    }

    public int getIndice() {
        return indice;
    }

    public void actualizarColumna(Columna columna) {
        this.getChildren().clear();
        VistaCarta base = new VistaCarta(null, 0, indice);
        this.getChildren().add(base);
        ArrayList<Carta> listaCartas = columna.getCartas();
        tamanio = listaCartas.size();
        for (int i = columna.size()-1; i >= 0; i--) {
            Carta carta = listaCartas.get(i);
            VistaCarta vc = new VistaCarta(carta, i, indice);
            this.getChildren().add(vc);
        }
    }

}
