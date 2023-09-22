package Reglas;

import Solitario.*;

import java.util.ArrayList;

public class Klondike implements Reglas {
    private final Mesa mesa;

    public Klondike() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.PICAS);
        palos.add(Palos.TREBOLES);
        palos.add(Palos.CORAZONES);
        palos.add(Palos.DIAMANTES);
        Mazo mazo = new Mazo(52, palos);
        mazo.mezclar();
        Mesa nuevaMesa = new Mesa(mazo);
        nuevaMesa.inicializarColumnasMesa(7, new ColumnaKlondike());
        nuevaMesa.inicializarColumnasFinales(4, new ColumnaKlondike());
        nuevaMesa.crearBarajaDescarte();
        this.mesa = nuevaMesa;
    }

    public void repartirCartasInicio() {
        for (int i=1; i <= 7; i++) {
            for (int j=0; j < i; j++) {
                Carta carta = mesa.sacarCarta();
                if (j == i-1) {
                    carta.darVuelta();
                    carta.cambiarInteractuabilidad();
                }
                mesa.columnasMesa.get(i-1).push(carta);
            }
        }
    }

    public boolean moverCartas(Integer origen, Integer destino, Integer carta) {
        Columna segmento = mesa.columnasMesa.get(origen).obtenerSegmento(carta);
        if (segmento == null) {
            return false;
        }
        boolean seInserto = mesa.columnasMesa.get(destino).insertarSegmento(segmento);
        if (!seInserto) {
            mesa.columnasMesa.get(origen).insertarSegmento(segmento);
            return false;
        }
        if (!mesa.columnasMesa.get(origen).peek().esVisible()) {
            mesa.columnasMesa.get(origen).peek().darVuelta();
        }
        return true;
    }

    private boolean sonCompatiblesColumnaFinal(Carta carta1, Carta carta2) {
        //Chequea si una carta es compatible con el tope de una columna final
        if (carta2 == null) {
            return carta1.getNumero() == 1;
        }
        return (carta1.getNumero() == carta2.getNumero() + 1) || (carta1.getPalo() == carta2.getPalo());
    }


    public static boolean sonCompatibles(Carta carta1, Carta carta2) {
        //Chequea que dos cartas sean compatibles entre si (Segun las reglas del juego)
        if (carta2 == null) {
            return carta1.getNumero() == 13;
        }
        if (carta1.getNumero() != (carta2.getNumero() - 1)) {
            return false;
        }
        return carta1.getColor() != carta2.getColor();
    }

    public boolean moverCartaColumnaFinal(Integer origen, Integer destino) {
        Columna carta = mesa.columnasMesa.get(origen).obtenerSegmento(0);
        if (carta == null) {
            return false;
        }
        boolean seInserto = mesa.columnasFinales.get(destino).insertarSegmento(carta);
        if (!seInserto) {
            mesa.columnasMesa.get(origen).insertarSegmento(carta);
            return false;
        }


        Carta topeColumnaFinal = mesa.columnasFinales.get(destino).peek();
        if (sonCompatiblesColumnaFinal(carta, topeColumnaFinal)) {
            mesa.columnasFinales.get(destino).push(carta);
            return true;
        }
        return false;
    }


    public boolean estaGanado() {
        for (Columna columna : mesa.columnasFinales) {
            if (columna.peek().getNumero() != 13) {
                return false;
            }
        }
        return true;
    }

    public boolean sacarDelMazo() {
        Carta carta = mesa.sacarCarta();
        if (carta == null) {
            reiniciarBaraja();
            return false;
        }
        carta.darVuelta();
        mesa.insertarCartaDescarte(carta);
        return true;
    }

    private void reiniciarBaraja() {
        Carta carta = mesa.sacarCartaDescarte();
        while (carta != null) {
            mesa.insertarEnMazo(carta);
            carta = mesa.sacarCartaDescarte();
        }
    }

    public boolean moverCartaDescarteAColumna(Integer destino) {
        Carta carta = mesa.sacarCartaDescarte();
        Carta topeColumna = mesa.columnasMesa.get(destino).peek();
        if (sonCompatibles(carta, topeColumna)) {
            mesa.columnasFinales.get(destino).push(carta);
            return true;
        }
        mesa.insertarCartaDescarte(carta);
        return false;
    }

    public boolean moverCartaColumnaFinalAColumna(Integer origen, Integer destino) {
        if (mesa.columnasFinales.get(origen).isEmpty()) {
            return false;
        }
        Carta carta = mesa.columnasFinales.get(origen).pop();
    }
}
