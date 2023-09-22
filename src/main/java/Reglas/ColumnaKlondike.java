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
            if (carta2 == null) {
                return carta1.getNumero() == 13;
            }
            if (carta1.getNumero() != (carta2.getNumero() - 1)) {
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
            auxiliar.push(cartas.get(i));
            cartas.remove(i);
        }
        return auxiliar;
    }

    public boolean insertarSegmento(Columna segmento) {
        Carta ultimaCarta = segmento.getCartas().get(cartas.size()-1);
        if ((peek().getNumero() == ultimaCarta.getNumero()+1) && (peek().getColor() != ultimaCarta.getColor())) {
            cartas.addAll(0, segmento);
            return true;
        }
        if ((isEmpty() && ultimaCarta.getNumero() == 13) || !peek().esVisible()) {
            cartas.addAll(0, segmento);
            return true;
        }
        return false;
    }

    public boolean insertarSegmentoColumnaFinal(Columna segmento) {
        Carta ultimaCarta = segmento.getCartas().get(cartas.size()-1);
        if ((this.isEmpty() && ultimaCarta.getNumero() == 1)) {
            cartas.addAll(0, segmento);
            return true;
        }
        if ((peek().getNumero() == ultimaCarta.getNumero()-1) && (peek().getColor() == ultimaCarta.getColor())) {
            cartas.addAll(0, segmento);
            return true;
        }

        return false;
    }

}
