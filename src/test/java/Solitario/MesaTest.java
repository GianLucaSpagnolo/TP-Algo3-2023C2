package Solitario;

import Reglas.ColumnaKlondike;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class MesaTest {

    @Test
    public void testCreacionMesa() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.CORAZONES);
        Mazo mazo = new Mazo();
        Semilla semilla = new Semilla("A0");
        mazo.generarBaraja(semilla, palos);

        Mesa mesa = new Mesa(mazo);
        mesa.crearBarajaDescarte();

        Carta carta = new Carta(1, Palos.CORAZONES);
        Carta cartaMazo = mesa.sacarCartaMazo();
        assertEquals(cartaMazo.getPalo(), carta.getPalo());
        assertEquals(cartaMazo.getNumero(), carta.getNumero());
        assertNull(mesa.sacarCartaDescarte());
        assertEquals(0, mesa.sizeColumnaMesa(), 0);
        assertEquals(0, mesa.sizeColumnaFinal(), 0);
    }

    @Test
    public void testInicializarColumnasMesa() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.PICAS);
        Mazo mazo = new Mazo();
        Semilla semilla = new Semilla(52, palos);
        mazo.generarBaraja(semilla, palos);
        Mesa mesa = new Mesa(mazo);

        for (int i = 0; i < 6; i++) {
            Columna columnaMesa = new ColumnaKlondike();
            mesa.inicializarColumnaMesa(columnaMesa);
        }
        assertEquals(6, mesa.sizeColumnaMesa(), 0);
        for (int i = 0; i < 6; i++) {
            assertTrue(mesa.columnaMesaEnPosicion(i).isEmpty());
        }
    }

    @Test
    public void testInicializarColumnasFinales() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.DIAMANTES);
        Mazo mazo = new Mazo();
        Semilla semilla = new Semilla(52, palos);
        mazo.generarBaraja(semilla, palos);
        Mesa mesa = new Mesa(mazo);

        for (int i = 0; i < 5; i++) {
            Columna columnaFinal = new ColumnaKlondike();
            mesa.inicializarColumnaFinal(columnaFinal);
        }
        assertEquals(5, mesa.sizeColumnaFinal(), 0);
        for (int i = 0; i < 5; i++) {
            assertTrue(mesa.columnaFinalEnPosicion(i).isEmpty());
        }
    }

    @Test
    public void testCartasBarajas() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.DIAMANTES);
        Mazo mazo = new Mazo();
        Semilla semilla = new Semilla(5, palos);
        mazo.generarBaraja(semilla, palos);
        Mesa mesa = new Mesa(mazo);
        mesa.crearBarajaDescarte();

        for (int i = 0; i < 5; i++) {
            Carta carta = mesa.sacarCartaMazo();
            carta.darVuelta();
            mesa.insertarCartaDescarte(carta);
        }
        assertNull(mesa.sacarCartaMazo());

        for (int j = 0; j < 5; j++) {
            Carta carta = mesa.sacarCartaDescarte();
            carta.darVuelta();
            mesa.insertarCartaMazo(carta);
        }
        assertNull(mesa.sacarCartaDescarte());
    }

    @Test
    public void generarMesaKlondike() {
        /*
         * Genera una mesa con todas las caracteristicas propias de un Solitario Klondike.
         * Utiliza un mazo compuesto por 52 cartas de 4 palos diferentes.
         */
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.PICAS);
        palos.add(Palos.DIAMANTES);
        palos.add(Palos.CORAZONES);
        palos.add(Palos.TREBOLES);
        Mazo mazo = new Mazo();
        Semilla semilla = new Semilla(52, palos);
        mazo.generarBaraja(semilla, palos);

        Mesa mesa = new Mesa(mazo);
        for (int i = 0; i < 7; i++) {
            Columna columnaMesa = new ColumnaKlondike();
            mesa.inicializarColumnaMesa(columnaMesa);
        }
        for (int i = 0; i < 4; i++) {
            Columna columnaFinal = new ColumnaKlondike();
            mesa.inicializarColumnaFinal(columnaFinal);
        }
        mesa.crearBarajaDescarte();

        for (int i = 0; i < 52; i++) {
            assertNotNull(mesa.sacarCartaMazo());
        }
        assertNull(mesa.sacarCartaDescarte());
        assertEquals(7, mesa.sizeColumnaMesa(), 0);
        assertEquals(4, mesa.sizeColumnaFinal(), 0);
    }

    @Test
    public void generarMesaMazoSimple() {
        /*
         * Genera una mesa con todas las caracteristicas propias de un solitario Spider.
         * Utiliza un mazo simple con cartas de un mismo palo.
         */
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.PICAS);
        Mazo mazo = new Mazo();
        Semilla semilla = new Semilla(104, palos);
        mazo.generarBaraja(semilla, palos);

        Mesa mesa = new Mesa(mazo);
        for (int i = 0; i < 10; i++) {
            Columna columnaMesa = new ColumnaKlondike();
            mesa.inicializarColumnaMesa(columnaMesa);
        }
        for (int i = 0; i < 10; i++) {
            Columna columnaFinal = new ColumnaKlondike();
            mesa.inicializarColumnaFinal(columnaFinal);
        }

        for (int i = 0; i < 104; i++) {
            Carta carta = mesa.sacarCartaMazo();
            carta.darVuelta();
            assertEquals(carta.getPalo(), Palos.PICAS);
        }
        assertEquals(10, mesa.sizeColumnaMesa(), 0);
        assertEquals(10, mesa.sizeColumnaFinal(), 0);
    }
}
