package Reglas;

import Solitario.*;

public interface Solitario {
    Mesa getEstadoMesa();
    boolean moverCartas(Integer origen, Integer destino, Integer carta);
    void repartirCartasInicio();
    boolean estaGanado();
    boolean sacarDelMazo();
}
