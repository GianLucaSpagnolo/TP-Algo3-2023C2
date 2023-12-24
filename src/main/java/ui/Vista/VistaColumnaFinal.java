package ui.Vista;

import Solitario.Carta;
import Solitario.Columna;
import javafx.scene.layout.StackPane;

public class VistaColumnaFinal extends StackPane implements VistaColumna {
    private final int indice;

    public VistaColumnaFinal(Columna columnaFinal, int indice, int tamanio){
        super();
        this.indice = indice;
        VistaCarta base = new VistaCarta(null, -1, indice, tamanio);
        this.getChildren().add(base);
        Carta carta = columnaFinal.peek();
        if (carta != null) {
            VistaCarta vc = new VistaCarta(carta, -1, indice, tamanio);
            this.getChildren().add(vc);
        }
    }

    public int getIndice() {
        return indice;
    }

    public void actualizar(Columna columna, int tamanio) {
        this.getChildren().clear();
        VistaCarta base = new VistaCarta(null, 0, indice, tamanio);
        this.getChildren().add(base);
        Carta carta = columna.peek();
        if (carta != null) {
            VistaCarta vc = new VistaCarta(carta, -1, indice, tamanio);
            this.getChildren().add(vc);
        }
    }

    public void pintar(int indice) {
        ((VistaCarta)this.getChildren().get(indice)).pintarCarta();
    }

    public void despintar() {
        ((VistaCarta)this.getChildren().get(1)).despintarCarta();
    }
}
