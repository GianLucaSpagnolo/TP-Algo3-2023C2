package ui.Vista;

import Reglas.Solitario;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Solitario.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class VistaKlondike implements VistaJuego {
    private Stage stage;
    private Pane ventana;
    private Solitario modelo;

    public VistaKlondike(Solitario modelo) {
        //escena principal
        this.modelo = modelo;
        VistaPrincipal base = new VistaPrincipal();
        this.stage = base.getStage();
        this.ventana = base.getVentana();

        //a sacar
        modelo.sacarDelMazo();

        Mesa mesa = modelo.getEstadoMesa();

        //sectorMazos
        VistaMazo vistaMazo = new VistaMazo(mesa.getBaraja());
        VistaMazo vistaMazoDescarte = new VistaMazo(mesa.getBarajaDescarte());
        HBox sectorMazos = new HBox(20);
        sectorMazos.getChildren().addAll(vistaMazo.getMazo(), vistaMazoDescarte.getMazo());
        sectorMazos.setLayoutX(40);
        sectorMazos.setLayoutY(80);
        ventana.getChildren().add(sectorMazos);

        //SectorColumnasFinales
        HBox sectorColumnasFinales = new HBox(20);
        for (int i=0; i < 4;i++) {

            //a sacar
            Carta carta = new Carta(i+1, Palos.PICAS);
            carta.darVuelta();
            mesa.columnaFinalEnPosicion(i).push(carta);

            VistaColumnaFinal vcf = new VistaColumnaFinal(mesa.columnaFinalEnPosicion(i));
            StackPane columna = vcf.getColumna();
            sectorColumnasFinales.getChildren().add(columna);
        }
        sectorColumnasFinales.setLayoutX(600);
        sectorColumnasFinales.setLayoutY(80);
        ventana.getChildren().add(sectorColumnasFinales);

        //SectorColumnasMesa
        HBox sectorColumnasMesa = new HBox(20);
        for (int i=0; i < 7; i++) {
            VistaColumnaMesa vcm = new VistaColumnaMesa(mesa.columnaMesaEnPosicion(i));
            VBox columna = vcm.getColumna();
            sectorColumnasMesa.getChildren().add(columna);
        }
        sectorColumnasMesa.setLayoutX(130);
        sectorColumnasMesa.setLayoutY(240);
        ventana.getChildren().add(sectorColumnasMesa);
        stage.show();
    }


}
