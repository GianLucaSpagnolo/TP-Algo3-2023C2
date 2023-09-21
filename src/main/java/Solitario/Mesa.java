package Solitario;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;

public class Mesa {
    private final Mazo baraja;
    private Mazo barajaDescarte;
    private ArrayList<Columna> columnasMesa;
    private ArrayList<Columna> columnasFinales;

    public Mesa(Mazo baraja) {
        this.baraja = baraja;
        this.columnasMesa = new ArrayList<>();
        this.columnasFinales = new ArrayList<>();
    }

    public void crearBarajaDescarte() {
        this.barajaDescarte = new Mazo(0, new ArrayList<>());
    }

    public void inicializarColumnasMesa(Integer cantidadColumnasMesa, Columna columna) {
        for (int i=0; i < cantidadColumnasMesa; i++) {
            columnasMesa.add(columna);
        }
    }

    public void inicializarColumnasFinales(Integer cantidadColumnasFinales, Columna columna) {
        for (int i=0; i < cantidadColumnasFinales; i++) {
            columnasFinales.add(columna);
        }
    }
}
