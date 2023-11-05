package Reglas;

import Solitario.*;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class ColumnaSpiderTest {
    private static final EstrategiaComparacion estrategia = new EstrategiaComparacionSpider();

    @Test
    public void columnaVacia() {
        Columna columna = new ColumnaSpider(estrategia);
        assertTrue(columna.isEmpty());
        assertEquals(new ArrayList<Carta>(), columna.getCartas());
        assertNull(columna.peek());
        assertNull(columna.pop());
    }

    @Test
    public void TestPushCarta() {
        Columna columna = new ColumnaSpider(estrategia);
        Carta carta = new Carta(5, Palos.PICAS);
        columna.push(carta);

        assertFalse(columna.isEmpty());
        assertEquals(1, columna.size(),0);
        assertEquals(carta, columna.peek());
        assertTrue(columna.esCadena(0));
    }

    @Test
    /**
     * Se introduce una carta y se la saca. Se comprueba que la columna se comporte como una columna vacía.
     */
    public void TestVaciarColumna() {
        Columna columna = new ColumnaSpider(estrategia);
        Carta cartaInsertada = new Carta(5, Palos.PICAS);
        columna.push(cartaInsertada);
        Carta cartaSacada = columna.pop();

        assertTrue(columna.isEmpty());
        assertEquals(cartaInsertada, cartaSacada);
        assertTrue(columna.esCadena(0));
        assertNull(columna.peek());
        assertNull(columna.pop());
    }

    @Test
    public void TestDosCartasCadena() {
        Columna columna = new ColumnaSpider(estrategia);
        Carta carta1 = new Carta(5, Palos.PICAS);
        Carta carta2 = new Carta(4, Palos.PICAS);

        carta1.darVuelta();
        carta2.darVuelta();
        columna.push(carta1);
        columna.push(carta2);

        assertFalse(columna.isEmpty());
        assertEquals(2, columna.size(),0);
        assertEquals(carta2, columna.peek());
        assertTrue(columna.esCadena(1));
    }

    @Test
    public void TestDosCartasNoCadena() {
        Columna columna = new ColumnaSpider(estrategia);
        Carta carta1 = new Carta(5, Palos.PICAS);
        Carta carta2 = new Carta(6, Palos.PICAS);

        carta1.darVuelta();
        carta2.darVuelta();
        columna.push(carta1);
        columna.push(carta2);

        assertFalse(columna.isEmpty());
        assertEquals(2, columna.size(),0);
        assertEquals(carta2, columna.peek());
        assertFalse(columna.esCadena(1));
    }

    @Test
    public void TestSegmentoLargoCadena() {
        Columna columna = generarColumnaSpider();
        assertFalse(columna.isEmpty());
        assertEquals(13, columna.size(),0);
        assertTrue(columna.esCadena(12));
    }

    /**
     * Genera una columna completa de 13 cartas que es cadena (Segun las reglas Spider)
     */
    private Columna generarColumnaSpider() {
        Columna columna = new ColumnaSpider(estrategia);
        for (int i=13; i > 0; i--) {
            Carta carta = new Carta(i, Palos.PICAS);
            carta.darVuelta();
            columna.push(carta);
        }
        return columna;
    }

    @Test
    public void TestObtenerSegmento() {
        Columna columna = generarColumnaSpider();
        Columna segmento = columna.obtenerSegmento(5);

        Columna segmentoEsperado = new ColumnaSpider(estrategia);
        for (int i=6; i > 0; i--) {
            Carta carta = new Carta(i, Palos.PICAS);
            carta.darVuelta();
            segmentoEsperado.push(carta);
        }

        Columna columnaRestante = new ColumnaSpider(estrategia);

        //Contruye lo que se espera que quede del segmento original, es decir, el segmento del que se extrajo el segmento
        for (int i=13; i > 6; i--) {
            Carta carta = new Carta(i, Palos.PICAS);
            carta.darVuelta();
            columnaRestante.push(carta);
        }

        assertFalse(columna.isEmpty());
        assertFalse(segmento.isEmpty());
        assertFalse(segmentoEsperado.isEmpty());
        assertFalse(columnaRestante.isEmpty());

        assertEquals(7, columna.size(),0);
        assertEquals(6, segmentoEsperado.size(),0);
        assertEquals(6, segmento.size(),0);
        assertEquals(7, columnaRestante.size(),0);

        assertTrue(segmentoEsperado.esCadena(5));
        assertTrue(segmento.esCadena(5));

        for (int i = 0; i < 6; i++) {
            Carta cartaEsperada = segmentoEsperado.pop();
            Carta cartaSegmento = segmento.pop();
            assertEquals(cartaEsperada.getPalo(), cartaSegmento.getPalo());
            assertEquals(cartaEsperada.getNumero(), cartaSegmento.getNumero());
        }
        for (int j = 0; j < 7; j++) {
            Carta cartaRestante = columnaRestante.pop();
            Carta cartaColumna = columna.pop();
            assertEquals(cartaRestante.getPalo(), cartaColumna.getPalo());
            assertEquals(cartaRestante.getNumero(), cartaColumna.getNumero());
        }
    }

    @Test
    /**
     * Extrae como segmento una pila completa de 13 cartas y verifica que la restante se comporte como columna vacia
     */
    public void TestObtenerSegmentoCompleto() {
        Columna columna = generarColumnaSpider();
        Columna segmento = columna.obtenerSegmento(12);
        Columna segmentoEsperado = generarColumnaSpider();

        assertTrue(columna.isEmpty());
        assertFalse(segmento.isEmpty());
        assertFalse(segmentoEsperado.isEmpty());

        assertEquals(13, segmentoEsperado.size(),0);
        assertEquals(13, segmento.size(),0);
        assertEquals(0, columna.size(), 0);

        assertTrue(segmentoEsperado.esCadena(12));
        assertTrue(segmento.esCadena(12));
        for (int i = 0; i < 13; i++) {
            Carta cartaEsperada = segmentoEsperado.pop();
            Carta cartaSegmento = segmento.pop();
            assertEquals(cartaEsperada.getPalo(), cartaSegmento.getPalo());
            assertEquals(cartaEsperada.getNumero(), cartaSegmento.getNumero());
        }
    }

    @Test
    /**
     * Se trata de extraer un segmento que no es cadena de una columna
     */
    public void TestObtenerSegmentoInvalido() {
        Columna columna = new ColumnaSpider(estrategia);
        for (int i=5; i > 1; i--) {
            Carta carta = new Carta(i, Palos.PICAS);
            carta.darVuelta();
            columna.push(carta);
        }
        //Esta carta en el tope rompe la cadena e impide extraer un segmento de mas de 1 carta
        Carta carta = new Carta(8, Palos.PICAS);
        carta.darVuelta();
        columna.push(carta);

        Columna segmentoExtraido = columna.obtenerSegmento(4);

        assertNull(segmentoExtraido);
        assertEquals(5, columna.size(),0);
    }

    @Test
    /**
     * Se trata de extraer un segmento de una columna vacia
     */
    public void TestObtenerSegmentoDeColumnaVacia() {
        Columna columnaVacia = new ColumnaSpider(estrategia);
        Columna segmentoExtraido = columnaVacia.obtenerSegmento(0);

        assertNull(segmentoExtraido);
        assertTrue(columnaVacia.isEmpty());
    }

    @Test
    /**
     * Se trata de extraer un segmento con un indice de una carta invisible
     */
    public void TestObtenerSegmentoSobreCartasInvisibles() {
        Columna columna = new ColumnaSpider(estrategia);
        for (int i=6; i > 2; i--) {
           Carta carta = new Carta(i, Palos.PICAS);
           columna.push(carta);
        }

        Carta carta1 = new Carta(2, Palos.PICAS);
        carta1.darVuelta();
        columna.push(carta1);

        Carta carta2 = new Carta(1, Palos.PICAS);
        carta2.darVuelta();
        columna.push(carta2);

        Columna segmento = columna.obtenerSegmento(5);
        assertNull(segmento);
        assertEquals(6, columna.size());
    }

    @Test
    /**
     * Extrae un segmento de una columna 1 (columna completa) y lo inserta en una columna 2 (Columna por la mitad)
     */
    public void TestInsertarSegmentoSimpleValido() {
        Columna columna1 = generarColumnaSpider();

        Columna columna2 = new ColumnaSpider(estrategia);
        for (int i=13; i > 6; i--) {
            Carta carta = new Carta(i, Palos.PICAS);
            carta.darVuelta();
            columna2.push(carta);
        }

        Columna segmentoExtraido = columna1.obtenerSegmento(5);
        boolean seInserto = columna2.insertarSegmento(segmentoExtraido); //La columna2 queda como una columna completa de 13 cartas

        Columna segmentoExtraidoEsperado = new ColumnaSpider(estrategia);
        for (int i=6; i > 0; i--) {
            Carta carta = new Carta(i, Palos.PICAS);
            carta.darVuelta();
            segmentoExtraidoEsperado.push(carta);
        }

        Columna columnaRestanteEsperada = new ColumnaSpider(estrategia);
        for (int i=13; i > 6; i--) {
            Carta carta = new Carta(i, Palos.PICAS);
            carta.darVuelta();
            columnaRestanteEsperada.push(carta);
        }

        assertFalse(columna1.isEmpty());
        assertFalse(columna2.isEmpty());
        assertFalse(segmentoExtraido.isEmpty());
        assertFalse(segmentoExtraidoEsperado.isEmpty());
        assertFalse(columnaRestanteEsperada.isEmpty());


        assertEquals(7, columna1.size(),0);
        assertEquals(7, columnaRestanteEsperada.size(),0);
        assertEquals(13, columna2.size(),0);
        assertEquals(6, segmentoExtraido.size(),0);
        assertEquals(6, segmentoExtraidoEsperado.size(),0);

        assertTrue(seInserto);
        for (int i = 0; i < 7; i++) {
            Carta cartaRestante = columnaRestanteEsperada.pop();
            Carta cartaColumna1 = columna1.pop();
            assertEquals(cartaRestante.getPalo(), cartaColumna1.getPalo());
            assertEquals(cartaRestante.getNumero(), cartaColumna1.getNumero());
        }
        for (int j = 0; j < 6; j++) {
            Carta cartaEsperada = segmentoExtraidoEsperado.pop();
            Carta cartaExtraida = segmentoExtraido.pop();
            assertEquals(cartaEsperada.getPalo(), cartaExtraida.getPalo());
            assertEquals(cartaEsperada.getNumero(), cartaExtraida.getNumero());
        }
        Columna columna3 = generarColumnaSpider();
        for (int k = 0; k < 13; k++) {
            Carta cartaEsperada = columna3.pop();
            Carta cartaMovida = columna2.pop();
            assertEquals(cartaEsperada.getPalo(), cartaMovida.getPalo());
            assertEquals(cartaEsperada.getNumero(), cartaMovida.getNumero());
        }
    }

    @Test
    /**
     * Extrae un segmento de una columna 1 (columna completa) y lo trata de insertar en una columna 2, pero es invalido
     */
    public void TestInsertarSegmentoSimpleInvalido() {
        Columna columna1 = generarColumnaSpider();

        Columna columna2 = new ColumnaSpider(estrategia);
        for (int i=13; i > 6; i--) {
            Carta carta = new Carta(i, Palos.PICAS);
            carta.darVuelta();
            columna2.push(carta);
        }

        Columna columnaResultado = new ColumnaSpider(estrategia);
        for (int i=13; i > 6; i--) {
            Carta carta = new Carta(i, Palos.PICAS);
            carta.darVuelta();
            columnaResultado.push(carta);
        }

        Columna segmentoExtraido = columna1.obtenerSegmento(3);
        boolean seInserto = columna2.insertarSegmento(segmentoExtraido);

        Columna segmentoExtraidoEsperado = new ColumnaSpider(estrategia);
        for (int i=4; i > 0; i--) {
            Carta carta = new Carta(i, Palos.PICAS);
            carta.darVuelta();
            segmentoExtraidoEsperado.push(carta);
        }

        Columna columnaRestanteEsperada = new ColumnaSpider(estrategia);
        for (int i=13; i > 4; i--) {
            Carta carta = new Carta(i, Palos.PICAS);
            carta.darVuelta();
            columnaRestanteEsperada.push(carta);
        }

        assertFalse(columna1.isEmpty());
        assertFalse(columna2.isEmpty());
        assertFalse(segmentoExtraido.isEmpty());
        assertFalse(segmentoExtraidoEsperado.isEmpty());
        assertFalse(columnaRestanteEsperada.isEmpty());
        assertFalse(seInserto);

        assertEquals(9, columna1.size(),0);
        assertEquals(9, columnaRestanteEsperada.size(),0);
        assertEquals(7, columna2.size(),0);
        assertEquals(7, columnaResultado.size(),0);
        assertEquals(4, segmentoExtraido.size(),0);
        assertEquals(4, segmentoExtraidoEsperado.size(),0);

        for (int i = 0; i < 9; i++) {
            Carta cartaRestante = columnaRestanteEsperada.pop();
            Carta cartaColumna = columna1.pop();
            assertEquals(cartaRestante.getPalo(), cartaColumna.getPalo());
            assertEquals(cartaRestante.getNumero(), cartaColumna.getNumero());
        }
        for (int j = 0; j < 7; j++) {
            Carta cartaResultado = columnaResultado.pop();
            Carta cartaColumna = columna2.pop();
            assertEquals(cartaResultado.getPalo(), cartaColumna.getPalo());
            assertEquals(cartaResultado.getNumero(), cartaColumna.getNumero());
        }
        for (int k = 0; k < 4; k++) {
            Carta cartaExtraida = segmentoExtraidoEsperado.pop();
            Carta cartaSegmento = segmentoExtraido.pop();
            assertEquals(cartaExtraida.getPalo(), cartaSegmento.getPalo());
            assertEquals(cartaExtraida.getNumero(), cartaSegmento.getNumero());
        }
    }

    @Test
    /**
     * Inserta un segmento de una carta sola
     */
    public void TestInsertarSegmentoValidoUnaCarta() {
        Columna columnaCompleta = generarColumnaSpider();
        Columna columna2 = new ColumnaSpider(estrategia);
        Carta carta1 = new Carta(2, Palos.PICAS);
        carta1.darVuelta();
        columna2.push(carta1);

        Columna segmentoExtraido = columnaCompleta.obtenerSegmento(0);
        boolean seInserto = columna2.insertarSegmento(segmentoExtraido);

        Columna columnaEsperada = new ColumnaSpider(estrategia);
        Carta carta2 = new Carta(2, Palos.PICAS);
        Carta carta3 = new Carta(1, Palos.PICAS);
        carta2.darVuelta();
        carta3.darVuelta();
        columnaEsperada.push(carta2);
        columnaEsperada.push(carta3);

        Columna columnaRestanteEsperada = new ColumnaSpider(estrategia);
        for (int i=13; i > 1; i--) {
            Carta carta = new Carta(i, Palos.PICAS);
            carta.darVuelta();
            columnaRestanteEsperada.push(carta);
        }

        assertFalse(columnaCompleta.isEmpty());
        assertFalse(columna2.isEmpty());
        assertFalse(segmentoExtraido.isEmpty());
        assertFalse(columnaRestanteEsperada.isEmpty());
        assertTrue(seInserto);

        assertEquals(12, columnaCompleta.size(),0);
        assertEquals(12, columnaRestanteEsperada.size(),0);
        assertEquals(2, columna2.size(),0);
        assertEquals(2, columnaEsperada.size(),0);
        assertEquals(1, segmentoExtraido.size(),0);

        for (int i = 0; i < 12; i++) {
            Carta cartaRestante = columnaRestanteEsperada.pop();
            Carta cartaColumna = columnaCompleta.pop();
            assertEquals(cartaRestante.getPalo(), cartaColumna.getPalo());
            assertEquals(cartaRestante.getNumero(), cartaColumna.getNumero());
        }
        for (int j = 0; j < 2; j++) {
            Carta cartaEsperada = columnaEsperada.pop();
            Carta cartaColumna = columna2.pop();
            assertEquals(cartaEsperada.getPalo(), cartaColumna.getPalo());
            assertEquals(cartaEsperada.getNumero(), cartaColumna.getNumero());
        }
        Carta cartaSegmento = segmentoExtraido.pop();
        assertEquals(Palos.PICAS, cartaSegmento.getPalo());
        assertEquals(1, cartaSegmento.getNumero(), 0);
    }

    @Test
    public void TestInsertarSegmentoValidoSobreColumnaVacia() {
        Columna columnaCompleta = generarColumnaSpider();
        Columna columnaVacia = new ColumnaSpider(estrategia);
        Columna segmentoExtraido = columnaCompleta.obtenerSegmento(12);
        boolean seInserto = columnaVacia.insertarSegmento(segmentoExtraido);

        assertTrue(seInserto);
        assertTrue(columnaCompleta.isEmpty());
        Columna columnaLlena = generarColumnaSpider();
        for (int i = 0; i < 13; i++) {
            Carta cartaReferencia = columnaLlena.pop();
            Carta cartaColumna = columnaVacia.pop();
            assertEquals(cartaReferencia.getPalo(), cartaColumna.getPalo());
            assertEquals(cartaReferencia.getNumero(), cartaColumna.getNumero());
        }
    }

    @Test
    public void TestInsertarSegmentoVacio() {
        Columna columna = new ColumnaSpider(estrategia);
        Carta carta = new Carta(4, Palos.PICAS);
        carta.darVuelta();
        columna.push(carta);

        Columna segmentoVacio = new ColumnaSpider(estrategia);

        boolean seInserto = columna.insertarSegmento(segmentoVacio);

        assertFalse(seInserto);
        assertEquals(1, columna.size(), 0);
        Carta cartaColumna = columna.pop();
        assertEquals(Palos.PICAS, cartaColumna.getPalo());
        assertEquals(4, cartaColumna.getNumero(), 0);
    }

    @Test
    public void TestInsertarSegmentoVacioSobreColumnaVacia() {
        Columna columnaVacia = new ColumnaSpider(estrategia);

        Columna segmentoVacio = new ColumnaSpider(estrategia);

        boolean seInserto = columnaVacia.insertarSegmento(segmentoVacio);

        assertFalse(seInserto);
        assertEquals(new ColumnaSpider(estrategia), columnaVacia);
    }

    @Test
    /**
     * Simula escenario donde se debe volver a colocar un segmento extraido en la columna de la que se sacó, antes de hacer visible la carta tope invisible
     */
    public void TestInsertarSegmentoSobreCartaNoVisible() {
        Columna columna = new ColumnaSpider(estrategia);
        Carta cartaNoVisible = new Carta(3, Palos.PICAS);
        Carta cartaVisible = new Carta(2, Palos.PICAS);
        cartaVisible.darVuelta();
        columna.push(cartaNoVisible);
        columna.push(cartaVisible);

        Columna segmentoExtraido = columna.obtenerSegmento(0);
        boolean seInserto = columna.insertarSegmento(segmentoExtraido);

        Columna columnaEsperada = new ColumnaSpider(estrategia);
        Carta carta1 = new Carta(3, Palos.PICAS);
        Carta carta2 = new Carta(2, Palos.PICAS);
        carta2.darVuelta();
        columnaEsperada.push(carta1);
        columnaEsperada.push(carta2);

        assertTrue(seInserto);
        assertEquals(2, columna.size(), 0);
        assertEquals(2, columnaEsperada.size(), 0);
        for (int i = 0; i < 2; i++) {
            Carta cartaEsperada = columnaEsperada.pop();
            if (!cartaEsperada.esVisible())
                cartaEsperada.darVuelta();
            Carta cartaColumna = columna.pop();
            if (!cartaColumna.esVisible())
                cartaColumna.darVuelta();
            assertEquals(cartaEsperada.getPalo(), cartaColumna.getPalo());
            assertEquals(cartaEsperada.getNumero(), cartaColumna.getNumero(), 0);
        }
    }

    @Test
    public void InsertarSegmentoEnColumnaFinal() {
        Columna columnaFinal = new ColumnaSpider(estrategia);
        Columna segmento = generarColumnaSpider();

        boolean seInserto = columnaFinal.insertarColumnaFinal(segmento);
        Carta topeEsperado = new Carta(1, Palos.PICAS);
        topeEsperado.darVuelta();

        assertTrue(seInserto);
        assertEquals(13, columnaFinal.size());
        assertEquals(topeEsperado.getNumero(), columnaFinal.peek().getNumero());
    }

    @Test
    public void InsertarSegmentoVacioEnColumnaFinal() {
        Columna columnaFinal = new ColumnaSpider(estrategia);
        Columna segmento = new ColumnaSpider(estrategia);

        boolean seInserto = columnaFinal.insertarColumnaFinal(segmento);
        Carta topeEsperado = null;

        assertFalse(seInserto);
        assertEquals(0, columnaFinal.size());
        assertEquals(topeEsperado, columnaFinal.peek());
    }

    @Test
    /**
     * Simula un escenario donde se extrajo un segmento de una columna, se lo quiso insertar a otra y es invalido.
     * El segmento DEBE VOLVER A LA COLUMNA DE DONDE VINO --> SE LO DEBE INSERTAR DEVUELTA
     */
    public void InsertarSegmentoDevuelta() {
        Columna columna1 = generarColumnaSpider();

        Columna columna2 = new ColumnaSpider(estrategia);
        for (int i=13; i > 6; i--) {
            Carta carta = new Carta(i, Palos.PICAS);
            carta.darVuelta();
            columna2.push(carta);
        }

        Columna segmentoExtraido = columna1.obtenerSegmento(3);
        boolean seInserto = columna2.insertarSegmento(segmentoExtraido);
        boolean seReinserto = columna1.insertarSegmentoDevuelta(segmentoExtraido);

        assertFalse(seInserto);
        assertTrue(seReinserto);

        assertEquals(13, columna1.size(), 0);
        assertEquals(7, columna2.size(), 0);
    }

    @Test
    /**
     * Simula un escenario donde se extrajo un segmento nulo de una columna y se lo quiso insertar a otra
     */
    public void ReinsertarSegmentoVacio() {

        Columna columna1 = new ColumnaSpider(estrategia);

        Columna columna2 = new ColumnaSpider(estrategia);
        Carta carta = new Carta(3, Palos.PICAS);
        carta.darVuelta();
        columna2.push(carta);

        Columna segmentoExtraido = columna1.obtenerSegmento(0);
        assertNull(segmentoExtraido);

        boolean seInserto = columna2.insertarSegmento(segmentoExtraido);
        boolean seReinserto = columna1.insertarSegmentoDevuelta(segmentoExtraido);

        assertFalse(seInserto);
        assertFalse(seReinserto);

        assertEquals(0, columna1.size(), 0);
        assertEquals(1, columna2.size(), 0);
    }
}
