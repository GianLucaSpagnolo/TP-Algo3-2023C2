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
        assertEquals(new ArrayList<Carta>(), columna.getCartas());
        assertNull(columna.peek());
        assertNull(columna.pop());
    }

    @Test
    public void TestPushCarta() {
        Columna columna = new ColumnaKlondike();
        Carta carta = new Carta(5, Palos.TREBOLES);
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
        Columna columna = new ColumnaKlondike();
        Carta cartaInsertada = new Carta(5, Palos.TREBOLES);
        columna.push(cartaInsertada);
        Carta cartaSacada = columna.pop();

        assertTrue(columna.isEmpty());
        assertEquals(cartaInsertada, cartaSacada);
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
    @Test
    public void TestSegmentoLargoCadena() {
        Columna columna = generarColumna();
        assertFalse(columna.isEmpty());
        assertEquals(13, columna.size(),0);
        assertTrue(columna.esCadena(12));
    }


    /**
     * Genera una columna completa de 13 cartas que es cadena
     */
    private Columna generarColumna() {
        Columna columna = new ColumnaKlondike();
        for (int i=13; i > 0; i--) {
            //inserta cartas intercaladas
            if (i % 2 == 0) {
                Carta cartaNegra = new Carta(i, Palos.TREBOLES);
                cartaNegra.darVuelta();
                columna.push(cartaNegra);
            } else {
                Carta cartaRoja = new Carta(i, Palos.CORAZONES);
                cartaRoja.darVuelta();
                columna.push(cartaRoja);
            }
        }
        return columna;
    }


    @Test
    public void TestObtenerSegmento() {
        Columna columna = generarColumna();
        Columna segmento = columna.obtenerSegmento(5);

        Columna segmentoEsperado = new ColumnaKlondike();
        for (int i=6; i > 0; i--) {
            //inserta cartas intercaladas
            if (i % 2 == 0) {
                Carta cartaNegra = new Carta(i, Palos.TREBOLES);
                cartaNegra.darVuelta();
                segmentoEsperado.push(cartaNegra);
            } else {
                Carta cartaRoja = new Carta(i, Palos.CORAZONES);
                cartaRoja.darVuelta();
                segmentoEsperado.push(cartaRoja);
            }
        }

        Columna columnaRestante = new ColumnaKlondike();

        //Contruye lo que se espera que quede del segmento original, es decir, el segmento del que se extrajo el segmento
        for (int i=13; i > 6; i--) {
            //inserta cartas intercaladas
            if (i % 2 == 0) {
                Carta cartaNegra = new Carta(i, Palos.TREBOLES);
                cartaNegra.darVuelta();
               columnaRestante.push(cartaNegra);
            } else {
                Carta cartaRoja = new Carta(i, Palos.CORAZONES);
                cartaRoja.darVuelta();
                columnaRestante.push(cartaRoja);
            }
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
        Columna columna = generarColumna();
        Columna segmento = columna.obtenerSegmento(12);
        Columna segmentoEsperado = generarColumna();

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
        Columna columna = new ColumnaKlondike();
        for (int i=5; i > 1; i--) {
            //inserta cartas intercaladas
            if (i % 2 == 0) {
                Carta cartaNegra = new Carta(i, Palos.TREBOLES);
                cartaNegra.darVuelta();
                columna.push(cartaNegra);
            } else {
                Carta cartaRoja = new Carta(i, Palos.CORAZONES);
                cartaRoja.darVuelta();
                columna.push(cartaRoja);
            }
        }

        //Esta carta en el tope rompe la cadena e impide extraer un segmento de mas de 1 carta
        Carta carta = new Carta(1, Palos.TREBOLES);
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
        Columna columnaVacia = new ColumnaKlondike();
        Columna segmentoExtraido = columnaVacia.obtenerSegmento(0);

        assertNull(segmentoExtraido);
        assertTrue(columnaVacia.isEmpty());
    }

    @Test
    /**
     * Se trata de extraer un segmento con un indice de una carta invisible
     */
    public void TestObtenerSegmentoSobreCartasInvisibles() {
        Columna columna = new ColumnaKlondike();
        for (int i=6; i > 2; i--) {
            //inserta cartas intercaladas no visibles
            if (i % 2 == 0) {
                Carta cartaNegra = new Carta(i, Palos.TREBOLES);
                columna.push(cartaNegra);
            } else {
                Carta cartaRoja = new Carta(i, Palos.CORAZONES);
                columna.push(cartaRoja);
            }
        }
        Carta carta1 = new Carta(2, Palos.TREBOLES);
        carta1.darVuelta();
        columna.push(carta1);

        Carta carta2 = new Carta(1, Palos.CORAZONES);
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
        Columna columna1 = generarColumna();

        Columna columna2 = new ColumnaKlondike();
        for (int i=13; i > 6; i--) {
            //inserta cartas intercaladas
            if (i % 2 == 0) {
                Carta cartaNegra = new Carta(i, Palos.TREBOLES);
                cartaNegra.darVuelta();
                columna2.push(cartaNegra);
            } else {
                Carta cartaRoja = new Carta(i, Palos.CORAZONES);
                cartaRoja.darVuelta();
                columna2.push(cartaRoja);
            }
        }

        Columna segmentoExtraido = columna1.obtenerSegmento(5);
        boolean seInserto = columna2.insertarSegmento(segmentoExtraido); //La columna2 queda como una columna completa de 13 cartas

        Columna segmentoExtraidoEsperado = new ColumnaKlondike();
        for (int i=6; i > 0; i--) {
            //inserta cartas intercaladas
            if (i % 2 == 0) {
                Carta cartaNegra = new Carta(i, Palos.TREBOLES);
                cartaNegra.darVuelta();
                segmentoExtraidoEsperado.push(cartaNegra);
            } else {
                Carta cartaRoja = new Carta(i, Palos.CORAZONES);
                cartaRoja.darVuelta();
                segmentoExtraidoEsperado.push(cartaRoja);
            }
        }

        Columna columnaRestanteEsperada = new ColumnaKlondike();
        for (int i=13; i > 6; i--) {
            //inserta cartas intercaladas
            if (i % 2 == 0) {
                Carta cartaNegra = new Carta(i, Palos.TREBOLES);
                cartaNegra.darVuelta();
                columnaRestanteEsperada.push(cartaNegra);
            } else {
                Carta cartaRoja = new Carta(i, Palos.CORAZONES);
                cartaRoja.darVuelta();
                columnaRestanteEsperada.push(cartaRoja);
            }
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
        Columna columna3 = generarColumna();
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
        Columna columna1 = generarColumna();

        Columna columna2 = new ColumnaKlondike();
        for (int i=13; i > 6; i--) {
            //inserta cartas intercaladas
            if (i % 2 == 0) {
                Carta cartaNegra = new Carta(i, Palos.TREBOLES);
                cartaNegra.darVuelta();
                columna2.push(cartaNegra);
            } else {
                Carta cartaRoja = new Carta(i, Palos.CORAZONES);
                cartaRoja.darVuelta();
                columna2.push(cartaRoja);
            }
        }

        Columna columnaResultado = new ColumnaKlondike();
        for (int i=13; i > 6; i--) {
            //inserta cartas intercaladas
            if (i % 2 == 0) {
                Carta cartaNegra = new Carta(i, Palos.TREBOLES);
                cartaNegra.darVuelta();
                columnaResultado.push(cartaNegra);
            } else {
                Carta cartaRoja = new Carta(i, Palos.CORAZONES);
                cartaRoja.darVuelta();
                columnaResultado.push(cartaRoja);
            }
        }

        Columna segmentoExtraido = columna1.obtenerSegmento(3);
        boolean seInserto = columna2.insertarSegmento(segmentoExtraido);

        Columna segmentoExtraidoEsperado = new ColumnaKlondike();
        for (int i=4; i > 0; i--) {
            //inserta cartas intercaladas
            if (i % 2 == 0) {
                Carta cartaNegra = new Carta(i, Palos.TREBOLES);
                cartaNegra.darVuelta();
                segmentoExtraidoEsperado.push(cartaNegra);
            } else {
                Carta cartaRoja = new Carta(i, Palos.CORAZONES);
                cartaRoja.darVuelta();
                segmentoExtraidoEsperado.push(cartaRoja);
            }
        }

        Columna columnaRestanteEsperada = new ColumnaKlondike();
        for (int i=13; i > 4; i--) {
            //inserta cartas intercaladas
            if (i % 2 == 0) {
                Carta cartaNegra = new Carta(i, Palos.TREBOLES);
                cartaNegra.darVuelta();
                columnaRestanteEsperada.push(cartaNegra);
            } else {
                Carta cartaRoja = new Carta(i, Palos.CORAZONES);
                cartaRoja.darVuelta();
                columnaRestanteEsperada.push(cartaRoja);
            }
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
        Columna columnaCompleta = generarColumna();
        Columna columna2 = new ColumnaKlondike();
        Carta carta1 = new Carta(2, Palos.TREBOLES);
        carta1.darVuelta();
        columna2.push(carta1);

        Columna segmentoExtraido = columnaCompleta.obtenerSegmento(0);
        boolean seInserto = columna2.insertarSegmento(segmentoExtraido);

        Columna columnaEsperada = new ColumnaKlondike();
        Carta carta2 = new Carta(2, Palos.TREBOLES);
        Carta carta3 = new Carta(1, Palos.CORAZONES);
        carta2.darVuelta();
        carta3.darVuelta();
        columnaEsperada.push(carta2);
        columnaEsperada.push(carta3);

        Columna columnaRestanteEsperada = new ColumnaKlondike();
        for (int i=13; i > 1; i--) {
            //inserta cartas intercaladas
            if (i % 2 == 0) {
                Carta cartaNegra = new Carta(i, Palos.TREBOLES);
                cartaNegra.darVuelta();
                columnaRestanteEsperada.push(cartaNegra);
            } else {
                Carta cartaRoja = new Carta(i, Palos.CORAZONES);
                cartaRoja.darVuelta();
                columnaRestanteEsperada.push(cartaRoja);
            }
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
        assertEquals(Palos.CORAZONES, cartaSegmento.getPalo());
        assertEquals(1, cartaSegmento.getNumero(), 0);
    }

    @Test
    public void TestInsertarSegmentoValidoSobreColumnaVacia() {
        Columna columnaCompleta = generarColumna();
        Columna columnaVacia = new ColumnaKlondike();
        Columna segmentoExtraido = columnaCompleta.obtenerSegmento(12);
        boolean seInserto = columnaVacia.insertarSegmento(segmentoExtraido);

        assertTrue(seInserto);
        assertTrue(columnaCompleta.isEmpty());
        Columna columnaLlena = generarColumna();
        for (int i = 0; i < 13; i++) {
            Carta cartaReferencia = columnaLlena.pop();
            Carta cartaColumna = columnaVacia.pop();
            assertEquals(cartaReferencia.getPalo(), cartaColumna.getPalo());
            assertEquals(cartaReferencia.getNumero(), cartaColumna.getNumero());
        }
    }

    @Test
    public void TestInsertarSegmentoInvalidoSobreColumnaVacia() {
        Columna columna = new ColumnaKlondike();
        Carta carta = new Carta(4, Palos.CORAZONES); //No es un 13
        carta.darVuelta();
        columna.push(carta);

        Columna columnaVacia = new ColumnaKlondike();

        Columna segmentoExtraido = columna.obtenerSegmento(0);

        boolean seInserto = columnaVacia.insertarSegmento(segmentoExtraido);

        assertFalse(seInserto);
        assertEquals(new ColumnaKlondike(), columna);
        assertEquals(new ColumnaKlondike(), columnaVacia);
    }

    @Test
    public void TestInsertarSegmentoVacio() {
        Columna columna = new ColumnaKlondike();
        Carta carta = new Carta(4, Palos.CORAZONES);
        carta.darVuelta();
        columna.push(carta);

        Columna segmentoVacio = new ColumnaKlondike();

        boolean seInserto = columna.insertarSegmento(segmentoVacio);

        assertFalse(seInserto);
        assertEquals(1, columna.size(), 0);
        Carta cartaColumna = columna.pop();
        assertEquals(Palos.CORAZONES, cartaColumna.getPalo());
        assertEquals(4, cartaColumna.getNumero(), 0);
    }

    @Test
    public void TestInsertarSegmentoVacioSobreColumnaVacia() {
        Columna columnaVacia = new ColumnaKlondike();

        Columna segmentoVacio = new ColumnaKlondike();

        boolean seInserto = columnaVacia.insertarSegmento(segmentoVacio);

        assertFalse(seInserto);
        assertEquals(new ColumnaKlondike(), columnaVacia);
    }

    @Test
    /**
     * Simula escenario donde se debe volver a colocar un segmento extraido en la columna de la que se sacó, antes de hacer visible la carta tope invisible
     */
    public void TestInsertarSegmentoSobreCartaNoVisible() {
        Columna columna = new ColumnaKlondike();
        Carta cartaNoVisible = new Carta(3, Palos.CORAZONES);
        Carta cartaVisible = new Carta(2, Palos.TREBOLES);
        cartaVisible.darVuelta();
        columna.push(cartaNoVisible);
        columna.push(cartaVisible);

        Columna segmentoExtraido = columna.obtenerSegmento(0);
        boolean seInserto = columna.insertarSegmento(segmentoExtraido);

        Columna columnaEsperada = new ColumnaKlondike();
        Carta carta1 = new Carta(3, Palos.CORAZONES);
        Carta carta2 = new Carta(2, Palos.TREBOLES);
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
    public void InsertarCartaValidaEnColumnaFinal() {
        Columna columnaFinal = new ColumnaKlondike();
        Carta carta1 = new Carta(1, Palos.PICAS);
        carta1.darVuelta();
        columnaFinal.push(carta1);

        Carta cartaAInsertar = new Carta(2, Palos.PICAS);
        cartaAInsertar.darVuelta();
        Columna segmento = new ColumnaKlondike();
        segmento.push(cartaAInsertar);

        boolean seInserto = columnaFinal.insertarColumnaFinal(segmento);

        assertTrue(seInserto);
        assertEquals(2, columnaFinal.size());
        assertEquals(cartaAInsertar, columnaFinal.peek());
    }

    @Test
    public void InsertarCartaNumeroInvalidoEnColumnaFinal() {
        Columna columnaFinal = new ColumnaKlondike();
        Carta carta1 = new Carta(1, Palos.PICAS);
        carta1.darVuelta();
        columnaFinal.push(carta1);

        Carta cartaAInsertar = new Carta(3, Palos.PICAS);
        cartaAInsertar.darVuelta();
        Columna segmento = new ColumnaKlondike();
        segmento.push(cartaAInsertar);

        boolean seInserto = columnaFinal.insertarColumnaFinal(segmento);

        assertFalse(seInserto);
        assertEquals(1, columnaFinal.size());
        assertEquals(carta1, columnaFinal.peek());
    }

    @Test
    public void InsertarCartaPaloInvalidoEnColumnaFinal() {
        Columna columnaFinal = new ColumnaKlondike();
        Carta carta1 = new Carta(1, Palos.PICAS);
        carta1.darVuelta();
        columnaFinal.push(carta1);

        Carta cartaAInsertar = new Carta(2, Palos.CORAZONES);
        cartaAInsertar.darVuelta();
        Columna segmento = new ColumnaKlondike();
        segmento.push(cartaAInsertar);

        boolean seInserto = columnaFinal.insertarColumnaFinal(segmento);

        assertFalse(seInserto);
        assertEquals(1, columnaFinal.size());
        assertEquals(carta1, columnaFinal.peek());
    }

    @Test
    public void InsertarCartaValidaEnColumnaFinalVacia() {
        Columna columnaFinal = new ColumnaKlondike();

        Carta cartaAInsertar = new Carta(1, Palos.CORAZONES);
        cartaAInsertar.darVuelta();
        Columna segmento = new ColumnaKlondike();
        segmento.push(cartaAInsertar);

        boolean seInserto = columnaFinal.insertarColumnaFinal(segmento);

        assertTrue(seInserto);
        assertEquals(1, columnaFinal.size());
        assertEquals(cartaAInsertar, columnaFinal.peek());
    }

    @Test
    public void InsertarSegmentoNullEnColumnaFinal() {
        Columna columnaFinal = new ColumnaKlondike();
        Carta carta1 = new Carta(1, Palos.PICAS);
        carta1.darVuelta();
        columnaFinal.push(carta1);

        boolean seInserto = columnaFinal.insertarColumnaFinal(null);

        assertFalse(seInserto);
        assertEquals(1, columnaFinal.size());
        assertEquals(carta1, columnaFinal.peek());
    }

    @Test
    /**
     * Simula un escenario donde se extrajo un segmento de una columna, se lo quiso insertar a otra y es invalido.
     * El segmento DEBE VOLVER A LA COLUMNA DE DONDE VINO --> SE LO DEBE INSERTAR DEVUELTA
     */
    public void InsertarSegmentoDevuelta() {
        Columna columna1 = generarColumna();

        Columna columna2 = new ColumnaKlondike();
        for (int i=13; i > 6; i--) {
            //inserta cartas intercaladas
            if (i % 2 == 0) {
                Carta cartaNegra = new Carta(i, Palos.TREBOLES);
                cartaNegra.darVuelta();
                columna2.push(cartaNegra);
            } else {
                Carta cartaRoja = new Carta(i, Palos.CORAZONES);
                cartaRoja.darVuelta();
                columna2.push(cartaRoja);
            }
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

        Columna columna1 = new ColumnaKlondike();

        Columna columna2 = new ColumnaKlondike();
        Carta carta = new Carta(3, Palos.TREBOLES);
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

    @Test
    public void MoverAColumnaFinalMismoColorDiferentePalo() {
        Columna columnaFinal = new ColumnaKlondike();
        Carta carta1 = new Carta(1, Palos.PICAS);
        carta1.darVuelta();
        columnaFinal.push(carta1);

        Carta cartaAInsertar = new Carta(2, Palos.TREBOLES);
        cartaAInsertar.darVuelta();

        Columna segmento = new ColumnaKlondike();
        segmento.push(cartaAInsertar);

        boolean seInserto = columnaFinal.insertarColumnaFinal(segmento);

        assertFalse(seInserto);
        assertEquals(1, columnaFinal.size());
        assertEquals(carta1, columnaFinal.peek());
    }

}
