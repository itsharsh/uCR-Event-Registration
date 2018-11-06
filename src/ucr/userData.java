/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Harsh Kumar Singh
 */
public class userData {

    final SimpleStringProperty teamid;
    final SimpleStringProperty payment;
    final SimpleStringProperty amount;
    final SimpleStringProperty enrollmentno;
    final SimpleStringProperty name;
    final SimpleStringProperty branch;
    final SimpleStringProperty sector;
    final SimpleStringProperty hosteler;
    final SimpleStringProperty contactno;
    final SimpleStringProperty whatsappno;
    final SimpleStringProperty email;
    connectDB dbConn = new connectDB();

    public userData() {
	this.teamid = new SimpleStringProperty();
	this.enrollmentno = new SimpleStringProperty();
	this.name = new SimpleStringProperty();
	this.branch = new SimpleStringProperty();
	this.sector = new SimpleStringProperty();
	this.hosteler = new SimpleStringProperty();
	this.contactno = new SimpleStringProperty();
	this.whatsappno = new SimpleStringProperty();
	this.email = new SimpleStringProperty();
	this.payment = new SimpleStringProperty();
	this.amount = new SimpleStringProperty();
    }

    public userData(String teamid, String enrollmentno, String name, String branch, String sector, String hosteler, String contactno, String whatsappno, String email, String payment, String amount) {
	this.teamid = new SimpleStringProperty(teamid);
	this.enrollmentno = new SimpleStringProperty(enrollmentno);
	this.name = new SimpleStringProperty(name);
	this.branch = new SimpleStringProperty(branch);
	this.sector = new SimpleStringProperty(sector);
	this.hosteler = new SimpleStringProperty(hosteler);
	this.contactno = new SimpleStringProperty(contactno);
	this.whatsappno = new SimpleStringProperty(whatsappno);
	this.email = new SimpleStringProperty(email);
	this.payment = new SimpleStringProperty(payment);
	this.amount = new SimpleStringProperty(amount);
    }

    public List<String> getAllUsers(String tableName) throws ClassNotFoundException, SQLException {
	List<String> list = new ArrayList<String>();
	String query = "select enrollmentno from " + tableName;
	ResultSet resultSet = dbConn.getDataDB(query);
	if (resultSet != null) {
	    while (resultSet.next()) {
		list.add(resultSet.getString("enrollmentno"));
	    }
	    resultSet.close();
	}
	return list;
    }

    public int getCurrentTeamID() throws SQLException, ClassNotFoundException {
	ResultSet resultSet = dbConn.getDataDB("select max(teamid) from kitreg");
	if (resultSet != null) {
	    if (resultSet.next()) {
		int tempTeamid = resultSet.getInt("max(teamid)") + 1;
		if (tempTeamid < 100) {
		    tempTeamid = 101;
		}
		resultSet.close();
		return tempTeamid;
	    }
	}
	return 0;
    }

    public void getInfo(String enteredEnrollmenNo) throws ClassNotFoundException, SQLException {
	String query = "select * from data1styr where enrollmentno=" + enteredEnrollmenNo;
	ResultSet resultSet = dbConn.getDataDB(query);
	if (resultSet != null) {
	    if (resultSet.next()) {
		this.teamid.set("");
		this.enrollmentno.set(resultSet.getString("enrollmentno"));
		this.name.set(resultSet.getString("name"));
		this.branch.set(resultSet.getString("branch"));
		this.sector.set(resultSet.getString("sector"));
		this.hosteler.set(resultSet.getString("hosteler"));
		this.contactno.set(resultSet.getString("contactno"));
		this.whatsappno.set(resultSet.getString("whatsappno"));
		this.email.set(resultSet.getString("email"));
		resultSet.close();
	    }
	}
    }

    public void addData(String tableName) throws SQLException, ClassNotFoundException {
	String statement = "insert into " + tableName + " values(";
	if (tableName.equals("kitreg")) {
	    statement = statement + this.teamid.get() + ",";
	}
	statement = statement
		+ this.enrollmentno.get() + ",'"
		+ this.name.get() + "','"
		+ this.branch.get() + "',"
		+ this.sector.get() + ",'"
		+ this.hosteler.get() + "',"
		+ this.contactno.get() + ","
		+ this.whatsappno.get() + ",'"
		+ this.email.get() + "'";
	if (tableName.equals("kitreg")) {
	    statement = statement
		    + ",'"
		    + this.payment.get() + "',"
		    + this.amount.get();
	}
	statement = statement + ")";
	if (dbConn.isConnected()) {
	    dbConn.addDataDB(statement);
	}
    }

    public void updateData(String tableName) throws ClassNotFoundException, SQLException {
	String statement = "update " + tableName + " set "
		+ "name='" + this.name.get()
		+ "',branch='" + this.branch.get()
		+ "',sector=" + this.sector.get()
		+ ",hosteler='" + this.hosteler.get()
		+ "',contactno=" + this.contactno.get()
		+ ",whatsappno=" + this.whatsappno.get()
		+ ",email='" + this.email.get() + "'";
	if (tableName.equals("kitreg")) {
	    statement = statement
		    + ",payment='" + this.payment.get()
		    + "',amount=" + this.amount.get();

	}
	statement = statement + " where enrollmentno=" + this.enrollmentno.get();
	if (dbConn.isConnected()) {
	    dbConn.addDataDB(statement);
	}
    }

    public void deleteData(String enrollmentno) throws ClassNotFoundException, SQLException {
	String statement = "delete from kitreg where enrollmentno=" + enrollmentno;
	dbConn.addDataDB(statement);
    }

    public String getTeamid() {
	return teamid.get();
    }

    public String getEnrollmentno() {
	return enrollmentno.get();
    }

    public String getName() {
	return name.get();
    }

    public String getBranch() {
	return branch.get();
    }

    public String getSector() {
	return sector.get();
    }

    public String getHosteler() {
	return hosteler.get();
    }

    public String getContactno() {
	return contactno.get();
    }

    public String getWhatsappno() {
	return whatsappno.get();
    }

    public String getEmail() {
	return email.get();
    }

    public String getPayment() {
	return payment.get();
    }

    public String getAmount() {
	return amount.get();
    }
}
