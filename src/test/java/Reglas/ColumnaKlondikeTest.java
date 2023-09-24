package Reglas;

import Solitario.Carta;
import Solitario.Columna;
import Solitario.Palos;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ColumnaKlondikeTest {

    @Test
    public void columnaVacia() {
        Columna columna = new ColumnaKlondike();

        assertTrue(columna.isEmpty());
        assertEquals(0, columna.size(),0);
        assertEquals(new ArrayList<Carta>(), columna.getCartas());
        assertNull(columna.peek());
        assertNull(columna.pop());
    }

    @Test
    public void TestUnaCarta() {
        Columna columna = new ColumnaKlondike();
        Carta carta = new Carta(5, Palos.TREBOLES);
        columna.push(carta);

        assertFalse(columna.isEmpty());
        assertEquals(1, columna.size(),0);
        assertEquals(carta, columna.peek());
        assertTrue(columna.esCadena(0));
    }

    @Test
    public void TestDosCartasCadena() {
        Columna columna = new ColumnaKlondike();
        Carta cartaNegra = new Carta(5, Palos.TREBOLES);
        Carta cartaRoja = new Carta(4, Palos.CORAZONES);

        cartaNegra.darVuelta();
        cartaRoja.darVuelta();
        columna.push(cartaNegra);
        columna.push(cartaRoja);

        assertFalse(columna.isEmpty());
        assertEquals(2, columna.size(),0);
        assertEquals(cartaRoja, columna.peek());
        assertTrue(columna.esCadena(1));
    }

    @Test
    public void TestDosCartasNoCadena() {
        Columna columna = new ColumnaKlondike();
        Carta cartaNegra = new Carta(5, Palos.TREBOLES);
        Carta cartaRoja = new Carta(4, Palos.TREBOLES);

        cartaNegra.darVuelta();
        cartaRoja.darVuelta();
        columna.push(cartaNegra);
        columna.push(cartaRoja);

        assertFalse(columna.isEmpty());
        assertEquals(2, columna.size(),0);
        assertEquals(cartaRoja, columna.peek());
        assertFalse(columna.esCadena(1));
    }

    //metodos columna

}