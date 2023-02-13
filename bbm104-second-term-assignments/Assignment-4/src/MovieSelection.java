import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.xml.soap.Text;
import java.awt.*;
import java.io.File;
import java.nio.file.Paths;

public class MovieSelection {
    static Movie movie = null;

    public static int addMovie(String filmName, String trailerPath, String duration){
        File movie = new File("assets/trailers/" + trailerPath);
        Media media = new Media(Paths.get("assets/effects/error.mp3").toUri().toString());
        if (filmName.equals("")){
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            return -1;
        }

        if (trailerPath.equals("")){
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            return -2;
        }

        try{
            int convertedDuration = Integer.parseInt(duration);
            ReadData.movies.add(new Movie(filmName, trailerPath, convertedDuration));
        } catch (Exception e){
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            return 0;
        }

        if (!movie.exists()){
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            return -3;
        }
        return 1;
    }
    public static Scene userMovieSelectionScreen(Stage stage){
        VBox outerLayout = new VBox(50);
        HBox topLayout = new HBox();
        Label topLabel = new Label("           Welcome " + Session.userLoggedIn.getUsername() + " " + Session.userLoggedIn.titleText() + "!\n Select a film and then click OK to Continue.");
        ChoiceBox<Movie> movieBox = new ChoiceBox<>(FXCollections.observableArrayList(ReadData.movies));
        HBox centerLayout = new HBox(30);
        HBox bottomLayout = new HBox();
        Button logoutButton = new Button("LOG OUT");
        bottomLayout.getChildren().add(logoutButton);
        Button okButton = new Button("OK");
        topLayout.getChildren().add(topLabel);
        centerLayout.getChildren().addAll(movieBox, okButton);
        outerLayout.getChildren().addAll(topLayout, centerLayout, bottomLayout);
        bottomLayout.setAlignment(Pos.BOTTOM_RIGHT);
        topLayout.setAlignment(Pos.TOP_CENTER);
        centerLayout.setAlignment(Pos.CENTER);
        movieBox.setValue(ReadData.movies.get(0));
        logoutButton.setOnAction(e -> {
            stage.setScene(Session.loginScreen(stage));
            Session.userLoggedIn = null;
        });
        okButton.setOnAction(e -> {
            movie = movieBox.getSelectionModel().getSelectedItem();
            stage.setScene(MovieScreen.userMovieScreen(stage));
        });
        HBox.setMargin(centerLayout, new Insets(0, 20, 0, 20));
        HBox.setMargin(logoutButton, new Insets(0, 20, 0, 0));
        Scene scene = new Scene(outerLayout, 450, 200);
        return scene;

    }

    public static Scene adminMovieSelectionScreen(Stage stage){
        VBox outerLayout = new VBox(50);
        HBox topLayout = new HBox();
        Label topLabel = new Label("Welcome " + Session.userLoggedIn.getUsername() + " " + Session.userLoggedIn.titleText() + "!\n You can either select film below or do edits.");
        ChoiceBox<Movie> movieBox = new ChoiceBox<>(FXCollections.observableArrayList(ReadData.movies));
        HBox centerLayout = new HBox(30);
        HBox bottomLayout = new HBox();
        HBox adminLayout = new HBox(30);
        Button addFilmButton = new Button("Add Film");
        Button removeFilmButton = new Button("Remove Film");
        Button editUsersButton = new Button("Edit Users");
        Button logoutButton = new Button("LOG OUT");
        adminLayout.getChildren().addAll(addFilmButton, removeFilmButton, editUsersButton);
        bottomLayout.getChildren().add(logoutButton);
        Button okButton = new Button("OK");
        topLayout.getChildren().add(topLabel);
        centerLayout.getChildren().addAll(movieBox, okButton);
        outerLayout.getChildren().addAll(topLayout, centerLayout, adminLayout, bottomLayout);
        topLayout.setAlignment(Pos.TOP_CENTER);
        centerLayout.setAlignment(Pos.CENTER);
        adminLayout.setAlignment(Pos.CENTER);
        bottomLayout.setAlignment(Pos.BOTTOM_RIGHT);
        movieBox.setValue(ReadData.movies.get(0));
        logoutButton.setOnAction(e -> {
            stage.setScene(Session.loginScreen(stage));
            Session.userLoggedIn = null;
        });
        addFilmButton.setOnAction(e-> stage.setScene(addMovieScreen(stage)));
        removeFilmButton.setOnAction(e-> stage.setScene(removeMovieScreen(stage)));
        editUsersButton.setOnAction(e -> stage.setScene(AdminUserOperations.adminEditUserScreen(stage)));
        okButton.setOnAction(e -> {
            movie = movieBox.getSelectionModel().getSelectedItem();
            stage.setScene(MovieScreen.adminMovieScreen(stage));});
        HBox.setMargin(centerLayout, new Insets(0, 20, 0, 20));
        HBox.setMargin(logoutButton, new Insets(0, 20, 0, 0));
        Scene scene = new Scene(outerLayout, 450, 300);
        return scene;

    }

