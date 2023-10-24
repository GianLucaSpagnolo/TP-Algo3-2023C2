package Reglas;

import Solitario.*;
public class ColumnaSpider extends Columna {
    public ColumnaSpider(EstrategiaComparacion estrategia) {
        super(estrategia);
    }

    /**
     * Obtiene una instancia de Columna considerada como un segmento de cartas de una columna hasta determinado
     * indice, verificando si esas cartas son una cadena correctamente, y las quita de la columna determinada.
     */
    public Columna obtenerSegmento(Integer indice) {
        if (isEmpty()) {
            return null;
        }
        EstrategiaComparacion estrategia = new EstrategiaComparacionSpider();
        Columna auxiliar = new ColumnaSpider(estrategia);
        if (!esCadena(indice)) {
            return null;
        }
        for (int i = indice; i >= 0; i--) {
            auxiliar.push(this.get(i));
            this.remove(i);
        }
        return auxiliar;
    }

    /**
     * Inserta en una columna final un segmento de cartas completo de 13 cartas (desde A hasta K).
     */
    public boolean insertarColumnaFinal(Columna segmento) {
        if (segmento == null || segmento.isEmpty()) {
            return false;
        }
        this.addAll(0, segmento.getCartas());
        return true;
    }
}
