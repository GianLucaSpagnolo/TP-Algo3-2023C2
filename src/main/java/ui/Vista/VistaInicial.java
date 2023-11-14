package ui.Vista;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class VistaInicial implements Initializable{
    @FXML
    private static ChoiceBox<String> cajaOpciones;
    @FXML
    private static Button botonCancelar;

    public static void mostrarVentanaInicial(String[] variantes, Stage stage) throws IOException {
        Scene escena = FXMLLoader.load((VistaInicial.class.getResource("ventanaInicio.fxml")));
        stage.setScene(escena);
        stage.setTitle("Solitario");
        stage.setResizable(false);
        stage.show();
    }

    public void cambiarEscena(ActionEvent actionEvent) {
        String varianteElegida = cajaOpciones.getValue();
        Pane ventana = null;
        try {
            ventana = FXMLLoader.load((Objects.requireNonNull(getClass().getClassLoader().getResource("ventanaJuego.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.close();
        Scene scene = new Scene(ventana);
        stage.setScene(scene);
        stage.show();

        botonCancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("hola");
            }
        });
        iniciarJuego(varianteElegida, null);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cajaOpciones.getItems().addAll(variantes);
        cajaOpciones.setOnAction(this::cambiarEscena);
        botonCancelar.setOnAction(actionEvent -> stage.close());
    }

}
