package Reglas;

import Solitario.Carta;
import Solitario.Columna;

public class ColumnaKlondike extends Columna {
    public ColumnaKlondike() {
        super();
    }

    public boolean esCadena(Integer indice) {
        for (int i = 0; i < indice; i++) {
            if (cartas.get(i).getNumero() != (cartas.get(i+1).getNumero() - 1)) {
                return false;
            }
            if (cartas.get(i).getColor() == cartas.get(i+1).getColor()) {
                return false;
            }
        }
        return true;
    }

    public Columna obtenerSegmento(Integer indice) {
        Columna auxiliar = new ColumnaKlondike();
        if (!esCadena(indice)) {
            return null;
        }
        for (int i = indice; i >= 0; i--) {
            auxiliar.push(cartas.get(i));
            cartas.remove(i);
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
}
