import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application{

    Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Online payment system");
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getResource("view/LoginView.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root, 600,400);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
