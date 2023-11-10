import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent ventana = FXMLLoader.load((getClass().getResource("ventanaInicial.fxml")));
            Scene escena = new Scene(ventana);
            stage.setScene(escena);
            stage.setTitle("Solitario");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
