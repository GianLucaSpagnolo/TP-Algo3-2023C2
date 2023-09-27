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
    public void TestVaciarColumna() {
        //Se introduce una carta y se la saca. Se comprueba que la columna se comporte como una columna vacía.
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


    private Columna generarColumna() {
        //Genera una columna completa de 13 cartas QUE ES CADENA
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
        assertEquals(segmentoEsperado, segmento);
        assertEquals(columnaRestante, columna);
    }


    @Test
    public void TestObtenerSegmentoCompleto() {
        //Extrae como segmento una pila completa de 13 cartas y verifica que la restante se comporte como columna vacia
        Columna columna = generarColumna();
        Columna segmento = columna.obtenerSegmento(12);
        Columna segmentoEsperado = generarColumna();
        Columna segmentoRestante = new ColumnaKlondike();

        assertTrue(columna.isEmpty());
        assertFalse(segmento.isEmpty());
        assertFalse(segmentoEsperado.isEmpty());
        assertTrue(segmentoRestante.isEmpty());

        assertEquals(13, segmentoEsperado.size(),0);
        assertEquals(13, segmento.size(),0);
        assertEquals(0, segmentoRestante.size(),0);

        assertTrue(segmentoEsperado.esCadena(12));
        assertTrue(segmento.esCadena(12));
        assertEquals(segmentoEsperado, segmento);
        assertEquals(segmentoRestante, columna);
    }

    @Test
    public void TestObtenerSegmentoInvalido() {
        //Se trata de extraer un segmento que no es cadena de una columna
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
        Carta carta = new Carta(1, Palos.TREBOLES); //Esta carta en el tope rompe la cadena e impide extraer un segmento de mas de 1 carta
        carta.darVuelta();
        columna.push(carta);

        Columna segmentoExtraido = columna.obtenerSegmento(4);

        assertNull(segmentoExtraido);
        assertEquals(5, columna.size(),0);
    }

    @Test
    public void TestObtenerSegmentoDeColumnaVacia() {
        //Se trata de extraer un segmento de una columna vacia
        Columna columnaVacia = new ColumnaKlondike();
        Columna segmentoExtraido = columnaVacia.obtenerSegmento(0);

        assertNull(segmentoExtraido);
        assertTrue(columnaVacia.isEmpty());
    }

    @Test
    public void TestObtenerSegmentoSobreCartasInvisibles() {
        //Se trata de extraer un segmento con un indice de una carta invisible
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
    public void TestInsertarSegmentoSimpleValido() {
        //Extrae un segmento de una columna 1 (columna completa) y lo inserta en una columna 2 (Columna por la mitad)
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
        assertEquals(columnaRestanteEsperada, columna1);
        assertEquals(segmentoExtraidoEsperado, segmentoExtraido);
        assertEquals(generarColumna(), columna2);
    }

    @Test
    public void TestInsertarSegmentoSimpleInvalido() {
        //Extrae un segmento de una columna 1 (columna completa) y lo trata de insertar en una columna 2, pero es invalido
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

        assertEquals(columnaRestanteEsperada, columna1);
        assertEquals(segmentoExtraidoEsperado, segmentoExtraido);
        assertEquals(columnaResultado, columna2);
    }

    @Test
    public void TestInsertarSegmentoValidoUnaCarta() {
        //Inserta un segmento de una carta sola
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

        assertEquals(columnaRestanteEsperada, columnaCompleta);
        assertEquals(columnaEsperada, columna2);
    }

    @Test
    public void TestInsertarSegmentoValidoSobreColumnaVacia() {
        Columna columnaCompleta = generarColumna();
        Columna columnaVacia = new ColumnaKlondike();
        Columna segmentoExtraido = columnaCompleta.obtenerSegmento(12);
        boolean seInserto = columnaVacia.insertarSegmento(segmentoExtraido);

        assertTrue(seInserto);
        assertEquals(new ColumnaKlondike(), columnaCompleta);
        assertEquals(generarColumna(), columnaVacia);
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
        assertEquals(new ColumnaKlondike(), columna);
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
    public void TestInsertarSegmentoSobreCartaNoVisible() {
        //Simula escenario donde se debe volver a colocar un segmento extraido en la columna de la que se sacó, antes de hacer visible la carta tope invisible
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
        assertEquals(columnaEsperada, columna);
    }

    @Test
    public void InsertarCartaValidaEnColumnaFinal() {
        Columna columnaFinal = new ColumnaKlondike();
        Carta carta1 = new Carta(1, Palos.PICAS);
        carta1.darVuelta();
        columnaFinal.push(carta1);

        Carta cartaAInsertar = new Carta(2, Palos.PICAS);
        cartaAInsertar.darVuelta();

        boolean seInserto = columnaFinal.insertarCartaColumnaFinal(cartaAInsertar);

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

        boolean seInserto = columnaFinal.insertarCartaColumnaFinal(cartaAInsertar);

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

        boolean seInserto = columnaFinal.insertarCartaColumnaFinal(cartaAInsertar);

        assertFalse(seInserto);
        assertEquals(1, columnaFinal.size());
        assertEquals(carta1, columnaFinal.peek());
    }

    @Test
    public void InsertarCartaValidaEnColumnaFinalVacia() {
        Columna columnaFinal = new ColumnaKlondike();

        Carta cartaAInsertar = new Carta(1, Palos.CORAZONES);
        cartaAInsertar.darVuelta();

        boolean seInserto = columnaFinal.insertarCartaColumnaFinal(cartaAInsertar);

        assertTrue(seInserto);
        assertEquals(1, columnaFinal.size());
        assertEquals(cartaAInsertar, columnaFinal.peek());
    }

    @Test
    public void InsertarCartaNullEnColumnaFinal() {
        Columna columnaFinal = new ColumnaKlondike();
        Carta carta1 = new Carta(1, Palos.PICAS);
        carta1.darVuelta();
        columnaFinal.push(carta1);

        Carta cartaAInsertar = null;

        boolean seInserto = columnaFinal.insertarCartaColumnaFinal(cartaAInsertar);

        assertFalse(seInserto);
        assertEquals(1, columnaFinal.size());
        assertEquals(carta1, columnaFinal.peek());
    }

    @Test
    public void InsertarSegmentoDevuelta() {
        // Simula un escenario donde se extrajo un segmento de una columna, se lo quiso insertar a otra y es invalido. El segmento DEBE VOLVER
        // A LA COLUMNA DE DONDE VINO --> SE LO DEBE INSERTAR DEVUELTA

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
    public void ReinsertarSegmentoVacio() {
        // Simula un escenario donde se extrajo un segmento nulo de una columna y se lo quiso insertar a otra

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
}