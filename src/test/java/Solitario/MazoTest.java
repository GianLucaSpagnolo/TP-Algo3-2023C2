package Solitario;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class MazoTest {

    @Test
    public void creacionBarajaVacia() {
        Mazo mazo = new Mazo(0, new ArrayList<>());
        assertTrue(mazo.mazoVacio());
    }

    @Test
    public void SacarVariasCartas() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.CORAZONES);
        Mazo mazo = new Mazo(5, palos);

        for (int i = 5; i > 0; i--) {
            Carta carta = mazo.sacarCarta();
            carta.darVuelta();

            assertEquals(i, carta.getNumero(), 0);
            assertEquals(Color.ROJO, carta.getColor());
            assertEquals(Palos.CORAZONES, carta.getPalo());
        }
        assertTrue(mazo.mazoVacio());
    }

    @Test
    public void SacarDeMazoVacio() {
        ArrayList<Palos> palos = new ArrayList<>();
        Mazo mazo = new Mazo(0, palos);
        assertNull(mazo.sacarCarta());
    }

    @Test
    public void AgregarUnaCarta() {
        ArrayList<Palos> palos = new ArrayList<>();
        Mazo mazo = new Mazo(0, palos);

        Carta carta = new Carta(12, Palos.DIAMANTES);
        carta.darVuelta();

        mazo.agregarCarta(carta);
        assertFalse(mazo.mazoVacio());

        carta = mazo.sacarCarta();
        assertEquals(12, carta.getNumero(), 0);
        assertEquals(Color.ROJO, carta.getColor());
        assertEquals(Palos.DIAMANTES, carta.getPalo());
    }

    @Test
    public void AgregarVariasCarta() {
        ArrayList<Palos> palos = new ArrayList<>();
        Mazo mazo = new Mazo(0, palos);

        Carta carta1 = new Carta(6, Palos.DIAMANTES);
        Carta carta2 = new Carta(8, Palos.PICAS);
        Carta carta3 = new Carta(1, Palos.TREBOLES);
        Carta carta4 = new Carta(5, Palos.CORAZONES);

        mazo.agregarCarta(carta1);
        mazo.agregarCarta(carta2);
        mazo.agregarCarta(carta3);
        mazo.agregarCarta(carta4);
        assertFalse(mazo.mazoVacio());
    }

    @Test
    public void creacionBarajaDeUnaCarta() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.DIAMANTES);
        Mazo mazo = new Mazo(1, palos);

        Carta carta = mazo.sacarCarta();
        carta.darVuelta();

        assertEquals(1, carta.getNumero(), 0);
        assertEquals(Color.ROJO, carta.getColor());
        assertEquals(Palos.DIAMANTES, carta.getPalo());

        assertTrue(mazo.mazoVacio());
    }

    @Test
    public void creacionBarajaSimple() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.PICAS);
        Mazo mazo = new Mazo(13, palos);

        for (int i = 13; i > 0; i--) {
            Carta carta = mazo.sacarCarta();
            carta.darVuelta();

            assertEquals(i, carta.getNumero(), 0);
            assertEquals(Color.NEGRO, carta.getColor());
            assertEquals(Palos.PICAS, carta.getPalo());
        }
        assertTrue(mazo.mazoVacio());
    }

    @Test
    public void creacionGranBarajaSimple() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.DIAMANTES);
        Mazo mazo = new Mazo(104, palos);

        for (int j = 0; j < 8; j++) {
            for (int i = 13; i > 0; i--) {
                Carta carta = mazo.sacarCarta();
                carta.darVuelta();

                assertEquals(i, carta.getNumero(), 0);
                assertEquals(Color.ROJO, carta.getColor());
                assertEquals(Palos.DIAMANTES, carta.getPalo());
            }
        }
        assertTrue(mazo.mazoVacio());
    }

    @Test
    public void creacionBarajaCompleta() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.PICAS);
        palos.add(Palos.TREBOLES);
        palos.add(Palos.CORAZONES);
        palos.add(Palos.DIAMANTES);
        Mazo mazo = new Mazo(52, palos);

        for (int j = 0; j < 4; j++) {
            for (int i = 13; i > 0; i--) {
                Carta carta = mazo.sacarCarta();
                carta.darVuelta();

                assertEquals(i, carta.getNumero(), 0);
                assertEquals(palos.get(3 - j), carta.getPalo());
            }
        }
        assertTrue(mazo.mazoVacio());
    }

    @Test
    public void testMezclarBarajaCantidad() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.PICAS);
        palos.add(Palos.TREBOLES);
        palos.add(Palos.CORAZONES);
        palos.add(Palos.DIAMANTES);
        Mazo mazo = new Mazo(52, palos);
        mazo.mezclar();

        for (int i = 0; i < 52; i++) {
            Carta carta = mazo.sacarCarta();
            assertNotNull(carta);
        }
        assertTrue(mazo.mazoVacio());
    }

    @Test
    public void testMezclarOrdenBaraja() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.PICAS);
        palos.add(Palos.TREBOLES);
        palos.add(Palos.CORAZONES);
        palos.add(Palos.DIAMANTES);
        Mazo mazo = new Mazo(52, palos);
        mazo.mezclar();

        int coincidencias = 0;
        for (int j = 0; j < 4; j++) {
            for (int i = 13; i > 0; i--) {
                Carta carta = mazo.sacarCarta();
                carta.darVuelta();

                boolean mismoNumero = i == carta.getNumero();
                boolean mismoPalo = palos.get(3 - j) == carta.getPalo();
                if (mismoPalo && mismoNumero)
                    coincidencias++;
            }
        }
        // La probabilidad de que una carta, luego de mezclar el mazo, este en el lugar que estaba es, considerando
        // una mezcla totalmente random, 1 entre 52. Por lo que se asume que no van a haber mas de 6 coincidencias.
        assertTrue(coincidencias < 6);
    }
}
