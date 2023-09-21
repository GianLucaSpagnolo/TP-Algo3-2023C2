package Reglas;

import Solitario.*;

import java.util.ArrayList;

public class Klondike implements Reglas {
    private Mesa mesa;

    public Klondike() {
        ArrayList<Palos> palos = new ArrayList<>();
        palos.add(Palos.PICAS);
        palos.add(Palos.TREBOLES);
        palos.add(Palos.CORAZONES);
        palos.add(Palos.DIAMANTES);
        Mazo mazo = new Mazo(52, palos);
        mazo.mezclar();
        Mesa nuevaMesa = new Mesa(mazo);
        nuevaMesa.inicializarColumnasMesa(7, new ColumnaKlondike());
        nuevaMesa.inicializarColumnasFinales(4, new ColumnaKlondike());
        nuevaMesa.crearBarajaDescarte();
        this.mesa = nuevaMesa;
    }

    public void repartirCartasInicio() {
        for (int i=1; i <= 7; i++) {
            for (int j=0; j < i; j++) {
                Carta carta = mesa.sacarCarta();
                if (j == i-1) {
                    carta.darVuelta();
                    carta.cambiarInteractuabilidad();
                }
                mesa.columnasMesa.get(i-1).push(carta);
            }
        }
    }

    public boolean moverCartas(Integer origen, Integer destino, Integer carta) {
        Columna segmento = mesa.columnasMesa.get(origen).obtenerSegmento(carta);
        return true;
    }
}
