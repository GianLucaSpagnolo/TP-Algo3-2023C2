package Solitario;

import java.io.Serializable;
import java.util.List;
import java.util.Stack;

public class Mazo implements Serializable {
    private final Stack<Carta> baraja;

    public Mazo() {
        this.baraja = new Stack<>();
    }

    /**
     * Genera una baraja del mazo a partir de una Semilla previamente instanciada cuyo string cumple un formato especifico.
     * Recibe una lista de palos, la cual la semilla determinara, mediante la posicion de cada palo, de que palo es cada carta.
     */
    public void generarBaraja(Semilla semilla, List<Palos> palos) {
        if (semilla.isEmpty())
            return;
        String numeros = "ABCDEFGHIJKLM";
        int cantidad = semilla.length() / 2;
        for (int i = 0; i < cantidad; i++) {
            char caracterNumero = semilla.charAt(i * 2);
            char caracterPalo = semilla.charAt((i * 2) + 1);
            int numero = numeros.lastIndexOf(caracterNumero) + 1;
            Palos palo = palos.get(Character.getNumericValue(caracterPalo));
            Carta carta = new Carta(numero, palo);
            baraja.push(carta);
        }
    }

    public void agregarCarta(Carta carta) {
        baraja.push(carta);
    }

    public Carta sacarCarta() {
        if (estaVacio()) {
            return null;
        }
        return baraja.pop();
    }

    public boolean estaVacio() {
        return baraja.empty();
    }

    public Carta peek() {
        if (estaVacio()) {
            return null;
        }
        return baraja.peek();
    }

    public int size() {
        return baraja.size();
    }
}
