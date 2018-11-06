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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Harsh Kumar Singh
 */
public class userScreenController implements Initializable {

    @FXML
    ComboBox comboTotalMem;
    @FXML
    Label lblTeamid;
    @FXML
    TextField txtEnrollmentNo1, txtEnrollmentNo2, txtEnrollmentNo3, txtEnrollmentNo4;
    @FXML
    TextField txtName1, txtName2, txtName3, txtName4;
    @FXML
    ComboBox<String> comboBranch1, comboBranch2, comboBranch3, comboBranch4;
    @FXML
    RadioButton radioSector621, radioSector622, radioSector623, radioSector624;
    @FXML
    RadioButton radioSector1281, radioSector1282, radioSector1283, radioSector1284;
    @FXML
    ToggleGroup radio1, radio2, radio3, radio4;
    @FXML
    CheckBox chkHosteler1, chkHosteler2, chkHosteler3, chkHosteler4;
    @FXML
    TextField txtContactNo1, txtContactNo2, txtContactNo3, txtContactNo4;
    @FXML
    CheckBox chkSameWhatsapp1, chkSameWhatsapp2, chkSameWhatsapp3, chkSameWhatsapp4;
    @FXML
    TextField txtWhatsappNo1, txtWhatsappNo2, txtWhatsappNo3, txtWhatsappNo4;
    @FXML
    TextField txtEmail1, txtEmail2, txtEmail3, txtEmail4;
    @FXML
    Label lblSubmitStatus;
    @FXML
    Button btnAdminBack;/*
    @FXML
    RadioButton radioCash, radioPaytm;
    @FXML
    ToggleGroup radioTogglePayment;*/
    @FXML
    CheckBox chkPayment;
    @FXML
    TextField txtAmount;

    ObservableList totalmemList = FXCollections.observableArrayList(1, 2, 3, 4);
    ObservableList<String> branchList = FXCollections.observableArrayList("CSE", "ECE", "IT", "BT", "Other");

    List<String> existingUsers;
    List<String> registeredUsers;

    String enteredTeamID;
    int currentTeamID;
    int totalMem;
    TextField[] txtEnrollmentNo;
    TextField[] txtName;
    ComboBox<String>[] comboBranch;
    RadioButton[] radioSector62;
    RadioButton[] radioSector128;
    ToggleGroup[] radio;
    CheckBox[] chkHosteler;
    TextField[] txtContactNo;
    CheckBox[] chkSameWhatsapp;
    TextField[] txtWhatsappNo;
    TextField[] txtEmail;

    userData[] newData;

    @FXML
    public void editTeam(ActionEvent event) throws ClassNotFoundException, SQLException {
	TextInputDialog dialog = new TextInputDialog("");
	dialog.setTitle("μCR - Edit Team");
	dialog.setHeaderText("Edit Team Info");
	dialog.setContentText("Please enter Team ID: ");
	Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
	stage.getIcons().add(new Image("ucrIcon.jpg"));
	Optional<String> result = dialog.showAndWait();
	if (result.isPresent()) {
	    enteredTeamID = result.get();
	    connectDB dbConn = new connectDB();
	    String query = "select * from kitreg where teamid=" + enteredTeamID;
	    ResultSet resultSet = dbConn.getDataDB(query);
	    int i = 0;
	    if (resultSet.next()) {
		lblTeamid.setText(enteredTeamID);
		do {
		    String tempEnrollmentNo = resultSet.getString("enrollmentno");
		    clearBox(i);
		    setDetails(tempEnrollmentNo, i++);
		    txtEnrollmentNo[i - 1].setText(tempEnrollmentNo);
		    comboTotalMem.setValue(i);
		    if (resultSet.getString("payment").equals("Yes")) {
			chkPayment.setSelected(true);
		    } else {
			chkPayment.setSelected(false);
		    }
		    txtAmount.setText(resultSet.getString("amount"));
		} while (resultSet.next());
	    } else {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("μCR");
		alert.setHeaderText("Team not Found!");
		alert.showAndWait();
	    }
	}
    }

