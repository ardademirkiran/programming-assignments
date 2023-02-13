
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    String text;
    Button button;
    Stage window;
    public static void main(String[] args) throws IOException {
        ReadData.dataReader();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        stage.setTitle(ReadData.title);
        stage.getIcons().add(new Image(("file:assets/icons/logo.png")));
        Scene scene = Session.loginScreen(stage);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {
            try {
                WriteBackup.writeBackupFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Platform.exit();
            System.exit(0);
        });

    }


}
