package ui.Vista;

import Solitario.Mesa;
import Main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class VistaPrincipal {
    @FXML
    private ChoiceBox<String> opciones;
    @FXML
    private ChoiceBox<String> tamanio;

    private final String[] tamaniosString = {"  Pequeño     ", "   Grande"};
    private final Stage stage;
    private static Stage stageVentanaSeleccion;
    private static Stage stageVentanaFinal;
    private static int contadorVentanaSeleccion = 0;

    private Pane ventana;
    private final Integer variante;

    private final Mesa estado;

    private final double[] medidasAnchoPequenio = {503, 635};
    private final double[] medidasAnchoGrande = {753, 953};
    private final double[] medidasAltoPequenio = {570, 630};
    private final double[] medidasAltoGrande = {850, 975};

    public VistaPrincipal(Mesa estadoJuego, int tamanioPantalla) {
        stage = new Stage();
        this.estado = estadoJuego;
        this.variante = estadoJuego.getTipoMesa();
        var loader = new FXMLLoader(getClass().getResource("/ventanaPrincipal.fxml"));
        loader.setController(this);

        boolean error = false;
        try {
            ventana = loader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            error = true;
            stage.close();
        }

        if (!error) {
            Scene escena = new Scene(ventana);
            stage.setScene(escena);
            setMedidas(tamanioPantalla);
            stage.setResizable(false);
            stage.setTitle("Solitario " + NombresVariantes.getListaVariantes()[variante]);
            opciones.getItems().add("Nuevo Juego");
            opciones.setOnAction(event -> {
                opciones.getItems().remove(0);
                opciones.getItems().add("Nuevo Juego");
                if (contadorVentanaSeleccion == 1) {
                    if (stageVentanaSeleccion != null)
                        stageVentanaSeleccion.close();
                    VistaInicial vi = new VistaInicial(stage);
                }
                else if (contadorVentanaSeleccion == 0) {
                    contadorVentanaSeleccion++;
                    if (stageVentanaFinal != null)
                        stageVentanaFinal.close();
                    VistaInicial vi = new VistaInicial(stage);
                }
            });

            for (String s : tamaniosString) tamanio.getItems().add(s);
            tamanio.setOnAction(this::cambiarTamanio);

            stage.setOnCloseRequest(windowEvent -> {
                if (stageVentanaFinal != null)
                    stageVentanaFinal.close();
                if (contadorVentanaSeleccion == 1)
                    stageVentanaSeleccion.close();
            });
        }
    }

    public void cambiarTamanio(ActionEvent actionEvent) {
        String stringElegido = tamanio.getValue();
        int tamanioElegido = 0;
        for (int i = 0; i < tamaniosString.length; i++) {
            if (tamaniosString[i].equals(stringElegido)) {
                tamanioElegido = i;
                break;
            }
        }

        stage.close();
        Main.nuevoTamanioJuego(tamanioElegido + 1, estado);
    }

    public Stage getStage() {
        return stage;
    }

    public Pane getVentana() {
        return ventana;
    }


    private void setMedidas(int tamanio) {
        if (tamanio == 1) {
            stage.setWidth(medidasAnchoPequenio[variante]);
            stage.setHeight(medidasAltoPequenio[variante]);
        } else if (tamanio == 2) {
            stage.setWidth(medidasAnchoGrande[variante]);
            stage.setHeight(medidasAltoGrande[variante]);
        }
    }

    public static void setStageVentanaSeleccion(Stage ventanaSeleccion) { stageVentanaSeleccion = ventanaSeleccion; }

    public static void actualizarContadorVentanaSeleccion() { contadorVentanaSeleccion = 0; }

    public static void setStageVentanaFinal(Stage ventanaFinal) { stageVentanaFinal = ventanaFinal; }
}
