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
        mazo.generarBaraja("A0", palos);

        Mesa mesa = new Mesa(mazo);
        mesa.crearBarajaDescarte();

        Carta carta = new Carta(1, Palos.CORAZONES);
        Carta cartaMazo = mesa.sacarCartaMazo();
        assertEquals(cartaMazo.getPalo(), carta.getPalo());
        assertEquals(cartaMazo.getNumero(), carta.getNumero());
        assertNull(mesa.sacarCartaDescarte());
        assertTrue(mesa.columnasMesa.isEmpty());
        assertTrue(mesa.columnasFinales.isEmpty());
    }

    @Test
    public void testInicializarColumnasMesa() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.PICAS);
        Mazo mazo = new Mazo();
        String semilla = mazo.generarSemilla(52, palos);
        mazo.generarBaraja(semilla, palos);
        Mesa mesa = new Mesa(mazo);

        mesa.inicializarColumnasMesa(6, new ColumnaKlondike());
        assertEquals(mesa.columnasMesa.size(), 6);
        for (int i = 0; i < 6; i++) {
            assertTrue(mesa.columnasMesa.get(i).isEmpty());
        }
    }

    @Test
    public void testInicializarColumnasFinales() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.DIAMANTES);
        Mazo mazo = new Mazo();
        String semilla = mazo.generarSemilla(52, palos);
        mazo.generarBaraja(semilla, palos);
        Mesa mesa = new Mesa(mazo);

        mesa.inicializarColumnasFinales(5, new ColumnaKlondike());
        assertEquals(mesa.columnasFinales.size(), 5);
        for (int i = 0; i < 5; i++) {
            assertTrue(mesa.columnasFinales.get(i).isEmpty());
        }
    }

    @Test
    public void testCartasBarajas() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.DIAMANTES);
        Mazo mazo = new Mazo();
        String semilla = mazo.generarSemilla(5, palos);
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
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.PICAS);
        palos.add(Palos.DIAMANTES);
        palos.add(Palos.CORAZONES);
        palos.add(Palos.TREBOLES);
        Mazo mazo = new Mazo();
        String semilla = mazo.generarSemilla(52, palos);
        mazo.generarBaraja(semilla, palos);

        Mesa mesa = new Mesa(mazo);
        mesa.inicializarColumnasMesa(7, new ColumnaKlondike());
        mesa.inicializarColumnasFinales(4, new ColumnaKlondike());
        mesa.crearBarajaDescarte();

        for (int i = 0; i < 52; i++) {
            assertNotNull(mesa.sacarCartaMazo());
        }
        assertNull(mesa.sacarCartaDescarte());
        assertEquals(mesa.columnasMesa.size(), 7);
        assertEquals(mesa.columnasFinales.size(), 4);
    }

    @Test
    public void generarMesaMazoSimple() { // Spider
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.PICAS);
        Mazo mazo = new Mazo();
        String semilla = mazo.generarSemilla(104, palos);
        mazo.generarBaraja(semilla, palos);

        Mesa mesa = new Mesa(mazo);
        mesa.inicializarColumnasMesa(10, new ColumnaKlondike());
        mesa.inicializarColumnasFinales(10, new ColumnaKlondike());

        for (int i = 0; i < 104; i++) {
            Carta carta = mesa.sacarCartaMazo();
            carta.darVuelta();
            assertEquals(carta.getPalo(), Palos.PICAS);
        }
        assertEquals(mesa.columnasMesa.size(), 10);
        assertEquals(mesa.columnasFinales.size(), 10);
    }
}
