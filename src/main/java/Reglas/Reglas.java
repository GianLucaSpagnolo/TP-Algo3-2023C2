package Reglas;

import java.util.Stack;
import Solitario.Carta;
import Solitario.Mazo;
import Solitario.Mesa;

public interface Reglas {
    boolean moverCarta(Carta carta, Integer columna);
    boolean moverCartaColumnaFinal(Carta carta, Integer columna);
    //Mesa inicializarJuego(Mesa mesa);
    void repartirCartasInicio();
    boolean estaGanado();
    boolean esPosibleContinuar();
    boolean sacarDelMazo();
}
