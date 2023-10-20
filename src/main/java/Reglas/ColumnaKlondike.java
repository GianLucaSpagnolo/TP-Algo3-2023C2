package Reglas;

import Solitario.Carta;
import Solitario.Columna;

public class ColumnaKlondike extends Columna {
    public ColumnaKlondike() {
        super();
    }

    /**
     Verifica si las cartas de la columna, hasta un determinado indice, es una cadena correcta en base a las
     reglas de un Solitario Klondike
     */
    public boolean esCadena(Integer indice) {
        for (int i = 0; i < indice; i++) {
            Carta carta1 = this.get(i);
            Carta carta2 = this.get(i+1);
            if (!sonCompatibles(carta1, carta2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Obtiene una instancia de Columna considerada como un segmento de cartas de una columna hasta determinado
     * indice, verificando si esas cartas son una cadena correctamente, y las quita de la columna determinada.
     */
    public Columna obtenerSegmento(Integer indice) {
        if (isEmpty()) {
            return null;
        }
        Columna auxiliar = new ColumnaKlondike();
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
     * Inserta el segmento de cartas previamente obtenido, en otra columna. Para eso verifica la compatibilidad
     * entre sus cartas e intenta insertar el segmento en caso positivo.
     */
    public boolean insertarSegmento(Columna segmento) {
        if (segmento == null || segmento.isEmpty()) {
            return false;
        }
        Carta ultimaCarta = segmento.getCartas().get(segmento.size()-1);
        Carta topeColumna = peek();
        if ((sonCompatibles(ultimaCarta, topeColumna))) {
            this.addAll(0, segmento.getCartas());
            return true;
        }
        return false;
    }

    /**
     * En caso de que no se haya realizado la accion se insertarSegmento() hacia una columna determinada, insertara
     * el segmento en la columna a la cual pertenecia. Para este caso, no se realiza ninguna comparacion previa ya
     * que el objetivo es dejar la columna origen tal como estaba antes de intentar hacer el movimiento.
     */
    public boolean insertarSegmentoDevuelta(Columna segmento) {
        if (segmento == null || segmento.isEmpty()) {
            return false;
        }
        this.addAll(0, segmento.getCartas());
        return true;
    }

    /**
     * Inserta una carta hacia una columna final, en base a la compatibilidad determinada por las reglas del
     * Solitario Klondike.
     */
    public boolean insertarCartaColumnaFinal(Carta carta) {
        if (carta == null) {
            return false;
        }
        Carta topeColumnaFinal = peek();
        if (sonCompatiblesColumnaFinal(carta, topeColumnaFinal)) {
            this.add(0, carta);
            return true;
        }
        return false;
    }

    /**
     * Verifica la compatibilidad entre dos cartas, en base a las reglas del Solitario Klondike:
     * Unicamente se puede mover una carta de numero N en una columna cuya carta en el tope sea de color opuesto
     * y con numero N + 1. Tambien se puede realizar el movimiento si la columna destino se encuentra vacia y
     * la carta a mover es de numero 13 (K).
     */
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
