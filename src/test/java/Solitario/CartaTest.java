package Solitario;

import org.junit.Test;
import static org.junit.Assert.*;

public class CartaTest {

    @Test
    public void cartaBocaAbajo() {
        Carta carta = new Carta(2, Palos.PICAS);

        assertNull(carta.getColor());
        assertNull(carta.getNumero());
        assertNull(carta.getPalo());
    }

    @Test
    public void testDarVuelta() {
        Carta carta = new Carta(10, Palos.DIAMANTES);

        assertFalse(carta.esVisible());
        assertFalse(carta.esInteractuable());

        carta.darVuelta();

        assertTrue(carta.esVisible());
        assertFalse(carta.esInteractuable());
    }

    @Test
    public void testHacerInteractuable() {
        Carta carta = new Carta(10, Palos.DIAMANTES);

        assertFalse(carta.esVisible());
        assertFalse(carta.esInteractuable());

        carta.cambiarInteractuabilidad();
        // No se puede cambiar la interactuabilidad de una carta si no es visible.

        assertFalse(carta.esVisible());
        assertFalse(carta.esInteractuable());
    }

    @Test
    public void testDarVueltaYHacerInteractuable() {
        Carta carta = new Carta(10, Palos.DIAMANTES);

        assertFalse(carta.esVisible());
        assertFalse(carta.esInteractuable());

        carta.darVuelta();
        carta.cambiarInteractuabilidad();

        assertTrue(carta.esVisible());
        assertTrue(carta.esInteractuable());
    }

    @Test
    public void cartaCorrectaCorazones() {
        Carta carta = new Carta(5, Palos.CORAZONES);

        Color colorEsperado = Color.ROJO;
        int numeroEsperado = 5;
        Palos paloEsperado = Palos.CORAZONES;

        carta.darVuelta();

        assertEquals(numeroEsperado, carta.getNumero(), 0);
        assertEquals(colorEsperado, carta.getColor());
        assertEquals(paloEsperado, carta.getPalo());
    }

    @Test
    public void cartaCorrectaDiamantes() {
        Carta carta = new Carta(12, Palos.DIAMANTES);

        Color colorEsperado = Color.ROJO;
        int numeroEsperado = 12;
        Palos paloEsperado = Palos.DIAMANTES;

        carta.darVuelta();

        assertEquals(numeroEsperado, carta.getNumero(), 0);
        assertEquals(colorEsperado, carta.getColor());
        assertEquals(paloEsperado, carta.getPalo());
    }

    @Test
    public void cartaCorrectaTreboles() {
        Carta carta = new Carta(7, Palos.TREBOLES);

        Color colorEsperado = Color.NEGRO;
        int numeroEsperado = 7;
        Palos paloEsperado = Palos.TREBOLES;

        carta.darVuelta();

        assertEquals(numeroEsperado, carta.getNumero(), 0);
        assertEquals(colorEsperado, carta.getColor());
        assertEquals(paloEsperado, carta.getPalo());
    }

    @Test
    public void cartaCorrectaPicas() {
        Carta carta = new Carta(1, Palos.PICAS);

        Color colorEsperado = Color.NEGRO;
        int numeroEsperado = 1;
        Palos paloEsperado = Palos.PICAS;

        carta.darVuelta();

        assertEquals(numeroEsperado, carta.getNumero(), 0);
        assertEquals(colorEsperado, carta.getColor());
        assertEquals(paloEsperado, carta.getPalo());
    }
}
