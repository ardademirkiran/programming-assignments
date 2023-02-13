
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.text.Text;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Timer;
import java.util.TimerTask;

public class Session {
    static  int wrongLoginCounter = 0;
    public static User userLoggedIn;

    public static void addUser(String username, String password){
        ReadData.users.add(new User(username, hashPassword(password), false, false));
    }

    public static int checkSignup(String username, String password, String password_2){
        Media media = new Media(Paths.get("assets/effects/error.mp3").toUri().toString());
        if (username.equals("")) {
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            return -1;
        }

        if (password.equals("") || password_2.equals("")){
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            return -3;
        }
        for(User user: ReadData.users){
            if (user.getUsername().equals(username)){
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                return 0;
            }
        }
        if (!password.equals(password_2)){
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            return -2;
        }
        addUser(username, password);
        return 1;
    }
    private static String hashPassword(String password){
        byte[] bytesOfPassword = password.getBytes(StandardCharsets.UTF_8);
        byte[] md5Digest = new byte[0];
        try{
            md5Digest = MessageDigest.getInstance("MD5").digest(bytesOfPassword);
        }catch (NoSuchAlgorithmException e){
            return null;
        }
        return Base64.getEncoder().encodeToString(md5Digest);
    }

    public static int checkLoginInput(String usernameInput, String passwordInput){
        for(User user: ReadData.users){
            if (user.getUsername().equals(usernameInput)){
                if (user.getPassword().equals(hashPassword(passwordInput))){
                    userLoggedIn = user;
                    return 1;
                }else {return 0;}
            }
        }
        return 0;
    }
    public static Scene loginScreen(Stage stage) {
        Media media = new Media(Paths.get("assets/effects/error.mp3").toUri().toString());
        Timer timer = new Timer();
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Label topLabel = new Label("    Welcome to HUCS Cinema Reservation System!\n  Please enter your credentials below and click LOGIN.\n You can create a new account by clicking SIGN UP button.");
        usernameField.setMaxSize(200, 200);
        passwordField.setMaxSize(200, 200);
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        Button loginButton = new Button("LOG IN");
        Button signupButton = new Button("SIGN UP");
        Label bottomLabel = new Label(" ");
        loginButton.setOnAction(e -> {
            if (wrongLoginCounter == ReadData.maxError){
                    bottomLabel.setText("ERROR: Please wait until end of the " + ReadData.blockTime + " seconds to make a new operation!" );
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();
            } else {
                switch (checkLoginInput(usernameField.getText(), passwordField.getText())) {
                    case 0:
                        bottomLabel.setText("ERROR: There is no such credential!");
                        MediaPlayer mediaPlayer = new MediaPlayer(media);
                        mediaPlayer.play();
                        wrongLoginCounter++;
                        if (wrongLoginCounter == ReadData.maxError) {
                            bottomLabel.setText("ERROR: Please wait for " + ReadData.blockTime + " seconds to make a new operation!");
                            MediaPlayer mediaPlayer_2 = new MediaPlayer(media);
                            mediaPlayer_2.play();
                            TimerTask timerTask = new TimerTask() {
                                @Override
                                public void run() {
                                    wrongLoginCounter = 0;
                                }
                            };
                            timer.schedule(timerTask, ReadData.blockTime * 1000L);
                        }
                        break;
                    case 1:
                        if (userLoggedIn.getCheck_admin()) {
                            stage.setScene(MovieSelection.adminMovieSelectionScreen(stage));
                        } else {
                            stage.setScene(MovieSelection.userMovieSelectionScreen(stage));
                        }
                        wrongLoginCounter = 0;
                        break;
                }
            }

        });
        signupButton.setOnAction(e -> stage.setScene(signupScreen(stage)));
        HBox usernameLayout = new HBox(30);
        usernameLayout.getChildren().addAll(usernameLabel, usernameField);
        HBox passwordLayout = new HBox(35);
        passwordLayout.getChildren().addAll(passwordLabel, passwordField);
        HBox buttonLayout = new HBox(50);
        buttonLayout.getChildren().addAll(loginButton, signupButton);
        VBox topLayout = new VBox();
        topLayout.getChildren().addAll(topLabel);
        HBox bottomLayout = new HBox();
        bottomLayout.getChildren().add(bottomLabel);
        VBox outerLayout = new VBox(30);
        outerLayout.getChildren().addAll(topLayout, usernameLayout, passwordLayout, buttonLayout, bottomLayout);
        VBox.setMargin(usernameLayout, new Insets(0, 0, 0, 30));
        VBox.setMargin(passwordLayout, new Insets(0, 0, 0, 30));
        VBox.setMargin(buttonLayout, new Insets(0, 30, 0, 120));
        VBox.setMargin(bottomLayout, new Insets(0, 0, 20, 80));
        topLayout.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(outerLayout, 400, 250);
        return scene;
    }

