package ui.Vista;

import Solitario.Carta;
import Solitario.Mazo;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;


public class VistaMazo{
    private StackPane mazo;
    public VistaMazo(Mazo mazo) {
        StackPane sp = new StackPane();
        Carta carta = mazo.peek();
        if (carta != null) {
            VistaCarta vc = new VistaCarta(carta);
            sp.getChildren().add(vc);
        }
        this.mazo = sp;
    }
    public StackPane getMazo() {
        return mazo;
    }
}
