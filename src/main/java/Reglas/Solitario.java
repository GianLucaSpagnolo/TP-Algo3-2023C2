package Reglas;

import Solitario.*;

public interface Solitario {
    Mesa getEstadoMesa();
    Columna seleccionarCartas(Integer origen, Integer carta);
    boolean moverCartas(Columna cartas, Integer origen, Integer destino);
    void repartirCartasInicio();
    boolean estaGanado();
    boolean sacarDelMazo();
}