    public static Scene addMovieScreen(Stage stage){
        Label topLabel = new Label("Please give name, relative path of the trailer and duration of the film.");
        Label nameLabel = new Label("Name:           ");
        Label pathLabel = new Label("Trailer (Path):");
        Label durationLabel = new Label("Duration (m): ");
        Label bottomLabel = new Label("");
        TextField nameField = new TextField();
        TextField pathField = new TextField();
        TextField durationField = new TextField();
        Button okButton = new Button("OK");
        Button goBackButton = new Button("BACK");
        HBox topLayout = new HBox();
        HBox nameLayout = new HBox(30);
        HBox pathLayout = new HBox(30);
        HBox durationLayout = new HBox(30);
        HBox buttonLayout = new HBox(150);
        VBox outerLayout = new VBox(30);
        HBox bottomLayout = new HBox();
        topLayout.getChildren().add(topLabel);
        nameLayout.getChildren().addAll(nameLabel, nameField);
        pathLayout.getChildren().addAll(pathLabel, pathField);
        durationLayout.getChildren().addAll(durationLabel, durationField);
        buttonLayout.getChildren().addAll(goBackButton, okButton);
        bottomLayout.getChildren().add(bottomLabel);
        outerLayout.getChildren().addAll(topLayout, nameLayout, pathLayout, durationLayout, buttonLayout, bottomLayout);
        nameLayout.setAlignment(Pos.CENTER);
        pathLayout.setAlignment(Pos.CENTER);
        durationLayout.setAlignment(Pos.CENTER);
        buttonLayout.setAlignment(Pos.CENTER);
        topLayout.setAlignment(Pos.TOP_CENTER);
        bottomLayout.setAlignment(Pos.BOTTOM_CENTER);
        okButton.setOnAction(e -> {
            switch (addMovie(nameField.getText(), pathField.getText(), durationField.getText())){
                case -1:
                    bottomLabel.setText("ERROR: Film name could not be empty!");
                    break;
                case -3:
                    bottomLabel.setText("ERROR: There is no such trailer!");
                    break;
                case -2:
                    bottomLabel.setText("ERROR: Trailer path could not be empty!");
                    break;
                case 0:
                    bottomLabel.setText("ERROR: Duration has to be a positive integer!");
                    break;
                case 1:
                    bottomLabel.setText("SUCCESS: Film added succesfully!");
                    nameField.clear();
                    pathField.clear();
                    durationField.clear();
            }
        });
        goBackButton.setOnAction(e -> {stage.setScene(adminMovieSelectionScreen(stage));});
        Scene scene = new Scene(outerLayout, 500 , 300);
        return scene;
    }

    public static Scene removeMovieScreen(Stage stage){
        Label textLabel = new Label("Select the film that you desire to remove and then click OK.");
        ChoiceBox<Movie> movieList = new ChoiceBox<>(FXCollections.observableArrayList(ReadData.movies));
        movieList.getSelectionModel().select(0);
        Button okButton = new Button("OK");
        Button goBackButton = new Button("BACK");
        HBox centerLayout = new HBox();
        HBox buttonLayout = new HBox(50);
        HBox topLayout = new HBox();
        topLayout.getChildren().add(textLabel);
        buttonLayout.getChildren().addAll(goBackButton, okButton);
        centerLayout.getChildren().add(movieList);
        VBox outerLayout = new VBox(20);
        outerLayout.getChildren().addAll(topLayout, centerLayout, buttonLayout);
        movieList.setMinWidth(350);
        topLayout.setAlignment(Pos.TOP_CENTER);
        centerLayout.setAlignment(Pos.CENTER);
        buttonLayout.setAlignment(Pos.BOTTOM_CENTER);
        okButton.setOnAction(e -> {ReadData.movies.remove(movieList.getValue());
            movieList.setItems(FXCollections.observableArrayList(ReadData.movies));
            movieList.getSelectionModel().select(0);});
        goBackButton.setOnAction(e -> stage.setScene(adminMovieSelectionScreen(stage)));
        Scene scene = new Scene(outerLayout, 400, 150);
        return scene;
    }
}
