/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucr;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class viewDataScreenController implements Initializable {

    @FXML
    TableView<userData> tableView;
    @FXML
    TableColumn<userData, String> columnTeamID;
    @FXML
    TableColumn<userData, String> columnEnrollmentNo;
    @FXML
    TableColumn<userData, String> columnName;
    @FXML
    TableColumn<userData, String> columnBranch;
    @FXML
    TableColumn<userData, String> columnSector;
    @FXML
    TableColumn<userData, String> columnHosteler;
    @FXML
    TableColumn<userData, String> columnContactNo;
    @FXML
    TableColumn<userData, String> columnWhatsappNo;
    @FXML
    TableColumn<userData, String> columnEmail;
    @FXML
    TableColumn<userData, String> columnPayment;
    @FXML
    TableColumn<userData, String> columnAmount;

    public ObservableList<userData> list;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
	try {
	    list = FXCollections.observableArrayList();
	    setCellValue();
	    connectDB dbConn = new connectDB();
	    String query = "select * from kitreg";
	    ResultSet resultSet = dbConn.getDataDB(query);
	    while (resultSet.next()) {
		list.add(new userData(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9),resultSet.getString(10),resultSet.getString(11)));
	    }
	    tableView.setItems(list);
	} catch (ClassNotFoundException | SQLException ex) {
	    System.out.println(ex);
	}
    }

    private void setCellValue() {
	columnTeamID.setCellValueFactory(new PropertyValueFactory<>("teamid"));
	columnEnrollmentNo.setCellValueFactory(new PropertyValueFactory<>("enrollmentno"));
	columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
	columnBranch.setCellValueFactory(new PropertyValueFactory<>("branch"));
	columnSector.setCellValueFactory(new PropertyValueFactory<>("sector"));
	columnHosteler.setCellValueFactory(new PropertyValueFactory<>("hosteler"));
	columnContactNo.setCellValueFactory(new PropertyValueFactory<>("contactno"));
	columnWhatsappNo.setCellValueFactory(new PropertyValueFactory<>("whatsappno"));
	columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
	columnPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
	columnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }
}
