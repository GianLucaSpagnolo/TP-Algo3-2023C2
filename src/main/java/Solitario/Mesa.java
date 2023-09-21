package Solitario;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;

public class Mesa {
    private final Mazo baraja;
    private Mazo barajaDescarte;
    private final Map<Integer, Stack<Carta>> columnasMesa;
    private final Map<Integer, Stack<Carta>> columnasFinales;

    public Mesa(Mazo baraja) {
        this.baraja = baraja;
        this.columnasMesa = new HashMap<>();
        this.columnasFinales = new HashMap<>();
    }

    public void crearBarajaDescarte() {
        this.barajaDescarte = new Mazo(0, new ArrayList<>());
    }

    public void inicializarColumnasMesa(Integer cantidadColumnas) {
        for (int i=0; i < cantidadColumnas; i++) {
            columnasMesa.put(i, new Stack<>());
        }
    }

    public void inicializarColumnasFinales(Integer cantidadColumnas) {
        for (int i=0; i < cantidadColumnas; i++) {
            columnasFinales.put(i, new Stack<>());
        }
    }
}
