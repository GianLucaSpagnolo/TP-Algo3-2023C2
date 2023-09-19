package Solitario;

import org.junit.Test;

import static org.junit.Assert.*;

public class CartaTest {

    @Test
    public void CartaBasica() {
        Carta carta = new Carta(5, Palos.CORAZONES);

        Color colorEsperado = Color.ROJO;
        int numeroEsperado = 5;
        Palos paloEsperado = Palos.CORAZONES;

        carta.darVuelta();

        assertEquals(numeroEsperado, carta.getNumero(), 0);
        assertEquals(colorEsperado, carta.getColor());
        assertEquals(paloEsperado, carta.getPalo());
    }
}