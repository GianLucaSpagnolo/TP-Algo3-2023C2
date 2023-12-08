package ui.Vista;

public enum NombresVariantes {
    Klondike("Klondike"),
    Spider("Spider");

    public final String variante;

    NombresVariantes(String nombre) { this.variante = nombre; }

    public static String[] getListaVariantes() {
        return new String[]{Klondike.variante, Spider.variante};
    }
}
