package Solitario;

import java.util.ArrayList;

public class Mesa {
    private final Mazo baraja;
    private Mazo barajaDescarte;
    public ArrayList<Columna> columnasMesa;
    public ArrayList<Columna> columnasFinales;

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

    public Carta sacarCartaMazo() {
        if (baraja.estaVacio()) {
            return null;
        }
        return baraja.sacarCarta();
    }

    public void insertarCartaMazo(Carta carta) {
        baraja.agregarCarta(carta);
    }

    public void insertarCartaDescarte(Carta carta) {
        barajaDescarte.agregarCarta(carta);
    }

    public Carta sacarCartaDescarte() {
        if (barajaDescarte.estaVacio()) {
            return null;
        }
        return barajaDescarte.sacarCarta();
    }
}
