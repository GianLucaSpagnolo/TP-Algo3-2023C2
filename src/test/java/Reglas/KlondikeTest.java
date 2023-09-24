package Reglas;

import org.junit.Test;

import static org.junit.Assert.*;

public class KlondikeTest {

    @Test
    public void test() {
        Klondike juego = new Klondike(null);
        juego.repartirCartasInicio();

    }
}