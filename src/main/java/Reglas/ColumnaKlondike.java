package Reglas;

import Solitario.Carta;
import Solitario.Columna;

public class ColumnaKlondike extends Columna {
    public ColumnaKlondike() {
        super();
    }

    public boolean esCadena(Integer indice) {
        for (int i = 0; i < indice; i++) {
            Carta carta1 = cartas.get(i);
            Carta carta2 = cartas.get(i+1);
            if (!Klondike.sonCompatibles(carta1, carta2)) {
                return false;
            }
        }
        return true;
    }

    public Columna obtenerSegmento(Integer indice) {
        if (isEmpty()) {
            return null;
        }
        Columna auxiliar = new ColumnaKlondike();
        if (!esCadena(indice)) {
            return null;
        }
        for (int i = indice; i >= 0; i--) {
            auxiliar.push(pop());
        }
        return auxiliar;
    }

    public boolean insertarSegmento(Columna segmento) {
        Carta ultimaCarta = segmento.getCartas().get(cartas.size()-1);
        if ((cartas.get(0).getNumero() == ultimaCarta.getNumero()+1) && (cartas.get(0).getColor() != ultimaCarta.getColor())) {
            cartas.addAll(0, segmento);
            return true;
        }
        if (this.isEmpty() || !this.peek().esVisible()) {
            cartas.addAll(0, segmento);
            return true;
        }
        return false;
    }

    public boolean

}
