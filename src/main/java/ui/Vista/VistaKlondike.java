package ui.Vista;

import Reglas.Solitario;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Solitario.*;
import ui.Vista.VistaMazo;


public class VistaKlondike implements VistaJuego {
    private Pane ventana;
    private Solitario modelo;
    private HBox sectorMazos;
    private HBox sectorColumnasFinales;
    private HBox sectorColumnasMesa;

    public VistaKlondike(Solitario modelo) {
        //escena principal
        this.modelo = modelo;
        VistaPrincipal base = new VistaPrincipal();
        Stage stage = base.getStage();
        this.ventana = base.getVentana();

        Mesa mesa = modelo.getEstadoMesa();

        //sectorMazos
        VistaMazo vistaMazo = new VistaMazo(mesa.getBaraja());
        VistaMazo vistaMazoDescarte = new VistaMazo(mesa.getBarajaDescarte());
        HBox sectorMazos = new HBox(20);
        Canvas c = new Canvas(71,94);
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGREEN);
        gc.fillOval(2,2,67,90);
        gc.setFill(Color.GREEN);
        gc.fillOval(6,6,59,83);
        sectorMazos.getChildren().add(c);
        sectorMazos.getChildren().addAll(vistaMazo, vistaMazoDescarte);
        sectorMazos.setLayoutX(40);
        sectorMazos.setLayoutY(80);
        this.sectorMazos = sectorMazos;
        ventana.getChildren().add(sectorMazos);

        //SectorColumnasFinales
        HBox sectorColumnasFinales = new HBox(20);
        for (int i=0; i < 4;i++) {
            VistaColumnaFinal vcf = new VistaColumnaFinal(mesa.columnaFinalEnPosicion(i));
            Canvas canvas = new Canvas(71,94);
            GraphicsContext graphicsContext = c.getGraphicsContext2D();
            graphicsContext.setFill(Color.LIGHTGREEN);
            graphicsContext.fillRect(2,2,67,90);
            graphicsContext.setFill(Color.GREEN);
            graphicsContext.fillRect(6,6,59,83);
            vcf.getChildren().add(canvas);
            sectorColumnasFinales.getChildren().add(vcf);
        }
        sectorColumnasFinales.setLayoutX(600);
        sectorColumnasFinales.setLayoutY(80);
        this.sectorColumnasFinales = sectorColumnasFinales;
        ventana.getChildren().add(sectorColumnasFinales);

        //SectorColumnasMesa
        HBox sectorColumnasMesa = new HBox(20);
        for (int i=0; i < 7; i++) {
            VistaColumnaMesa vcm = new VistaColumnaMesa(mesa.columnaMesaEnPosicion(i), i);
            sectorColumnasMesa.getChildren().add(vcm);
        }
        sectorColumnasMesa.setLayoutX(130);
        sectorColumnasMesa.setLayoutY(240);
        this.sectorColumnasMesa = sectorColumnasMesa;
        ventana.getChildren().add(sectorColumnasMesa);
        stage.show();
    }

    public void registrarSacarDelMazo(EventHandler<MouseEvent> eventHandler) {
        StackPane mazo = (StackPane) sectorMazos.getChildren().get(0);
        mazo.setOnMouseClicked(eventHandler);
    }

    public void actualizarMazos(Mazo mazo, Mazo mazoDescarte) {
        ((VistaMazo) sectorMazos.getChildren().get(0)).actualizarMazo(mazo);
        ((VistaMazo) sectorMazos.getChildren().get(1)).actualizarMazo(mazoDescarte);
    }

    public void registrarClickEnColumnaMesa(EventHandler<MouseEvent> eventHandler) {
        for (int i = 0; i < 7; i++) {
            VistaColumnaMesa vcm = (VistaColumnaMesa) sectorColumnasMesa.getChildren().get(i);
            vcm.setOnMouseClicked(eventHandler);
        }
    }

    public void seleccionarCartas(int indice, int indiceColumnaMesa) {
        VistaColumnaMesa vcm = (VistaColumnaMesa) sectorColumnasMesa.getChildren().get(indiceColumnaMesa);
        vcm.pintarCartas(indice);
    }

    public void deseleccionarCartas(int indiceColumnaMesa) {
        VistaColumnaMesa vcm = (VistaColumnaMesa) sectorColumnasMesa.getChildren().get(indiceColumnaMesa);
        vcm.despintarCartas();
    }

    public void actualizarColumnasMesa(Columna columna1, Columna columna2, int indice1, int indice2) {
        ((VistaColumnaMesa)sectorColumnasMesa.getChildren().get(indice1)).actualizarColumna(columna1);
        ((VistaColumnaMesa)sectorColumnasMesa.getChildren().get(indice2)).actualizarColumna(columna2);
    }



}
