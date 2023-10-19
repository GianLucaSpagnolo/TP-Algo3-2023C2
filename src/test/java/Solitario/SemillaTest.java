package Solitario;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class SemillaTest {
    @Test
    public void generarSemillaVacia() {
        ArrayList<Palos> palos = new ArrayList<>();
        Semilla semilla = new Semilla(0, palos);
        assertTrue(semilla.isEmpty());
    }

    @Test
    public void definirSemillaDeterminada() {
        String stringSemilla = "L1D0C3G2H1G0J2D2D1A3M3J1A2B1F3I2E1B3K0E3I3G1L0K2J0H0B2I0H2C1C2L2E0A0J3M2K1A1F2I1B0M1C0F0L3F1D3G3K3H3M0E2";
        Semilla semilla = new Semilla(stringSemilla);
        assertEquals(52 * 2, semilla.length());
        for (int i = 0; i < 52 * 2; i++) {
            assertEquals(stringSemilla.charAt(i), semilla.charAt(i));
        }
    }

    @Test
    public void generarSemillaSimple() {
        /*
         * Semilla simple de 13 cartas de un mismo palo.
         */
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.TREBOLES);
        Semilla semilla = new Semilla(13, palos);
        assertEquals(13 * 2, semilla.length());
    }

    @Test
    public void generarGranSemillaSimple() {
        /*
         * Gran semilla simple de 104 cartas de un mismo palo.
         */
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.PICAS);
        Semilla semilla = new Semilla(104, palos);
        assertEquals(104 * 2, semilla.length());
    }

    @Test
    public void generarSemillaDeBarajaCompleta() {
        /*
         * Semilla de baraja completa, que contiene 54 cartas entre 4 palos diferentes.
         */
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.PICAS);
        palos.add(Palos.TREBOLES);
        palos.add(Palos.CORAZONES);
        palos.add(Palos.DIAMANTES);
        Semilla semilla = new Semilla(52, palos);
        assertEquals(52 * 2, semilla.length());
    }

    @Test
    public void verificacionSemillaAleatoria() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.PICAS);
        palos.add(Palos.TREBOLES);
        palos.add(Palos.CORAZONES);
        palos.add(Palos.DIAMANTES);
        Mazo mazo = new Mazo();
        Semilla semilla = new Semilla(52, palos);
        assertEquals(52 * 2, semilla.length());

        mazo.generarBaraja(semilla, palos);
        int coincidencias = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                Carta carta = mazo.sacarCarta();
                carta.darVuelta();

                boolean mismoNumero = carta.getNumero() == j;
                boolean mismoPalo = carta.getPalo() == palos.get(i);
                if (mismoPalo && mismoNumero)
                    coincidencias++;
            }
        }
        /*
        * La probabilidad de que una carta, luego de mezclar el mazo, este en el lugar que estaba es, considerando
        * una mezcla totalmente random, 1 entre 52. Por lo que se asume que no van a haber mas de 6 coincidencias.
         */
        assertTrue(coincidencias < 6);
    }
}
