package Reglas;

import Solitario.*;

public class ColumnaKlondike extends Columna {
    public ColumnaKlondike(EstrategiaComparacion estrategia) {
        super(estrategia);
    }

    /**
     * Obtiene una instancia de Columna considerada como un segmento de cartas de una columna hasta determinado
     * indice, verificando si esas cartas son una cadena correctamente, y las quita de la columna determinada.
     */
    public Columna obtenerSegmento(Integer indice) {
        if (isEmpty()) {
            return null;
        }
        EstrategiaComparacion estrategia = new EstrategiaComparacionKlondike();
        Columna auxiliar = new ColumnaKlondike(estrategia);
        if (!esCadena(indice)) {
            return null;
        }
        for (int i = indice; i >= 0; i--) {
            auxiliar.push(this.get(i));
            this.remove(i);
        }
        return auxiliar;
    }

    /**
     * Inserta una carta hacia una columna final, en base a la compatibilidad determinada por las reglas del
     * Solitario Klondike.
     */
    public boolean insertarColumnaFinal(Columna segmento) {
        if ((segmento == null) || (segmento.size() != 1)) {
            return false;
        }
        Carta topeColumnaFinal = peek();
        Carta carta = segmento.peek();
        if (sonCompatiblesColumnaFinal(carta, topeColumnaFinal)) {
            this.add(0, carta);
            return true;
        }
        return false;
    }

    /**
     * Verifica la compatibilidad entre dos cartas, en base a las reglas del Solitario Klondike:
     * Unicamente se puede mover una carta de numero N hacia una columna final cuya carta en el tope sea del mismo
     * color y de numero N - 1. Tambien se puede realizar el movimiento si la columna final destino se encuentra
     * vacia y la carta a mover es de numero 1 (A).
     */
    private boolean sonCompatiblesColumnaFinal(Carta carta, Carta cartaColumnaFinal) {
        if (cartaColumnaFinal == null) {
            return carta.getNumero() == 1;
        }
        return (cartaColumnaFinal.getNumero() == carta.getNumero() - 1) && (cartaColumnaFinal.getPalo() == carta.getPalo());
    }

}
