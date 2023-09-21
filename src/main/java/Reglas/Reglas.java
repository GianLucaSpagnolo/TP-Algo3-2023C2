package Reglas;

import Solitario.Carta;
import Solitario.Columna;


public interface Reglas {
    boolean moverCartas(Integer origen, Integer destino, Integer carta);
    boolean moverCartaColumnaFinal(Carta carta, Integer columna);
    void repartirCartasInicio();
    boolean estaGanado();
    boolean esPosibleContinuar();
    boolean sacarDelMazo();
}
