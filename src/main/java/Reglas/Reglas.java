package Reglas;

import Solitario.*;

public interface Reglas {
    Mesa getEstadoMesa();
    boolean moverCartas(Integer origen, Integer destino, Integer carta);
    boolean moverCartaColumnaFinal(Integer origen, Integer destino);
    void repartirCartasInicio();
    boolean estaGanado();
    boolean sacarDelMazo();
}
