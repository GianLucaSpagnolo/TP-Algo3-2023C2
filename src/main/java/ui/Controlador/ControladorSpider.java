package ui.Controlador;

import Reglas.Spider;
import Solitario.Columna;
import javafx.scene.input.PickResult;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ui.Vista.*;

public class ControladorSpider {
    private final VistaSpider vista;
    private final Spider modelo;

    private Columna segmentoSeleccionado;
    private int indiceColumnaMesa;
    private Boolean hayCartaColumnaMesa = false;
    private boolean hayJuegoGanado = false;

    public ControladorSpider(Spider modelo, VistaSpider vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    private void reproducirSonidoCarta() {
        Media sonidoCarta = new Media(getClass().getResource("/Sonidos/sonido_carta.mp3").toString());
        MediaPlayer reproducirSonidoCarta = new MediaPlayer(sonidoCarta);
        reproducirSonidoCarta.play();
    }

    private void reproducirSonidoInicial() {
        Media sonidoInicial = new Media(getClass().getResource("/Sonidos/sonido_comienzo.mp3").toString());
        MediaPlayer reproducirSonidoInicial = new MediaPlayer(sonidoInicial);
        reproducirSonidoInicial.play();
    }

    private void reproducirSonidoVictoria() {
        Media sonidoVictoria = new Media(getClass().getResource("/Sonidos/sonido_victoria.mp3").toString());
        MediaPlayer reproducirSonidoVictoria = new MediaPlayer(sonidoVictoria);
        reproducirSonidoVictoria.play();
    }

    public void iniciar() {

        reproducirSonidoInicial();

        vista.registrarSacarDelMazo(mouseEvent -> {
            if (!hayCartaColumnaMesa) {
                if (!modelo.getEstadoMesa().getBaraja().estaVacio())
                    reproducirSonidoCarta();
                modelo.sacarDelMazo();
                vista.actualizarMazo();
                for (int i = 0; i < 10; i++) {
                    vista.actualizarColumnaMesa(i);
                }
            } else {
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
            } else if (hayCartaColumnaMesa && ((indice != -1) || ((modelo.getEstadoMesa().columnaMesaEnPosicion(indiceColumnaSeleccionada).isEmpty()) && (indice == -1))) && ((vistaCarta == null) || (vistaCarta.getCarta().esVisible()))) {
                boolean movimiento = modelo.moverCartas(segmentoSeleccionado, indiceColumnaMesa, indiceColumnaSeleccionada);
                if (movimiento && (indiceColumnaMesa != indiceColumnaSeleccionada))
                    reproducirSonidoCarta();
                vista.actualizarColumnaMesa(indiceColumnaMesa);
                vista.actualizarColumnaMesa(indiceColumnaSeleccionada);
                vista.deseleccionarCartas(indiceColumnaMesa);
                hayCartaColumnaMesa = false;
                for (int i = 0; i < 8; i++) {
                    vista.actualizarColumnaFinal(i);
                }
                if (modelo.estaGanado() && !hayJuegoGanado) {
                    hayJuegoGanado = true;
                    reproducirSonidoVictoria();
                    VistaVictoria vi = new VistaVictoria(vista.getStage());
                }
            }
        });
    }
}
