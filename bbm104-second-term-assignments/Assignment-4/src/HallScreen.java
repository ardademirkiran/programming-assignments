import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HallScreen {
    public static int checkHallInput(String hallName, String price) {
        Media media = new Media(Paths.get("assets/effects/error.mp3").toUri().toString());
        if (hallName.equals("")) {
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            return -1;
        }
        try {
            int intPrice = Integer.parseInt(price);
        }  catch (Exception e) {
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
                return 0;
            }
        return 1;
        }

    public static void buySeat(User user, SeatButton seatButton){
        Seat seat = seatButton.getSeat();
            seat.owner = user;
            seat.price = (user.getCheck_cm()) ? (int) (MovieScreen.hall.seatPrice - ((MovieScreen.hall.seatPrice * ReadData.discountPercentage) / 100)) : MovieScreen.hall.seatPrice;
    }

    public static Scene adminHallScreen(Stage stage){
        Image emptySeatImage = new Image("File:assets/icons/empty_seat.png");
        Image reservedSeatImage = new Image("File:assets/icons/reserved_seat.png");
        Label topLabel = new Label(MovieSelection.movie.toString() + " (" + MovieSelection.movie.getDuration() + " minutes)");
        Label bottomLabel = new Label();
        Label lastOperationLabel = new Label();
        Button backButton = new Button("BACK");
        ChoiceBox<User> userBox = new ChoiceBox<>(FXCollections.observableArrayList(ReadData.users));
        userBox.getSelectionModel().select(0);
        VBox seatLayout = new VBox(20);
        VBox bottomLayout = new VBox(30);
        HBox topLayout = new HBox();
        HBox centerLayout = new HBox();
        VBox outerLayout = new VBox(20);
        HBox extraLayout = new HBox(40);
        topLayout.getChildren().add(topLabel);
        for (int i = 0; i < MovieScreen.hall.seats.length; i++){
            HBox seatLineLayout = new HBox(20);
            for (int x = 0; x < MovieScreen.hall.seats[i].length; x++){
                Seat seat = MovieScreen.hall.seats[i][x];
                SeatButton seatButton = new SeatButton(seat);
                seatButton.setOnMouseEntered(e -> bottomLabel.setText(seatButton.processLabelText()));
                seatButton.setOnMouseExited(e -> bottomLabel.setText(""));
                seatButton.setOnAction(e -> {
                    buySeat(userBox.getSelectionModel().getSelectedItem(), seatButton);
                    lastOperationLabel.setText("Seat at " + seatButton.getSeat().toCoordinate() + " bought for " + userBox.getSelectionModel().getSelectedItem().getUsername() + " for " + seatButton.getSeat().price + " TL!");
                    ImageView soldBackgroundImageView = new ImageView(reservedSeatImage);
                    soldBackgroundImageView.setFitHeight(50);
                    soldBackgroundImageView.setFitWidth(50);
                    seatButton.setGraphic(soldBackgroundImageView);
                }
                );
                if (seatButton.getSeat().owner != null){
                    ImageView soldBackgroundImageView = new ImageView(reservedSeatImage);
                    soldBackgroundImageView.setFitHeight(50);
                    soldBackgroundImageView.setFitWidth(50);
                    seatButton.setGraphic(soldBackgroundImageView);
                } else {
                    ImageView emptybackgroundImageView = new ImageView(emptySeatImage);
                    emptybackgroundImageView.setFitHeight(50);
                    emptybackgroundImageView.setFitWidth(50);
                    seatButton.setGraphic(emptybackgroundImageView);
                }
                seatButton.setMinSize(50, 50);
                seatLineLayout.getChildren().add(seatButton);
            }
            seatLayout.getChildren().add(seatLineLayout);
        }
        backButton.setOnAction(e -> stage.setScene(MovieScreen.adminMovieScreen(stage)));
        extraLayout.getChildren().addAll(backButton, userBox);
        bottomLayout.getChildren().addAll(extraLayout, bottomLabel, lastOperationLabel);
        centerLayout.getChildren().add(seatLayout);
        extraLayout.setAlignment(Pos.BOTTOM_CENTER);
        centerLayout.setAlignment(Pos.CENTER);
        topLayout.setAlignment(Pos.TOP_CENTER);
        bottomLayout.setAlignment(Pos.BOTTOM_CENTER);
        outerLayout.getChildren().addAll(topLayout, centerLayout, extraLayout, bottomLayout);
        Scene scene = new Scene(outerLayout, 900, 950);
        return scene;
     }

     public static Scene userHallScreen(Stage stage){
         Image emptySeatImage = new Image("File:assets/icons/empty_seat.png");
         Image reservedSeatImage = new Image("File:assets/icons/reserved_seat.png");
         Label topLabel = new Label(MovieSelection.movie.toString() + " (" + MovieSelection.movie.getDuration() + " minutes)");
         Label lastOperationLabel = new Label();
         Button backButton = new Button("BACK");
         VBox seatLayout = new VBox(20);
         VBox bottomLayout = new VBox(30);
         HBox topLayout = new HBox();
         HBox centerLayout = new HBox();
         VBox outerLayout = new VBox(20);
         HBox extraLayout = new HBox(40);
         topLayout.getChildren().add(topLabel);
         for (int i = 0; i < MovieScreen.hall.seats.length; i++){
             HBox seatLineLayout = new HBox(20);
             for (int x = 0; x < MovieScreen.hall.seats[i].length; x++){
                 Seat seat = MovieScreen.hall.seats[i][x];
                 SeatButton seatButton = new SeatButton(seat);
                 seatButton.setOnAction(e -> {
                             if (seatButton.getSeat().owner == Session.userLoggedIn){
                                 ImageView emptyBackgroundImageView = new ImageView(emptySeatImage);
                                 emptyBackgroundImageView.setFitHeight(50);
                                 emptyBackgroundImageView.setFitWidth(50);
                                 seatButton.setGraphic(emptyBackgroundImageView);
                                 seatButton.getSeat().owner = null;
                                 lastOperationLabel.setText("Seat at "+ seatButton.getSeat().toCoordinate() + " is refunded successfully!");
                             } else {
                                 buySeat(Session.userLoggedIn, seatButton);
                                 lastOperationLabel.setText("Seat at " + seatButton.getSeat().toCoordinate() + " is bought for " + seatButton.getSeat().price + " TL!");
                                 ImageView soldBackgroundImageView = new ImageView(reservedSeatImage);
                                 soldBackgroundImageView.setFitHeight(50);
                                 soldBackgroundImageView.setFitWidth(50);
                                 seatButton.setGraphic(soldBackgroundImageView);
                             }
                         }
                 );
                 if (seatButton.getSeat().owner != null){
                     ImageView soldBackgroundImageView = new ImageView(reservedSeatImage);
                     soldBackgroundImageView.setFitHeight(50);
                     soldBackgroundImageView.setFitWidth(50);
                     seatButton.setGraphic(soldBackgroundImageView);
                 } else {
                     ImageView emptybackgroundImageView = new ImageView(emptySeatImage);
                     emptybackgroundImageView.setFitHeight(50);
                     emptybackgroundImageView.setFitWidth(50);
                     seatButton.setGraphic(emptybackgroundImageView);
                 }
                 if (seatButton.getSeat().owner != null && seatButton.getSeat().owner != Session.userLoggedIn){
                     seatButton.setDisable(true);
                 }
                 seatButton.setMinSize(50, 50);
                 seatLineLayout.getChildren().add(seatButton);
             }
             seatLayout.getChildren().add(seatLineLayout);
         }
         backButton.setOnAction(e -> stage.setScene(MovieScreen.userMovieScreen(stage)));
         extraLayout.getChildren().addAll(backButton);
         bottomLayout.getChildren().addAll(extraLayout, lastOperationLabel);
         centerLayout.getChildren().add(seatLayout);
         extraLayout.setAlignment(Pos.BOTTOM_CENTER);
         centerLayout.setAlignment(Pos.CENTER);
         topLayout.setAlignment(Pos.TOP_CENTER);
         bottomLayout.setAlignment(Pos.BOTTOM_CENTER);
         outerLayout.getChildren().addAll(topLayout, centerLayout, extraLayout, bottomLayout);
         Scene scene = new Scene(outerLayout, 900, 950);
         return scene;

     }

     public static Scene addHallScreen(Stage stage){
        ArrayList<Integer> numbers = new ArrayList<>();
         for(int i = 3; i < 11 ; i++){
             numbers.add(i);
         }
         Label topLabel = new Label(MovieSelection.movie.getFilmName() + " (" + MovieSelection.movie.getDuration() + " Minutes)");
         Label rowLabel = new Label("Row:");
         Label columnLabel = new Label("Column:");
         Label nameLabel = new Label("Name:");
         Label priceLabel = new Label("Price:");
         Label bottomLabel = new Label();
        Button goBackButton = new Button("BACK");
        Button okButton = new Button("OK");
        ChoiceBox<Integer> rowBox = new ChoiceBox<>(FXCollections.observableArrayList(numbers));
        ChoiceBox<Integer> columnBox = new ChoiceBox<>(FXCollections.observableArrayList(numbers));
        rowBox.getSelectionModel().select(0);
        columnBox.getSelectionModel().select(0);
        TextField nameField = new TextField();
        TextField priceField = new TextField();
        HBox topLayout = new HBox();
        HBox rowLayout = new HBox(170);
        HBox columnLayout = new HBox(150);
        HBox nameLayout = new HBox(45);
        HBox priceLayout = new HBox(50);
        HBox buttonLayout = new HBox(50);
        HBox bottomLayout = new HBox(50);
        VBox centerLayout = new VBox(30);
        VBox outerLayout = new VBox(20);
        topLayout.getChildren().add(topLabel);
        rowLayout.getChildren().addAll(rowLabel, rowBox);
        columnLayout.getChildren().addAll(columnLabel, columnBox);
        nameLayout.getChildren().addAll(nameLabel, nameField);
        priceLayout.getChildren().addAll(priceLabel, priceField);
        buttonLayout.getChildren().addAll(goBackButton, okButton);
        bottomLayout.getChildren().addAll(bottomLabel);
        centerLayout.getChildren().addAll(rowLayout, columnLayout, nameLayout, priceLayout);
        outerLayout.getChildren().addAll(topLayout, centerLayout, buttonLayout, bottomLayout);
        topLayout.setAlignment(Pos.TOP_CENTER);
        buttonLayout.setAlignment(Pos.BOTTOM_CENTER);
        rowLayout.setAlignment(Pos.CENTER);
        columnLayout.setAlignment(Pos.CENTER);
        nameLayout.setAlignment(Pos.CENTER);
        priceLayout.setAlignment(Pos.CENTER);
        bottomLayout.setAlignment(Pos.CENTER);
        okButton.setOnAction(e -> {
            switch (checkHallInput(nameField.getText(), priceField.getText())){
                case -1:
                    bottomLabel.setText("ERROR: Hall Name could not be empty!");
                    break;
                case 0:
                    bottomLabel.setText("ERROR: Price must be a positive integer.");
                    break;
                case 1:
                    Hall hall = new Hall(MovieSelection.movie, nameField.getText(), Integer.parseInt(priceField.getText()), rowBox.getValue(), columnBox.getValue());
                    hall.createSeats();
                    MovieSelection.movie.getHalls().add(hall);
                    bottomLabel.setText("SUCCESS: Hall successfully created!");
                    break;

            }
        });

        goBackButton.setOnAction(e -> stage.setScene(MovieScreen.adminMovieScreen(stage)));
        Scene scene = new Scene(outerLayout, 500, 500);
        return scene;
    }

    public static Scene removeHallScreen(Stage stage){
        Label textLabel = new Label("Select the hall that you desired to remove from the " + MovieSelection.movie.toString() + " amd then click OK.");
        ChoiceBox<Hall> movieBox = new ChoiceBox<>(FXCollections.observableArrayList(MovieSelection.movie.getHalls()));
        movieBox.setMinWidth(80);
        movieBox.getSelectionModel().select(0);
        Button goBackButton = new Button("BACK");
        Button okButton = new Button("OK");
        HBox buttonLayout = new HBox(10);
        VBox centerLayout = new VBox(30);
        buttonLayout.getChildren().addAll(goBackButton, okButton);
        centerLayout.getChildren().addAll(textLabel, movieBox, buttonLayout);
        VBox outerLayout = new VBox();
        outerLayout.getChildren().addAll(centerLayout);
        centerLayout.setAlignment(Pos.CENTER);
        buttonLayout.setAlignment(Pos.CENTER);
        okButton.setOnAction(e -> {
            MovieSelection.movie.getHalls().remove(movieBox.getSelectionModel().getSelectedItem());
            movieBox.setItems(FXCollections.observableArrayList(MovieSelection.movie.getHalls()));
            movieBox.getSelectionModel().select(0);});
        goBackButton.setOnAction(e -> stage.setScene(MovieScreen.adminMovieScreen(stage)));
        Scene scene = new Scene(outerLayout, 650, 200);
        return scene;
    }
}
