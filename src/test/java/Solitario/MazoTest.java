package Solitario;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class MazoTest {

    @Test
    public void testCreacionMazo() {
        Mazo mazo = new Mazo();
        assertTrue(mazo.estaVacio());
    }

    @Test
    public void AgregarUnaCartaAlMazo() {
        Mazo mazo = new Mazo();

        Carta carta = new Carta(12, Palos.DIAMANTES);
        carta.darVuelta();

        mazo.agregarCarta(carta);
        assertFalse(mazo.estaVacio());

        carta = mazo.sacarCarta();
        assertEquals(12, carta.getNumero(), 0);
        assertEquals(Color.ROJO, carta.getColor());
        assertEquals(Palos.DIAMANTES, carta.getPalo());
    }

    @Test
    public void AgregarVariasCartaAlMazo() {
        Mazo mazo = new Mazo();

        Carta carta1 = new Carta(6, Palos.DIAMANTES);
        Carta carta2 = new Carta(8, Palos.PICAS);
        Carta carta3 = new Carta(1, Palos.TREBOLES);
        Carta carta4 = new Carta(5, Palos.CORAZONES);

        mazo.agregarCarta(carta1);
        mazo.agregarCarta(carta2);
        mazo.agregarCarta(carta3);
        mazo.agregarCarta(carta4);
        assertFalse(mazo.estaVacio());
    }

    @Test
    /**
     * Crea una baraja a partir de una semilla vacia, lo cual resulta en un mazo vacio.
     */
    public void creacionBarajaSemillaVacia() {
        Mazo mazo = new Mazo();
        ArrayList<Palos> palos = new ArrayList<>();
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConString("");
        mazo.generarBaraja(semilla, palos);
        assertTrue(mazo.estaVacio());
    }

    @Test
    public void SacarDeMazoVacio() {
        Mazo mazo = new Mazo();
        assertNull(mazo.sacarCarta());
    }

    @Test
    public void SacarVariasCartasDelMazo() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.CORAZONES);
        Mazo mazo = new Mazo();
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConString("A0B0C0D0E0");
        mazo.generarBaraja(semilla, palos);

        for (int i = 5; i > 0; i--) {
            Carta carta = mazo.sacarCarta();
            carta.darVuelta();

            assertEquals(i, carta.getNumero(), 0);
            assertEquals(Color.ROJO, carta.getColor());
            assertEquals(Palos.CORAZONES, carta.getPalo());
        }
        assertTrue(mazo.estaVacio());
    }

    @Test
    /**
     * Crea una baraja con una unica carta.
     */
    public void creacionBarajaDeUnaCarta() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.DIAMANTES);
        Mazo mazo = new Mazo();
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConString("A0");
        mazo.generarBaraja(semilla, palos);

        Carta carta = mazo.sacarCarta();
        carta.darVuelta();

        assertEquals(1, carta.getNumero(), 0);
        assertEquals(Color.ROJO, carta.getColor());
        assertEquals(Palos.DIAMANTES, carta.getPalo());

        assertTrue(mazo.estaVacio());
    }

    @Test
    /**
     * Crea una baraja de 13 cartas de un mismo palo.
     */
    public void creacionBarajaSimple() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.PICAS);
        Mazo mazo = new Mazo();
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConString("A0B0C0D0E0F0G0H0I0J0K0L0M0");
        mazo.generarBaraja(semilla, palos);

        for (int i = 13; i > 0; i--) {
            Carta carta = mazo.sacarCarta();
            carta.darVuelta();

            assertEquals(i, carta.getNumero(), 0);
            assertEquals(Color.NEGRO, carta.getColor());
            assertEquals(Palos.PICAS, carta.getPalo());
        }
        assertTrue(mazo.estaVacio());
    }

    @Test
    /**
     * Crea una baraja de 104 cartas de un mismo palo.
     */
    public void creacionGranBarajaSimple() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.DIAMANTES);
        Mazo mazo = new Mazo();
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConString
                ("A0B0C0D0E0F0G0H0I0J0K0L0M0A0B0C0D0E0F0G0H0I0J0K0L0M0A0B0C0D0E0F0G0H0I0J0K0L0M0A0B0C0D0E0F0G0H0I0J0K0L0M0" +
                "A0B0C0D0E0F0G0H0I0J0K0L0M0A0B0C0D0E0F0G0H0I0J0K0L0M0A0B0C0D0E0F0G0H0I0J0K0L0M0A0B0C0D0E0F0G0H0I0J0K0L0M0");
        mazo.generarBaraja(semilla, palos);

        for (int j = 0; j < 8; j++) {
            for (int i = 13; i > 0; i--) {
                Carta carta = mazo.sacarCarta();
                carta.darVuelta();

                assertEquals(i, carta.getNumero(), 0);
                assertEquals(Color.ROJO, carta.getColor());
                assertEquals(Palos.DIAMANTES, carta.getPalo());
            }
        }
        assertTrue(mazo.estaVacio());
    }

    @Test
    /**
     * Crea una baraja de 52 cartas entre 4 palos diferentes.
     */
    public void creacionBarajaCompleta() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.PICAS);
        palos.add(Palos.TREBOLES);
        palos.add(Palos.CORAZONES);
        palos.add(Palos.DIAMANTES);
        Mazo mazo = new Mazo();
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConString
                ("A0B0C0D0E0F0G0H0I0J0K0L0M0A1B1C1D1E1F1G1H1I1J1K1L1M1A2B2C2D2E2F2G2H2I2J2K2L2M2A3B3C3D3E3F3G3H3I3J3K3L3M3");
        mazo.generarBaraja(semilla, palos);

        for (int j = 0; j < 4; j++) {
            for (int i = 13; i > 0; i--) {
                Carta carta = mazo.sacarCarta();
                carta.darVuelta();

                assertEquals(i, carta.getNumero(), 0);
                assertEquals(palos.get(3 - j), carta.getPalo());
            }
        }
        assertTrue(mazo.estaVacio());
    }
}
