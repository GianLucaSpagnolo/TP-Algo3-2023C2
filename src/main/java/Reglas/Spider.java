package Reglas;

import Solitario.*;
import java.util.ArrayList;
public class Spider implements Solitario {
    private final Mesa mesa;
    private Integer posicionColumnaFinal = 0;

    /**
     * Genera una instancia del juego Solitario, en base a las reglas y a la disposicion de las columnas y del mazo
     * propias de Spider. Este posee una mesa con una baraja de 104 cartas todas del palo Picas, no posee una baraja
     * descarte, 10 columnas de mesa para realizar movimientos del juego y 8 columnas finales imposibles de acceder
     * para el usuario, las cuales determinan si el juego eventualmente esta completado.
     * Puede recibir una semilla previamente creada para crear la baraja a partir de ella, y tambien puede recibir
     * una instancia de una mesa (que debe ser configurada con formato Spider) la cual puede inicializar un estado de
     * juego previamente determinado.
     */
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
            for (int j=0; j < 6; j++) {
                if ((i <= 4) || (j < 5)) {
                    Carta carta = mesa.sacarCartaMazo();
                    if (carta == null)
                        break;
                    if ((i <= 4 && j == 5) || (i > 4 && j == 4)) {
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
        boolean seMovio = moverCartasColumnaFinal(destino, posicionColumnaFinal);
        if (seMovio) {
            posicionColumnaFinal++;
        }
        return true;
    }

    /**
     * Mueve un segmento de 13 cartas a una columna final.
     */
    private boolean moverCartasColumnaFinal(Integer origen, Integer destino) {
        Columna segmento = mesa.columnaMesaEnPosicion(origen).obtenerSegmento(12);
        if (segmento == null) {
            return false;
        }
        mesa.columnaFinalEnPosicion(destino).insertarColumnaFinal(segmento);
        if ((mesa.columnaMesaEnPosicion(origen).peek() != null) && (!mesa.columnaMesaEnPosicion(origen).peek().esVisible())) {
            mesa.columnaMesaEnPosicion(origen).peek().darVuelta();
        }
        return true;
    }


    /**
     * Verifica si el juego llego a un estado de victoria.
     */
    public boolean estaGanado() {
        return posicionColumnaFinal == 8;
    }

    /**
     * Reparte una carta a cada una de las 10 columnas mesa.
     */
    public boolean sacarDelMazo() {
        Carta carta = mesa.sacarCartaMazo();
        if (carta==null) {
            return false;
        }
        for (int i = 0; i < 10; i++) {
            if (mesa.columnaMesaEnPosicion(i).isEmpty()) {
                return false;
            }
        }
        mesa.insertarCartaMazo(carta);
        for (int i = 0; i < 10; i++) {
            carta = mesa.sacarCartaMazo();
            carta.darVuelta();
            mesa.columnaMesaEnPosicion(i).push(carta);
        }
        return true;
    }

}


