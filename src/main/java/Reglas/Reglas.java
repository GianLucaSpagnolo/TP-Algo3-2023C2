package Reglas;

import java.util.Stack;
import Solitario.Carta;
import Solitario.Mesa;

public interface Reglas {
    boolean mover_carta(Carta carta, Integer columna);
    boolean mover_carta_columna_final(Carta carta, Integer columna);
    Stack inicializar_caraja();
    Mesa inicializar_juego(Mesa mesa);
    void repartir_cartas();
    boolean esta_ganado();
    boolean es_posible_continuar();
}
