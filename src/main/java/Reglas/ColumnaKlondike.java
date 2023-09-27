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
            if (!sonCompatibles(carta1, carta2)) {
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
        if (segmento == null || segmento.isEmpty()) {
            return false;
        }
        Carta ultimaCarta = segmento.getCartas().get(segmento.size()-1);
        Carta topeColumna = peek();
        if ((sonCompatibles(ultimaCarta, topeColumna))) {
            cartas.addAll(0, segmento.getCartas());
            return true;
        }
        return false;
    }

    public void insertarSegmentoDevuelta(Columna segmento) {
        if (segmento == null || segmento.isEmpty()) {
            return;
        }
        cartas.addAll(0, segmento.getCartas());
    }

    public boolean insertarCartaColumnaFinal(Carta carta) {
        if (carta == null) {
            return false;
        }
        Carta topeColumnaFinal = peek();
        if (sonCompatiblesColumnaFinal(carta, topeColumnaFinal)) {
            cartas.add(0, carta);
            return true;
        }
        return false;
    }

    private boolean sonCompatibles(Carta carta1, Carta carta2) {
        if (!carta1.esVisible()) {
            return false;
        }
        if (carta2 == null) {
            return carta1.getNumero() == 13;
        }
        if (!carta2.esVisible()) {
            return true;
        }
        return (carta1.getNumero() == (carta2.getNumero() - 1)) && (carta1.getColor() != carta2.getColor());
    }

    private boolean sonCompatiblesColumnaFinal(Carta carta, Carta cartaColumnaFinal) {
        if (cartaColumnaFinal == null) {
            return carta.getNumero() == 1;
        }
        return (cartaColumnaFinal.getNumero() == carta.getNumero() - 1) && (cartaColumnaFinal.getColor() == carta.getColor());
    }

}