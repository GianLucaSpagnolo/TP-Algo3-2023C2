package ui.Vista;

import Solitario.Carta;
import Solitario.Columna;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class VistaColumnaMesa {
    private VBox columna;

    public VistaColumnaMesa(Columna columnaMesa) {
        this.columna = new VBox(-70);
        ArrayList<Carta> listaCartas = columnaMesa.getCartas();
        for (int i = columnaMesa.size()-1; i >= 0; i--) {
            Carta carta = listaCartas.get(i);
            VistaCarta vc = new VistaCarta(carta);
            this.columna.getChildren().add(vc);
        }
    }

    public VBox getColumna() {
        return columna;
    }
}
