/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucr;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Harsh Kumar Singh
 */
public class loginScreenController implements Initializable {

    @FXML
    TextField txtUsername;
    @FXML
    TextField txtPassword;
    @FXML
    Label lblLoginStatus;
    @FXML
    public ComboBox<String> comboUserLevel;
    ObservableList<String> list = FXCollections.observableArrayList("Coordinator", "Volunteer");

    loginData newUser = new loginData();

    @FXML
    public void logIn(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {

	boolean infoEntered = true;
	String enteredUsername = txtUsername.getText();
	String enteredPassword = txtPassword.getText();
	String enteredUserlevel = comboUserLevel.getValue();
	if (enteredUserlevel == null) {
	    lblLoginStatus.setText("Select User Level");
	    lblLoginStatus.setTextFill(Color.RED);
	    infoEntered = false;
	}
	if (enteredPassword == null || enteredPassword.equals("")) {
	    lblLoginStatus.setText("Enter Password");
	    lblLoginStatus.setTextFill(Color.RED);
	    infoEntered = false;
	}
	if (enteredUsername == null || enteredUsername.equals("")) {
	    lblLoginStatus.setText("Enter Username");
	    lblLoginStatus.setTextFill(Color.RED);
	    infoEntered = false;
	}

	if (infoEntered) {
	    newUser.newUser(enteredUsername, enteredPassword, enteredUserlevel);
	    String userlevelFXML = "userScreen.fxml";
	    int sceneHeight = 785, sceneWidth = 800;

	    switch (newUser.checkLoginDetails()) {
		case 1:
		    lblLoginStatus.setText("User Not Found");
		    lblLoginStatus.setTextFill(Color.RED);
		    txtPassword.setText(null);
		    txtUsername.setText(null);
		    comboUserLevel.setValue("<Select User>");
		    break;
		case 2:
		    lblLoginStatus.setText("Incorrent Password");
		    lblLoginStatus.setTextFill(Color.BLUE);
		    txtPassword.setText(null);
		    comboUserLevel.setValue("<Select User>");
		    break;
		case 3:
		    lblLoginStatus.setText("Access Denied");
		    lblLoginStatus.setTextFill(Color.RED);
		    txtPassword.setText(null);
		    comboUserLevel.setValue("<Select User>");
		    break;
		case 4:
		    if (newUser.userlevel.equals("A") && comboUserLevel.getValue().equals("Coordinator")) {
			userlevelFXML = "adminScreen.fxml";
			sceneHeight = 400;
			sceneWidth = 400;
		    }
		    lblLoginStatus.setText("Login Success");
		    lblLoginStatus.setTextFill(Color.GREEN);
		    txtUsername.setText(null);
		    txtPassword.setText(null);
		    comboUserLevel.setValue("<Select User>");
		    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		    Parent parent = FXMLLoader.load(getClass().getResource(userlevelFXML));
		    Scene scene = new Scene(parent, sceneWidth, sceneHeight);
		    primaryStage.setTitle(enteredUserlevel + ": " + enteredUsername.toUpperCase());
		    primaryStage.setScene(scene);
		    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		    primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
		    primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
		    primaryStage.getIcons().add(new Image("ucrIcon.jpg"));
		    primaryStage.show();
		    break;
		default:
		    lblLoginStatus.setText("Something Wrong");
		    lblLoginStatus.setTextFill(Color.RED);
		    txtPassword.setText(null);
		    txtUsername.setText(null);
		    comboUserLevel.setValue("<Select User>");
	    }
	}
    }

    @FXML
    public void userLevelChanged(ActionEvent event) {
	newUser.userlevel = comboUserLevel.getValue().toLowerCase();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
	comboUserLevel.setItems(list);
    }
}
