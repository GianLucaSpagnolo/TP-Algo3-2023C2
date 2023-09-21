package Reglas;

import Solitario.Columna;

public class ColumnaKlondike extends Columna {
    public ColumnaKlondike() {
        super();
    }

    public boolean esCadena(Integer indice) {
        for (int i = 0; i < indice; i++) {
            if (cartas.get(i).getNumero() != (cartas.get(i+1).getNumero() - 1)) {
                return false;
            }
        }
        return true;
    }

    public Columna obtenerSegmento(Integer indice) {
        Columna auxiliar = new ColumnaKlondike();
        if (!esCadena(indice)) {
            return null;
        }
        for (int i = indice; i >= 0; i--) {
            auxiliar.push(cartas.get(i));
            cartas.remove(i);
        }
        return auxiliar;
    }

    public void insertarSegmento(Columna segmento) {
        if (cartas.get(0).getNumero() == segmento.getCartas().get(cartas.size()-1).getNumero()+1) {
            cartas.addAll(0, segmento);
        }
    }
}
