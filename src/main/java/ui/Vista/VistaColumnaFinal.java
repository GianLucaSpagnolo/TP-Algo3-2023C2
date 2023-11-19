package ui.Vista;

import Solitario.Carta;
import Solitario.Columna;
import javafx.scene.layout.StackPane;

public class VistaColumnaFinal {
    private StackPane columna;

    public VistaColumnaFinal(Columna columnaFinal) {
        this.columna = new StackPane();
        Carta carta = columnaFinal.peek();
        if (carta != null) {
            VistaCarta vc = new VistaCarta(carta);
            this.columna.getChildren().add(vc);
        }
    }

    public StackPane getColumna() {
        return columna;
    }

}