    @FXML
    public void deleteMember(ActionEvent event) throws ClassNotFoundException, SQLException {
	final String[] arrayData = {"1", "2", "3", "4"};
	List<String> dialogData;
	dialogData = Arrays.asList(arrayData);
	ChoiceDialog dialog = new ChoiceDialog(dialogData.get(0), dialogData);
	dialog.setTitle("μCR - Edit Team Info");
	dialog.setHeaderText("Select Team Member to delete: ");
	Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
	stage.getIcons().add(new Image("ucrIcon.jpg"));
	Optional<String> result = dialog.showAndWait();
	String selected = "";
	if (result.isPresent()) {
	    selected = result.get();
	    getFormData();
	    if (Integer.parseInt(selected) <= totalMem) {
		userData temp = new userData();
		temp.deleteData(newData[Integer.parseInt(selected) - 1].enrollmentno.get());
		for (int i = Integer.parseInt(selected) - 1; i < totalMem - 1; i++) {
		    newData[i] = newData[i + 1];
		}
		comboTotalMem.setValue(--totalMem);
		startUserScreenController();
		editTeam(event);
	    } else {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("μCR");
		alert.setHeaderText("Invalid Team Member");
		alert.showAndWait();
	    }
	}
    }

    @FXML
    public void fetchData(Event event) {
	String enteredEnrollmentNo = null;
	int i, makeChanges = 0;
	for (i = 0; i < totalMem; i++) {
	    if (txtName[i].getText() == null || "".equals(txtName[i].getText())) {
		makeChanges = 1;
		break;
	    }
	}
	if (makeChanges == 1) {
	    enteredEnrollmentNo = txtEnrollmentNo[i].getText();
	    try {
		long enrollment = Long.parseUnsignedLong(enteredEnrollmentNo);
		if (enrollment > 14000000) {
		    if (registeredUsers.contains(enteredEnrollmentNo)) {
			lblSubmitStatus.setText(enteredEnrollmentNo + " already registered.");
		    } else if (existingUsers.contains(enteredEnrollmentNo)) {
			setDetails(enteredEnrollmentNo, i);
		    } else {
			lblSubmitStatus.setText("");
		    }
		    if (enrollment > 20000000) {
			radioSector128[i].setSelected(true);
		    }
		}
	    } catch (NumberFormatException exception) {
		System.out.println(exception);
	    } catch (ClassNotFoundException | SQLException ex) {
		Logger.getLogger(userScreenController.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }

    public void setDetails(String enteredEnrollmentNo, int i) throws ClassNotFoundException, SQLException {
	userData temp = new userData();
	temp.getInfo(enteredEnrollmentNo);

	if (temp.sector.get() == (null)) {
	    radioSector62[i].setSelected(false);
	    radioSector128[i].setSelected(false);
	} else if (temp.sector.get().equals("62")) {
	    radioSector62[i].setSelected(true);
	} else if (temp.sector.get().equals("128")) {
	    radioSector128[i].setSelected(true);
	}
	if (temp.hosteler.get() == (null)) {
	    chkHosteler[i].setSelected(false);
	} else if (temp.hosteler.get().equals("Yes")) {
	    chkHosteler[i].setSelected(true);
	} else if (temp.hosteler.get().equals("No")) {
	    chkHosteler[i].setSelected(false);
	}
	txtContactNo[i].setText(temp.contactno.get());
	txtWhatsappNo[i].setText(temp.whatsappno.get());
	if (txtContactNo[i].getText() != null && txtWhatsappNo[i].getText() != null) {
	    if (!"".equals(txtContactNo[i].getText()) && !"".equals(txtWhatsappNo[i].getText())) {
		if (txtContactNo[i].getText().equals(txtWhatsappNo[i].getText())) {
		    chkSameWhatsapp[i].setSelected(true);
		} else {
		    chkSameWhatsapp[i].setSelected(false);
		}
	    } else {
		chkSameWhatsapp[i].setSelected(false);
	    }
	}

	txtEmail[i].setText(temp.email.get());
	txtName[i].setText(temp.name.get());
	comboBranch[i].setValue(temp.branch.get());
    }

    @FXML
    public void userSubmit(ActionEvent event) throws SQLException, ClassNotFoundException {
	String teamID;
	if (enteredTeamID.equals("")) {
	    teamID = "" + currentTeamID;
	} else {
	    teamID = enteredTeamID;
	}
	Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.CANCEL);
	alert.setTitle("μCR");
	alert.setHeaderText("Register with Team ID: " + teamID + "?");
	alert.showAndWait();
	if (alert.getResult() == ButtonType.YES) {
	    getFormData();
	    for (int i = 0; i < totalMem; i++) {
		newData[i].teamid.set(teamID);
		if (existingUsers.contains(newData[i].enrollmentno.get())) {
		    newData[i].updateData("data1styr");
		} else {
		    newData[i].addData("data1styr");
		}
		if (registeredUsers.contains(newData[i].enrollmentno.get())) {
		    newData[i].updateData("kitreg");
		} else {
		    newData[i].addData("kitreg");
		}
	    }
	    currentTeamID = 0;
	    enteredTeamID = "";/*
	    DirectoryChooser directory = new DirectoryChooser();
	    directory.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
	    if (directory != null) {
		connectDB dbConn = new connectDB();
		String statement = "CALL CSVWRITE('" + directory.getInitialDirectory() + "\\KitReg.csv', 'select * from kitreg')";
		dbConn.addDataDB(statement);
	    }*/
	    startUserScreenController();
	}
    }

    public void getFormData() {
	for (int i = 0; i < totalMem; i++) {
	    newData[i].enrollmentno.set(txtEnrollmentNo[i].getText());
	    if (registeredUsers.contains(newData[i].enrollmentno.get())) {
		lblSubmitStatus.setText(newData[i].enrollmentno + " already registered.");
	    } else {
		lblSubmitStatus.setText("");
	    }
	    newData[i].name.set(txtName[i].getText());
	    newData[i].branch.set(comboBranch[i].getValue());
	    newData[i].sector.set((String) radio[i].getSelectedToggle().getUserData());
	    newData[i].contactno.set(txtContactNo[i].getText());
	    newData[i].whatsappno.set(txtWhatsappNo[i].getText());
	    if ("".equals(txtContactNo[i].getText()) || txtContactNo[i].getText() == null) {
		newData[i].contactno.set("null");
	    }
	    if ("".equals(txtWhatsappNo[i].getText()) || txtWhatsappNo[i].getText() == null) {
		newData[i].whatsappno.set("null");
	    }
	    newData[i].email.set(txtEmail[i].getText());
	    newData[i].amount.set(txtAmount.getText());
	    if (newData[i].email.get() == null) {
		newData[i].email.set("");
	    }
	    if (comboBranch[i].getValue() == null) {
		newData[i].branch.set("");
	    }
	    if (chkHosteler[i].isSelected()) {
		newData[i].hosteler.set("Yes");
	    } else {
		newData[i].hosteler.set("No");
	    }
	    if (chkPayment.isSelected()) {
		newData[i].payment.set("Yes");
	    } else {
		newData[i].payment.set("No");
	    }
	}
    }

    @FXML
    public void userLogout(ActionEvent event) throws IOException {
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
    public void sameWhatsapp(ActionEvent event) {
	for (int i = 0; i < totalMem; i++) {
	    if (chkSameWhatsapp[i].isSelected()) {
		txtWhatsappNo[i].setText(txtContactNo[i].getText());
	    }
	}
    }

    @FXML
    public void totalmemChanged(ActionEvent event) {
	if (!"<Select>".equals(comboTotalMem.getValue())) {
	    totalMem = (int) comboTotalMem.getValue();
	    boolean disable = false;
	    for (int i = 0; i < 4; i++) {
		disableBox(i, disable);
		if (i >= totalMem) {
		    clearBox(i);
		}
		if (i == totalMem - 1) {
		    disable = true;
		}
	    }
	}
    }

    public void paymentChanged(ActionEvent event) {
	if (chkPayment.isSelected()) {
	    txtAmount.setText("2000");
	} else {
	    txtAmount.setText("0");
	}
    }

    public void disableBox(int i, boolean value) {
	txtEnrollmentNo[i].setDisable(value);
	txtName[i].setDisable(value);
	comboBranch[i].setDisable(value);
	radioSector62[i].setDisable(value);
	radioSector128[i].setDisable(value);
	chkHosteler[i].setDisable(value);
	txtContactNo[i].setDisable(value);
	chkSameWhatsapp[i].setDisable(value);
	txtWhatsappNo[i].setDisable(value);
	txtEmail[i].setDisable(value);
    }

    public void clearBox(int i) {
	if (i < 4) {
	    txtEnrollmentNo[i].setText("");
	    txtName[i].setText("");
	    comboBranch[i].setValue("<Select>");
	    radioSector62[i].setSelected(true);
	    radioSector128[i].setSelected(false);
	    chkHosteler[i].setSelected(false);
	    txtContactNo[i].setText("");
	    chkSameWhatsapp[i].setSelected(false);
	    txtWhatsappNo[i].setText("");
	    txtEmail[i].setText("");
	}
    }

    @FXML
    public void startUserScreenController() throws SQLException, ClassNotFoundException {
	txtEnrollmentNo = new TextField[]{txtEnrollmentNo1, txtEnrollmentNo2, txtEnrollmentNo3, txtEnrollmentNo4};
	txtName = new TextField[]{txtName1, txtName2, txtName3, txtName4};
	comboBranch = new ComboBox[]{comboBranch1, comboBranch2, comboBranch3, comboBranch4};
	radioSector62 = new RadioButton[]{radioSector621, radioSector622, radioSector623, radioSector624};
	radioSector128 = new RadioButton[]{radioSector1281, radioSector1282, radioSector1283, radioSector1284};
	chkHosteler = new CheckBox[]{chkHosteler1, chkHosteler2, chkHosteler3, chkHosteler4};
	txtContactNo = new TextField[]{txtContactNo1, txtContactNo2, txtContactNo3, txtContactNo4};
	chkSameWhatsapp = new CheckBox[]{chkSameWhatsapp1, chkSameWhatsapp2, chkSameWhatsapp3, chkSameWhatsapp4};
	txtWhatsappNo = new TextField[]{txtWhatsappNo1, txtWhatsappNo2, txtWhatsappNo3, txtWhatsappNo4};
	txtEmail = new TextField[]{txtEmail1, txtEmail2, txtEmail3, txtEmail4};
	radio = new ToggleGroup[]{radio1, radio2, radio3, radio4};
	registeredUsers = new ArrayList<>();
	existingUsers = new ArrayList<String>();/*
	radioPaytm = new RadioButton();
	radioCash = new RadioButton();
	radioTogglePayment = new ToggleGroup();*/
	chkPayment.setSelected(false);
	txtAmount.setText("0");

	enteredTeamID = "";
	comboTotalMem.setItems(totalmemList);
	comboTotalMem.setValue("<Select>");
	userData temp = new userData();
	existingUsers = temp.getAllUsers("data1styr");
	registeredUsers = temp.getAllUsers("kitreg");
	currentTeamID = temp.getCurrentTeamID();
	lblTeamid.setText("" + currentTeamID);
	lblSubmitStatus.setText("");
	newData = new userData[4];
	for (int i = 0; i < 4; i++) {
	    disableBox(i, true);
	    clearBox(i);
	    newData[i] = new userData();
//            Set possibleSuggesions
//            Set<String> autoCompletions = new HashSet<>(possibleSuggesions);
//            SuggestionProvider<String> possibleSuggesions = SuggestionProvider.create(autoCompletions);
//            new AutoCompletionTextFieldBinding<>(txtEnrollmentNo[i], possibleSuggesions);
//
//            Set<String> filteredAutoCompletions = new HashSet<>possibleSuggesions;
//            possibleSuggesions.clearSuggestions();
//            possibleSuggesions.addPossibleSuggestions(filteredAutoCompletions);
	    comboBranch[i].setItems(branchList);
	    radioSector62[i].setUserData("62");
	    radioSector128[i].setUserData("128");
	    TextFields.bindAutoCompletion(txtEnrollmentNo[i], existingUsers);
	}
	disableBox(0, true);
	btnAdminBack.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
	try {
	    startUserScreenController();
	} catch (SQLException | ClassNotFoundException ex) {
	    System.out.println(ex);
	}
    }
}
