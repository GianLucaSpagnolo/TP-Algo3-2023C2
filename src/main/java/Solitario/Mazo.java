package Solitario;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Mazo {
    private final Stack<Carta> baraja;

    public Mazo(Integer cantidad, List<Palos> palos) {
        this.baraja = new Stack<>();
        int contador = 0;
        int limite = cantidad/palos.size();
        for (Palos palo : palos) {
            while (contador < limite) {
                for (int i = 1; i < 14; i++) {
                    Carta carta = new Carta(i, palo);
                    baraja.push(carta);
                    contador++;
                }
            }
            contador = 0;
        }
    }

    public void agregarCarta(Carta carta) {
        baraja.push(carta);
    }

    public Carta sacarCarta() {
        if (baraja.empty()) {
            return null;
        }
        return baraja.pop();
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
        }
    }

}
