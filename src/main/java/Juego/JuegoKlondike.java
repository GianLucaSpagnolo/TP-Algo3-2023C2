package Juego;

import Reglas.Klondike;

public class JuegoKlondike implements Juego{
    public void ejecutarJuego() {
        Klondike klondike = new Klondike(null, null);
        klondike.repartirCartasInicio();
        while (!klondike.estaGanado()) {

        }
    }
}
