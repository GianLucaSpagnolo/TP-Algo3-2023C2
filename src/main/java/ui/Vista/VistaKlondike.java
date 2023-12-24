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
    private HBox sectorMazos;
    private HBox sectorColumnasFinales;
    private HBox sectorColumnasMesa;

    private final int dimension;

    private int columnasFinalesX;
    private int columnasFinalesY;
    private int columnasMesaX;
    private int columnasMesaY;
    private int mazosX;
    private int mazosY;


    public VistaKlondike(Solitario modelo, int tamanio) {
        //escena principal
        this.modelo = modelo;

        Mesa mesa = modelo.getEstadoMesa();

        this.dimension = tamanio;
        VistaPrincipal base = new VistaPrincipal(mesa, dimension);
        this.stage = base.getStage();
        Pane ventana = base.getVentana();

        if (tamanio == 1)
            dibujarklondikePequenio(ventana, mesa);
        else if (tamanio == 2)
            dibujarklondikeGrande(ventana, mesa);

        stage.show();
    }

    private void dibujarklondikePequenio(Pane ventana, Mesa mesa) {
        // Tamaños para Klondike pequeño
        columnasFinalesX = 221;
        columnasFinalesY = 50;
        columnasMesaX = 50;
        columnasMesaY = 120;
        mazosX = 50;
        mazosY = 50;

        //sectorMazos
        VistaMazo vistaMazo = new VistaMazo(mesa.getBaraja(), dimension);
        VistaMazo vistaMazoDescarte = new VistaMazo(mesa.getBarajaDescarte(), dimension);
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
            VistaColumnaFinal vcf = new VistaColumnaFinal(mesa.columnaFinalEnPosicion(i), i, dimension);
            Canvas rectangulo = dibujarRectanguloPequenio(columnasFinalesX, columnasFinalesY, i);
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
            VistaColumnaMesa vcm = new VistaColumnaMesa(mesa.columnaMesaEnPosicion(i), i, dimension);
            Canvas rectangulo = dibujarRectanguloPequenio(columnasMesaX, columnasMesaY + 15, i);
            ventana.getChildren().add(rectangulo);
            sectorColumnasMesa.getChildren().add(vcm);
        }
        sectorColumnasMesa.setLayoutX(columnasMesaX);
        sectorColumnasMesa.setLayoutY(columnasMesaY);
        this.sectorColumnasMesa = sectorColumnasMesa;
        ventana.getChildren().add(sectorColumnasMesa);
    }

    private void dibujarklondikeGrande(Pane ventana, Mesa mesa) {
        // Tamaños para klondike grande
        columnasFinalesX = 333;
        columnasFinalesY = 60;
        columnasMesaX = 75;
        columnasMesaY = 190;
        mazosX = 75;
        mazosY = 60;

        //sectorMazos
        VistaMazo vistaMazo = new VistaMazo(mesa.getBaraja(), dimension);
        VistaMazo vistaMazoDescarte = new VistaMazo(mesa.getBarajaDescarte(), dimension);
        HBox sectorMazos = new HBox(20);

        Canvas circulo = new Canvas(71,94);
        GraphicsContext gc1 = circulo.getGraphicsContext2D();
        gc1.setFill(Color.LIGHTGREEN);
        gc1.fillOval(2,2,67,90);
        gc1.setFill(Color.GREEN);
        gc1.fillOval(6,6,59,83);
        circulo.setLayoutX(mazosX);
        circulo.setLayoutY(mazosY);
        ventana.getChildren().add(circulo);

        sectorMazos.getChildren().addAll(vistaMazo, vistaMazoDescarte);
        sectorMazos.setLayoutX(mazosX);
        sectorMazos.setLayoutY(mazosY);
        this.sectorMazos = sectorMazos;
        ventana.getChildren().add(sectorMazos);

        //SectorColumnasFinales
        HBox sectorColumnasFinales = new HBox(15);
        for (int i=0; i < 4;i++) {
            VistaColumnaFinal vcf = new VistaColumnaFinal(mesa.columnaFinalEnPosicion(i), i, dimension);
            Canvas rectangulo = dibujarRectanguloGrande(columnasFinalesX, columnasFinalesY, i);
            ventana.getChildren().add(rectangulo);
            sectorColumnasFinales.getChildren().add(vcf);
        }
        sectorColumnasFinales.setLayoutX(columnasFinalesX);
        sectorColumnasFinales.setLayoutY(columnasFinalesY);
        this.sectorColumnasFinales = sectorColumnasFinales;
        ventana.getChildren().add(sectorColumnasFinales);

        //SectorColumnasMesa
        HBox sectorColumnasMesa = new HBox(15);
        for (int i=0; i < 7; i++) {
            VistaColumnaMesa vcm = new VistaColumnaMesa(mesa.columnaMesaEnPosicion(i), i, dimension);
            Canvas rectangulo = dibujarRectanguloGrande(columnasMesaX, columnasMesaY + 25, i);
            ventana.getChildren().add(rectangulo);
            sectorColumnasMesa.getChildren().add(vcm);
        }
        sectorColumnasMesa.setLayoutX(columnasMesaX);
        sectorColumnasMesa.setLayoutY(columnasMesaY);
        this.sectorColumnasMesa = sectorColumnasMesa;
        ventana.getChildren().add(sectorColumnasMesa);
    }

    private Canvas dibujarRectanguloPequenio(int x, int y, int i) {
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

    private Canvas dibujarRectanguloGrande(int x, int y, int i) {
        Canvas rectangulo = new Canvas(71,94);
        GraphicsContext gc2 = rectangulo.getGraphicsContext2D();
        gc2.setFill(Color.LIGHTGREEN);
        gc2.fillRect(2,2,67,90);
        gc2.setFill(Color.GREEN);
        gc2.fillRect(6,6,59,83);
        rectangulo.setLayoutX(x + 71 * i + 15 * i);
        rectangulo.setLayoutY(y);
        return rectangulo;
    }


    public Stage getStage() {return stage;}

    public void registrarSacarDelMazo(EventHandler<MouseEvent> eventHandler) {
        StackPane mazo = (StackPane) sectorMazos.getChildren().get(0);
        mazo.setOnMouseClicked(eventHandler);
    }

    public void actualizarMazos() {
        Mesa mesa = modelo.getEstadoMesa();
        ((VistaMazo) sectorMazos.getChildren().get(0)).actualizarMazo(mesa.getBaraja(), dimension);
        ((VistaMazo) sectorMazos.getChildren().get(1)).actualizarMazo(mesa.getBarajaDescarte(), dimension);
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
        ((VistaColumnaMesa)sectorColumnasMesa.getChildren().get(indice)).actualizar(mesa.columnaMesaEnPosicion(indice), dimension);
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
        ((VistaColumnaFinal)sectorColumnasFinales.getChildren().get(indice)).actualizar(mesa.columnaFinalEnPosicion(indice), dimension);
    }

}
