package Solitario;

import java.util.ArrayList;
import java.util.List;

public abstract class Columna extends ArrayList<Carta> {
    protected final ArrayList<Carta> cartas;

    public Columna() {
        this.cartas = new ArrayList<>();
    }

    public Carta peek() {
        if (cartas.isEmpty()) {
            return null;
        }
        return cartas.get(0);
    }
    public void push(Carta carta) {
        cartas.add(0, carta);
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    public Carta pop() {
        Carta carta = cartas.get(0);
        cartas.remove(0);
        return carta;
    }

    @Override
    public int size() {
        return cartas.size();
    }

    @Override
    public boolean isEmpty() {
        return cartas.isEmpty();
    }

    public abstract boolean esCadena(Integer indice);

    public abstract Columna obtenerSegmento(Integer indice);

    public abstract boolean insertarSegmento(Columna segmento );
    public abstract boolean insertarSegmentoColumnaFinal(Columna segmento);




}
