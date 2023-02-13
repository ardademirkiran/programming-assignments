import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TableView;


public class AdminUserOperations{
    public static void promoteClubMember(User user){
        user.setCheck_cm(!user.getCheck_cm());
    }

    public static void promoteAdmin(User user){
        user.setCheck_admin(!user.getCheck_admin());
    }

    public static Scene adminEditUserScreen(Stage stage){
        ReadData.users.remove(Session.userLoggedIn);
        TableView<User> userTable = new TableView<>(FXCollections.observableArrayList(ReadData.users));
        userTable.setMinSize(500, 400);
        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        TableColumn<User, Boolean> cmCheckColumn = new TableColumn<>("Club Member");
        TableColumn<User, Boolean> adminCheckColumn = new TableColumn<>("Admin");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        cmCheckColumn.setCellValueFactory(new PropertyValueFactory<>("check_cm"));
        adminCheckColumn.setCellValueFactory(new PropertyValueFactory<>("check_admin"));
        usernameColumn.setMinWidth(30);
        cmCheckColumn.setMinWidth(30);
        adminCheckColumn.setMinWidth(30);
        Button goBackButton = new Button("BACK");
        Button cmOperationsButton = new Button("Promote/Demote Club Member");
        Button adminOperationsButton = new Button("Promote/Demote Admin");
        userTable.getColumns().addAll(usernameColumn, cmCheckColumn, adminCheckColumn);
        HBox tableLayout = new HBox();
        HBox buttonLayout = new HBox(25);
        VBox outerLayout = new VBox(20);
        tableLayout.getChildren().add(userTable);
        buttonLayout.getChildren().addAll(goBackButton, cmOperationsButton, adminOperationsButton);
        outerLayout.getChildren().addAll(tableLayout, buttonLayout);
        tableLayout.setAlignment(Pos.CENTER);
        buttonLayout.setAlignment(Pos.BOTTOM_CENTER);
        cmOperationsButton.setOnAction(e -> {User user = userTable.getSelectionModel().getSelectedItem();
        promoteClubMember(user);
        userTable.refresh();});
        adminOperationsButton.setOnAction(e -> {User user = userTable.getSelectionModel().getSelectedItem();
        promoteAdmin(user);
        userTable.refresh();});
        goBackButton.setOnAction(e -> {
            ReadData.users.add(Session.userLoggedIn);
            stage.setScene(MovieSelection.adminMovieSelectionScreen(stage));
        });
        Scene scene = new Scene(outerLayout, 600 , 470);
        return scene;

    }
}
