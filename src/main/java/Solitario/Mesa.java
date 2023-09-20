package Solitario;

import java.util.*;

public class Mesa {
    private Mazo baraja;
    private Mazo barajaDescarte;
    private Map<Integer, Stack<Carta>> columnasMesa;
    private Map<Integer, Stack<Carta>> columnasFinales;

    public Mesa(Mazo baraja) {
        this.baraja = baraja;
        this.columnasMesa = new HashMap<Integer, Stack<Carta>>();
        this.columnasFinales = new HashMap<Integer, Stack<Carta>>();
    }

    public void crearBarajaDescarte() {
        this.barajaDescarte = new Mazo(0, new ArrayList<Palos>());
    }

    public void inicializarColumnasMesa(Integer cantidadColumnas) {
        for (int i=0; i < cantidadColumnas; i++) {
            columnasMesa.put(i, new Stack<Carta>());
        }
    }

    public void inicializarColumnasFinales(Integer cantidadColumnas) {
        for (int i=0; i < cantidadColumnas; i++) {
            columnasFinales.put(i, new Stack<Carta>());
        }
    }
}
