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
    private final VistaMazo mazo;
    private final HBox sectorColumnasFinales;
    private final HBox sectorColumnasMesa;

    private final int columnasFinalesX = 30;
    private final int columnasFinalesY = 500;
    private final int columnasMesaX = 30;
    private final int columnasMesaY = 30;
    private final int mazoX = 544;
    private final int mazoY = 500;

    public VistaSpider(Solitario modelo, int tamanio) {
        //escena principal
        this.modelo = modelo;

        Mesa mesa = modelo.getEstadoMesa();

        VistaPrincipal base = new VistaPrincipal(mesa, tamanio);
        this.stage = base.getStage();
        Pane ventana = base.getVentana();

        //mazo
        VistaMazo vistaMazo = new VistaMazo(mesa.getBaraja());
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
        for (int i=0; i < 10; i++) {
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

    public Stage getStage() {
        return stage;
    }

    public void registrarSacarDelMazo(EventHandler<MouseEvent> eventHandler) {
        mazo.setOnMouseClicked(eventHandler);
    }

    public void actualizarMazo() {
        Mesa mesa = modelo.getEstadoMesa();
        mazo.actualizarMazo(mesa.getBaraja());
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
        ((VistaColumnaMesa)sectorColumnasMesa.getChildren().get(indice)).actualizar(mesa.columnaMesaEnPosicion(indice));
    }

    public void actualizarColumnaFinal(int indice) {
        Mesa mesa = modelo.getEstadoMesa();
        ((VistaColumnaFinal)sectorColumnasFinales.getChildren().get(indice)).actualizar(mesa.columnaFinalEnPosicion(indice));
    }

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
}
