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
        Carta carta = mesa.sacarCartaMazo();
        assertFalse(carta.esVisible());
        assertNull(mesa.sacarCartaDescarte());
        for (int i = 0; i < 51; i++)
            assertTrue(klondike.sacarDelMazo());
        Mesa nuevaMesa = klondike.getEstadoMesa();
        Carta nuevaCarta = mesa.sacarCartaDescarte();
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



    // Tests Klondike con semilla particular
    // "L1D0C3G2H1G0J2D2D1A3M3J1A2B1F3I2E1B3K0E3I3G1L0K2J0H0B2I0H2C1C2L2E0A0J3M2K1A1F2I1B0M1C0F0L3F1D3G3K3H3M0E2"
    @Test
    public void klondikeSemillaVacia() {
        Klondike klondike = new Klondike("", null);
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
    }

    @Test
    public void testSemillaCompleta() {
        Klondike klondike = new Klondike("L1D0C3G2H1G0J2D2D1A3M3J1A2B1F3I2E1B3K0E3I3G1L0K2J0H0B2I0H2C1C2L2E0A0J3M2K1A1F2I1B0M1C0F0L3F1D3G3K3H3M0E2", null);
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
        Klondike klondike = new Klondike("L1D0C3G2H1G0J2D2D1A3M3J1A2B1F3I2E1B3K0E3I3G1L0K2J0H0B2I0H2C1C2L2E0A0J3M2K1A1F2I1B0M1C0F0L3F1D3G3K3H3M0E2", null);
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
        Klondike klondike = new Klondike("L1D0C3G2H1G0J2D2D1A3M3J1A2B1F3I2E1B3K0E3I3G1L0K2J0H0B2I0H2C1C2L2E0A0J3M2K1A1F2I1B0M1C0F0L3F1D3G3K3H3M0E2", null);
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
        Klondike klondike = new Klondike("L1D0C3G2H1G0J2D2D1A3M3J1A2B1F3I2E1B3K0E3I3G1L0K2J0H0B2I0H2C1C2L2E0A0J3M2K1A1F2I1B0M1C0F0L3F1D3G3K3H3M0E2", null);
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
        Klondike klondike = new Klondike("L1D0C3G2H1G0J2D2D1A3M3J1A2B1F3I2E1B3K0E3I3G1L0K2J0H0B2I0H2C1C2L2E0A0J3M2K1A1F2I1B0M1C0F0L3F1D3G3K3H3M0E2", null);
        klondike.repartirCartasInicio();

    }

    @Test
    public void testMoverCartaColumnaFinalAColumnaMesa() {
        Klondike klondike = new Klondike("L1D0C3G2H1G0J2D2D1A3M3J1A2B1F3I2E1B3K0E3I3G1L0K2J0H0B2I0H2C1C2L2E0A0J3M2K1A1F2I1B0M1C0F0L3F1D3G3K3H3M0E2", null);
        klondike.repartirCartasInicio();

    }

    @Test
    public void moverVariasCartasEntreColumnas() {
        Klondike klondike = new Klondike("L1D0C3G2H1G0J2D2D1A3M3J1A2B1F3I2E1B3K0E3I3G1L0K2J0H0B2I0H2C1C2L2E0A0J3M2K1A1F2I1B0M1C0F0L3F1D3G3K3H3M0E2", null);
        klondike.repartirCartasInicio();

    }

    @Test
    public void verificarJuegoGanado() {
    }

    // Tests Klondike en estado particular
    @Test
    public void manipulacionDeEstadosParticulares() {
    }
}
