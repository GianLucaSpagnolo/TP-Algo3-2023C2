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
        assertEquals(0, columna.size(),0);
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

        assertEquals(0, columna.size(),0);
        assertEquals(13, segmentoEsperado.size(),0);
        assertEquals(13, segmento.size(),0);
        assertEquals(0, segmentoRestante.size(),0);

        assertTrue(segmentoEsperado.esCadena(12));
        assertTrue(segmento.esCadena(12));
        assertEquals(segmentoEsperado, segmento);
        assertEquals(segmentoRestante, columna);
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

    //segmentos vacios
}