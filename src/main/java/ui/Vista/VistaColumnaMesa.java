package ui.Vista;

import Solitario.Carta;
import Solitario.Columna;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class VistaColumnaMesa extends VBox {
    private int tamaño;
    private int indice;

    public VistaColumnaMesa(Columna columnaMesa, int indiceMesa)  {
        super(-70);
        indice = indiceMesa;
        ArrayList<Carta> listaCartas = columnaMesa.getCartas();
        tamaño = listaCartas.size();
        for (int i = columnaMesa.size()-1; i >= 0; i--) {
            Carta carta = listaCartas.get(i);
            VistaCarta vc = new VistaCarta(carta, i, indiceMesa);
            this.getChildren().add(vc);
        }
    }

    public int size() {
        return tamaño;
    }

    public void pintarCartas(int indice) {
        int indiceEnColumna = tamaño-indice-1; //indice adaptado al VBox
        for (int i = tamaño-1; i >= indiceEnColumna; i--) {
            ((VistaCarta)this.getChildren().get(i)).pintarCarta();
        }
    }

    public void despintarCartas() {
        for (int i = tamaño-1; i >= 0; i--) {
            ((VistaCarta)this.getChildren().get(i)).despintarCarta();
        }
    }

    public int getIndice() {
        return indice;
    }

    public void actualizarColumna(Columna columna) {
        this.getChildren().clear();
        ArrayList<Carta> listaCartas = columna.getCartas();
        tamaño = listaCartas.size();
        if (columna.isEmpty()) {
            ImageView iv = new ImageView("Cartas/Medium/transparente.png");
            this.getChildren().add(iv);
        } else {
            for (int i = columna.size()-1; i >= 0; i--) {
                Carta carta = listaCartas.get(i);
                VistaCarta vc = new VistaCarta(carta, i, indice);
                this.getChildren().add(vc);
            }
        }
    }

}
