package Reglas;

import Solitario.*;
import java.util.ArrayList;

public class Klondike implements Solitario {
    private final Mesa mesa;

    public Klondike(String semilla, Mesa mesa) {
        if (mesa == null) {
            ArrayList<Palos> palos = new ArrayList<>();
            palos.add(Palos.PICAS);
            palos.add(Palos.TREBOLES);
            palos.add(Palos.CORAZONES);
            palos.add(Palos.DIAMANTES);
            Mazo mazo = new Mazo();
            if (semilla == null) {
                semilla = mazo.generarSemilla(52, palos);
            }
            mazo.generarBaraja(semilla, palos);

            Mesa nuevaMesa = new Mesa(mazo);
            for (int i = 0; i < 7; i++) {
                Columna columnaMesa = new ColumnaKlondike();
                nuevaMesa.inicializarColumnaMesa(columnaMesa);
            }
            for (int j = 0; j < 4; j++) {
                Columna columnaFinal = new ColumnaKlondike();
                nuevaMesa.inicializarColumnaFinal(columnaFinal);
            }
            nuevaMesa.crearBarajaDescarte();
            mesa = nuevaMesa;
        }
        this.mesa = mesa;
    }

    public Mesa getEstadoMesa() {
        return this.mesa;
    }

    public void repartirCartasInicio() {
        for (int i=1; i <= 7; i++) {
            for (int j=0; j < i; j++) {
                Carta carta = mesa.sacarCartaMazo();
                if (carta == null)
                    break;
                if (j == i-1) {
                    carta.darVuelta();
                }
                mesa.columnaMesaEnPosicion(i-1).push(carta);
            }
        }
    }

    public boolean moverCartas(Integer origen, Integer destino, Integer carta) {
        Columna segmento = mesa.columnaMesaEnPosicion(origen).obtenerSegmento(carta);
        if (segmento == null) {
            return false;
        }
        boolean seInserto = mesa.columnaMesaEnPosicion(destino).insertarSegmento(segmento);
        if (!seInserto) {
            mesa.columnaMesaEnPosicion(origen).insertarSegmentoDevuelta(segmento);
            return false;
        }
        if ((mesa.columnaMesaEnPosicion(origen).peek() != null) && (!mesa.columnaMesaEnPosicion(origen).peek().esVisible())) {
            mesa.columnaMesaEnPosicion(origen).peek().darVuelta();
        }
        return true;
    }

    public boolean moverCartaColumnaFinal(Integer origen, Integer destino) {
        Carta carta = mesa.columnaMesaEnPosicion(origen).pop();
        if (carta == null) {
            return false;
        }
        boolean seInserto = mesa.columnaFinalEnPosicion(destino).insertarCartaColumnaFinal(carta);
        if (!seInserto) {
            mesa.columnaMesaEnPosicion(origen).push(carta);
            return false;
        }
        if ((mesa.columnaMesaEnPosicion(origen).peek() != null) && (!mesa.columnaMesaEnPosicion(origen).peek().esVisible())) {
            mesa.columnaMesaEnPosicion(origen).peek().darVuelta();
        }
        return true;
    }


    public boolean estaGanado() {
        for (int i = 0; i < 4; i++) {
            Columna columnaFinal = mesa.columnaFinalEnPosicion(i);
            if ((columnaFinal.isEmpty()) || (columnaFinal.peek().getNumero() != 13)) {
                return false;
            }
        }
        return true;
    }

    public boolean sacarDelMazo() {
        Carta carta = mesa.sacarCartaMazo();
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
            carta.darVuelta();
            mesa.insertarCartaMazo(carta);
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
        boolean seInserto = mesa.columnaMesaEnPosicion(destino).insertarSegmento(columnaCarta);
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
        boolean seInserto = mesa.columnaFinalEnPosicion(destino).insertarCartaColumnaFinal(carta);
        if (!seInserto) {
            mesa.insertarCartaDescarte(carta);
            return false;
        }
        return true;
    }

    public boolean moverCartaColumnaFinalAColumnaMesa(Integer origen, Integer destino) {
        Columna carta = mesa.columnaFinalEnPosicion(origen).obtenerSegmento(0);
        if (carta == null) {
            return false;
        }
        boolean seInserto = mesa.columnaMesaEnPosicion(destino).insertarSegmento(carta);
        if (!seInserto) {
            mesa.columnaFinalEnPosicion(origen).insertarCartaColumnaFinal(carta.pop());
            return false;
        }
        return true;
    }
}
