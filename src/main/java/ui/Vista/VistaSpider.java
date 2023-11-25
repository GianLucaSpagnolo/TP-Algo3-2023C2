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

    private final int columnasFinalesX = 45;
    private final int columnasFinalesY = 800;
    private final int columnasMesaX = 45;
    private final int columnasMesaY = 50;
    private final int mazoX = 865;
    private final int mazoY = 800;

    public VistaSpider(Solitario modelo) {
        //escena principal
        this.modelo = modelo;

        Mesa mesa = modelo.getEstadoMesa();

        VistaPrincipal base = new VistaPrincipal(mesa.getTipoMesa());
        this.stage = base.getStage();
        Pane ventana = base.getVentana();

        //mazo
        VistaMazo vistaMazo = new VistaMazo(mesa.getBaraja());
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
        HBox sectorColumnasFinales = new HBox(20);
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
        HBox sectorColumnasMesa = new HBox(20);
        for (int i=0; i < 10; i++) {
            VistaColumnaMesa vcm = new VistaColumnaMesa(mesa.columnaMesaEnPosicion(i), i);
            Canvas rectangulo = dibujarRectangulo(columnasMesaX, columnasMesaY + 23, i);
            ventana.getChildren().add(rectangulo);
            sectorColumnasMesa.getChildren().add(vcm);
        }
        sectorColumnasMesa.setLayoutX(columnasMesaX);
        sectorColumnasMesa.setLayoutY(columnasMesaY);
        this.sectorColumnasMesa = sectorColumnasMesa;
        ventana.getChildren().add(sectorColumnasMesa);

        stage.show();
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void registrarSacarDelMazo(EventHandler<MouseEvent> eventHandler) {
        mazo.setOnMouseClicked(eventHandler);
    }

    public void actualizarMazo() {
        Mesa mesa = modelo.getEstadoMesa();
        mazo.actualizarMazo(mesa.getBaraja());
    }

    @Override
    public void registrarClickEnColumnaMesa(EventHandler<MouseEvent> eventHandler) {
        for (int i = 0; i < 10; i++) {
            VistaColumnaMesa vcm = (VistaColumnaMesa) sectorColumnasMesa.getChildren().get(i);
            vcm.setOnMouseClicked(eventHandler);
        }
    }

    @Override
    public void seleccionarCartas(int indice, int indiceColumnaMesa) {
        VistaColumnaMesa vcm = (VistaColumnaMesa) sectorColumnasMesa.getChildren().get(indiceColumnaMesa);
        vcm.pintar(indice);
    }

    @Override
    public void deseleccionarCartas(int indiceColumnaMesa) {
        VistaColumnaMesa vcm = (VistaColumnaMesa) sectorColumnasMesa.getChildren().get(indiceColumnaMesa);
        vcm.despintar();
    }

    @Override
    public void actualizarColumnaMesa(int indice) {
        Mesa mesa = modelo.getEstadoMesa();
        ((VistaColumnaMesa)sectorColumnasMesa.getChildren().get(indice)).actualizar(mesa.columnaMesaEnPosicion(indice));
    }

    public void actualizarColumnaFinal(int indice) {
        Mesa mesa = modelo.getEstadoMesa();
        ((VistaColumnaFinal)sectorColumnasFinales.getChildren().get(indice)).actualizar(mesa.columnaFinalEnPosicion(indice));
    }

    private Canvas dibujarRectangulo(int x, int y, int i) {
        Canvas rectangulo = new Canvas(71,94);
        GraphicsContext gc2 = rectangulo.getGraphicsContext2D();
        gc2.setFill(Color.LIGHTGREEN);
        gc2.fillRect(2,2,67,90);
        gc2.setFill(Color.GREEN);
        gc2.fillRect(6,6,59,83);
        rectangulo.setLayoutX(x + 71 * i + 20 * i);
        rectangulo.setLayoutY(y);
        return rectangulo;
    }
}
