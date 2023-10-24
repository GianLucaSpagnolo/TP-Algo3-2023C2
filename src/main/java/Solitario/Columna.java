package Solitario;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Columna extends ArrayList<Carta> implements Serializable {
    private final EstrategiaComparacion estrategia;
    public Columna(EstrategiaComparacion estrategia) {
        super();
        this.estrategia = estrategia;
    }

    public Carta peek() {
        if (this.isEmpty()) {
            return null;
        }
        return this.get(0);
    }
    public void push(Carta carta) { this.add(0, carta); }

    public Carta pop() {
        if (this.isEmpty()) {
            return null;
        }
        Carta carta = this.get(0);
        this.remove(0);
        return carta;
    }

    public ArrayList<Carta> getCartas() {
        return this;
    }

    /**
     Verifica si las cartas de la columna, hasta un determinado indice, es una cadena correcta en base a las
     reglas de un Solitario Klondike
     */
    public boolean esCadena(Integer indice) {
        for (int i = 0; i < indice; i++) {
            Carta carta1 = this.get(i);
            Carta carta2 = this.get(i+1);
            if (!estrategia.sonCompatibles(carta1, carta2)) {
                return false;
            }
        }
        return true;
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
        if ((estrategia.sonCompatibles(ultimaCarta, topeColumna))) {
            this.addAll(0, segmento.getCartas());
            return true;
        }
        return false;
    }

    /**
     * En caso de que no se haya realizado exitosamente la accion se insertarSegmento() hacia una columna determinada,
     * insertara el segmento en la columna a la cual pertenecia. Para este caso, no se realiza ninguna comparacion
     * previa ya que el objetivo es dejar la columna origen tal como estaba antes de intentar hacer el movimiento.
     */
    public boolean insertarSegmentoDevuelta(Columna segmento) {
        if (segmento == null || segmento.isEmpty()) {
            return false;
        }
        this.addAll(0, segmento.getCartas());
        return true;
    }

    public abstract Columna obtenerSegmento(Integer indice);

    public abstract boolean insertarColumnaFinal(Columna segmento);
}
