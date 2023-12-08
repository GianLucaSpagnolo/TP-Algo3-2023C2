package Solitario;

public enum Palos {
    PICAS("PICAS"),
    TREBOLES("TREBOLES"),
    CORAZONES("CORAZONES"),
    DIAMANTES("DIAMANTES");

    public final String nombre;

    Palos(String nombre) {
        this.nombre = nombre;
    }
}
