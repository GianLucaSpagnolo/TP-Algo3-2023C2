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

    public boolean moverCartaColumnaFinal(Integer origen, Integer destino) {
        Columna carta = mesa.columnasMesa.get(origen).obtenerSegmento(0);
        if (carta == null) {
            return false;
        }
        boolean seInserto = mesa.columnasFinales.get(destino).insertarSegmentoColumnaFinal(carta);
        if (!seInserto) {
            mesa.columnasMesa.get(origen).insertarSegmento(carta);
            return false;
        }
        if (!mesa.columnasMesa.get(origen).peek().esVisible()) {
            mesa.columnasMesa.get(origen).peek().darVuelta();
        }
        return true;
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

    public boolean moverCartaDescarteAColumnaMesa(Integer destino) {
        Carta carta = mesa.sacarCartaDescarte();
        if (carta == null) {
            return false;
        }
        Columna columnaCarta = new ColumnaKlondike();
        columnaCarta.push(carta);
        boolean seInserto = mesa.columnasMesa.get(destino).insertarSegmento(columnaCarta);
        if (!seInserto) {
            mesa.insertarCartaDescarte(carta);
            return false;
        }
        return true;
    }

    public boolean moverCartaDescarteAColumnaFinal(Integer destino) {
        Carta carta = mesa.sacarCartaDescarte();
        if (carta == null) {
            return false;
        }
        Columna columnaCarta = new ColumnaKlondike();
        columnaCarta.push(carta);
        boolean seInserto = mesa.columnasMesa.get(destino).insertarSegmentoColumnaFinal(columnaCarta);
        if (!seInserto) {
            mesa.insertarCartaDescarte(carta);
            return false;
        }
        return true;
    }

    public boolean moverCartaColumnaFinalAColumnaMesa(Integer origen, Integer destino) {
        Columna carta = mesa.columnasFinales.get(origen).obtenerSegmento(0);
        if (carta == null) {
            return false;
        }
        boolean seInserto = mesa.columnasMesa.get(destino).insertarSegmento(carta);
        if (!seInserto) {
            mesa.columnasFinales.get(origen).insertarSegmentoColumnaFinal(carta);
            return false;
        }
        return true;
    }
}
