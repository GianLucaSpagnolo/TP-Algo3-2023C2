package ui.Vista;

import Solitario.Carta;
import Solitario.Mazo;
import javafx.scene.layout.StackPane;


public class VistaMazo extends StackPane{

    public VistaMazo(Mazo mazo) {
        super();
        Carta carta = mazo.peek();
        VistaCarta vc = new VistaCarta(carta, -1,-1, 1);
        this.getChildren().add(vc);
    }

    public void actualizarMazo(Mazo mazo) {
        this.getChildren().clear();
        Carta carta = mazo.peek();
        VistaCarta vc = new VistaCarta(carta, -1,-1, 1);
        this.getChildren().add(vc);
    }

    public void pintarCartaDescarte() {
        ((VistaCarta)this.getChildren().get(0)).pintarCarta();
    }

    public void despintarCartaDescarte() {
        ((VistaCarta)this.getChildren().get(0)).despintarCarta();
    }
}
