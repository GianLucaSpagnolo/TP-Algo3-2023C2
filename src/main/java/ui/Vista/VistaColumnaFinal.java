package ui.Vista;

import Solitario.Carta;
import Solitario.Columna;
import javafx.scene.layout.StackPane;

public class VistaColumnaFinal extends StackPane {

    public VistaColumnaFinal(Columna columnaFinal){
        super();
        Carta carta = columnaFinal.peek();
        if (carta != null) {
            VistaCarta vc = new VistaCarta(carta, -1,-1);
            this.getChildren().add(vc);
        }
    }

}
