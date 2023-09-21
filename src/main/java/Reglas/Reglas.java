package Reglas;

import Solitario.Carta;
import Solitario.Columna;


public interface Reglas {
    boolean moverCartas(Columna origen, Columna destino, Integer indice);
    boolean moverCartaColumnaFinal(Carta carta, Integer columna);
    //Mesa inicializarJuego(Mesa mesa);
    void repartirCartasInicio();
    boolean estaGanado();
    boolean esPosibleContinuar();
    boolean sacarDelMazo();
}
