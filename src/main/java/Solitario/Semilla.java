package Solitario;

import java.util.List;
import java.util.Random;

public class Semilla {
    private final String semilla;

    public Semilla(Integer cantidad, List<Palos> palos) {
        /*
         * Genera una semilla aleatoria de formato String a partir de una cantidad de cartas y una lista de palos definidos.
         */
        StringBuilder semillaCreada = new StringBuilder();
        String numeros = "ABCDEFGHIJKLM";
        if (cantidad != 0 && !palos.isEmpty()) {
            int contador = 0;
            int limite = cantidad / palos.size();
            for (Palos palo : palos) {
                for (int i = 1; i <= limite; i++) {
                    if (contador > 12)
                        contador = 0;
                    char caracter = numeros.charAt(contador);
                    semillaCreada.append(caracter);
                    semillaCreada.append(palos.lastIndexOf(palo));
                    contador++;
                }
                contador = 0;
            }
        }
        StringBuilder nuevaSemilla = mezclarSemilla(semillaCreada);
        this.semilla = nuevaSemilla.toString();
    }

    public Semilla(String semillaEscrita) {
        /*
         * Genera una semilla a partir de un string determinado.
         * Para que un string sea considerado como semilla valida debe cumplir con un formato especifico:
         * - Caracter en posicion par: letra de A hasta M, que representa un numero entre 1 y 13 respectivamente.
         * - Caracter en posicion impar: numero que simboliza la posicion del palo que se encuentra en la lista de palos, al que corresponde
         * la carta cuyo numero es el caracter anterior.
         */
        semilla = semillaEscrita;
    }

    private StringBuilder mezclarSemilla(StringBuilder semilla) {
        /*
         * Recibe el StringBuilder el cual el constructor de Semilla usaba para formar la semilla que el usuario precisa.
         * Recorre cada par de caracteres (que corresponde a un numero y palo de una carta) y lo reposiciona de manera aleatoria en el string.
         */
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

    public boolean isEmpty() {
        return semilla.isEmpty();
    }

    public int length() {
        return semilla.length();
    }

    public char charAt(int indice) {
        return semilla.charAt(indice);
    }
}