    public static Scene signupScreen(Stage stage){
        Label topLabel = new Label("  Welcome to the HUCS Cinema Reservation System!\n   Fill the form below to create a new account.\nYou can go to Log In page by clicking LOG IN button.");
        HBox topLayout = new HBox();
        topLayout.getChildren().add(topLabel);
        Label bottomLabel = new Label();
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        Label passwordLabel_2 = new Label("Password:");
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        PasswordField passwordField_2 = new PasswordField();
        HBox usernameLayout = new HBox(30);
        usernameLayout.getChildren().addAll(usernameLabel, usernameField);
        HBox passwordLayout = new HBox(35);
        passwordLayout.getChildren().addAll(passwordLabel, passwordField);
        HBox passwordLayout_2 = new HBox(35);
        passwordLayout_2.getChildren().addAll(passwordLabel_2, passwordField_2);
        HBox bottomLayout = new HBox();
        bottomLayout.getChildren().add(bottomLabel);
        Button signupButton = new Button("SIGN UP");
        signupButton.setOnAction(e -> {
            switch (checkSignup(usernameField.getText(), passwordField.getText(), passwordField_2.getText())){
                case -1: bottomLabel.setText("ERROR: Username cannot be empty!"); passwordField.clear(); usernameField.clear(); passwordField_2.clear();
                break;
                case 0: bottomLabel.setText("ERROR: This username already exists!"); passwordField.clear(); usernameField.clear(); passwordField_2.clear();
                break;
                case -2: bottomLabel.setText("ERROR: Passwords do not match!"); passwordField.clear(); usernameField.clear(); passwordField_2.clear();
                break;
                case -3: bottomLabel.setText("ERROR: Password cannot be empty!"); passwordField.clear(); usernameField.clear(); passwordField_2.clear();
                break;
                case 1: bottomLabel.setText("You have succesfully registered with your new credentials!"); passwordField.clear(); usernameField.clear(); passwordField_2.clear();
                break;

            }
        });
        Button loginButton = new Button("LOG IN");
        loginButton.setOnAction(e -> stage.setScene(loginScreen(stage)));
        HBox buttonLayout = new HBox(50);
        buttonLayout.getChildren().addAll(signupButton, loginButton);
        VBox outerLayout = new VBox(30);
        outerLayout.getChildren().addAll(topLayout, usernameLayout, passwordLayout, passwordLayout_2, buttonLayout, bottomLayout);
        bottomLayout.setAlignment(Pos.BOTTOM_CENTER);
        topLayout.setAlignment(Pos.TOP_CENTER);
        VBox.setMargin(usernameLayout, new Insets(0, 0, 0, 30));
        VBox.setMargin(passwordLayout, new Insets(0, 0, 0, 30));
        VBox.setMargin(passwordLayout_2, new Insets(0, 0, 0, 30));
        VBox.setMargin(buttonLayout, new Insets(0, 30, 0, 120));

        Scene scene = new Scene(outerLayout, 400, 300);
        return scene;
    }
}
