package Reglas;

import Solitario.Carta;
import Solitario.Columna;


public interface Reglas {
    boolean moverCartas(Integer origen, Integer destino, Integer carta);
    boolean moverCartaColumnaFinal(Integer origen, Integer destino);
    void repartirCartasInicio();
    boolean estaGanado();
    boolean sacarDelMazo();
}
