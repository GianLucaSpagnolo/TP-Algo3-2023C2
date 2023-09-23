package Solitario;

import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Mazo {
    private final Stack<Carta> baraja;

    public Mazo() {
        this.baraja = new Stack<>();
    }

    public void generarBaraja(String semilla, List<Palos> palos) {
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

    private StringBuilder mezclarSemilla(StringBuilder semilla) {
        if (semilla.isEmpty())
            return semilla;
        Random generador = new Random();
        int cantidad = semilla.length() / 2;
        for (int i = 0; i < cantidad; i++) {
            int indice = generador.nextInt(0, cantidad);
            char caracterNumero1 = semilla.charAt(i * 2);
            char caracterPalo1 = semilla.charAt((i * 2) + 1);
            char caracterNumero2 = semilla.charAt(indice * 2);
            char caracterPalo2 = semilla.charAt((indice * 2) + 1);

            semilla.deleteCharAt(i * 2);
            semilla.insert(i * 2, caracterNumero2);
            semilla.deleteCharAt((i * 2) + 1);
            semilla.insert((i * 2) + 1, caracterPalo2);

            semilla.deleteCharAt(indice * 2);
            semilla.insert(indice * 2, caracterNumero1);
            semilla.deleteCharAt((indice * 2) + 1);
            semilla.insert((indice * 2) + 1, caracterPalo1);
        }
        return semilla;
    }

    public String generarSemilla(Integer cantidad, List<Palos> palos) {
        StringBuilder semilla = new StringBuilder();
        String numeros = "ABCDEFGHIJKLM";
        if (cantidad != 0 && !palos.isEmpty()) {
            int contador = 0;
            int limite = cantidad / palos.size();
            for (Palos palo : palos) {
                for (int i = 1; i <= limite; i++) {
                    if (contador > 12)
                        contador = 0;
                    char caracter = numeros.charAt(contador);
                    semilla.append(caracter);
                    semilla.append(palos.lastIndexOf(palo));
                    contador++;
                }
                contador = 0;
            }
        }
        StringBuilder nuevaSemilla = mezclarSemilla(semilla);
        return nuevaSemilla.toString();
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
}
