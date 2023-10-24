package Reglas;

import Solitario.*;

import java.io.Serializable;

public class EstrategiaComparacionSpider implements EstrategiaComparacion, Serializable {
    /**
     * Verifica la compatibilidad entre dos cartas, en base a las reglas del Solitario Spider:
     * Unicamente se puede mover una carta de numero N en una columna cuya carta en el tope tenga
     * numero N + 1. Tambien se puede realizar el movimiento si la columna destino se encuentra vacia,
     * en este caso no importa de que numero es la carta a mover.
     */
    public boolean sonCompatibles(Carta carta1, Carta carta2) {
        if (!carta1.esVisible()) {
            return false;
        }
        if (carta2 == null) {
            return true;
        }
        if (!carta2.esVisible()) {
            return true;
        }
        return (carta1.getNumero() == (carta2.getNumero() - 1));
    }
}
