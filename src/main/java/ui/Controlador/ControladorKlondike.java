package ui.Controlador;

import Reglas.Klondike;
import Solitario.Columna;
import javafx.scene.input.PickResult;
import ui.Vista.*;

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

        vista.registrarSacarDelMazo(mouseEvent -> {
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
            } else if (hayCartaColumnaFinal) {
                hayCartaColumnaFinal = false;
                vista.deseleccionarCartaColumnaFinal(indiceColumnaFinal);
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

            if ((!hayCartaColumnaMesa) && (!hayCartaDescarte) && (!hayCartaColumnaFinal) && (indice != -1)) {
                segmentoSeleccionado = modelo.seleccionarCartas(indiceColumnaSeleccionada, indice);
                indiceColumnaMesa = indiceColumnaSeleccionada;
                if (segmentoSeleccionado != null ) {
                    vista.seleccionarCartas(indice, indiceColumnaSeleccionada);
                    hayCartaColumnaMesa = true;
                }
            } else if ((hayCartaColumnaMesa) && (!hayCartaDescarte) && ((indice != -1) || ((segmentoSeleccionado.getCartas().get(segmentoSeleccionado.size() - 1).getNumero() == 13) && (indice == -1))) && ((vistaCarta == null) || (vistaCarta.getCarta().esVisible()))) {
                modelo.moverCartas(segmentoSeleccionado, indiceColumnaMesa, indiceColumnaSeleccionada);
                vista.actualizarColumnaMesa(indiceColumnaMesa);
                vista.actualizarColumnaMesa(indiceColumnaSeleccionada);
                vista.deseleccionarCartas(indiceColumnaMesa);
                hayCartaColumnaMesa = false;
            } else if (hayCartaDescarte && ((indice != -1) || ((modelo.getEstadoMesa().getBarajaDescarte().peek().getNumero() == 13) && (indice == -1))) && ((vistaCarta == null) || (vistaCarta.getCarta().esVisible()))) {
                modelo.moverCartaDescarteAColumnaMesa(indiceColumnaSeleccionada);
                vista.deseleccionarCartaDescarte();
                vista.actualizarMazos();
                vista.actualizarColumnaMesa(indiceColumnaSeleccionada);
                hayCartaDescarte = false;
            } else if (hayCartaColumnaFinal && ((indice != -1) || ((modelo.getEstadoMesa().columnaFinalEnPosicion(indiceColumnaFinal).peek().getNumero() == 13) && (indice == -1))) && ((vistaCarta == null) || (vistaCarta.getCarta().esVisible()))) {
                hayCartaColumnaFinal = false;
                modelo.moverCartaColumnaFinalAColumnaMesa(indiceColumnaFinal, indiceColumnaSeleccionada);
                vista.deseleccionarCartaColumnaFinal(indiceColumnaFinal);
                vista.actualizarColumnaMesa(indiceColumnaSeleccionada);
                vista.actualizarColumnaFinal(indiceColumnaFinal);
            }
        });

        vista.registrarClickEnBarajaDescarte(mouseEvent -> {
            if ((!hayCartaDescarte) && (!hayCartaColumnaMesa) && (!hayCartaColumnaFinal)) {
                hayCartaDescarte = true;
                vista.seleccionarCartaDescarte();
            } else if (hayCartaColumnaMesa) {
                hayCartaColumnaMesa = false;
                modelo.moverCartas(segmentoSeleccionado, indiceColumnaMesa, -1);
                vista.deseleccionarCartas(indiceColumnaMesa);
                vista.actualizarColumnaMesa(indiceColumnaMesa);
            } else if (hayCartaColumnaFinal){
                hayCartaColumnaFinal = false;
                vista.deseleccionarCartaColumnaFinal(indiceColumnaFinal);
            } else {
                hayCartaDescarte = false;
                vista.deseleccionarCartaDescarte();
            }
        });

        vista.registrarClickEnColumnaFinal(mouseEvent -> {
            int indiceColumnaSeleccionada = 0;
            boolean estaVacia = false;
            VistaCarta vistaCarta = null;
            PickResult click = mouseEvent.getPickResult();
            try {
                vistaCarta = (VistaCarta) click.getIntersectedNode();
            } catch (ClassCastException e) {
                VistaColumnaFinal vcf = (VistaColumnaFinal) click.getIntersectedNode();
                indiceColumnaSeleccionada = vcf.getIndice();
                estaVacia = true;
            }
            if (vistaCarta != null) {
                indiceColumnaSeleccionada = vistaCarta.getIndiceColumna();
            }
            if ((!hayCartaColumnaMesa) && (!hayCartaDescarte) && (!hayCartaColumnaFinal) && (!estaVacia)) {
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
                if (modelo.estaGanado()) {
                    VistaVictoria vi = new VistaVictoria(vista.getStage());
                }
            } else if (hayCartaColumnaMesa) {
                hayCartaColumnaMesa = false;
                modelo.moverCartaColumnaFinal(segmentoSeleccionado, indiceColumnaMesa, indiceColumnaSeleccionada);
                vista.actualizarColumnaFinal(indiceColumnaSeleccionada);
                vista.actualizarColumnaMesa(indiceColumnaMesa);
                if (modelo.estaGanado()) {
                    VistaVictoria vi = new VistaVictoria(vista.getStage());
                }
            }
        });

    }

}
