package Solitario;

import java.util.ArrayList;

public class Mesa {
    private final Mazo baraja;
    private Mazo barajaDescarte;
    private final ArrayList<Columna> columnasMesa;
    private final ArrayList<Columna> columnasFinales;

    public Mesa(Mazo baraja) {
        this.baraja = baraja;
        this.columnasMesa = new ArrayList<>();
        this.columnasFinales = new ArrayList<>();
    }

    public void crearBarajaDescarte() {
        this.barajaDescarte = new Mazo();
    }


    public void inicializarColumnaMesa(Columna columna) {
        columnasMesa.add(columna);
    }

    public void inicializarColumnaFinal(Columna columna) {
        columnasFinales.add(columna);
    }

    public Columna columnaMesaEnPosicion(Integer indice) {
        return columnasMesa.get(indice);
    }

    public Columna columnaFinalEnPosicion(Integer indice) {
        return columnasFinales.get(indice);
    }

    public Integer sizeColumnaMesa() {
        return columnasMesa.size();
    }

    public Integer sizeColumnaFinal() {
        return columnasFinales.size();
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
