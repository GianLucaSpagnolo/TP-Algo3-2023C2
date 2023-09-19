package Reglas;

import java.util.Stack;
import Solitario.Carta;
import Solitario.Mazo;
import Solitario.Mesa;

public interface Reglas {
    boolean moverCarta(Carta carta, Integer columna);
    boolean moverCartaColumnaFinal(Carta carta, Integer columna);
    Mazo inicializarCaraja();
    Mesa inicializarJuego(Mesa mesa);
    void repartirCartas();
    boolean estaGanado();
    boolean esPosibleContinuar();
}
