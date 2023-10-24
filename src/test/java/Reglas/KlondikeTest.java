package Reglas;

import Solitario.Carta;
import Solitario.GeneradorSemillas;
import Solitario.Mesa;
import Solitario.Palos;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class KlondikeTest {

    /**
     * Tests Klondike iniciado de cero
     */
    @Test
    public void inicioMazoKlondike() {
        Klondike klondike = new Klondike(null, null);
        Mesa mesa = klondike.getEstadoMesa();
        Carta carta = mesa.sacarCartaMazo();
        assertFalse(carta.esVisible());
        assertNull(mesa.sacarCartaDescarte());
        for (int i = 0; i < 51; i++)
            assertTrue(klondike.sacarDelMazo());
        Mesa nuevaMesa = klondike.getEstadoMesa();
        Carta nuevaCarta = nuevaMesa.sacarCartaDescarte();
        assertTrue(nuevaCarta.esVisible());
        assertNull(nuevaMesa.sacarCartaMazo());
    }

    @Test
    public void inicioColumnasKlondike() {
        Klondike klondike = new Klondike(null, null);
        Mesa mesa = klondike.getEstadoMesa();
        assertEquals(7, mesa.sizeColumnaMesa(), 0);
        for (int i = 0; i < 7; i++)
            assertTrue(mesa.columnaMesaEnPosicion(i).isEmpty());
        assertEquals(4, mesa.sizeColumnaFinal(), 0);
        for (int i = 0; i < 4; i++)
            assertTrue(mesa.columnaFinalEnPosicion(i).isEmpty());
    }

    @Test
    public void testRepartirCartas() {
        Klondike klondike = new Klondike(null, null);
        klondike.repartirCartasInicio();
        Mesa mesa = klondike.getEstadoMesa();
        for (int i = 0; i < 7; i++) {
            assertEquals(i + 1, mesa.columnaMesaEnPosicion(i).size(), 0);
            for (int j = 0; j <= i; j++) {
                if (j == 0)
                    assertTrue(mesa.columnaMesaEnPosicion(i).peek().esVisible());
                else
                    assertFalse(mesa.columnaMesaEnPosicion(i).getCartas().get(j).esVisible());
            }
        }
        for (int i = 0; i < 4; i++)
            assertTrue(mesa.columnaFinalEnPosicion(i).isEmpty());

        for (int i = 0; i < 24; i++)
            assertTrue(klondike.sacarDelMazo());
        Mesa nuevaMesa = klondike.getEstadoMesa();
        assertNull(nuevaMesa.sacarCartaMazo());
    }

    @Test
    public void testReiniciarBaraja() {
        Klondike klondike = new Klondike(null, null);
        klondike.repartirCartasInicio();
        for (int i = 0; i < 24; i++)
            assertTrue(klondike.sacarDelMazo());
        Mesa mesa1 = klondike.getEstadoMesa();
        assertNull(mesa1.sacarCartaMazo());
        Carta carta1 = mesa1.sacarCartaDescarte();
        assertTrue(carta1.esVisible());

        assertFalse(klondike.sacarDelMazo());
        Mesa mesa2 = klondike.getEstadoMesa();
        assertNull(mesa2.sacarCartaDescarte());
        Carta carta2 = mesa2.sacarCartaMazo();
        assertFalse(carta2.esVisible());

        for (int i = 0; i < 22; i++)
            assertTrue(klondike.sacarDelMazo());
        Mesa mesa3 = klondike.getEstadoMesa();
        assertNull(mesa3.sacarCartaMazo());
        Carta carta3 = mesa3.sacarCartaDescarte();
        assertTrue(carta3.esVisible());
    }


    /**
     * Tests Klondike con semilla particular
     * "L1D0C3G2H1G0J2D2D1A3M3J1A2B1F3I2E1B3K0E3I3G1L0K2J0H0B2I0H2C1C2L2E0A0J3M2K1A1F2I1B0M1C0F0L3F1D3G3K3H3M0E2"
     */
    @Test
    public void klondikeSemillaVacia() {
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConString("");
        Klondike klondike = new Klondike(semilla, null);
        Mesa mesa1 = klondike.getEstadoMesa();
        assertNull(mesa1.sacarCartaMazo());
        assertNull(mesa1.sacarCartaDescarte());

        klondike.repartirCartasInicio();
        Mesa mesa2 = klondike.getEstadoMesa();
        assertNull(mesa2.sacarCartaMazo());
        assertNull(mesa2.sacarCartaDescarte());
        for (int i = 0; i < 7; i++)
            assertTrue(mesa2.columnaMesaEnPosicion(i).isEmpty());
        for (int i = 0; i < 4; i++)
            assertTrue(mesa2.columnaFinalEnPosicion(i).isEmpty());

        assertFalse(klondike.sacarDelMazo());
        Mesa mesa3 = klondike.getEstadoMesa();
        assertNull(mesa3.sacarCartaMazo());
        assertNull(mesa3.sacarCartaDescarte());

        assertFalse(klondike.moverCartaDescarteAColumnaMesa(2));
        assertFalse(klondike.moverCartaDescarteAColumnaFinal(1));
        assertFalse(klondike.moverCartas(1, 0, 3));
        assertFalse(klondike.moverCartaColumnaFinal(6, 0));
        assertFalse(klondike.moverCartaColumnaFinalAColumnaMesa(3, 4));
    }

    @Test
    public void klondikeSemillaCompleta() {
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConString
                ("L1D0C3G2H1G0J2D2D1A3M3J1A2B1F3I2E1B3K0E3I3G1L0K2J0H0B2I0H2C1C2L2E0A0J3M2K1A1F2I1B0M1C0F0L3F1D3G3K3H3M0E2");
        Klondike klondike = new Klondike(semilla, null);
        klondike.repartirCartasInicio();
        klondike.sacarDelMazo();
        Mesa mesa = klondike.getEstadoMesa();

        Carta cartaBarajaDescarte = mesa.sacarCartaDescarte();
        assertEquals(11, cartaBarajaDescarte.getNumero(), 0);
        assertEquals(Palos.CORAZONES, cartaBarajaDescarte.getPalo());

        assertEquals(5, mesa.columnaMesaEnPosicion(0).peek().getNumero(), 0);
        assertEquals(Palos.CORAZONES, mesa.columnaMesaEnPosicion(0).peek().getPalo());
        assertEquals(8, mesa.columnaMesaEnPosicion(1).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa.columnaMesaEnPosicion(1).peek().getPalo());
        assertEquals(4, mesa.columnaMesaEnPosicion(2).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa.columnaMesaEnPosicion(2).peek().getPalo());
        assertEquals(3, mesa.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(3).peek().getPalo());
        assertEquals(1, mesa.columnaMesaEnPosicion(4).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa.columnaMesaEnPosicion(4).peek().getPalo());
        assertEquals(12, mesa.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(Palos.CORAZONES, mesa.columnaMesaEnPosicion(5).peek().getPalo());
        assertEquals(10, mesa.columnaMesaEnPosicion(6).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa.columnaMesaEnPosicion(6).peek().getPalo());
    }

    @Test
    public void moverUnaCartaEntreColumnas() {
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConString
                ("L1D0C3G2H1G0J2D2D1A3M3J1A2B1F3I2E1B3K0E3I3G1L0K2J0H0B2I0H2C1C2L2E0A0J3M2K1A1F2I1B0M1C0F0L3F1D3G3K3H3M0E2");
        Klondike klondike = new Klondike(semilla, null);
        klondike.repartirCartasInicio();

        assertFalse(klondike.moverCartas(0, 5, 0));
        Mesa mesa1 = klondike.getEstadoMesa();
        assertEquals(5, mesa1.columnaMesaEnPosicion(0).peek().getNumero(), 0);
        assertEquals(Palos.CORAZONES, mesa1.columnaMesaEnPosicion(0).peek().getPalo());
        assertEquals(1, mesa1.columnaMesaEnPosicion(0).size(), 0);
        assertEquals(12, mesa1.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(Palos.CORAZONES, mesa1.columnaMesaEnPosicion(5).peek().getPalo());
        assertEquals(6, mesa1.columnaMesaEnPosicion(5).size(), 0);

        assertFalse(klondike.moverCartas(3, 0, 0));
        Mesa mesa2 = klondike.getEstadoMesa();
        assertEquals(3, mesa2.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa2.columnaMesaEnPosicion(3).peek().getPalo());
        assertEquals(4, mesa2.columnaMesaEnPosicion(3).size(), 0);
        assertEquals(5, mesa2.columnaMesaEnPosicion(0).peek().getNumero(), 0);
        assertEquals(Palos.CORAZONES, mesa2.columnaMesaEnPosicion(0).peek().getPalo());
        assertEquals(1, mesa2.columnaMesaEnPosicion(0).size(), 0);

        assertTrue(klondike.moverCartas(3, 2, 0));
        Mesa mesa3 = klondike.getEstadoMesa();
        assertEquals(6, mesa3.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa3.columnaMesaEnPosicion(3).peek().getPalo());
        assertEquals(3, mesa3.columnaMesaEnPosicion(3).size(), 0);
        assertEquals(3, mesa3.columnaMesaEnPosicion(2).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa3.columnaMesaEnPosicion(2).peek().getPalo());
        assertEquals(4, mesa3.columnaMesaEnPosicion(2).size(), 0);

        assertTrue(klondike.moverCartas(0, 3, 0));
        Mesa mesa4 = klondike.getEstadoMesa();
        assertNull(mesa4.columnaMesaEnPosicion(0).peek());
        assertEquals(0, mesa4.columnaMesaEnPosicion(0).size(), 0);
        assertEquals(5, mesa4.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(Palos.CORAZONES, mesa4.columnaMesaEnPosicion(3).peek().getPalo());
        assertEquals(4, mesa4.columnaMesaEnPosicion(3).size(), 0);

        assertFalse(klondike.moverCartas(0, 6, 0));
        Mesa mesa5 = klondike.getEstadoMesa();
        assertNull(mesa5.columnaMesaEnPosicion(0).peek());
        assertEquals(0, mesa5.columnaMesaEnPosicion(0).size());
        assertEquals(10, mesa4.columnaMesaEnPosicion(6).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa4.columnaMesaEnPosicion(6).peek().getPalo());
        assertEquals(7, mesa4.columnaMesaEnPosicion(6).size(), 0);
    }

    @Test
    public void testMoverCartaDescarteAColumnaMesa() {
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConString
                ("L1D0C3G2H1G0J2D2D1A3M3J1A2B1F3I2E1B3K0E3I3G1L0K2J0H0B2I0H2C1C2L2E0A0J3M2K1A1F2I1B0M1C0F0L3F1D3G3K3H3M0E2");
        Klondike klondike = new Klondike(semilla, null);
        klondike.repartirCartasInicio();
        assertFalse(klondike.moverCartaDescarteAColumnaMesa(4));

        klondike.sacarDelMazo();
        assertFalse(klondike.moverCartaDescarteAColumnaMesa(5));
        Mesa mesa1 = klondike.getEstadoMesa();
        assertEquals(12, mesa1.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(Palos.CORAZONES, mesa1.columnaMesaEnPosicion(5).peek().getPalo());
        assertEquals(6, mesa1.columnaMesaEnPosicion(5).size(), 0);

        assertFalse(klondike.moverCartaDescarteAColumnaMesa(3));
        Mesa mesa2 = klondike.getEstadoMesa();
        assertEquals(3, mesa2.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa2.columnaMesaEnPosicion(3).peek().getPalo());
        assertEquals(4, mesa2.columnaMesaEnPosicion(3).size(), 0);

        klondike.sacarDelMazo();
        klondike.sacarDelMazo();
        assertTrue(klondike.moverCartaDescarteAColumnaMesa(1));
        Mesa mesa3 = klondike.getEstadoMesa();
        Carta carta3 = mesa3.sacarCartaDescarte();
        assertEquals(12, carta3.getNumero(), 0);
        assertEquals(Palos.PICAS, carta3.getPalo());
        assertEquals(7, mesa3.columnaMesaEnPosicion(1).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa3.columnaMesaEnPosicion(1).peek().getPalo());
        assertEquals(3, mesa3.columnaMesaEnPosicion(1).size(), 0);

        klondike.sacarDelMazo();
        assertTrue(klondike.moverCartaDescarteAColumnaMesa(6));
        Mesa mesa4 = klondike.getEstadoMesa();
        Carta carta4 = mesa4.sacarCartaDescarte();
        assertEquals(11, carta4.getNumero(), 0);
        assertEquals(Palos.CORAZONES, carta4.getPalo());
        assertEquals(9, mesa4.columnaMesaEnPosicion(6).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa4.columnaMesaEnPosicion(6).peek().getPalo());
        assertEquals(8, mesa4.columnaMesaEnPosicion(6).size(), 0);

        klondike.sacarDelMazo();
        klondike.moverCartas(3, 2, 0);
        assertTrue(klondike.moverCartaDescarteAColumnaMesa(3));
        Mesa mesa5 = klondike.getEstadoMesa();
        assertNull(mesa5.sacarCartaDescarte());
        assertEquals(5, mesa5.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa5.columnaMesaEnPosicion(3).peek().getPalo());
        assertEquals(4, mesa5.columnaMesaEnPosicion(3).size(), 0);

        klondike.sacarDelMazo();
        assertTrue(klondike.moverCartaDescarteAColumnaMesa(5));
        Mesa mesa6 = klondike.getEstadoMesa();
        assertNull(mesa6.sacarCartaDescarte());
        assertEquals(11, mesa6.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa6.columnaMesaEnPosicion(5).peek().getPalo());
        assertEquals(7, mesa6.columnaMesaEnPosicion(5).size(), 0);

        klondike.sacarDelMazo();
        assertTrue(klondike.moverCartaDescarteAColumnaMesa(2));
        Mesa mesa7 = klondike.getEstadoMesa();
        assertNull(mesa7.sacarCartaDescarte());
        assertEquals(2, mesa7.columnaMesaEnPosicion(2).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa7.columnaMesaEnPosicion(2).peek().getPalo());
        assertEquals(5, mesa7.columnaMesaEnPosicion(2).size(), 0);

        klondike.sacarDelMazo();
        klondike.moverCartaColumnaFinal(4, 1);
        assertTrue(klondike.moverCartaDescarteAColumnaMesa(4));
        Mesa mesa8 = klondike.getEstadoMesa();
        assertNull(mesa8.sacarCartaDescarte());
        assertEquals(5, mesa8.columnaMesaEnPosicion(4).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa8.columnaMesaEnPosicion(4).peek().getPalo());
        assertEquals(5, mesa8.columnaMesaEnPosicion(4).size(), 0);

        klondike.sacarDelMazo();
        klondike.sacarDelMazo();
        klondike.sacarDelMazo();
        assertFalse(klondike.moverCartaDescarteAColumnaMesa(5));
        Mesa mesa9 = klondike.getEstadoMesa();
        Carta carta9 = mesa9.sacarCartaDescarte();
        assertEquals(2, carta9.getNumero(), 0);
        assertEquals(Palos.TREBOLES, carta9.getPalo());
        assertEquals(11, mesa9.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa9.columnaMesaEnPosicion(5).peek().getPalo());
        assertEquals(7, mesa9.columnaMesaEnPosicion(5).size(), 0);
    }

    @Test
    public void testMoverCartaDescarteAColumnaFinal() {
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConString
                ("L1D0C3G2H1G0J2D2D1A3M3J1A2B1F3I2E1B3K0E3I3G1L0K2J0H0B2I0H2C1C2L2E0A0J3M2K1A1F2I1B0M1C0F0L3F1D3G3K3H3M0E2");
        Klondike klondike = new Klondike(semilla, null);
        klondike.repartirCartasInicio();
        assertFalse(klondike.moverCartaDescarteAColumnaFinal(2));

        klondike.sacarDelMazo();
        assertFalse(klondike.moverCartaDescarteAColumnaFinal(0));
        Mesa mesa1 = klondike.getEstadoMesa();
        Carta carta1 = mesa1.sacarCartaDescarte();
        assertEquals(11, carta1.getNumero(), 0);
        assertEquals(Palos.CORAZONES, carta1.getPalo());
        assertTrue(mesa1.columnaFinalEnPosicion(0).isEmpty());

        klondike.sacarDelMazo();
        assertFalse(klondike.moverCartaDescarteAColumnaFinal(2));
        Mesa mesa2 = klondike.getEstadoMesa();
        Carta carta2 = mesa2.sacarCartaDescarte();
        assertEquals(12, carta2.getNumero(), 0);
        assertEquals(Palos.PICAS, carta2.getPalo());
        assertTrue(mesa2.columnaFinalEnPosicion(2).isEmpty());

        for (int i = 0; i < 10; i++)
            klondike.sacarDelMazo();
        assertTrue(klondike.moverCartaDescarteAColumnaFinal(1));
        Mesa mesa3 = klondike.getEstadoMesa();
        Carta carta3 = mesa3.sacarCartaDescarte();
        assertEquals(2, carta3.getNumero(), 0);
        assertEquals(Palos.TREBOLES, carta3.getPalo());
        assertFalse(mesa3.columnaFinalEnPosicion(1).isEmpty());
        assertEquals(1, mesa3.columnaFinalEnPosicion(1).peek().getNumero(), 0);
        assertEquals(Palos.CORAZONES, mesa3.columnaFinalEnPosicion(1).peek().getPalo());

        for (int i = 0; i < 3; i++)
            klondike.sacarDelMazo();
        assertTrue(klondike.moverCartaDescarteAColumnaFinal(3));
        Mesa mesa4 = klondike.getEstadoMesa();
        Carta carta4 = mesa4.sacarCartaDescarte();
        assertEquals(13, carta4.getNumero(), 0);
        assertEquals(Palos.DIAMANTES, carta4.getPalo());
        assertFalse(mesa4.columnaFinalEnPosicion(3).isEmpty());
        assertEquals(1, mesa4.columnaFinalEnPosicion(3).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa4.columnaFinalEnPosicion(3).peek().getPalo());

        for (int i = 0; i < 15; i++)
            klondike.sacarDelMazo();
        assertTrue(klondike.moverCartaDescarteAColumnaFinal(3));
        Mesa mesa5 = klondike.getEstadoMesa();
        Carta carta5 = mesa5.sacarCartaDescarte();
        assertEquals(11, carta5.getNumero(), 0);
        assertEquals(Palos.PICAS, carta5.getPalo());
        assertFalse(mesa5.columnaFinalEnPosicion(3).isEmpty());
        assertEquals(2, mesa5.columnaFinalEnPosicion(3).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa5.columnaFinalEnPosicion(3).peek().getPalo());

        for (int i = 0; i < 11; i++)
            klondike.sacarDelMazo();
        assertTrue(klondike.moverCartaDescarteAColumnaFinal(3));
        Mesa mesa6 = klondike.getEstadoMesa();
        Carta carta6 = mesa6.sacarCartaDescarte();
        assertEquals(7, carta6.getNumero(), 0);
        assertEquals(Palos.CORAZONES, carta6.getPalo());
        assertFalse(mesa6.columnaFinalEnPosicion(3).isEmpty());
        assertEquals(3, mesa6.columnaFinalEnPosicion(3).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa6.columnaFinalEnPosicion(3).peek().getPalo());

        klondike.sacarDelMazo();
        assertFalse(klondike.moverCartaDescarteAColumnaFinal(3));
        Mesa mesa7 = klondike.getEstadoMesa();
        Carta carta7 = mesa7.sacarCartaDescarte();
        assertEquals(4, carta7.getNumero(), 0);
        assertEquals(Palos.PICAS, carta7.getPalo());
        assertFalse(mesa7.columnaFinalEnPosicion(3).isEmpty());
        assertEquals(3, mesa7.columnaFinalEnPosicion(3).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa7.columnaFinalEnPosicion(3).peek().getPalo());
    }

    @Test
    public void testMoverCartaColumnaFinal() {
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConString
                ("L1D0C3G2H1G0J2D2D1A3M3J1A2B1F3I2E1B3K0E3I3G1L0K2J0H0B2I0H2C1C2L2E0A0J3M2K1A1F2I1B0M1C0F0L3F1D3G3K3H3M0E2");
        Klondike klondike = new Klondike(semilla, null);
        klondike.repartirCartasInicio();

        assertFalse(klondike.moverCartaColumnaFinal(2, 1));
        Mesa mesa1 = klondike.getEstadoMesa();
        assertEquals(4, mesa1.columnaMesaEnPosicion(2).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa1.columnaMesaEnPosicion(2).peek().getPalo());
        assertTrue(mesa1.columnaFinalEnPosicion(1).isEmpty());

        assertFalse(klondike.moverCartaColumnaFinal(5, 2));
        Mesa mesa2 = klondike.getEstadoMesa();
        assertEquals(12, mesa2.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(Palos.CORAZONES, mesa2.columnaMesaEnPosicion(5).peek().getPalo());
        assertTrue(mesa2.columnaFinalEnPosicion(2).isEmpty());

        assertTrue(klondike.moverCartaColumnaFinal(4, 0));
        Mesa mesa3 = klondike.getEstadoMesa();
        assertEquals(6, mesa3.columnaMesaEnPosicion(4).peek().getNumero(), 0);
        assertEquals(Palos.CORAZONES, mesa3.columnaMesaEnPosicion(4).peek().getPalo());
        assertEquals(4, mesa3.columnaMesaEnPosicion(4).size(), 0);
        assertFalse(mesa3.columnaFinalEnPosicion(0).isEmpty());
        assertEquals(1, mesa3.columnaFinalEnPosicion(0).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa3.columnaFinalEnPosicion(0).peek().getPalo());

        for (int i = 0; i < 3; i++)
            klondike.sacarDelMazo();
        klondike.moverCartaDescarteAColumnaMesa(1);
        for (int i = 0; i < 12; i++)
            klondike.sacarDelMazo();
        klondike.moverCartaDescarteAColumnaFinal(3);
        for (int i = 0; i < 16; i++)
            klondike.sacarDelMazo();
        klondike.moverCartaDescarteAColumnaFinal(3);
        for (int i = 0; i < 14; i++)
            klondike.sacarDelMazo();
        klondike.moverCartaDescarteAColumnaFinal(3);
        assertTrue(klondike.moverCartaColumnaFinal(2, 3));
        Mesa mesa4 = klondike.getEstadoMesa();
        assertEquals(7, mesa4.columnaMesaEnPosicion(2).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa4.columnaMesaEnPosicion(2).peek().getPalo());
        assertEquals(2, mesa4.columnaMesaEnPosicion(2).size(), 0);
        assertEquals(4, mesa4.columnaFinalEnPosicion(3).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa4.columnaFinalEnPosicion(3).peek().getPalo());
        assertEquals(4, mesa4.columnaFinalEnPosicion(3).size(), 0);

        assertFalse(klondike.moverCartaColumnaFinal(1, 0));
        Mesa mesa5 = klondike.getEstadoMesa();
        assertEquals(7, mesa5.columnaMesaEnPosicion(1).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa5.columnaMesaEnPosicion(1).peek().getPalo());
        assertEquals(3, mesa5.columnaMesaEnPosicion(1).size(), 0);
        assertEquals(1, mesa5.columnaFinalEnPosicion(0).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa5.columnaFinalEnPosicion(0).peek().getPalo());
        assertEquals(1, mesa5.columnaFinalEnPosicion(0).size(), 0);

        assertFalse(klondike.moverCartaColumnaFinal(0, 3));
        Mesa mesa6 = klondike.getEstadoMesa();
        assertEquals(5, mesa6.columnaMesaEnPosicion(0).peek().getNumero(), 0);
        assertEquals(Palos.CORAZONES, mesa6.columnaMesaEnPosicion(0).peek().getPalo());
        assertEquals(1, mesa6.columnaMesaEnPosicion(0).size(), 0);
        assertEquals(4, mesa6.columnaFinalEnPosicion(3).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa6.columnaFinalEnPosicion(3).peek().getPalo());
        assertEquals(4, mesa6.columnaFinalEnPosicion(3).size(), 0);

        assertFalse(klondike.moverCartaColumnaFinal(2, 3));
        Mesa mesa7 = klondike.getEstadoMesa();
        assertEquals(7, mesa7.columnaMesaEnPosicion(2).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa7.columnaMesaEnPosicion(2).peek().getPalo());
        assertEquals(2, mesa7.columnaMesaEnPosicion(2).size(), 0);
        assertEquals(4, mesa7.columnaFinalEnPosicion(3).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa7.columnaFinalEnPosicion(3).peek().getPalo());
        assertEquals(4, mesa7.columnaFinalEnPosicion(3).size(), 0);

        assertFalse(klondike.moverCartaColumnaFinal(6, 3));
        Mesa mesa8 = klondike.getEstadoMesa();
        assertEquals(10, mesa8.columnaMesaEnPosicion(6).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa8.columnaMesaEnPosicion(6).peek().getPalo());
        assertEquals(7, mesa8.columnaMesaEnPosicion(6).size(), 0);
        assertEquals(4, mesa8.columnaFinalEnPosicion(3).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa8.columnaFinalEnPosicion(3).peek().getPalo());
        assertEquals(4, mesa8.columnaFinalEnPosicion(3).size(), 0);
    }

    @Test
    public void testMoverCartaColumnaFinalAColumnaMesa() {
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConString
                ("L1D0C3G2H1G0J2D2D1A3M3J1A2B1F3I2E1B3K0E3I3G1L0K2J0H0B2I0H2C1C2L2E0A0J3M2K1A1F2I1B0M1C0F0L3F1D3G3K3H3M0E2");
        Klondike klondike = new Klondike(semilla, null);
        klondike.repartirCartasInicio();

        assertFalse(klondike.moverCartaColumnaFinalAColumnaMesa(1, 3));
        Mesa mesa1 = klondike.getEstadoMesa();
        assertTrue(mesa1.columnaFinalEnPosicion(1).isEmpty());
        assertEquals(3, mesa1.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa1.columnaMesaEnPosicion(3).peek().getPalo());
        assertEquals(4, mesa1.columnaMesaEnPosicion(3).size(), 0);

        klondike.moverCartaColumnaFinal(4, 1);
        for (int i = 0; i < 7; i++)
            klondike.sacarDelMazo();
        klondike.moverCartaDescarteAColumnaMesa(3);
        for (int i = 0; i < 4; i++)
            klondike.sacarDelMazo();
        klondike.moverCartaDescarteAColumnaFinal(1);
        klondike.sacarDelMazo();
        klondike.moverCartaDescarteAColumnaFinal(2);
        for (int i = 0; i < 4; i++)
            klondike.sacarDelMazo();
        klondike.moverCartaDescarteAColumnaMesa(0);
        for (int i = 0; i < 6; i++)
            klondike.sacarDelMazo();
        klondike.moverCartaDescarteAColumnaMesa(0);
        assertFalse(klondike.moverCartaColumnaFinalAColumnaMesa(1, 2));
        Mesa mesa2 = klondike.getEstadoMesa();
        assertEquals(2, mesa2.columnaFinalEnPosicion(1).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa2.columnaFinalEnPosicion(1).peek().getPalo());
        assertEquals(2, mesa2.columnaFinalEnPosicion(1).size());
        assertEquals(4, mesa2.columnaMesaEnPosicion(2).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa2.columnaMesaEnPosicion(2).peek().getPalo());
        assertEquals(3, mesa2.columnaMesaEnPosicion(2).size());

        assertFalse(klondike.moverCartaColumnaFinalAColumnaMesa(2, 3));
        Mesa mesa3 = klondike.getEstadoMesa();
        assertEquals(1, mesa3.columnaFinalEnPosicion(2).peek().getNumero(), 0);
        assertEquals(Palos.CORAZONES, mesa3.columnaFinalEnPosicion(2).peek().getPalo());
        assertEquals(1, mesa3.columnaFinalEnPosicion(2).size());
        assertEquals(2, mesa3.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa3.columnaMesaEnPosicion(3).peek().getPalo());
        assertEquals(5, mesa3.columnaMesaEnPosicion(3).size());

        assertTrue(klondike.moverCartaColumnaFinalAColumnaMesa(1, 0));
        Mesa mesa4 = klondike.getEstadoMesa();
        assertEquals(1, mesa4.columnaFinalEnPosicion(1).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa4.columnaFinalEnPosicion(1).peek().getPalo());
        assertEquals(1, mesa4.columnaFinalEnPosicion(1).size());
        assertEquals(2, mesa4.columnaMesaEnPosicion(0).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa4.columnaMesaEnPosicion(0).peek().getPalo());
        assertEquals(4, mesa4.columnaMesaEnPosicion(0).size());

        assertTrue(klondike.moverCartaColumnaFinalAColumnaMesa(1, 3));
        Mesa mesa5 = klondike.getEstadoMesa();
        assertTrue(mesa5.columnaFinalEnPosicion(1).isEmpty());
        assertEquals(1, mesa5.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa5.columnaMesaEnPosicion(3).peek().getPalo());
        assertEquals(6, mesa5.columnaMesaEnPosicion(3).size());

        assertTrue(klondike.moverCartaColumnaFinalAColumnaMesa(2, 0));
        Mesa mesa6 = klondike.getEstadoMesa();
        assertTrue(mesa6.columnaFinalEnPosicion(2).isEmpty());
        assertEquals(1, mesa6.columnaMesaEnPosicion(0).peek().getNumero(), 0);
        assertEquals(Palos.CORAZONES, mesa6.columnaMesaEnPosicion(0).peek().getPalo());
        assertEquals(5, mesa6.columnaMesaEnPosicion(0).size());

        assertFalse(klondike.moverCartaColumnaFinalAColumnaMesa(2, 4));
        Mesa mesa7 = klondike.getEstadoMesa();
        assertTrue(mesa7.columnaFinalEnPosicion(2).isEmpty());
        assertEquals(6, mesa7.columnaMesaEnPosicion(4).peek().getNumero(), 0);
        assertEquals(Palos.CORAZONES, mesa7.columnaMesaEnPosicion(4).peek().getPalo());
        assertEquals(4, mesa7.columnaMesaEnPosicion(4).size());
    }

    @Test
    public void moverVariasCartasEntreColumnas() {
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConString
                ("L1D0C3G2H1G0J2D2D1A3M3J1A2B1F3I2E1B3K0E3I3G1L0K2J0H0B2I0H2C1C2L2E0A0J3M2K1A1F2I1B0M1C0F0L3F1D3G3K3H3M0E2");
        Klondike klondike = new Klondike(semilla, null);
        klondike.repartirCartasInicio();

        assertTrue(klondike.moverCartas(3, 2, 0));
        assertTrue(klondike.moverCartas(0, 3, 0));
        for (int i = 0; i < 3; i++)
            klondike.sacarDelMazo();
        assertTrue(klondike.moverCartaDescarteAColumnaMesa(1));
        klondike.sacarDelMazo();
        assertTrue(klondike.moverCartaDescarteAColumnaMesa(6));
        klondike.sacarDelMazo();
        klondike.sacarDelMazo();
        assertTrue(klondike.moverCartaDescarteAColumnaMesa(5));
        klondike.sacarDelMazo();
        assertTrue(klondike.moverCartaDescarteAColumnaMesa(2));
        assertTrue(klondike.moverCartas(4, 2, 0));
        klondike.sacarDelMazo();
        assertTrue(klondike.moverCartaDescarteAColumnaMesa(4));

        assertFalse(klondike.moverCartas(3, 0, 0));
        assertFalse(klondike.moverCartaDescarteAColumnaMesa(0));
        Mesa mesa1 = klondike.getEstadoMesa();
        assertEquals(0, mesa1.columnaMesaEnPosicion(0).size(), 0);
        assertEquals(5, mesa1.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(Palos.CORAZONES, mesa1.columnaMesaEnPosicion(3).peek().getPalo());
        assertEquals(4, mesa1.columnaMesaEnPosicion(3).size(), 0);
        mesa1.sacarCartaDescarte();
        Carta carta1 = mesa1.sacarCartaDescarte();
        assertEquals(12, carta1.getNumero(), 0);
        assertEquals(Palos.PICAS, carta1.getPalo());

        assertFalse(klondike.moverCartas(1, 5, 1));
        Mesa mesa2 = klondike.getEstadoMesa();
        assertEquals(3, mesa2.columnaMesaEnPosicion(1).size(), 0);
        assertEquals(7, mesa2.columnaMesaEnPosicion(1).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa2.columnaMesaEnPosicion(1).peek().getPalo());
        assertEquals(7, mesa2.columnaMesaEnPosicion(5).size());
        assertEquals(11, mesa2.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa2.columnaMesaEnPosicion(5).peek().getPalo());

        assertFalse(klondike.moverCartas(5, 4, 1));
        Mesa mesa3 = klondike.getEstadoMesa();
        assertEquals(7, mesa3.columnaMesaEnPosicion(5).size(), 0);
        assertEquals(11, mesa3.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa3.columnaMesaEnPosicion(5).peek().getPalo());
        assertEquals(5, mesa3.columnaMesaEnPosicion(4).size());
        assertEquals(5, mesa3.columnaMesaEnPosicion(4).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa3.columnaMesaEnPosicion(4).peek().getPalo());

        assertFalse(klondike.moverCartas(1, 6, 1));
        Mesa mesa4 = klondike.getEstadoMesa();
        assertEquals(3, mesa4.columnaMesaEnPosicion(1).size(), 0);
        assertEquals(7, mesa4.columnaMesaEnPosicion(1).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa4.columnaMesaEnPosicion(1).peek().getPalo());
        assertEquals(8, mesa4.columnaMesaEnPosicion(6).size());
        assertEquals(9, mesa4.columnaMesaEnPosicion(6).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa4.columnaMesaEnPosicion(6).peek().getPalo());

        assertTrue(klondike.moverCartas(2, 4, 3));
        Mesa mesa5 = klondike.getEstadoMesa();
        assertEquals(2, mesa5.columnaMesaEnPosicion(2).size(), 0);
        assertEquals(7, mesa5.columnaMesaEnPosicion(2).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa5.columnaMesaEnPosicion(2).peek().getPalo());
        assertEquals(9, mesa5.columnaMesaEnPosicion(4).size());
        assertEquals(1, mesa5.columnaMesaEnPosicion(4).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa5.columnaMesaEnPosicion(4).peek().getPalo());

        assertTrue(klondike.moverCartas(4, 1, 5));
        Mesa mesa6 = klondike.getEstadoMesa();
        assertEquals(3, mesa6.columnaMesaEnPosicion(4).size(), 0);
        assertEquals(9, mesa6.columnaMesaEnPosicion(4).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa6.columnaMesaEnPosicion(4).peek().getPalo());
        assertEquals(9, mesa6.columnaMesaEnPosicion(1).size());
        assertEquals(1, mesa6.columnaMesaEnPosicion(1).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa6.columnaMesaEnPosicion(1).peek().getPalo());

        assertTrue(klondike.moverCartas(1, 4, 7));
        Mesa mesa7 = klondike.getEstadoMesa();
        assertEquals(1, mesa7.columnaMesaEnPosicion(1).size(), 0);
        assertEquals(13, mesa7.columnaMesaEnPosicion(1).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa7.columnaMesaEnPosicion(1).peek().getPalo());
        assertEquals(11, mesa7.columnaMesaEnPosicion(4).size());
        assertEquals(1, mesa7.columnaMesaEnPosicion(4).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa7.columnaMesaEnPosicion(4).peek().getPalo());

        assertTrue(klondike.moverCartas(1, 0, 0));
        for (int i = 0; i < 6; i++)
            klondike.sacarDelMazo();
        assertTrue(klondike.moverCartaDescarteAColumnaMesa(1));
        for (int i = 0; i < 4; i++)
            klondike.sacarDelMazo();
        assertTrue(klondike.moverCartaDescarteAColumnaMesa(5));
        assertTrue(klondike.moverCartas(5, 0, 2));
        Mesa mesa8 = klondike.getEstadoMesa();
        assertEquals(5, mesa8.columnaMesaEnPosicion(5).size(), 0);
        assertEquals(5, mesa8.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa8.columnaMesaEnPosicion(5).peek().getPalo());
        assertEquals(4, mesa8.columnaMesaEnPosicion(0).size());
        assertEquals(10, mesa8.columnaMesaEnPosicion(0).peek().getNumero(), 0);
        assertEquals(Palos.CORAZONES, mesa8.columnaMesaEnPosicion(0).peek().getPalo());

        assertTrue(klondike.moverCartas(4, 0, 8));
        Mesa mesa9 = klondike.getEstadoMesa();
        assertEquals(2, mesa9.columnaMesaEnPosicion(4).size(), 0);
        assertEquals(2, mesa9.columnaMesaEnPosicion(4).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa9.columnaMesaEnPosicion(4).peek().getPalo());
        assertEquals(13, mesa9.columnaMesaEnPosicion(0).size());
        assertEquals(1, mesa9.columnaMesaEnPosicion(0).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa9.columnaMesaEnPosicion(0).peek().getPalo());
        // Columna de mesa 0 llena de una escalera de cartas con palos intercalados (solitario working)

        assertTrue(klondike.moverCartas(0, 5, 3));
        Mesa mesa10 = klondike.getEstadoMesa();
        assertEquals(9, mesa10.columnaMesaEnPosicion(0).size(), 0);
        assertEquals(5, mesa10.columnaMesaEnPosicion(0).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa10.columnaMesaEnPosicion(0).peek().getPalo());
        assertEquals(9, mesa10.columnaMesaEnPosicion(5).size());
        assertEquals(1, mesa10.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa10.columnaMesaEnPosicion(5).peek().getPalo());

        assertFalse(klondike.moverCartas(0, 6, 3));
        Mesa mesa11 = klondike.getEstadoMesa();
        assertEquals(9, mesa11.columnaMesaEnPosicion(0).size(), 0);
        assertEquals(5, mesa11.columnaMesaEnPosicion(0).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa11.columnaMesaEnPosicion(0).peek().getPalo());
        assertEquals(8, mesa11.columnaMesaEnPosicion(6).size());
        assertEquals(9, mesa11.columnaMesaEnPosicion(6).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa11.columnaMesaEnPosicion(6).peek().getPalo());
    }

    @Test
    public void verificarJuegoGanado() {
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConString
                ("M3M1M2M0L3L1L2L0K3K1K2K0J3J1J2J0I3I1I2I0H3H1H2H0B1D0E2F2G0G1G3B2C3E0F0F3G2B0C1D3E3F1A3C2D1E1A1C0D2A2B3A0");
        Klondike klondike = new Klondike(semilla, null);
        // Semilla especial que al repartir permite tener las cartas en la baraja de manera ordenada que, al repartir, cada carta
        // queda en una posicion conveniente para enviarla directamente a una columna final correspondiente
        klondike.repartirCartasInicio();

        int contadorFinal = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = i; j < 7 ; j++) {
                assertFalse(klondike.estaGanado());
                klondike.moverCartaColumnaFinal(j, contadorFinal);
                contadorFinal++;
                if (contadorFinal > 3)
                    contadorFinal = 0;
            }
        }
        for (int i = 0; i < 24; i++) {
            assertFalse(klondike.estaGanado());
            klondike.sacarDelMazo();
            klondike.moverCartaDescarteAColumnaFinal(contadorFinal);
            contadorFinal++;
            if (contadorFinal > 3)
                contadorFinal = 0;
        }

        // Momento de la verdad
        assertTrue(klondike.estaGanado());
    }


    /**
     * Tests Klondike en estado particular
     */
    @Test
    public void manipulacionDeEstadosParticularesDeMesa() {
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConString
                ("L1D0C3G2H1G0J2D2D1A3M3J1A2B1F3I2E1B3K0E3I3G1L0K2J0H0B2I0H2C1C2L2E0A0J3M2K1A1F2I1B0M1C0F0L3F1D3G3K3H3M0E2");
        Klondike klondike1 = new Klondike(semilla, null);
        klondike1.repartirCartasInicio();

        klondike1.moverCartas(3, 2, 0);
        klondike1.moverCartaColumnaFinal(4, 1);
        for (int i = 0; i < 3; i++) {
            klondike1.sacarDelMazo();
        }
        Mesa estado1 = klondike1.getEstadoMesa();
        Carta carta1 = estado1.sacarCartaDescarte();
        assertEquals(7, carta1.getNumero(), 0);
        assertEquals(Palos.TREBOLES, carta1.getPalo());
        assertEquals(4, estado1.columnaMesaEnPosicion(2).size(), 0);
        assertEquals(3, estado1.columnaMesaEnPosicion(2).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, estado1.columnaMesaEnPosicion(2).peek().getPalo());
        assertEquals(1, estado1.columnaFinalEnPosicion(1).size(), 0);
        assertEquals(1, estado1.columnaFinalEnPosicion(1).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, estado1.columnaFinalEnPosicion(1).peek().getPalo());
        estado1.insertarCartaDescarte(carta1);

        Klondike klondike2 = new Klondike(null, estado1);
        klondike2.moverCartaDescarteAColumnaMesa(1);
        klondike2.sacarDelMazo();
        klondike2.moverCartaDescarteAColumnaMesa(6);
        klondike2.sacarDelMazo();
        klondike2.moverCartaDescarteAColumnaMesa(3);
        klondike2.sacarDelMazo();
        klondike2.moverCartaDescarteAColumnaMesa(5);
        klondike2.sacarDelMazo();
        Mesa estado2 = klondike2.getEstadoMesa();
        Carta carta2 = estado2.sacarCartaDescarte();
        assertEquals(2, carta2.getNumero(), 0);
        assertEquals(Palos.DIAMANTES, carta2.getPalo());
        assertEquals(3, estado2.columnaMesaEnPosicion(1).size(), 0);
        assertEquals(7, estado2.columnaMesaEnPosicion(1).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, estado2.columnaMesaEnPosicion(1).peek().getPalo());
        assertEquals(8, estado2.columnaMesaEnPosicion(6).size(), 0);
        assertEquals(9, estado2.columnaMesaEnPosicion(6).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, estado2.columnaMesaEnPosicion(6).peek().getPalo());
        assertEquals(4, estado2.columnaMesaEnPosicion(3).size(), 0);
        assertEquals(5, estado2.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, estado2.columnaMesaEnPosicion(3).peek().getPalo());
        assertEquals(7, estado2.columnaMesaEnPosicion(5).size(), 0);
        assertEquals(11, estado2.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, estado2.columnaMesaEnPosicion(5).peek().getPalo());
        estado2.insertarCartaDescarte(carta2);

        GeneradorSemillas nuevaSemilla = GeneradorSemillas.generarSemillaConString
                ("L1D0C3G2H1G0J2D2D1A3M3J1A2B1F3I2E1B3K0E3I3G1L0K2J0H0B2I0H2C1C2L2E0A0J3M2K1A1F2I1B0M1C0F0L3F1D3G3K3H3M0E2");
        Klondike klondike3 = new Klondike(nuevaSemilla, estado2);
        klondike3.moverCartas(4, 1, 0);
        klondike3.sacarDelMazo();
        klondike3.moverCartaDescarteAColumnaMesa(1);
        klondike3.moverCartas(2, 1, 1);
        klondike3.moverCartaDescarteAColumnaMesa(1);
        klondike3.moverCartaColumnaFinalAColumnaMesa(1, 1);
        for (int i = 0; i < 6; i++) {
            klondike1.sacarDelMazo();
        }
        Mesa estado3 = klondike3.getEstadoMesa();
        Carta carta3 = estado3.sacarCartaDescarte();
        assertEquals(13, carta3.getNumero(), 0);
        assertEquals(Palos.DIAMANTES, carta3.getPalo());
        assertEquals(9, estado3.columnaMesaEnPosicion(1).size(), 0);
        assertEquals(1, estado3.columnaMesaEnPosicion(1).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, estado3.columnaMesaEnPosicion(1).peek().getPalo());
        assertTrue(estado3.columnaFinalEnPosicion(1).isEmpty());
    }

    @Test
    public void verificacionDeCorrectaPersistenciaDeMesa() {
        GeneradorSemillas semilla = GeneradorSemillas.generarSemillaConString
                ("L1D0C3G2H1G0J2D2D1A3M3J1A2B1F3I2E1B3K0E3I3G1L0K2J0H0B2I0H2C1C2L2E0A0J3M2K1A1F2I1B0M1C0F0L3F1D3G3K3H3M0E2");
        Klondike klondike1 = new Klondike(semilla, null);
        klondike1.repartirCartasInicio();

        // Forma el primer estado del juego
        klondike1.moverCartas(3, 2, 0);
        klondike1.moverCartaColumnaFinal(4, 1);
        for (int i = 0; i < 3; i++) {
            klondike1.sacarDelMazo();
        }
        Mesa mesa1 = klondike1.getEstadoMesa();
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

        // Verifica la correcta carga del primer estado de juego
        Klondike klondike2 = new Klondike(null, nuevaMesa1);
        Mesa mesa2 = klondike2.getEstadoMesa();
        Carta carta1 = mesa2.sacarCartaDescarte();
        assertEquals(7, carta1.getNumero(), 0);
        assertEquals(Palos.TREBOLES, carta1.getPalo());
        assertEquals(4, mesa2.columnaMesaEnPosicion(2).size(), 0);
        assertEquals(3, mesa2.columnaMesaEnPosicion(2).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa2.columnaMesaEnPosicion(2).peek().getPalo());
        assertEquals(1, mesa2.columnaFinalEnPosicion(1).size(), 0);
        assertEquals(1, mesa2.columnaFinalEnPosicion(1).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa2.columnaFinalEnPosicion(1).peek().getPalo());
        mesa2.insertarCartaDescarte(carta1);

        // Forma el segundo estado del juego
        klondike2.moverCartaDescarteAColumnaMesa(1);
        klondike2.sacarDelMazo();
        klondike2.moverCartaDescarteAColumnaMesa(6);
        klondike2.sacarDelMazo();
        klondike2.moverCartaDescarteAColumnaMesa(3);
        klondike2.sacarDelMazo();
        klondike2.moverCartaDescarteAColumnaMesa(5);
        klondike2.sacarDelMazo();
        mesa2 = klondike2.getEstadoMesa();
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

        // Verifica la correcta carga del primer estado de juego
        Klondike klondike3 = new Klondike(null, nuevaMesa2);
        Mesa mesa3 = klondike3.getEstadoMesa();
        Carta carta2 = mesa3.sacarCartaDescarte();
        assertEquals(2, carta2.getNumero(), 0);
        assertEquals(Palos.DIAMANTES, carta2.getPalo());
        assertEquals(3, mesa3.columnaMesaEnPosicion(1).size(), 0);
        assertEquals(7, mesa3.columnaMesaEnPosicion(1).peek().getNumero(), 0);
        assertEquals(Palos.TREBOLES, mesa3.columnaMesaEnPosicion(1).peek().getPalo());
        assertEquals(8, mesa3.columnaMesaEnPosicion(6).size(), 0);
        assertEquals(9, mesa3.columnaMesaEnPosicion(6).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa3.columnaMesaEnPosicion(6).peek().getPalo());
        assertEquals(4, mesa3.columnaMesaEnPosicion(3).size(), 0);
        assertEquals(5, mesa3.columnaMesaEnPosicion(3).peek().getNumero(), 0);
        assertEquals(Palos.DIAMANTES, mesa3.columnaMesaEnPosicion(3).peek().getPalo());
        assertEquals(7, mesa3.columnaMesaEnPosicion(5).size(), 0);
        assertEquals(11, mesa3.columnaMesaEnPosicion(5).peek().getNumero(), 0);
        assertEquals(Palos.PICAS, mesa3.columnaMesaEnPosicion(5).peek().getPalo());
        mesa3.insertarCartaDescarte(carta2);
    }
}
