package lk.ijse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/loginform.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Cafe");
        stage.setScene(scene);
         stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
