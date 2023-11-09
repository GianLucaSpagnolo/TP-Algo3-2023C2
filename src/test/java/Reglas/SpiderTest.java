package Reglas;

import Solitario.Carta;
import Solitario.GeneradorSemillas;
import Solitario.Semilla;
import Solitario.Mesa;
import Solitario.Palos;
import Solitario.Columna;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
        for (int i = 0; i < 10; i++)
            assertTrue(mesa.columnaMesaEnPosicion(i).isEmpty());
        assertEquals(8, mesa.sizeColumnaFinal(), 0);
        for (int i = 0; i < 8; i++)
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
        Semilla semilla = GeneradorSemillas.generarSemillaConString("");
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

        Columna seleccion = spider.seleccionarCartas(1, 3);
        assertNull(seleccion);
        assertFalse(spider.moverCartas(seleccion, 1, 0));
    }

    @Test
    public void spiderSemillaCompleta() {
        Semilla semilla = GeneradorSemillas.generarSemillaConString
                ("I0F0E0B0I0A0K0I0H0C0E0I0B0E0I0M0C0J0M0L0G0L0F0J0G0D0I0K0J0J0F0G0D0G0E0M0G0F0H0K0A0E0K0M0D0A0B0A0J0M0F0M0" +
                        "F0H0L0B0C0C0J0L0C0I0J0C0H0B0E0D0C0L0A0F0H0H0A0H0E0E0D0K0L0M0G0D0K0B0D0K0B0D0A0G0L0F0G0K0H0I0L0M0B0C0J0A0");
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

    @Test
    public void spiderSacarDelMazoVariasVeces() {
        Semilla semilla = GeneradorSemillas.generarSemillaConString
                ("I0F0E0B0I0A0K0I0H0C0E0I0B0E0I0M0C0J0M0L0G0L0F0J0G0D0I0K0J0J0F0G0D0G0E0M0G0F0H0K0A0E0K0M0D0A0B0A0J0M0F0M0" +
                        "F0H0L0B0C0C0J0L0C0I0J0C0H0B0E0D0C0L0A0F0H0H0A0H0E0E0D0K0L0M0G0D0K0B0D0K0B0D0A0G0L0F0G0K0H0I0L0M0B0C0J0A0");
        Spider spider = new Spider(semilla, null);
        spider.repartirCartasInicio();
        spider.sacarDelMazo();
        spider.sacarDelMazo();
        spider.sacarDelMazo();
        Mesa mesa = spider.getEstadoMesa();

        Carta carta1 = mesa.sacarCartaMazo();
        carta1.darVuelta();
        assertEquals(12, carta1.getNumero(), 0);
        assertEquals(Palos.PICAS, carta1.getPalo());
        Carta carta2 = mesa.sacarCartaMazo();
        carta2.darVuelta();
        assertEquals(13, carta2.getNumero(), 0);
        assertEquals(Palos.PICAS, carta2.getPalo());
        Carta carta3 = mesa.sacarCartaMazo();
        carta3.darVuelta();
        assertEquals(10,carta3.getNumero(), 0);
        assertEquals(Palos.PICAS, carta3.getPalo());

        assertEquals(10, mesa.columnaMesaEnPosicion(0).peek().getNumero(), 0);
        assertEquals(9, mesa.columnaMesaEnPosicion(0).size(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(0).peek().getPalo());
        assertEquals(10, mesa.columnaMesaEnPosicion(1).peek().getNumero(), 0);
        assertEquals(9, mesa.columnaMesaEnPosicion(1).size(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(1).peek().getPalo());
        assertEquals(11, mesa.columnaMesaEnPosicion(2).peek().getNumero(), 0);
        assertEquals(9, mesa.columnaMesaEnPosicion(2).size(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(2).peek().getPalo());
        assertEquals(9, mesa.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(9, mesa.columnaMesaEnPosicion(3).size(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(3).peek().getPalo());
        assertEquals(4, mesa.columnaMesaEnPosicion(4).peek().getNumero(), 0);
        assertEquals(8, mesa.columnaMesaEnPosicion(4).size(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(4).peek().getPalo());
        assertEquals(7, mesa.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(8, mesa.columnaMesaEnPosicion(5).size(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(5).peek().getPalo());
        assertEquals(10, mesa.columnaMesaEnPosicion(6).peek().getNumero(), 0);
        assertEquals(8, mesa.columnaMesaEnPosicion(6).size(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(6).peek().getPalo());
        assertEquals(6, mesa.columnaMesaEnPosicion(7).peek().getNumero(), 0);
        assertEquals(8, mesa.columnaMesaEnPosicion(7).size(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(7).peek().getPalo());
        assertEquals(12, mesa.columnaMesaEnPosicion(8).peek().getNumero(), 0);
        assertEquals(8, mesa.columnaMesaEnPosicion(8).size(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(8).peek().getPalo());
        assertEquals(7, mesa.columnaMesaEnPosicion(9).peek().getNumero(), 0);
        assertEquals(8, mesa.columnaMesaEnPosicion(9).size(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(9).peek().getPalo());
    }

    @Test
    public void moverUnaCartaEntreColumnas() {
        Semilla semilla = GeneradorSemillas.generarSemillaConString
                ("I0F0E0B0I0A0K0I0H0C0E0I0B0E0I0M0C0J0M0L0G0L0F0J0G0D0I0K0J0J0F0G0D0G0E0M0G0F0H0K0A0E0K0M0D0A0B0A0J0M0F0M0" +
                        "F0H0L0B0C0C0J0L0C0I0J0C0H0B0E0D0C0L0A0F0H0H0A0H0E0E0D0K0L0M0G0D0K0B0D0K0B0D0A0G0L0F0G0K0H0I0L0M0B0C0J0A0");
        Spider spider = new Spider(semilla, null);
        spider.repartirCartasInicio();

        Columna seleccion1 = spider.seleccionarCartas(6, 0);
        assertNotNull(seleccion1);
        assertTrue(spider.moverCartas(seleccion1, 6, 7));
        Columna seleccion2 = spider.seleccionarCartas(6, 0);
        assertNotNull(seleccion2);
        assertTrue(spider.moverCartas(seleccion2, 6, 9));
        Columna seleccion3 = spider.seleccionarCartas(5, 0);
        assertNotNull(seleccion3);
        assertTrue(spider.moverCartas(seleccion3, 5, 7));
        Columna seleccion4 = spider.seleccionarCartas(0, 0);
        assertNotNull(seleccion4);
        assertFalse(spider.moverCartas(seleccion4, 0, 1));
        Columna seleccion5 = spider.seleccionarCartas(1, 0);
        assertNotNull(seleccion5);
        assertFalse(spider.moverCartas(seleccion5, 1, 2));
        Columna seleccion6 = spider.seleccionarCartas(2, 0);
        assertNotNull(seleccion6);
        assertFalse(spider.moverCartas(seleccion6, 2, 1));
        Columna seleccion7 = spider.seleccionarCartas(8, 0);
        assertNotNull(seleccion7);
        assertFalse(spider.moverCartas(seleccion7, 8, 7));
        Mesa mesa = spider.getEstadoMesa();

        assertEquals(12, mesa.columnaMesaEnPosicion(0).peek().getNumero(), 0);
        assertEquals(6, mesa.columnaMesaEnPosicion(0).size(), 0);
        assertEquals(12, mesa.columnaMesaEnPosicion(1).peek().getNumero(), 0);
        assertEquals(6, mesa.columnaMesaEnPosicion(1).size(), 0);
        assertEquals(4, mesa.columnaMesaEnPosicion(2).peek().getNumero(), 0);
        assertEquals(6, mesa.columnaMesaEnPosicion(2).size(), 0);
        assertEquals(12, mesa.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(6, mesa.columnaMesaEnPosicion(3).size(), 0);
        assertEquals(8, mesa.columnaMesaEnPosicion(4).peek().getNumero(), 0);
        assertEquals(5, mesa.columnaMesaEnPosicion(4).size(), 0);
        assertEquals(6, mesa.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(4, mesa.columnaMesaEnPosicion(5).size(), 0);
        assertEquals(4, mesa.columnaMesaEnPosicion(6).peek().getNumero(), 0);
        assertEquals(3, mesa.columnaMesaEnPosicion(6).size(), 0);
        assertEquals(1, mesa.columnaMesaEnPosicion(7).peek().getNumero(), 0);
        assertEquals(7, mesa.columnaMesaEnPosicion(7).size(), 0);
        assertEquals(2, mesa.columnaMesaEnPosicion(8).peek().getNumero(), 0);
        assertEquals(5, mesa.columnaMesaEnPosicion(8).size(), 0);
        assertEquals(5, mesa.columnaMesaEnPosicion(9).peek().getNumero(), 0);
        assertEquals(6, mesa.columnaMesaEnPosicion(9).size(), 0);
    }

    @Test
    public void moverVariasCartasEntreColumnas() {
        Semilla semilla = GeneradorSemillas.generarSemillaConString
                ("I0F0E0B0I0A0K0I0H0C0E0I0B0E0I0M0C0J0M0L0G0L0F0J0G0D0I0K0J0J0F0G0D0G0E0M0G0F0H0K0A0E0K0M0D0A0B0A0J0M0F0M0" +
                        "F0H0L0B0C0C0J0L0C0I0J0C0H0B0E0D0C0L0A0F0H0H0A0H0E0E0D0K0L0M0G0D0K0B0D0K0B0D0A0G0L0F0G0K0H0I0L0M0B0C0J0A0");
        Spider spider = new Spider(semilla, null);
        spider.repartirCartasInicio();

        assertFalse(spider.moverCartas(spider.seleccionarCartas(8, 1), 8, 2));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(5, 0), 5, 6));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(6, 1), 6, 7));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(7, 2), 7, 2));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(2, 3), 2, 6));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(6, 4), 6, 9));
        assertFalse(spider.moverCartas(spider.seleccionarCartas(3, 1), 3, 4));
        assertFalse(spider.moverCartas(spider.seleccionarCartas(9, 5), 9, 0));
        Columna seleccion = spider.seleccionarCartas(9, 5);
        assertNotNull(seleccion);
        assertFalse(spider.moverCartas(seleccion, 9, 5));
        Mesa mesa = spider.getEstadoMesa();

        assertEquals(12, mesa.columnaMesaEnPosicion(0).peek().getNumero(), 0);
        assertEquals(6, mesa.columnaMesaEnPosicion(0).size(), 0);
        assertEquals(12, mesa.columnaMesaEnPosicion(1).peek().getNumero(), 0);
        assertEquals(6, mesa.columnaMesaEnPosicion(1).size(), 0);
        assertEquals(11, mesa.columnaMesaEnPosicion(2).peek().getNumero(), 0);
        assertEquals(5, mesa.columnaMesaEnPosicion(2).size(), 0);
        assertEquals(12, mesa.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(6, mesa.columnaMesaEnPosicion(3).size(), 0);
        assertEquals(8, mesa.columnaMesaEnPosicion(4).peek().getNumero(), 0);
        assertEquals(5, mesa.columnaMesaEnPosicion(4).size(), 0);
        assertEquals(6, mesa.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(4, mesa.columnaMesaEnPosicion(5).size(), 0);
        assertEquals(4, mesa.columnaMesaEnPosicion(6).peek().getNumero(), 0);
        assertEquals(3, mesa.columnaMesaEnPosicion(6).size(), 0);
        assertEquals(9, mesa.columnaMesaEnPosicion(7).peek().getNumero(), 0);
        assertEquals(4, mesa.columnaMesaEnPosicion(7).size(), 0);
        assertEquals(2, mesa.columnaMesaEnPosicion(8).peek().getNumero(), 0);
        assertEquals(5, mesa.columnaMesaEnPosicion(8).size(), 0);
        assertEquals(1, mesa.columnaMesaEnPosicion(9).peek().getNumero(), 0);
        assertEquals(10, mesa.columnaMesaEnPosicion(9).size(), 0);
    }

    @Test
    public void testVaciarUnaColumnaMesa() {
        Semilla semilla = GeneradorSemillas.generarSemillaConString
                ("I0F0E0B0I0A0K0I0H0C0E0I0B0E0I0M0C0J0M0L0G0L0F0J0G0D0I0K0J0J0F0G0D0G0E0M0G0F0H0K0A0E0K0M0D0A0B0A0J0M0F0M0" +
                        "F0H0L0B0C0C0J0L0C0I0J0C0H0B0E0D0C0L0A0F0H0H0A0H0E0E0D0K0L0M0G0D0K0B0D0K0B0D0A0G0L0F0G0K0H0I0L0M0B0C0J0A0");
        Spider spider = new Spider(semilla, null);
        spider.repartirCartasInicio();

        assertTrue(spider.moverCartas(spider.seleccionarCartas(8, 0), 8, 7));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(7, 1), 7, 2));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(4, 0), 4, 7));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(2, 2), 2, 4));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(2, 0), 2, 0));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(2, 0), 2, 8));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(4, 3), 4, 9));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(2, 0), 2, 4));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(2, 0), 2, 6));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(2, 0), 2, 7));

        // Se pueden mover cartas o segmentos de cartas sin importar a una columna vacia
        assertTrue(spider.moverCartas(spider.seleccionarCartas(0, 1), 0, 2));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(2, 1), 2, 0));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(6, 0), 6, 2));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(2, 0), 2, 6));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(9, 3), 9, 2));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(2, 3), 2, 9));
        assertFalse(spider.moverCartas(spider.seleccionarCartas(3, 1), 3, 2));
        // Se verifica que no se puede sacar cartas del mazo si hay una columna vacia
        assertFalse(spider.sacarDelMazo());
        Mesa mesa = spider.getEstadoMesa();

        assertTrue(mesa.columnaMesaEnPosicion(2).isEmpty());
        assertEquals(11, mesa.columnaMesaEnPosicion(0).peek().getNumero(), 0);
        assertEquals(7, mesa.columnaMesaEnPosicion(0).size(), 0);
        assertEquals(12, mesa.columnaMesaEnPosicion(1).peek().getNumero(), 0);
        assertEquals(6, mesa.columnaMesaEnPosicion(1).size(), 0);
        assertEquals(12, mesa.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(6, mesa.columnaMesaEnPosicion(3).size(), 0);
        assertEquals(4, mesa.columnaMesaEnPosicion(4).peek().getNumero(), 0);
        assertEquals(4, mesa.columnaMesaEnPosicion(4).size(), 0);
        assertEquals(1, mesa.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(5, mesa.columnaMesaEnPosicion(5).size(), 0);
        assertEquals(1, mesa.columnaMesaEnPosicion(6).peek().getNumero(), 0);
        assertEquals(6, mesa.columnaMesaEnPosicion(6).size(), 0);
        assertEquals(7, mesa.columnaMesaEnPosicion(7).peek().getNumero(), 0);
        assertEquals(6, mesa.columnaMesaEnPosicion(7).size(), 0);
        assertEquals(2, mesa.columnaMesaEnPosicion(8).peek().getNumero(), 0);
        assertEquals(5, mesa.columnaMesaEnPosicion(8).size(), 0);
        assertEquals(2, mesa.columnaMesaEnPosicion(9).peek().getNumero(), 0);
        assertEquals(9, mesa.columnaMesaEnPosicion(9).size(), 0);
    }

    /**
     * Realiza una serie de movimientos de cartas entre columnas que genera una cadena de 13 cartas (desde A hasta K)
     * en una columna mesa, lo cual deberia enviar dicho segmento de cartas a una columna final automaticamente.
     */
    void completarUnaColumnaFinal(Spider spider) {
        assertTrue(spider.moverCartas(spider.seleccionarCartas(8, 0), 8, 7));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(7, 0), 7, 8));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(6, 0), 6, 7));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(5, 0), 5, 8));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(8, 2), 8, 2));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(2, 3), 2, 6));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(2, 0), 2, 0));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(2, 0), 2, 8));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(7, 1), 7, 2));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(4, 0), 4, 7));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(2, 2), 2, 4));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(2, 0), 2, 4));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(2, 0), 2, 7));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(6, 4), 6, 9));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(8, 1), 8, 6));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(7, 2), 7, 8));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(8, 3), 8, 0));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(9, 5), 9, 0));
        assertTrue(spider.moverCartas(spider.seleccionarCartas(0, 11), 0, 9));
    }

    /**
     * Verifica que se haya movido correctamente el segmento de cartas cadena (desde A hasta K) a una columna final
     * correspondiente, y que el resto de las columnas mesa esten con sus respectivas cartas.
     */
    void verificarUnaColumnaFinal(Mesa mesa) {
        Carta carta = mesa.sacarCartaMazo();
        carta.darVuelta();
        assertEquals(13, carta.getNumero(), 0);
        carta.darVuelta();
        mesa.insertarCartaMazo(carta);

        assertEquals(13, mesa.columnaFinalEnPosicion(0).size(), 0);
        assertEquals(1, mesa.columnaFinalEnPosicion(0).peek().getNumero(), 0);

        assertEquals(13, mesa.columnaMesaEnPosicion(0).peek().getNumero(), 0);
        assertEquals(5, mesa.columnaMesaEnPosicion(0).size(), 0);
        assertEquals(12, mesa.columnaMesaEnPosicion(1).peek().getNumero(), 0);
        assertEquals(6, mesa.columnaMesaEnPosicion(1).size(), 0);
        assertNull(mesa.columnaMesaEnPosicion(2).peek());
        assertEquals(0, mesa.columnaMesaEnPosicion(2).size(), 0);
        assertEquals(12, mesa.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(6, mesa.columnaMesaEnPosicion(3).size(), 0);
        assertEquals(1, mesa.columnaMesaEnPosicion(4).peek().getNumero(), 0);
        assertEquals(8, mesa.columnaMesaEnPosicion(4).size(), 0);
        assertEquals(6, mesa.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(4, mesa.columnaMesaEnPosicion(5).size(), 0);
        assertEquals(2, mesa.columnaMesaEnPosicion(6).peek().getNumero(), 0);
        assertEquals(5, mesa.columnaMesaEnPosicion(6).size(), 0);
        assertEquals(10, mesa.columnaMesaEnPosicion(7).peek().getNumero(), 0);
        assertEquals(3, mesa.columnaMesaEnPosicion(7).size(), 0);
        assertEquals(12, mesa.columnaMesaEnPosicion(8).peek().getNumero(), 0);
        assertEquals(1, mesa.columnaMesaEnPosicion(8).size(), 0);
        assertEquals(6, mesa.columnaMesaEnPosicion(9).peek().getNumero(), 0);
        assertEquals(3, mesa.columnaMesaEnPosicion(9).size(), 0);
    }

    @Test
    public void testCompletarUnaColumnaFinal() {
        Semilla semilla = GeneradorSemillas.generarSemillaConString
                ("I0F0E0B0I0A0K0I0H0C0E0I0B0E0I0M0C0J0M0L0G0L0F0J0G0D0I0K0J0J0F0G0D0G0E0M0G0F0H0K0A0E0K0M0D0A0B0A0J0M0F0M0" +
                        "F0H0L0B0C0C0J0L0C0I0J0C0H0B0E0D0C0L0A0F0H0H0A0H0E0E0D0K0L0M0G0D0K0B0D0K0B0D0A0G0L0F0G0K0H0I0L0M0B0C0J0A0");
        Spider spider = new Spider(semilla, null);
        spider.repartirCartasInicio();
        completarUnaColumnaFinal(spider);

        Mesa mesa = spider.getEstadoMesa();
        verificarUnaColumnaFinal(mesa);
    }

    @Test
    public void verificarJuegoGanado() {
        Semilla semilla = GeneradorSemillas.generarSemillaConString
                ("C0B0A0L0K0J0F0E0D0A0L0K0J0I0H0G0I0H0G0B0I0H0G0F0E0D0F0E0D0C0F0E0D0C0B0A0C0B0A0M0C0B0A0L0K0J0L0K0J0M0" +
                        "L0I0F0C0L0K0H0E0B0K0J0G0D0A0J0I0F0C0L0I0H0E0B0K0H0G0D0A0J0G0I0F0C0L0I0F0H0E0B0K0H0E0G0D0A0J0G0D0M0M0M0M0M0M0");
        Spider spider = new Spider(semilla, null);
        spider.repartirCartasInicio();
        for (int i = 0; i < 5; i++)
            assertTrue(spider.sacarDelMazo());

        assertTrue(spider.moverCartas(spider.seleccionarCartas(0, 2), 0, 1));

        int cantidadCartas = 3;
        for (int j = 0; j < 11; j++) {
            for (int k = 1; k < 10; k++) {
                assertFalse(spider.estaGanado());

                if (cantidadCartas == 11) {
                    assertTrue(spider.moverCartas(spider.seleccionarCartas(k, cantidadCartas), k, 0));
                    cantidadCartas = 0;
                }

                else {
                    if (j == 0) {
                        if (k == 3) {
                            assertTrue(spider.moverCartas(spider.seleccionarCartas(k, cantidadCartas), k, 1));
                            cantidadCartas++;
                            break;
                        } else
                            assertTrue(spider.moverCartas(spider.seleccionarCartas(k, cantidadCartas), k, k + 1));
                    } else {
                        if (k == 9)
                            assertTrue(spider.moverCartas(spider.seleccionarCartas(k, cantidadCartas), k, 1));
                        else
                            assertTrue(spider.moverCartas(spider.seleccionarCartas(k, cantidadCartas), k, k + 1));
                    }
                    cantidadCartas++;
                }
            }
        }

        // Momento de la verdad
        assertTrue(spider.estaGanado());

        Mesa mesa = spider.getEstadoMesa();
        for (int i = 0; i < 8; i++) {
            assertEquals(13, mesa.columnaFinalEnPosicion(i).size(), 0);
            assertEquals(1, mesa.columnaFinalEnPosicion(i).peek().getNumero(), 0);
        }
    }

    /**
     * Tests Klondike en estado particular
     */
    @Test
    public void EstadosParticularesDeMesaEnColumnasMesa() {
        Semilla semilla = GeneradorSemillas.generarSemillaConString
                ("I0F0E0B0I0A0K0I0H0C0E0I0B0E0I0M0C0J0M0L0G0L0F0J0G0D0I0K0J0J0F0G0D0G0E0M0G0F0H0K0A0E0K0M0D0A0B0A0J0M0F0M0" +
                        "F0H0L0B0C0C0J0L0C0I0J0C0H0B0E0D0C0L0A0F0H0H0A0H0E0E0D0K0L0M0G0D0K0B0D0K0B0D0A0G0L0F0G0K0H0I0L0M0B0C0J0A0");
        Spider spider1 = new Spider(semilla, null);
        spider1.repartirCartasInicio();
        assertTrue(spider1.sacarDelMazo());
        assertTrue(spider1.sacarDelMazo());
        assertTrue(spider1.sacarDelMazo());
        assertTrue(spider1.moverCartas(spider1.seleccionarCartas(1, 0), 1, 2));
        assertTrue(spider1.moverCartas(spider1.seleccionarCartas(3, 0), 3, 2));
        assertTrue(spider1.moverCartas(spider1.seleccionarCartas(2, 2), 2, 8));
        Mesa mesa1 = spider1.getEstadoMesa();
        Carta carta1 = mesa1.sacarCartaMazo();
        carta1.darVuelta();
        assertEquals(12, carta1.getNumero(), 0);
        carta1.darVuelta();
        mesa1.insertarCartaMazo(carta1);
        assertEquals(10, mesa1.columnaMesaEnPosicion(0).peek().getNumero(), 0);
        assertEquals(9, mesa1.columnaMesaEnPosicion(0).size(), 0);
        assertEquals(8, mesa1.columnaMesaEnPosicion(1).peek().getNumero(), 0);
        assertEquals(8, mesa1.columnaMesaEnPosicion(1).size(), 0);
        assertEquals(6, mesa1.columnaMesaEnPosicion(2).peek().getNumero(), 0);
        assertEquals(8, mesa1.columnaMesaEnPosicion(2).size(), 0);
        assertEquals(7, mesa1.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(8, mesa1.columnaMesaEnPosicion(3).size(), 0);
        assertEquals(4, mesa1.columnaMesaEnPosicion(4).peek().getNumero(), 0);
        assertEquals(8, mesa1.columnaMesaEnPosicion(4).size(), 0);
        assertEquals(7, mesa1.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(8, mesa1.columnaMesaEnPosicion(5).size(), 0);
        assertEquals(10, mesa1.columnaMesaEnPosicion(6).peek().getNumero(), 0);
        assertEquals(8, mesa1.columnaMesaEnPosicion(6).size(), 0);
        assertEquals(6, mesa1.columnaMesaEnPosicion(7).peek().getNumero(), 0);
        assertEquals(8, mesa1.columnaMesaEnPosicion(7).size(), 0);
        assertEquals(9, mesa1.columnaMesaEnPosicion(8).peek().getNumero(), 0);
        assertEquals(11, mesa1.columnaMesaEnPosicion(8).size(), 0);
        assertEquals(7, mesa1.columnaMesaEnPosicion(9).peek().getNumero(), 0);
        assertEquals(8, mesa1.columnaMesaEnPosicion(9).size(), 0);

        Spider spider2 = new Spider(null, mesa1);
        assertTrue(spider2.sacarDelMazo());
        assertTrue(spider2.sacarDelMazo());
        assertFalse(spider2.sacarDelMazo());
        assertTrue(spider2.moverCartas(spider2.seleccionarCartas(1, 0), 1, 2));
        assertTrue(spider2.moverCartas(spider2.seleccionarCartas(4, 0), 4, 6));
        assertTrue(spider2.moverCartas(spider2.seleccionarCartas(6, 1), 6, 0));
        Mesa mesa2 = spider2.getEstadoMesa();
        assertNull(mesa2.sacarCartaMazo());
        assertEquals(1, mesa2.columnaMesaEnPosicion(0).peek().getNumero(), 0);
        assertEquals(13, mesa2.columnaMesaEnPosicion(0).size(), 0);
        assertEquals(13, mesa2.columnaMesaEnPosicion(1).peek().getNumero(), 0);
        assertEquals(9, mesa2.columnaMesaEnPosicion(1).size(), 0);
        assertEquals(8, mesa2.columnaMesaEnPosicion(2).peek().getNumero(), 0);
        assertEquals(11, mesa2.columnaMesaEnPosicion(2).size(), 0);
        assertEquals(11, mesa2.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(10, mesa2.columnaMesaEnPosicion(3).size(), 0);
        assertEquals(13, mesa2.columnaMesaEnPosicion(4).peek().getNumero(), 0);
        assertEquals(9, mesa2.columnaMesaEnPosicion(4).size(), 0);
        assertEquals(9, mesa2.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(10, mesa2.columnaMesaEnPosicion(5).size(), 0);
        assertEquals(5, mesa2.columnaMesaEnPosicion(6).peek().getNumero(), 0);
        assertEquals(9, mesa2.columnaMesaEnPosicion(6).size(), 0);
        assertEquals(5, mesa2.columnaMesaEnPosicion(7).peek().getNumero(), 0);
        assertEquals(10, mesa2.columnaMesaEnPosicion(7).size(), 0);
        assertEquals(6, mesa2.columnaMesaEnPosicion(8).peek().getNumero(), 0);
        assertEquals(13, mesa2.columnaMesaEnPosicion(8).size(), 0);
        assertEquals(9, mesa2.columnaMesaEnPosicion(9).peek().getNumero(), 0);
        assertEquals(10, mesa2.columnaMesaEnPosicion(9).size(), 0);

        Semilla nuevaSemilla = GeneradorSemillas.generarSemillaConString
                ("I0F0E0B0I0A0K0I0H0C0E0I0B0E0I0M0C0J0M0L0G0L0F0J0G0D0I0K0J0J0F0G0D0G0E0M0G0F0H0K0A0E0K0M0D0A0B0A0J0M0F0M0" +
                        "F0H0L0B0C0C0J0L0C0I0J0C0H0B0E0D0C0L0A0F0H0H0A0H0E0E0D0K0L0M0G0D0K0B0D0K0B0D0A0G0L0F0G0K0H0I0L0M0B0C0J0A0");
        Spider spider3 = new Spider(nuevaSemilla, mesa2);
        assertFalse(spider3.sacarDelMazo());
        assertTrue(spider3.moverCartas(spider3.seleccionarCartas(6, 0), 6, 8));
        assertTrue(spider3.moverCartas(spider3.seleccionarCartas(2, 2), 2, 3));
        Mesa mesa3 = spider3.getEstadoMesa();
        assertNull(mesa3.sacarCartaMazo());
        assertEquals(1, mesa3.columnaMesaEnPosicion(0).peek().getNumero(), 0);
        assertEquals(13, mesa3.columnaMesaEnPosicion(0).size(), 0);
        assertEquals(13, mesa3.columnaMesaEnPosicion(1).peek().getNumero(), 0);
        assertEquals(9, mesa3.columnaMesaEnPosicion(1).size(), 0);
        assertEquals(6, mesa3.columnaMesaEnPosicion(2).peek().getNumero(), 0);
        assertEquals(8, mesa3.columnaMesaEnPosicion(2).size(), 0);
        assertEquals(8, mesa3.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(13, mesa3.columnaMesaEnPosicion(3).size(), 0);
        assertEquals(13, mesa3.columnaMesaEnPosicion(4).peek().getNumero(), 0);
        assertEquals(9, mesa3.columnaMesaEnPosicion(4).size(), 0);
        assertEquals(9, mesa3.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(10, mesa3.columnaMesaEnPosicion(5).size(), 0);
        assertEquals(10, mesa3.columnaMesaEnPosicion(6).peek().getNumero(), 0);
        assertEquals(8, mesa3.columnaMesaEnPosicion(6).size(), 0);
        assertEquals(5, mesa3.columnaMesaEnPosicion(7).peek().getNumero(), 0);
        assertEquals(10, mesa3.columnaMesaEnPosicion(7).size(), 0);
        assertEquals(5, mesa3.columnaMesaEnPosicion(8).peek().getNumero(), 0);
        assertEquals(14, mesa3.columnaMesaEnPosicion(8).size(), 0);
        assertEquals(9, mesa3.columnaMesaEnPosicion(9).peek().getNumero(), 0);
        assertEquals(10, mesa3.columnaMesaEnPosicion(9).size(), 0);
    }

    @Test
    public void EstadosParticularesDeMesaEnColumnasFinales() {
        Semilla semilla = GeneradorSemillas.generarSemillaConString
                ("I0F0E0B0I0A0K0I0H0C0E0I0B0E0I0M0C0J0M0L0G0L0F0J0G0D0I0K0J0J0F0G0D0G0E0M0G0F0H0K0A0E0K0M0D0A0B0A0J0M0F0M0" +
                        "F0H0L0B0C0C0J0L0C0I0J0C0H0B0E0D0C0L0A0F0H0H0A0H0E0E0D0K0L0M0G0D0K0B0D0K0B0D0A0G0L0F0G0K0H0I0L0M0B0C0J0A0");
        Spider spider1 = new Spider(semilla, null);
        spider1.repartirCartasInicio();
        completarUnaColumnaFinal(spider1);
        Mesa mesa1 = spider1.getEstadoMesa();

        Spider spider2 = new Spider(null, mesa1);
        Mesa mesa2 = spider2.getEstadoMesa();
        verificarUnaColumnaFinal(mesa2);
    }

    @Test
    public void verificacionDeCorrectaPersistenciaDeMesa() {
        Semilla semilla = GeneradorSemillas.generarSemillaConString
                ("I0F0E0B0I0A0K0I0H0C0E0I0B0E0I0M0C0J0M0L0G0L0F0J0G0D0I0K0J0J0F0G0D0G0E0M0G0F0H0K0A0E0K0M0D0A0B0A0J0M0F0M0" +
                        "F0H0L0B0C0C0J0L0C0I0J0C0H0B0E0D0C0L0A0F0H0H0A0H0E0E0D0K0L0M0G0D0K0B0D0K0B0D0A0G0L0F0G0K0H0I0L0M0B0C0J0A0");
        Spider spider1 = new Spider(semilla, null);
        spider1.repartirCartasInicio();

        // Forma el primer estado del juego
        completarUnaColumnaFinal(spider1);
        Mesa mesa1 = spider1.getEstadoMesa();
        // Serializa el primer estado del juego
        boolean seGrabo1 = false;
        try {
            FileOutputStream fileOut = new FileOutputStream("src/main/resources/mesa.txt");
            mesa1.serializar(fileOut);
            seGrabo1 = true;
        } catch (IOException ex) {
            fail();
        }
        assertTrue(seGrabo1);
        // Deserializa el primer estado del juego
        Mesa nuevaMesa1 = null;
        try {
            FileInputStream fileIn = new FileInputStream("src/main/resources/mesa.txt");
            nuevaMesa1 = Mesa.deserializar(fileIn);

        } catch (IOException | ClassNotFoundException ex) {
            fail();
        }
        assertNotNull(nuevaMesa1);

        // Verifica la correcta carga y lectura del primer estado del juego
        Spider spider2 = new Spider(null, nuevaMesa1);
        Mesa mesa2 = spider2.getEstadoMesa();
        verificarUnaColumnaFinal(mesa2);

        // Forma el segundo estado del juego
        assertTrue(spider2.moverCartas(spider2.seleccionarCartas(0, 0), 0, 2));
        assertTrue(spider2.sacarDelMazo());
        assertTrue(spider2.sacarDelMazo());
        assertTrue(spider2.moverCartas(spider2.seleccionarCartas(7, 0), 7, 5));
        assertTrue(spider2.moverCartas(spider2.seleccionarCartas(5, 1), 5, 9));
        assertTrue(spider2.moverCartas(spider2.seleccionarCartas(9, 2), 9, 8));
        assertTrue(spider2.moverCartas(spider2.seleccionarCartas(8, 3), 8, 1));
        mesa2 = spider2.getEstadoMesa();
        // Serializa el segundo estado del juego
        boolean seGrabo2 = false;
        try {
            FileOutputStream fileOut = new FileOutputStream("src/main/resources/mesa.txt");
            mesa2.serializar(fileOut);
            seGrabo2 = true;
        } catch (IOException ex) {
            fail();
        }
        assertTrue(seGrabo2);
        // Deserializa el segundo estado del juego
        Mesa nuevaMesa2 = null;
        try {
            FileInputStream fileIn = new FileInputStream("src/main/resources/mesa.txt");
            nuevaMesa2 = Mesa.deserializar(fileIn);

        } catch (IOException | ClassNotFoundException ex) {
            fail();
        }
        assertNotNull(nuevaMesa2);

        // Verifica la correcta carga y lectura del segundo estado del juego
        Semilla nuevaSemilla = GeneradorSemillas.generarSemillaConString
                ("I0F0E0B0I0A0K0I0H0C0E0I0B0E0I0M0C0J0M0L0G0L0F0J0G0D0I0K0J0J0F0G0D0G0E0M0G0F0H0K0A0E0K0M0D0A0B0A0J0M0F0M0" +
                        "F0H0L0B0C0C0J0L0C0I0J0C0H0B0E0D0C0L0A0F0H0H0A0H0E0E0D0K0L0M0G0D0K0B0D0K0B0D0A0G0L0F0G0K0H0I0L0M0B0C0J0A0");
        Spider spider3 = new Spider(nuevaSemilla, nuevaMesa2);
        Mesa mesa3 = spider3.getEstadoMesa();
        Carta carta = mesa3.sacarCartaMazo();
        carta.darVuelta();
        assertEquals(10, carta.getNumero(), 0);
        carta.darVuelta();
        mesa3.insertarCartaMazo(carta);

        assertEquals(13, mesa3.columnaFinalEnPosicion(0).size(), 0);
        assertEquals(1, mesa3.columnaFinalEnPosicion(0).peek().getNumero(), 0);

        assertEquals(11, mesa3.columnaMesaEnPosicion(0).peek().getNumero(), 0);
        assertEquals(6, mesa3.columnaMesaEnPosicion(0).size(), 0);
        assertEquals(4, mesa3.columnaMesaEnPosicion(1).peek().getNumero(), 0);
        assertEquals(12, mesa3.columnaMesaEnPosicion(1).size(), 0);
        assertEquals(6, mesa3.columnaMesaEnPosicion(2).peek().getNumero(), 0);
        assertEquals(3, mesa3.columnaMesaEnPosicion(2).size(), 0);
        assertEquals(7, mesa3.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(8, mesa3.columnaMesaEnPosicion(3).size(), 0);
        assertEquals(13, mesa3.columnaMesaEnPosicion(4).peek().getNumero(), 0);
        assertEquals(10, mesa3.columnaMesaEnPosicion(4).size(), 0);
        assertEquals(4, mesa3.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(5, mesa3.columnaMesaEnPosicion(5).size(), 0);
        assertEquals(7, mesa3.columnaMesaEnPosicion(6).peek().getNumero(), 0);
        assertEquals(7, mesa3.columnaMesaEnPosicion(6).size(), 0);
        assertEquals(11, mesa3.columnaMesaEnPosicion(7).peek().getNumero(), 0);
        assertEquals(4, mesa3.columnaMesaEnPosicion(7).size(), 0);
        assertEquals(5, mesa3.columnaMesaEnPosicion(8).peek().getNumero(), 0);
        assertEquals(2, mesa3.columnaMesaEnPosicion(8).size(), 0);
        assertEquals(1, mesa3.columnaMesaEnPosicion(9).peek().getNumero(), 0);
        assertEquals(4, mesa3.columnaMesaEnPosicion(9).size(), 0);

        assertTrue(spider3.sacarDelMazo());
        assertTrue(spider3.sacarDelMazo());
        assertTrue(spider3.sacarDelMazo());
        assertFalse(spider3.sacarDelMazo());
    }
}
