package ui.Vista;

import Reglas.Solitario;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Solitario.*;


public class VistaKlondike implements VistaJuego {
    private final Solitario modelo;
    private final Stage stage;
    private final HBox sectorMazos;
    private final HBox sectorColumnasFinales;
    private final HBox sectorColumnasMesa;

    private final int columnasFinalesX = 221;
    private final int columnasFinalesY = 50;
    private final int columnasMesaX = 50;
    private final int columnasMesaY = 120;
    private final int mazosX = 50;
    private final int mazosY = 50;


    public VistaKlondike(Solitario modelo) {
        //escena principal
        this.modelo = modelo;

        Mesa mesa = modelo.getEstadoMesa();

        VistaPrincipal base = new VistaPrincipal(mesa.getTipoMesa());
        this.stage = base.getStage();
        Pane ventana = base.getVentana();


        //sectorMazos
        VistaMazo vistaMazo = new VistaMazo(mesa.getBaraja());
        VistaMazo vistaMazoDescarte = new VistaMazo(mesa.getBarajaDescarte());
        HBox sectorMazos = new HBox(10);

        Canvas circulo = new Canvas(47,62);
        GraphicsContext gc1 = circulo.getGraphicsContext2D();
        gc1.setFill(Color.LIGHTGREEN);
        gc1.fillOval(2,2,43,58);
        gc1.setFill(Color.GREEN);
        gc1.fillOval(5.5,5.5,36,51);
        circulo.setLayoutX(mazosX);
        circulo.setLayoutY(mazosY);
        ventana.getChildren().add(circulo);

        sectorMazos.getChildren().addAll(vistaMazo, vistaMazoDescarte);
        sectorMazos.setLayoutX(mazosX);
        sectorMazos.setLayoutY(mazosY);
        this.sectorMazos = sectorMazos;
        ventana.getChildren().add(sectorMazos);

        //SectorColumnasFinales
        HBox sectorColumnasFinales = new HBox(10);
        for (int i=0; i < 4;i++) {
            VistaColumnaFinal vcf = new VistaColumnaFinal(mesa.columnaFinalEnPosicion(i), i);
            Canvas rectangulo = dibujarRectangulo(columnasFinalesX, columnasFinalesY, i);
            ventana.getChildren().add(rectangulo);
            sectorColumnasFinales.getChildren().add(vcf);
        }
        sectorColumnasFinales.setLayoutX(columnasFinalesX);
        sectorColumnasFinales.setLayoutY(columnasFinalesY);
        this.sectorColumnasFinales = sectorColumnasFinales;
        ventana.getChildren().add(sectorColumnasFinales);

        //SectorColumnasMesa
        HBox sectorColumnasMesa = new HBox(10);
        for (int i=0; i < 7; i++) {
            VistaColumnaMesa vcm = new VistaColumnaMesa(mesa.columnaMesaEnPosicion(i), i);
            Canvas rectangulo = dibujarRectangulo(columnasMesaX, columnasMesaY + 15, i);
            ventana.getChildren().add(rectangulo);
            sectorColumnasMesa.getChildren().add(vcm);
        }
        sectorColumnasMesa.setLayoutX(columnasMesaX);
        sectorColumnasMesa.setLayoutY(columnasMesaY);
        this.sectorColumnasMesa = sectorColumnasMesa;
        ventana.getChildren().add(sectorColumnasMesa);
        stage.show();
    }

    public Stage getStage() {return stage;}

    private Canvas dibujarRectangulo(int x, int y, int i) {
        Canvas rectangulo = new Canvas(47,62);
        GraphicsContext gc2 = rectangulo.getGraphicsContext2D();
        gc2.setFill(Color.LIGHTGREEN);
        gc2.fillRect(2,2,43,58);
        gc2.setFill(Color.GREEN);
        gc2.fillRect(5.5,5.5,36,51);
        rectangulo.setLayoutX(x + 47 * i + 10 * i);
        rectangulo.setLayoutY(y);
        return rectangulo;
    }


    public void registrarSacarDelMazo(EventHandler<MouseEvent> eventHandler) {
        StackPane mazo = (StackPane) sectorMazos.getChildren().get(0);
        mazo.setOnMouseClicked(eventHandler);
    }

    public void actualizarMazos() {
        Mesa mesa = modelo.getEstadoMesa();
        ((VistaMazo) sectorMazos.getChildren().get(0)).actualizarMazo(mesa.getBaraja());
        ((VistaMazo) sectorMazos.getChildren().get(1)).actualizarMazo(mesa.getBarajaDescarte());
    }

    public void registrarClickEnColumnaMesa(EventHandler<MouseEvent> eventHandler) {
        for (int i = 0; i < 7; i++) {
            VistaColumnaMesa vcm = (VistaColumnaMesa) sectorColumnasMesa.getChildren().get(i);
            vcm.setOnMouseClicked(eventHandler);
        }
    }

    public void seleccionarCartas(int indice, int indiceColumnaMesa) {
        VistaColumnaMesa vcm = (VistaColumnaMesa) sectorColumnasMesa.getChildren().get(indiceColumnaMesa);
        vcm.pintar(indice);
    }

    public void deseleccionarCartas(int indiceColumnaMesa) {
        VistaColumnaMesa vcm = (VistaColumnaMesa) sectorColumnasMesa.getChildren().get(indiceColumnaMesa);
        vcm.despintar();
    }

    public void seleccionarCartaColumnaFinal(int indiceColumnaFinal) {
        VistaColumnaFinal vcf = (VistaColumnaFinal) sectorColumnasFinales.getChildren().get(indiceColumnaFinal);
        vcf.pintar(1);
    }

    public void deseleccionarCartaColumnaFinal(int indiceColumnaFinal) {
        VistaColumnaFinal vcf = (VistaColumnaFinal) sectorColumnasFinales.getChildren().get(indiceColumnaFinal);
        vcf.despintar();
    }

    public void actualizarColumnaMesa(int indice) {
        Mesa mesa = modelo.getEstadoMesa();
        ((VistaColumnaMesa)sectorColumnasMesa.getChildren().get(indice)).actualizar(mesa.columnaMesaEnPosicion(indice));
    }

    public void registrarClickEnBarajaDescarte(EventHandler<MouseEvent> eventHandler) {
        StackPane mazoDescarte = (StackPane) sectorMazos.getChildren().get(1);
        mazoDescarte.setOnMouseClicked(eventHandler);
    }

    public void seleccionarCartaDescarte() {
        ((VistaMazo) sectorMazos.getChildren().get(1)).pintarCartaDescarte();
    }

    public void deseleccionarCartaDescarte() {
        ((VistaMazo) sectorMazos.getChildren().get(1)).despintarCartaDescarte();
    }

    public void registrarClickEnColumnaFinal(EventHandler<MouseEvent> eventHandler) {
        for (int i = 0; i < 4; i++) {
            VistaColumnaFinal vcf = (VistaColumnaFinal) sectorColumnasFinales.getChildren().get(i);
            vcf.setOnMouseClicked(eventHandler);
        }
    }

    public void actualizarColumnaFinal(int indice) {
        Mesa mesa = modelo.getEstadoMesa();
        ((VistaColumnaFinal)sectorColumnasFinales.getChildren().get(indice)).actualizar(mesa.columnaFinalEnPosicion(indice));
    }

}
