package ui.Vista;

import Reglas.Solitario;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Solitario.*;

public class VistaSpider implements VistaJuego{
    private final Solitario modelo;
    private final Stage stage;
    private VistaMazo mazo;
    private HBox sectorColumnasFinales;
    private HBox sectorColumnasMesa;

    private final int dimension;

    private int columnasFinalesX;
    private int columnasFinalesY;
    private int columnasMesaX;
    private int columnasMesaY;
    private int mazoX;
    private int mazoY;

    public VistaSpider(Solitario modelo, int tamanio) {
        //escena principal
        this.modelo = modelo;

        Mesa mesa = modelo.getEstadoMesa();

        this.dimension = tamanio;
        VistaPrincipal base = new VistaPrincipal(mesa, dimension);
        this.stage = base.getStage();
        Pane ventana = base.getVentana();

        if (dimension == 1)
            dibujarSpiderPequenio(ventana, mesa);
        else if (dimension == 2)
            dibujarSpiderGrande(ventana, mesa);

        stage.show();
    }

    private void dibujarSpiderPequenio(Pane ventana, Mesa mesa) {
        // Tamaños para Spider pequeño
        columnasFinalesX = 30;
        columnasFinalesY = 500;
        columnasMesaX = 30;
        columnasMesaY = 30;
        mazoX = 544;
        mazoY = 500;

        //Mazo
        VistaMazo vistaMazo = new VistaMazo(mesa.getBaraja(), dimension);
        Canvas circulo = new Canvas(47,62);
        GraphicsContext gc1 = circulo.getGraphicsContext2D();
        gc1.setFill(javafx.scene.paint.Color.LIGHTGREEN);
        gc1.fillOval(2,2,43,58);
        gc1.setFill(Color.GREEN);
        gc1.fillOval(5.5,5.5,36,51);
        circulo.setLayoutX(mazoX);
        circulo.setLayoutY(mazoY);
        ventana.getChildren().add(circulo);
        this.mazo = vistaMazo;
        vistaMazo.setLayoutX(mazoX);
        vistaMazo.setLayoutY(mazoY);
        ventana.getChildren().add(vistaMazo);

        //SectorColumnasFinales
        HBox sectorColumnasFinales = new HBox(10);
        for (int i=0; i < 8;i++) {
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
        for (int i=0; i < 10; i++) {
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

    private void dibujarSpiderGrande(Pane ventana, Mesa mesa) {
        // Tamaños para Spider grande
        columnasFinalesX = 45;
        columnasFinalesY = 800;
        columnasMesaX = 45;
        columnasMesaY = 40;
        mazoX = 820;
        mazoY = 800;

        //Mazo
        VistaMazo vistaMazo = new VistaMazo(mesa.getBaraja(), dimension);
        Canvas circulo = new Canvas(71,94);
        GraphicsContext gc1 = circulo.getGraphicsContext2D();
        gc1.setFill(javafx.scene.paint.Color.LIGHTGREEN);
        gc1.fillOval(2,2,67,90);
        gc1.setFill(Color.GREEN);
        gc1.fillOval(6,6,59,83);
        circulo.setLayoutX(mazoX);
        circulo.setLayoutY(mazoY);
        ventana.getChildren().add(circulo);
        this.mazo = vistaMazo;
        vistaMazo.setLayoutX(mazoX);
        vistaMazo.setLayoutY(mazoY);
        ventana.getChildren().add(vistaMazo);

        //SectorColumnasFinales
        HBox sectorColumnasFinales = new HBox(15);
        for (int i=0; i < 8;i++) {
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
        for (int i=0; i < 10; i++) {
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

    public Stage getStage() {
        return stage;
    }

    public void registrarSacarDelMazo(EventHandler<MouseEvent> eventHandler) {
        mazo.setOnMouseClicked(eventHandler);
    }

    public void actualizarMazo() {
        Mesa mesa = modelo.getEstadoMesa();
        mazo.actualizarMazo(mesa.getBaraja(), dimension);
    }

    public void registrarClickEnColumnaMesa(EventHandler<MouseEvent> eventHandler) {
        for (int i = 0; i < 10; i++) {
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

    public void actualizarColumnaMesa(int indice) {
        Mesa mesa = modelo.getEstadoMesa();
        ((VistaColumnaMesa)sectorColumnasMesa.getChildren().get(indice)).actualizar(mesa.columnaMesaEnPosicion(indice), dimension);
    }

    public void actualizarColumnaFinal(int indice) {
        Mesa mesa = modelo.getEstadoMesa();
        ((VistaColumnaFinal)sectorColumnasFinales.getChildren().get(indice)).actualizar(mesa.columnaFinalEnPosicion(indice), dimension);
    }
}
