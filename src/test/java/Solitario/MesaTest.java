package Solitario;

import Reglas.ColumnaKlondike;
import Reglas.EstrategiaComparacionKlondike;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MesaTest {

    @Test
    public void testCreacionMesa() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.CORAZONES);
        Mazo mazo = new Mazo();
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConString("A0");
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
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConCantidadYPalos(52, palos);
        mazo.generarBaraja(semilla, palos);
        Mesa mesa = new Mesa(mazo);

        EstrategiaComparacion estrategia = new EstrategiaComparacionKlondike();
        for (int i = 0; i < 6; i++) {
            Columna columnaMesa = new ColumnaKlondike(estrategia);
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
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConCantidadYPalos(52, palos);
        mazo.generarBaraja(semilla, palos);
        Mesa mesa = new Mesa(mazo);

        EstrategiaComparacion estrategia = new EstrategiaComparacionKlondike();
        for (int i = 0; i < 5; i++) {
            Columna columnaFinal = new ColumnaKlondike(estrategia);
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
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConCantidadYPalos(5, palos);
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
    /**
     * Genera una mesa con todas las caracteristicas propias de un Solitario Klondike.
     * Utiliza un mazo compuesto por 52 cartas de 4 palos diferentes.
     */
    public void generarMesaKlondike() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.PICAS);
        palos.add(Palos.DIAMANTES);
        palos.add(Palos.CORAZONES);
        palos.add(Palos.TREBOLES);
        Mazo mazo = new Mazo();
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConCantidadYPalos(52, palos);
        mazo.generarBaraja(semilla, palos);

        Mesa mesa = new Mesa(mazo);
        EstrategiaComparacion estrategia = new EstrategiaComparacionKlondike();
        for (int i = 0; i < 7; i++) {
            Columna columnaMesa = new ColumnaKlondike(estrategia);
            mesa.inicializarColumnaMesa(columnaMesa);
        }
        for (int i = 0; i < 4; i++) {
            Columna columnaFinal = new ColumnaKlondike(estrategia);
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
    /**
     * Genera una mesa con todas las caracteristicas propias de un solitario Spider.
     * Utiliza un mazo simple con cartas de un mismo palo.
     */
    public void generarMesaMazoSimple() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.PICAS);
        Mazo mazo = new Mazo();
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConCantidadYPalos(104, palos);
        mazo.generarBaraja(semilla, palos);

        EstrategiaComparacion estrategia = new EstrategiaComparacionKlondike();
        Mesa mesa = new Mesa(mazo);
        for (int i = 0; i < 10; i++) {
            Columna columnaMesa = new ColumnaKlondike(estrategia);
            mesa.inicializarColumnaMesa(columnaMesa);
        }
        for (int i = 0; i < 10; i++) {
            Columna columnaFinal = new ColumnaKlondike(estrategia);
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

    @Test
    public void testSerializarYDeserializar() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.CORAZONES);
        Mazo mazo = new Mazo();
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConString("A0B0C0D0E0F0G0H0I0J0K0L0M0");
        mazo.generarBaraja(semilla, palos);

        EstrategiaComparacion estrategia = new EstrategiaComparacionKlondike();
        Mesa mesa = new Mesa(mazo);
        for (int i = 0; i < 6; i++) {
            Columna columnaMesa = new ColumnaKlondike(estrategia);
            mesa.inicializarColumnaMesa(columnaMesa);
        }
        for (int i = 0; i < 2; i++) {
            Columna columnaFinal = new ColumnaKlondike(estrategia);
            mesa.inicializarColumnaFinal(columnaFinal);
        }

        // Serializacion de la Mesa
        boolean seGrabo = false;
        try {
            FileOutputStream fileOut = new FileOutputStream("src/main/resources/mesa.txt");
            mesa.serializar(fileOut);
            seGrabo = true;
        } catch (IOException ex) {
            fail();
        }
        assertTrue(seGrabo);

        // Deserializacion de la Mesa
        Mesa nuevaMesa = null;
        try {
            FileInputStream fileIn = new FileInputStream("src/main/resources/mesa.txt");
            nuevaMesa = Mesa.deserializar(fileIn);

        } catch (IOException | ClassNotFoundException ex) {
            fail();
        }
        assertNotNull(nuevaMesa);

        assertEquals(mesa.sizeColumnaMesa(), nuevaMesa.sizeColumnaMesa());
        assertEquals(mesa.sizeColumnaFinal(), nuevaMesa.sizeColumnaFinal());
        for (int i = 13; i > 0; i--) {
            Carta carta1 = mesa.sacarCartaMazo();
            carta1.darVuelta();
            Carta carta2 = nuevaMesa.sacarCartaMazo();
            carta2.darVuelta();
            assertEquals(carta1.getPalo(), carta2.getPalo());
            assertEquals(carta1.getNumero(), carta2.getNumero(), 0);
        }
    }

}
