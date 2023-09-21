package Solitario;

public class Carta {
    private final Integer numero;
    private final Color color;
    private final Palos palo;
    private boolean esVisible;
    private boolean esInteractuable;

    public Carta(Integer numero, Palos palo) {
        this.numero = numero;
        this.palo = palo;
        if (palo == Palos.PICAS || palo == Palos.TREBOLES) {
            this.color = Color.NEGRO;
        } else {
            this.color = Color.ROJO;
        }
        this.esVisible = false;
        this.esInteractuable = false;
    }

    public Color getColor() {
        if (!this.esVisible()) {
            return null;
        }
        return color;
    }

    public Integer getNumero() {
        if (!this.esVisible()) {
            return null;
        }
        return numero;
    }

    public Palos getPalo() {
        if (!this.esVisible()) {
            return null;
        }
        return palo;
    }

    public boolean esVisible() {
        return esVisible;
    }

    public boolean esInteractuable() {
        return esInteractuable;
    }

    public void darVuelta() {
        this.esVisible = !esVisible;
    }

    public void cambiarInteractuabilidad() {
        if (this.esVisible())
            this.esInteractuable = !esInteractuable;
    }
}
