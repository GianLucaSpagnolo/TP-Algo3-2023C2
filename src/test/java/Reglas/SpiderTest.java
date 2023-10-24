package Reglas;

import Solitario.Carta;
import Solitario.GeneradorSemillas;
import Solitario.Mesa;
import Solitario.Palos;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpiderTest {

    /**
     * Tests Spider iniciado de cero
     */
    @Test
    public void inicioMazoSpider() {
        Spider spider = new Spider(null, null);
        Mesa mesa = spider.getEstadoMesa();
        assertNotNull(mesa.sacarCartaMazo());
        assertFalse(spider.sacarDelMazo());  // Se intenta sacar cartas del mazo pero esta la mesa vacia
        Mesa nuevaMesa = spider.getEstadoMesa();
        assertNotNull(nuevaMesa.sacarCartaMazo());
    }

    @Test
    public void inicioColumnasSpider() {
        Spider spider = new Spider(null, null);
        Mesa mesa = spider.getEstadoMesa();
        assertEquals(10, mesa.sizeColumnaMesa(), 0);
        for (int i = 0; i < 7; i++)
            assertTrue(mesa.columnaMesaEnPosicion(i).isEmpty());
        assertEquals(8, mesa.sizeColumnaFinal(), 0);
        for (int i = 0; i < 4; i++)
            assertTrue(mesa.columnaFinalEnPosicion(i).isEmpty());
    }

    @Test
    public void testRepartirCartas() {
        Spider spider = new Spider(null, null);
        spider.repartirCartasInicio();
        Mesa mesa = spider.getEstadoMesa();
        for (int i = 0; i < 10; i++) {
            int cantidadCartas;
            if (i < 4)
                cantidadCartas = 6;
            else
                cantidadCartas = 5;
            assertEquals(cantidadCartas, mesa.columnaMesaEnPosicion(i).size(), 0);
            for (int j = 0; j < cantidadCartas; j++) {
                if (j == 0)
                    assertTrue(mesa.columnaMesaEnPosicion(i).peek().esVisible());
                else
                    assertFalse(mesa.columnaMesaEnPosicion(i).getCartas().get(j).esVisible());
            }
        }
        assertTrue(spider.sacarDelMazo());
    }

    @Test
    public void testVaciarBaraja() {
        Spider spider = new Spider(null, null);
        spider.repartirCartasInicio();
        for (int i = 0; i < 5; i++)
            assertTrue(spider.sacarDelMazo());

        Mesa mesa = spider.getEstadoMesa();
        for (int i = 0; i < 10; i++) {
            int cantidadCartas;
            if (i < 4)
                cantidadCartas = 11;
            else
                cantidadCartas = 10;
            assertEquals(cantidadCartas, mesa.columnaMesaEnPosicion(i).size(), 0);
        }
        assertFalse(spider.sacarDelMazo());
    }


    /**
     * Tests Spider con semilla particular
     * "I0F0E0B0I0A0K0I0H0C0E0I0B0E0I0M0C0J0M0L0G0L0F0J0G0D0I0K0J0J0F0G0D0G0E0M0G0F0H0K0A0E0K0M0D0A0B0A0J0M0F0F0 +
     * M0H0L0B0C0C0J0L0C0I0J0C0H0B0E0D0C0L0A0F0H0H0A0H0E0E0D0K0L0M0G0D0K0B0D0K0B0D0A0G0L0F0G0K0H0I0L0M0B0C0J0A0"
     */
    @Test
    public void spiderSemillaVacia() {
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConString("");
        Spider spider = new Spider(semilla, null);
        Mesa mesa1 = spider.getEstadoMesa();
        assertNull(mesa1.sacarCartaMazo());
        assertNull(mesa1.sacarCartaDescarte());

        spider.repartirCartasInicio();
        Mesa mesa2 = spider.getEstadoMesa();
        assertNull(mesa2.sacarCartaMazo());
        assertNull(mesa2.sacarCartaDescarte());
        for (int i = 0; i < 10; i++)
            assertTrue(mesa2.columnaMesaEnPosicion(i).isEmpty());
        for (int i = 0; i < 8; i++)
            assertTrue(mesa2.columnaFinalEnPosicion(i).isEmpty());

        assertFalse(spider.sacarDelMazo());
        Mesa mesa3 = spider.getEstadoMesa();
        assertNull(mesa3.sacarCartaMazo());
        assertNull(mesa3.sacarCartaDescarte());

        assertFalse(spider.moverCartas(1, 0, 3));
    }

    @Test
    public void spiderSemillaCompleta() {
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConString
                ("I0F0E0B0I0A0K0I0H0C0E0I0B0E0I0M0C0J0M0L0G0L0F0J0G0D0I0K0J0J0F0G0D0G0E0M0G0F0H0K0A0E0K0M0D0A0B0A0J0M0F0F0" +
                        "M0H0L0B0C0C0J0L0C0I0J0C0H0B0E0D0C0L0A0F0H0H0A0H0E0E0D0K0L0M0G0D0K0B0D0K0B0D0A0G0L0F0G0K0H0I0L0M0B0C0J0A0");
        Spider spider = new Spider(semilla, null);
        spider.repartirCartasInicio();
        Mesa mesa = spider.getEstadoMesa();

        Carta carta1 = mesa.sacarCartaMazo();
        carta1.darVuelta();
        assertEquals(13, carta1.getNumero(), 0);
        assertEquals(Palos.PICAS, carta1.getPalo());
        Carta carta2 = mesa.sacarCartaMazo();
        carta2.darVuelta();
        assertEquals(10, carta2.getNumero(), 0);
        assertEquals(Palos.PICAS, carta2.getPalo());
        Carta carta3 = mesa.sacarCartaMazo();
        carta3.darVuelta();
        assertEquals(1,carta3.getNumero(), 0);
        assertEquals(Palos.PICAS, carta3.getPalo());

        assertEquals(12, mesa.columnaMesaEnPosicion(0).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(0).peek().getPalo());
        assertEquals(12, mesa.columnaMesaEnPosicion(1).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(1).peek().getPalo());
        assertEquals(4, mesa.columnaMesaEnPosicion(2).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(2).peek().getPalo());
        assertEquals(12, mesa.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(3).peek().getPalo());
        assertEquals(8, mesa.columnaMesaEnPosicion(4).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(4).peek().getPalo());
        assertEquals(1, mesa.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(5).peek().getPalo());
        assertEquals(2, mesa.columnaMesaEnPosicion(6).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(6).peek().getPalo());
        assertEquals(3, mesa.columnaMesaEnPosicion(7).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(7).peek().getPalo());
        assertEquals(2, mesa.columnaMesaEnPosicion(8).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(8).peek().getPalo());
        assertEquals(6, mesa.columnaMesaEnPosicion(9).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(9).peek().getPalo());
    }


    /**
     * Tests Klondike en estado particular
     */

}