package Solitario;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Mazo {
    private final Stack<Carta> baraja;

    public Mazo(Integer cantidad, List<Palos> palos) {
        this.baraja = new Stack<>();
        if (cantidad != 0 && !palos.isEmpty()) {
            int contador = 1;
            int limite = cantidad / palos.size();
            for (Palos palo : palos) {
                for (int i = 1; i <= limite; i++) {
                    if (contador > 13)
                        contador = 1;
                    Carta carta = new Carta(contador, palo);
                    baraja.push(carta);
                    contador++;
                }
                contador = 1;
            }
        }
    }

    public void agregarCarta(Carta carta) {
        baraja.push(carta);
    }

    public Carta sacarCarta() {
        if (mazoVacio()) {
            return null;
        }
        return baraja.pop();
    }

    public boolean mazoVacio() {
        return baraja.empty();
    }

    public void mezclar() {
        ArrayList<Carta> auxiliar = new ArrayList<>();
        while (!baraja.empty()) {
            auxiliar.add(sacarCarta());
        }
        Random generador = new Random();
        int cantidad = auxiliar.size();
        for (int i = 0; i < cantidad; i++) {
            int indice = generador.nextInt(0, cantidad - i);
            agregarCarta(auxiliar.get(indice));
            auxiliar.remove(indice);
        }
    }

}
