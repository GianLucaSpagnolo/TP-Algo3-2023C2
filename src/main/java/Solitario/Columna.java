package Solitario;

import java.util.ArrayList;

public abstract class Columna extends ArrayList<Carta> {
    public Columna() {
        super();
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

    public abstract boolean esCadena(Integer indice);

    public abstract Columna obtenerSegmento(Integer indice);

    public abstract boolean insertarSegmento(Columna segmento);

    public abstract boolean insertarSegmentoDevuelta(Columna segmento);

    public abstract boolean insertarCartaColumnaFinal(Carta carta);
}
