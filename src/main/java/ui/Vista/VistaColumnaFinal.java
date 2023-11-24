package ui.Vista;

import Solitario.Carta;
import Solitario.Columna;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class VistaColumnaFinal extends StackPane {
    private final int indice;

    public VistaColumnaFinal(Columna columnaFinal, int indice){
        super();
        this.indice = indice;
        VistaCarta base = new VistaCarta(null, -1, indice);
        this.getChildren().add(base);
        Carta carta = columnaFinal.peek();
        if (carta != null) {
            VistaCarta vc = new VistaCarta(carta, -1, indice);
            this.getChildren().add(vc);
        }
    }

    public int getIndice() {
        return indice;
    }

    public void actualizarColumnaFinal(Columna columna) {
        this.getChildren().clear();
        VistaCarta base = new VistaCarta(null, 0, indice);
        this.getChildren().add(base);
        Carta carta = columna.peek();
        if (carta != null) {
            VistaCarta vc = new VistaCarta(carta, -1, indice);
            this.getChildren().add(vc);
        }
    }

    public void pintarCarta() {
        ((VistaCarta)this.getChildren().get(1)).pintarCarta();
    }

    public void despintarCarta() {
        ((VistaCarta)this.getChildren().get(1)).despintarCarta();
    }
}
