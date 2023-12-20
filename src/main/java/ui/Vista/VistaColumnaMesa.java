package ui.Vista;

import Solitario.Carta;
import Solitario.Columna;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class VistaColumnaMesa extends VBox implements VistaColumna {
    private int tamanio;
    private final int indice;

    public VistaColumnaMesa(Columna columnaMesa, int indiceMesa)  {
        super(-45);
        indice = indiceMesa;
        ArrayList<Carta> listaCartas = columnaMesa.getCartas();
        tamanio = listaCartas.size();
        VistaCarta base = new VistaCarta(null, 0, indice, 2);
        this.getChildren().add(base);
        for (int i = columnaMesa.size()-1; i >= 0; i--) {
            Carta carta = listaCartas.get(i);
            VistaCarta vc = new VistaCarta(carta, i, indiceMesa, 2);
            this.getChildren().add(vc);
        }
        if (tamanio >= 22) {
            this.setSpacing(-48);
        }
        if (tamanio >= 32) {
            this.setSpacing(-51);
        }
    }

    public int size() {
        return tamanio;
    }

    public void pintar(int indice) {
        int indiceEnColumna = tamanio-indice; //indice adaptado al VBox
        for (int i = tamanio; i >= indiceEnColumna; i--) {
            ((VistaCarta)this.getChildren().get(i)).pintarCarta();
        }
    }

    public void despintar() {
        for (int i = tamanio; i >= 0; i--) {
            ((VistaCarta)this.getChildren().get(i)).despintarCarta();
        }
    }

    public int getIndice() {
        return indice;
    }

    public void actualizar(Columna columna) {
        if (columna.size() >= 22) {
            this.setSpacing(-48);
        }
        if (columna.size() >= 32) {
            this.setSpacing(-51);
        }
        if (columna.size() < 22) {
            this.setSpacing(-45);
        }
        this.getChildren().clear();
        VistaCarta base = new VistaCarta(null, 0, indice, 2);
        this.getChildren().add(base);
        ArrayList<Carta> listaCartas = columna.getCartas();
        tamanio = listaCartas.size();
        for (int i = columna.size()-1; i >= 0; i--) {
            Carta carta = listaCartas.get(i);
            VistaCarta vc = new VistaCarta(carta, i, indice, 2);
            this.getChildren().add(vc);
        }
    }

}
