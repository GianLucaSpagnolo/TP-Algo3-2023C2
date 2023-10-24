package Reglas;

import Solitario.*;

import java.io.Serializable;

public class EstrategiaComparacionKlondike implements EstrategiaComparacion, Serializable {

    /**
     * Verifica la compatibilidad entre dos cartas, en base a las reglas del Solitario Klondike:
     * Unicamente se puede mover una carta de numero N en una columna cuya carta en el tope sea de color opuesto
     * y con numero N + 1. Tambien se puede realizar el movimiento si la columna destino se encuentra vacia y
     * la carta a mover es de numero 13 (K).
     */
    @Override
    public boolean sonCompatibles(Carta carta1, Carta carta2) {
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

}
