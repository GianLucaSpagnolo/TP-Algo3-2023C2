package Reglas;

import Solitario.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class KlondikeTest {

    // Tests Klondike iniciado de cero
    @Test
    public void inicioMazoKlondike() {
        Klondike klondike = new Klondike(null, null);
        Mesa mesa = klondike.getEstadoMesa();
        assertNull(mesa.sacarCartaDescarte());
        for (int i = 0; i < 52; i++)
            klondike.sacarDelMazo();
        Mesa nuevaMesa = klondike.getEstadoMesa();
        assertNull(nuevaMesa.sacarCartaMazo());
    }

    @Test
    public void inicioColumnasKlondike() {
        Klondike klondike = new Klondike(null, null);
        Mesa mesa = klondike.getEstadoMesa();
        assertEquals(7, mesa.columnasMesa.size(), 0);
        for (int i = 0; i < 7; i++)
            assertTrue(mesa.columnasMesa.get(i).isEmpty());
        assertEquals(4, mesa.columnasFinales.size(), 0);
        for (int i = 0; i < 4; i++)
            assertTrue(mesa.columnasFinales.get(i).isEmpty());
    }

    @Test
    public void testRepartirCartas() {
        Klondike klondike = new Klondike(null, null);
        klondike.repartirCartasInicio();
        Mesa mesa = klondike.getEstadoMesa();
        for (int i = 0; i < 7; i++)
            assertEquals(i + 1, mesa.columnasMesa.get(i).size(), 0);
        for (int i = 0; i < 4; i++)
            assertTrue(mesa.columnasFinales.get(i).isEmpty());

        for (int i = 0; i < 24; i++)
            klondike.sacarDelMazo();
        Mesa nuevaMesa = klondike.getEstadoMesa();
        assertNull(nuevaMesa.sacarCartaMazo());
    }

    @Test
    public void testReiniciarBaraja() {
        Klondike klondike = new Klondike(null, null);
        klondike.repartirCartasInicio();

    }
    // Tests Klondike en estado particular




    // Tests Klondike con semilla particular





}