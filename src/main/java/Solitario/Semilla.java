package Solitario;

public class Semilla {
    private final String semilla;
    public Semilla(String semilla) {
        this.semilla = semilla;
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
