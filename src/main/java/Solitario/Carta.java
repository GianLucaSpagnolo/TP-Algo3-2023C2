package Solitario;

import java.io.Serializable;

public class Carta implements Serializable {
    private final Integer numero;
    private final Color color;
    private final Palos palo;
    private boolean esVisible;

    public Carta(Integer numero, Palos palo) {
        this.numero = numero;
        this.palo = palo;
        if (palo == Palos.PICAS || palo == Palos.TREBOLES) {
            this.color = Color.NEGRO;
        } else {
            this.color = Color.ROJO;
        }
        this.esVisible = false;
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


    public void darVuelta() {
        this.esVisible = !esVisible;
    }

}
