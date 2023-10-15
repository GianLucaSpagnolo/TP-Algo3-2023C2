package Reglas;

import Solitario.*;
import java.util.ArrayList;

public class Klondike implements Solitario {
    private final Mesa mesa;

    public Klondike(Semilla semilla, Mesa mesa) {
        /*
         * Genera una instancia del juego Solitario, en base a las reglas y a la disposicion de las columnas y del mazo
         * propias de Klondike. Este posee una mesa con una baraja de 52 cartas de 4 palos distintos, una baraja
         * descarte donde van las cartas que se sacan del mazo, 7 columnas de mesa para realizar movimientos del juego
         * y 4 columnas finales las cuales determinan si el juego eventualmente esta completado.
         *
         * Puede recibir una semilla previamente creada para crear la baraja a partir de ella, y tambien puede recibir
         * una instancia de una mesa (que debe ser configurada con formato Klondike) la cual puede inicializar un
         * estado de juego previamente determinado.
         */
        if (mesa == null) {
            ArrayList<Palos> palos = new ArrayList<>();
            palos.add(Palos.PICAS);
            palos.add(Palos.TREBOLES);
            palos.add(Palos.CORAZONES);
            palos.add(Palos.DIAMANTES);
            Mazo mazo = new Mazo();
            if (semilla == null) {
                semilla = new Semilla(52, palos);
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
        /*
         * Reparte las cartas de la baraja creada entre las 7 columnas de la mesa, de acuerdo a las reglas del
         * Solitario Klondike.
         */
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
        /*
         * Mueve una o varias cartas de una columna mesa a otra.
         */
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
        /*
         * Mueve una carta de una columna mesa a una columna final.
         */
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
        /*
         * Verifica si el juego llego a un estado de victoria, y para ello verifica que los valores de las cartas al
         * tope de cada columna final tengan numero 13 (K).
         */
        for (int i = 0; i < 4; i++) {
            Columna columnaFinal = mesa.columnaFinalEnPosicion(i);
            if ((columnaFinal.isEmpty()) || (columnaFinal.peek().getNumero() != 13)) {
                return false;
            }
        }
        return true;
    }

    public boolean sacarDelMazo() {
        /*
         * Mueve una carta de la baraja principal a la baraja descarte.
         */
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
        /*
         * Una vez se agotan las cartas en la baraja principal, mueve todas las cartas de la baraja descarte a la
         * baraja principal realizando pop() y push().
         */
        Carta carta = mesa.sacarCartaDescarte();
        while (carta != null) {
            carta.darVuelta();
            mesa.insertarCartaMazo(carta);
            carta = mesa.sacarCartaDescarte();
        }
    }

    public boolean moverCartaDescarteAColumnaMesa(Integer destino) {
        /*
         * Mueve una carta de la baraja descarte a una columna mesa.
         */
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
        /*
         * Mueve una carta de la baraja descarte a una columna final.
         */
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
        /*
         * Mueve una carta de una columna final a una columna mesa.
         */
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
