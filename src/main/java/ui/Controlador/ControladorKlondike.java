package ui.Controlador;

import Reglas.Klondike;
import Solitario.Carta;
import Solitario.Columna;
import Solitario.Mesa;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import ui.Vista.VistaCarta;
import ui.Vista.VistaColumnaMesa;
import ui.Vista.VistaKlondike;
import ui.Vista.VistaMazo;

public class ControladorKlondike {
    private Columna segmentoSeleccionado;
    private int origenMovimiento;
    private VistaKlondike vista;
    private Klondike modelo;
    public ControladorKlondike(Klondike modelo, VistaKlondike vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void iniciar() {
        vista.registrarSacarDelMazo(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                modelo.sacarDelMazo();
                Mesa mesa = modelo.getEstadoMesa();
                vista.actualizarMazos(mesa.getBaraja(), mesa.getBarajaDescarte());
            }
        });


        vista.registrarClickEnColumnaMesa(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                PickResult click = mouseEvent.getPickResult();
                VistaCarta vistaCarta = (VistaCarta) click.getIntersectedNode();
                int indice = vistaCarta.getIndice();
                int indiceColumnaMesa = vistaCarta.getIndiceColumnaMesa();
                if (segmentoSeleccionado != null) {
                    boolean seMovio = modelo.moverCartas(segmentoSeleccionado, origenMovimiento, indiceColumnaMesa);
                    if (seMovio) {
                        Mesa mesa = modelo.getEstadoMesa();
                        Columna columna1 = mesa.columnaMesaEnPosicion(origenMovimiento);
                        Columna columna2 = mesa.columnaMesaEnPosicion(indiceColumnaMesa);
                        vista.actualizarColumnasMesa(columna1, columna2, origenMovimiento, indiceColumnaMesa);
                    }
                    vista.deseleccionarCartas(origenMovimiento);
                    segmentoSeleccionado = null;
                } else {
                    segmentoSeleccionado = modelo.seleccionarCartas(indiceColumnaMesa, indice);
                    origenMovimiento = indiceColumnaMesa;
                    if (segmentoSeleccionado != null ) {
                        vista.seleccionarCartas(indice, indiceColumnaMesa);
                    }
                }
            }
        });

    }

}
