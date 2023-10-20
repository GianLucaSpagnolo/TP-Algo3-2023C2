package Reglas;

import Solitario.*;
import java.util.ArrayList;
public class Spider implements Solitario {
    private final Mesa mesa;

    public Spider(GeneradorSemillas semilla, Mesa mesa) {
        if (mesa == null) {
            ArrayList<Palos> palos = new ArrayList<>();
            palos.add(Palos.PICAS);
            Mazo mazo = new Mazo();
            if (semilla == null) {
                semilla = GeneradorSemillas.generarSemillaConCantidadYPalos(104, palos);
            }
            mazo.generarBaraja(semilla, palos);

            Mesa nuevaMesa = new Mesa(mazo);
            for (int i = 0; i < 10; i++) {
                Columna columnaMesa = new ColumnaSpider();
                nuevaMesa.inicializarColumnaMesa(columnaMesa);
            }
            for (int j = 0; j < 8; j++) {
                Columna columnaFinal = new ColumnaSpider();
                nuevaMesa.inicializarColumnaFinal(columnaFinal);
            }
            mesa = nuevaMesa;
        }
        this.mesa = mesa;
    }

    public Mesa getEstadoMesa() {
        return this.mesa;
    }

    /**
     * Reparte las cartas de la baraja creada entre las 10 columnas de la mesa, de acuerdo a las reglas del
     * Solitario Spider.
     */
    public void repartirCartasInicio() {
        for (int i=1; i <= 10; i++) {
            if (i <= 4) {
                for (int j=0; j < 6; j++) {
                    Carta carta = mesa.sacarCartaMazo();
                    if (carta == null)
                        break;
                    if (j == 5) {
                        carta.darVuelta();
                    }
                    mesa.columnaMesaEnPosicion(i-1).push(carta);
                }
            } else {
                for (int j=0; j < 5; j++) {
                    Carta carta = mesa.sacarCartaMazo();
                    if (carta == null)
                        break;
                    if (j == 4) {
                        carta.darVuelta();
                    }
                    mesa.columnaMesaEnPosicion(i - 1).push(carta);
                }
            }
        }
    }

    /**
     * Mueve una o varias cartas de una columna mesa a otra.
     */
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

    /**
     * Mueve una carta de una columna mesa a una columna final.
     */
    public boolean moverCartaColumnaFinal(Integer origen, Integer destino) {
        Columna segmento = mesa.columnaMesaEnPosicion(origen).obtenerSegmento(0);
        if (segmento == null) {
            return false;
        }
        boolean seInserto = mesa.columnaFinalEnPosicion(destino).insertarColumnaFinal(segmento);
        if (!seInserto) {
            mesa.columnaMesaEnPosicion(origen).insertarSegmentoDevuelta(segmento);
            return false;
        }
        if ((mesa.columnaMesaEnPosicion(origen).peek() != null) && (!mesa.columnaMesaEnPosicion(origen).peek().esVisible())) {
            mesa.columnaMesaEnPosicion(origen).peek().darVuelta();
        }
        return true;
    }


    /**
     * Verifica si el juego llego a un estado de victoria, y para ello verifica que los valores de las cartas al
     * tope de cada columna final tengan numero 13 (K).
     */
    public boolean estaGanado() {
        for (int i = 0; i < 4; i++) {
            Columna columnaFinal = mesa.columnaFinalEnPosicion(i);
            if ((columnaFinal.isEmpty()) || (columnaFinal.peek().getNumero() != 13)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Mueve una carta de la baraja principal a la baraja descarte.
     */
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

    /**
     * Una vez se agotan las cartas en la baraja principal, mueve todas las cartas de la baraja descarte a la
     * baraja principal realizando pop() y push().
     */
    private void reiniciarBaraja() {
        Carta carta = mesa.sacarCartaDescarte();
        while (carta != null) {
            carta.darVuelta();
            mesa.insertarCartaMazo(carta);
            carta = mesa.sacarCartaDescarte();
        }
    }

    /**
     * Mueve una carta de la baraja descarte a una columna mesa.
     */
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

    /**
     * Mueve una carta de la baraja descarte a una columna final.
     */
    public boolean moverCartaDescarteAColumnaFinal(Integer destino) {
        Carta carta = mesa.sacarCartaDescarte();
        if (carta == null) {
            return false;
        }
        Columna segmento = new ColumnaKlondike();
        segmento.push(carta);
        boolean seInserto = mesa.columnaFinalEnPosicion(destino).insertarColumnaFinal(segmento);
        if (!seInserto) {
            mesa.insertarCartaDescarte(carta);
            return false;
        }
        return true;
    }

    /**
     * Mueve una carta de una columna final a una columna mesa.
     */
    public boolean moverCartaColumnaFinalAColumnaMesa(Integer origen, Integer destino) {
        Columna carta = mesa.columnaFinalEnPosicion(origen).obtenerSegmento(0);
        if (carta == null) {
            return false;
        }
        boolean seInserto = mesa.columnaMesaEnPosicion(destino).insertarSegmento(carta);
        if (!seInserto) {
            mesa.columnaFinalEnPosicion(origen).insertarColumnaFinal(carta);
            return false;
        }
        return true;
    }
}


