package ui.Controlador;

import Reglas.Klondike;
import Solitario.Carta;
import Solitario.Columna;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import ui.Vista.VistaCarta;
import ui.Vista.VistaColumnaMesa;
import ui.Vista.VistaKlondike;

public class ControladorKlondike {
    private final VistaKlondike vista;
    private final Klondike modelo;

    private Columna segmentoSeleccionado;
    private int origenMovimiento = -1;
    private Boolean hayCartaDescarte = false;
    private int cartaColumnaFinal = -1;
    public ControladorKlondike(Klondike modelo, VistaKlondike vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void iniciar() {

        vista.registrarSacarDelMazo(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                modelo.sacarDelMazo();
                vista.actualizarMazos();
            }
        });

        vista.registrarClickEnColumnaMesa(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int indice = 0;
                int indiceColumnaMesa = 0;
                VistaCarta vistaCarta = null;
                PickResult click = mouseEvent.getPickResult();

                try {
                    vistaCarta = (VistaCarta) click.getIntersectedNode();
                } catch (ClassCastException ex) {
                    VistaColumnaMesa vcm = (VistaColumnaMesa) click.getIntersectedNode();
                    indice = -1;
                    indiceColumnaMesa = vcm.getIndice();
                }
                if (vistaCarta != null) {
                    indice = vistaCarta.getIndice();
                    indiceColumnaMesa = vistaCarta.getIndiceColumnaMesa();
                }

                if ((segmentoSeleccionado == null) && (indice != -1)) {
                    segmentoSeleccionado = modelo.seleccionarCartas(indiceColumnaMesa, indice);
                    origenMovimiento = indiceColumnaMesa;
                    if (segmentoSeleccionado != null ) {
                        vista.seleccionarCartas(indice, indiceColumnaMesa);
                    }
                } else if (segmentoSeleccionado != null) {
                    modelo.moverCartas(segmentoSeleccionado, origenMovimiento, indiceColumnaMesa);
                    vista.actualizarColumnasMesa(origenMovimiento, indiceColumnaMesa);
                    vista.deseleccionarCartas(origenMovimiento);
                    segmentoSeleccionado = null;
                }
            }
        });

    }

}
