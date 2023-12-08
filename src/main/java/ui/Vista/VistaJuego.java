package ui.Vista;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public interface VistaJuego {
    Stage getStage();

    void registrarSacarDelMazo(EventHandler<MouseEvent> eventHandler);

    void registrarClickEnColumnaMesa(EventHandler<MouseEvent> eventHandler);

    void seleccionarCartas(int indice, int indiceColumnaMesa);

    void deseleccionarCartas(int indiceColumnaMesa);

    void actualizarColumnaMesa(int indice);
}
