/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucr;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Harsh Kumar Singh
 */
public class adminScreenController implements Initializable {

    public adminScreenController() {

    }

    @FXML
    public void addUser(ActionEvent event) throws ClassNotFoundException, SQLException {
	String enterUsername = "";
	String enteredPassword = "";
	String enteredUserlevel = "U";
	TextInputDialog dialog = new TextInputDialog("");
	dialog.setTitle("μCR - Add User");
	dialog.setHeaderText("Enter details of User: ");
	dialog.setContentText("Username: ");
	Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
	stage.getIcons().add(new Image("ucrIcon.jpg"));
	Optional<String> result = dialog.showAndWait();
	connectDB dbConn = new connectDB();
	if (result.isPresent()) {
	    enterUsername = result.get();
	    ;
	    dialog = new TextInputDialog("");
	    dialog.setContentText("Password: ");
	    stage = (Stage) dialog.getDialogPane().getScene().getWindow();
	    stage.getIcons().add(new Image("ucrIcon.jpg"));
	    result = dialog.showAndWait();
	    if (result.isPresent()) {
		enteredPassword = result.get();
		final String[] arrayData = {"Coordinator", "Volunteer"};
		List<String> dialogData;
		dialogData = Arrays.asList(arrayData);
		ChoiceDialog dialogChoice = new ChoiceDialog(dialogData.get(0), dialogData);
		dialogChoice.setContentText("Userlevel: ");
//		stage = (Stage) dialog.getDialogPane().getScene().getWindow();
//		stage.getIcons().add(new Image("ucrIcon.jpg"));
		result = dialogChoice.showAndWait();
		if (result.isPresent()) {
		    enteredUserlevel = result.get();
		    if ("Coordinator".equals(enteredUserlevel)) {
			enteredUserlevel = "A";
		    } else {
			enteredUserlevel = "U";
		    }
		    String query = "insert into logindata values('" + enterUsername + "','" + enteredPassword + "','" + enteredUserlevel + "')";
		    dbConn.addDataDB(query);
		}
	    }
	}
    }

    @FXML
    public void deleteUser(ActionEvent event) throws SQLException, ClassNotFoundException {
	String enteredUsername;
	TextInputDialog dialog = new TextInputDialog("");
	dialog.setTitle("μCR - Delete User");
	dialog.setHeaderText("Enter Username: ");
	Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
	stage.getIcons().add(new Image("ucrIcon.jpg"));
	Optional<String> result = dialog.showAndWait();
	enteredUsername = result.get();
	connectDB dbConn = new connectDB();
	String query = "delete from logindata where username='" + enteredUsername + "'";
	dbConn.addDataDB(query);
	Alert alert = new Alert(Alert.AlertType.INFORMATION);
	alert.setHeaderText("User Deleted Successfully");
	alert.showAndWait();
    }

    @FXML
    public void adminLogout(ActionEvent event) throws IOException {
	Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	Parent root = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
	Scene scene = new Scene(root, 300, 470);
	primaryStage.setTitle("μCR");
	primaryStage.setScene(scene);
	Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
	primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
	primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
	primaryStage.show();
    }

    @FXML
    public void addData(ActionEvent event) throws IOException {
	Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	Parent parent = FXMLLoader.load(getClass().getResource("userScreen.fxml"));
	Scene scene = new Scene(parent, 800, 850);
	primaryStage.setTitle("μCR Coordinator");
	primaryStage.setScene(scene);
	Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
	primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
	primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
	primaryStage.getIcons().add(new Image("ucrIcon.jpg"));
	primaryStage.show();
    }

    @FXML
    public void viewData(ActionEvent event) throws IOException {
	Stage secondaryStage = new Stage();
	Parent parent = FXMLLoader.load(getClass().getResource("viewDataScreen.fxml"));
	Scene scene = new Scene(parent, 1270, 700);
	secondaryStage.setTitle("μCR - View Data");
	secondaryStage.setScene(scene);
	Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
	secondaryStage.setX((primScreenBounds.getWidth() - 1150) / 2);
	secondaryStage.setY(primScreenBounds.getMinY() + 10);
	secondaryStage.setHeight(primScreenBounds.getHeight() - 10);
	secondaryStage.getIcons().add(new Image("ucrIcon.jpg"));
	secondaryStage.show();
    }

    @FXML
    public void exportData(ActionEvent event) throws SQLException, ClassNotFoundException {
	DirectoryChooser directory = new DirectoryChooser();
	directory.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
	File selectedDirectory = directory.showDialog(null);
	if (directory != null) {
	    connectDB dbConn = new connectDB();
	    String statement = "CALL CSVWRITE('" + selectedDirectory + "\\KitReg.csv', 'select * from kitreg')";
	    dbConn.addDataDB(statement);
	}
    }

    @FXML
    public void viewStatistics(ActionEvent event) throws ClassNotFoundException, SQLException {
	connectDB conn = new connectDB();
	String statement = "SELECT distinct teamid,amount FROM KITREG";
	ResultSet resultset = conn.getDataDB(statement);
	int sum = 0;
	while (resultset.next()) {
	    sum += Integer.parseInt(resultset.getString("amount"));
	}
	Alert alert = new Alert(Alert.AlertType.INFORMATION);
	alert.setTitle("μCR");
	alert.setHeaderText("Total Fund Collected: " + sum);
	alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
    }

}
