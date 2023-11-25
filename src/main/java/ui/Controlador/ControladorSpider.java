package ui.Controlador;

import Reglas.Klondike;
import Reglas.Spider;
import Solitario.Columna;
import javafx.scene.input.PickResult;
import ui.Vista.*;

public class ControladorSpider {
    private final VistaSpider vista;
    private final Spider modelo;

    private Columna segmentoSeleccionado;
    private int indiceColumnaMesa;
    private Boolean hayCartaColumnaMesa = false;
    public ControladorSpider(Spider modelo, VistaSpider vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void iniciar() {
        vista.registrarSacarDelMazo(mouseEvent -> {
            if (!hayCartaColumnaMesa) {
                modelo.sacarDelMazo();
                vista.actualizarMazo();
                for (int i = 0; i < 10; i++) {
                    vista.actualizarColumnaMesa(i);
                }

            } else if (hayCartaColumnaMesa) {
                hayCartaColumnaMesa = false;
                modelo.moverCartas(segmentoSeleccionado, indiceColumnaMesa, -1);
                vista.deseleccionarCartas(indiceColumnaMesa);
                vista.actualizarColumnaMesa(indiceColumnaMesa);
            }
        });

        vista.registrarClickEnColumnaMesa(mouseEvent -> {
            int indice = 0;
            int indiceColumnaSeleccionada = 0;
            VistaCarta vistaCarta = null;
            PickResult click = mouseEvent.getPickResult();

            try {
                vistaCarta = (VistaCarta) click.getIntersectedNode();
            } catch (ClassCastException ex) {
                VistaColumnaMesa vcm = (VistaColumnaMesa) click.getIntersectedNode();
                indice = -1;
                indiceColumnaSeleccionada = vcm.getIndice();
            }
            if (vistaCarta != null) {
                indice = vistaCarta.getIndice();
                indiceColumnaSeleccionada = vistaCarta.getIndiceColumna();
            }

            if ((!hayCartaColumnaMesa) && (indice != -1)) {
                segmentoSeleccionado = modelo.seleccionarCartas(indiceColumnaSeleccionada, indice);
                indiceColumnaMesa = indiceColumnaSeleccionada;
                if (segmentoSeleccionado != null ) {
                    vista.seleccionarCartas(indice, indiceColumnaSeleccionada);
                    hayCartaColumnaMesa = true;
                }
            } else if (hayCartaColumnaMesa) {
                modelo.moverCartas(segmentoSeleccionado, indiceColumnaMesa, indiceColumnaSeleccionada);
                vista.actualizarColumnaMesa(indiceColumnaMesa);
                vista.actualizarColumnaMesa(indiceColumnaSeleccionada);
                vista.deseleccionarCartas(indiceColumnaMesa);
                hayCartaColumnaMesa = false;
                for (int i = 0; i < 8; i++) {
                    vista.actualizarColumnaFinal(i);
                }
                if (modelo.estaGanado()) {
                    VistaVictoria vi = new VistaVictoria(vista.getStage());
                }
            }
        });

    }
}
