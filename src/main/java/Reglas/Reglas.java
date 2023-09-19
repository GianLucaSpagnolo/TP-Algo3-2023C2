package Reglas;

import java.util.Stack;
import Solitario.Carta;
import Solitario.Mesa;

public interface Reglas {
    boolean moverCarta(Carta carta, Integer columna);
    boolean moverCartaColumnaFinal(Carta carta, Integer columna);
    Stack inicializarCaraja();
    Mesa inicializarJuego(Mesa mesa);
    void repartirCartas();
    boolean estaGanado();
    boolean esPosibleContinuar();
}
