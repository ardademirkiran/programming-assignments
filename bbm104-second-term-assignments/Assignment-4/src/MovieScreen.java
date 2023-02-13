import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.nio.file.Paths;

public class MovieScreen {
    public static Hall hall;

    public static Scene adminMovieScreen(Stage stage){
        Media media = new Media(new File("assets/trailers/" + MovieSelection.movie.getTrailerPath()).toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setAutoPlay(true);
        MediaView view = new MediaView(player);
        Label topLabel = new Label(MovieSelection.movie.toString() + " (" + MovieSelection.movie.getDuration() + " minutes)");
        Button startStopButton = new Button("   ||   ");
        Button movieBackButton = new Button(" << ");
        Button movieForwardButton = new Button(" >> ");
        Button movieRewindButton = new Button(" |<<");
        Button goBackButton = new Button("BACK");
        Button addHallButton = new Button("Add Hall");
        Button removeHallButton = new Button("Remove Hall");
        Button okButton = new Button("OK");
        Slider voiceManager = new Slider();
        ChoiceBox<Hall> movieBox = new ChoiceBox<>(FXCollections.observableArrayList(MovieSelection.movie.getHalls()));
        movieBox.getSelectionModel().select(0);
        voiceManager.setMin(0);
        voiceManager.setMax(1);
        voiceManager.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                player.setVolume((double) newValue);
            }
        });
        voiceManager.setValue(1);
        voiceManager.setOrientation(Orientation.VERTICAL);
        HBox topLayout = new HBox();
        HBox bottomButtonLayout = new HBox(20);
        HBox centerLayout = new HBox(30);
        VBox centerButtonLayout = new VBox(20);
        movieBackButton.setOnAction(e -> player.seek(new Duration(player.getCurrentTime().toMillis() - 5000)));
        movieForwardButton.setOnAction(e -> player.seek(new Duration(player.getCurrentTime().toMillis() + 5000)));
        movieRewindButton.setOnAction(e -> player.seek(new Duration(0)));
        startStopButton.setOnAction(e -> {if(player.getStatus() == MediaPlayer.Status.PAUSED) {player.play();} else {player.pause();}});
        goBackButton.setOnAction(e -> {
            stage.setScene(MovieSelection.adminMovieSelectionScreen(stage));
            player.stop();
        });
        okButton.setOnAction(e -> {player.stop();
        hall = movieBox.getSelectionModel().getSelectedItem();
        stage.setScene(HallScreen.adminHallScreen(stage));});
        addHallButton.setOnAction(e -> {
            player.stop();
            stage.setScene(HallScreen.addHallScreen(stage));});
        removeHallButton.setOnAction(e -> {
            player.stop();
            stage.setScene(HallScreen.removeHallScreen(stage));
        });
        centerButtonLayout.getChildren().addAll(startStopButton, movieBackButton, movieForwardButton, movieRewindButton, voiceManager);
        centerLayout.getChildren().addAll(view, centerButtonLayout);
        bottomButtonLayout.getChildren().addAll(goBackButton, addHallButton, removeHallButton, movieBox, okButton);
        topLayout.getChildren().add(topLabel);
        VBox outerLayout = new VBox(30);
        outerLayout.getChildren().addAll(topLayout, centerLayout, bottomButtonLayout);
        centerButtonLayout.setAlignment(Pos.CENTER_RIGHT);
        centerLayout.setAlignment(Pos.CENTER);
        topLayout.setAlignment(Pos.TOP_CENTER);
        bottomButtonLayout.setAlignment(Pos.BOTTOM_CENTER);
        view.setFitHeight(500);
        view.setFitWidth(800);
        movieBox.setMinWidth(100);
        Scene scene = new Scene(outerLayout, 1000, 600);
        return scene;
    }

    public static Scene userMovieScreen(Stage stage) {
        Media media = new Media(Paths.get("assets/trailers/" + MovieSelection.movie.getTrailerPath()).toUri().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setAutoPlay(true);
        MediaView view = new MediaView(player);
        Label topLabel = new Label(MovieSelection.movie.toString() + " (" + MovieSelection.movie.getDuration() + " minutes)");
        Button startStopButton = new Button("   ||   ");
        Button movieBackButton = new Button(" << ");
        Button movieForwardButton = new Button(" >> ");
        Button movieRewindButton = new Button(" |<<");
        Button goBackButton = new Button("BACK");
        Button okButton = new Button("OK");
        Slider voiceManager = new Slider();
        ChoiceBox<Hall> movieBox = new ChoiceBox<>(FXCollections.observableArrayList(MovieSelection.movie.getHalls()));
        movieBox.getSelectionModel().select(0);
        voiceManager.setMin(0);
        voiceManager.setMax(1);
        voiceManager.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                player.setVolume((double) newValue);
            }
        });
        voiceManager.setValue(1);
        voiceManager.setOrientation(Orientation.VERTICAL);
        HBox topLayout = new HBox();
        HBox bottomButtonLayout = new HBox(20);
        HBox centerLayout = new HBox(30);
        VBox centerButtonLayout = new VBox(20);
        movieBackButton.setOnAction(e -> player.seek(new Duration(player.getCurrentTime().toMillis() - 5000)));
        movieForwardButton.setOnAction(e -> player.seek(new Duration(player.getCurrentTime().toMillis() + 5000)));
        movieRewindButton.setOnAction(e -> player.seek(new Duration(0)));
        startStopButton.setOnAction(e -> {
            if (player.getStatus() == MediaPlayer.Status.PAUSED) {
                player.play();
            } else {
                player.pause();
            }
        });
        goBackButton.setOnAction(e -> {
            stage.setScene(MovieSelection.userMovieSelectionScreen(stage));
            player.stop();
        });
        okButton.setOnAction(e -> {
            hall = movieBox.getSelectionModel().getSelectedItem();
            stage.setScene(HallScreen.userHallScreen(stage));
            player.stop();});
        centerButtonLayout.getChildren().addAll(startStopButton, movieBackButton, movieForwardButton, movieRewindButton, voiceManager);
        centerLayout.getChildren().addAll(view, centerButtonLayout);
        bottomButtonLayout.getChildren().addAll(goBackButton, movieBox, okButton);
        topLayout.getChildren().add(topLabel);
        VBox outerLayout = new VBox(30);
        outerLayout.getChildren().addAll(topLayout, centerLayout, bottomButtonLayout);
        centerButtonLayout.setAlignment(Pos.CENTER_RIGHT);
        centerLayout.setAlignment(Pos.CENTER);
        topLayout.setAlignment(Pos.TOP_CENTER);
        bottomButtonLayout.setAlignment(Pos.BOTTOM_CENTER);
        view.setFitHeight(500);
        view.setFitWidth(800);
        movieBox.setMinWidth(100);
        Scene scene = new Scene(outerLayout, 1000, 600);
        return scene;
    }
}
