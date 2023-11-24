package ui.Controlador;

import Reglas.Klondike;
import Solitario.Carta;
import Solitario.Columna;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import ui.Vista.VistaCarta;
import ui.Vista.VistaColumnaFinal;
import ui.Vista.VistaColumnaMesa;
import ui.Vista.VistaKlondike;

public class ControladorKlondike {
    private final VistaKlondike vista;
    private final Klondike modelo;

    private Columna segmentoSeleccionado;
    private int indiceColumnaMesa;
    private int indiceColumnaFinal;
    private Boolean hayCartaDescarte = false;
    private Boolean hayCartaColumnaMesa = false;
    private Boolean hayCartaColumnaFinal = false;
    public ControladorKlondike(Klondike modelo, VistaKlondike vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void iniciar() {

        vista.registrarSacarDelMazo(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if ((!hayCartaDescarte) && (!hayCartaColumnaMesa) && (!hayCartaColumnaFinal)) {
                    modelo.sacarDelMazo();
                    vista.actualizarMazos();
                } else if (hayCartaColumnaMesa) {
                    hayCartaColumnaMesa = false;
                    modelo.moverCartas(segmentoSeleccionado, indiceColumnaMesa, -1);
                    vista.deseleccionarCartas(indiceColumnaMesa);
                    vista.actualizarColumnaMesa(indiceColumnaMesa);
                } else if (hayCartaDescarte) {
                    hayCartaDescarte = false;
                    vista.deseleccionarCartaDescarte();
                }
            }
        });

        vista.registrarClickEnColumnaMesa(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
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

                if ((!hayCartaColumnaMesa) && (!hayCartaDescarte) && (indice != -1)) {
                    segmentoSeleccionado = modelo.seleccionarCartas(indiceColumnaSeleccionada, indice);
                    indiceColumnaMesa = indiceColumnaSeleccionada;
                    if (segmentoSeleccionado != null ) {
                        vista.seleccionarCartas(indice, indiceColumnaSeleccionada);
                        hayCartaColumnaMesa = true;
                    }
                } else if ((hayCartaColumnaMesa) && (!hayCartaDescarte)) {
                    modelo.moverCartas(segmentoSeleccionado, indiceColumnaMesa, indiceColumnaSeleccionada);
                    vista.actualizarColumnaMesa(indiceColumnaMesa);
                    vista.actualizarColumnaMesa(indiceColumnaSeleccionada);
                    vista.deseleccionarCartas(indiceColumnaMesa);
                    hayCartaColumnaMesa = false;
                } else if (hayCartaDescarte) {
                    modelo.moverCartaDescarteAColumnaMesa(indiceColumnaSeleccionada);
                    vista.deseleccionarCartaDescarte();
                    vista.actualizarMazos();
                    vista.actualizarColumnaMesa(indiceColumnaSeleccionada);
                    hayCartaDescarte = false;
                }
            }
        });

        vista.registrarClickEnBarajaDescarte(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if ((!hayCartaDescarte) && (!hayCartaColumnaMesa) && (!hayCartaColumnaFinal)) {
                    hayCartaDescarte = true;
                    vista.seleccionarCartaDescarte();
                } else if (hayCartaColumnaMesa) {
                    hayCartaColumnaMesa = false;
                    modelo.moverCartas(segmentoSeleccionado, indiceColumnaMesa, -1);
                    vista.deseleccionarCartas(indiceColumnaMesa);
                    vista.actualizarColumnaMesa(indiceColumnaMesa);
                } else {
                    hayCartaDescarte = false;
                    vista.deseleccionarCartaDescarte();
                }
            }
        });

        vista.registrarClickEnColumnaFinal(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {;
                int indiceColumnaSeleccionada = 0;
                VistaCarta vistaCarta = null;
                PickResult click = mouseEvent.getPickResult();
                try {
                    vistaCarta = (VistaCarta) click.getIntersectedNode();
                } catch (ClassCastException e) {
                    VistaColumnaFinal vcf = (VistaColumnaFinal) click.getIntersectedNode();
                    indiceColumnaSeleccionada = vcf.getIndice();
                }
                if (vistaCarta != null) {
                    indiceColumnaSeleccionada = vistaCarta.getIndiceColumna();
                }
                if ((!hayCartaColumnaMesa) && (!hayCartaDescarte) && (!hayCartaColumnaFinal)) {
                    indiceColumnaFinal = indiceColumnaSeleccionada;
                    hayCartaColumnaFinal = true;
                    vista.seleccionarCartaColumnaFinal(indiceColumnaFinal);
                } else if (hayCartaColumnaFinal) {
                    hayCartaColumnaFinal = false;
                    modelo.moverEntreColumnasFinales(indiceColumnaFinal, indiceColumnaSeleccionada);
                    vista.deseleccionarCartaColumnaFinal(indiceColumnaFinal);
                    vista.actualizarColumnaFinal(indiceColumnaSeleccionada);
                    vista.actualizarColumnaFinal(indiceColumnaFinal);
                } else if (hayCartaDescarte) {
                    hayCartaDescarte = false;
                    modelo.moverCartaDescarteAColumnaFinal(indiceColumnaSeleccionada);
                    vista.deseleccionarCartaDescarte();
                    vista.actualizarColumnaFinal(indiceColumnaSeleccionada);
                    vista.actualizarMazos();
                } else if (hayCartaColumnaMesa) {
                    hayCartaColumnaMesa = false;
                    modelo.moverCartaColumnaFinal(segmentoSeleccionado, indiceColumnaMesa, indiceColumnaSeleccionada);
                    vista.actualizarColumnaFinal(indiceColumnaSeleccionada);
                    vista.actualizarColumnaMesa(indiceColumnaMesa);
                }
            }
        });

    }

}
