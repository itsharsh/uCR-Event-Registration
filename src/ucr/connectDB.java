/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucr;

import static java.lang.System.exit;
import java.sql.*;
import javafx.scene.control.*;

/**
 *
 * @author Harsh Kumar Singh
 */
public class connectDB {

    String uname;
    String pass;
    String ulevel;

    final String dbDriver = "org.h2.Driver";
    final String dbName = "jdbc:h2:./data/ucr";
    final String dbUsername = "admin";
    final String dbPassword = "admin";//----------------------------------------------------------------------------------------------------

    Connection dbConn;

    connectDB() {
	this.uname = null;
	this.pass = null;
	this.ulevel = null;
    }

    public boolean isConnected() throws ClassNotFoundException {
	try {
	    Class.forName(dbDriver);

	    this.dbConn = DriverManager.getConnection(dbName, dbUsername, dbPassword);
	    if (this.dbConn != null) {
		return true;
	    }
	} catch (SQLException ex) {
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setTitle("uCR");
	    alert.setHeaderText("Database Error");
	    alert.setContentText("Please Close existing connection of Database");
	    alert.showAndWait();
	    exit(0);
	}
	return false;
    }

    public ResultSet getDataDB(String query) throws ClassNotFoundException, SQLException {
	if (this.isConnected()) {
	    PreparedStatement preparedStatement = this.dbConn.prepareStatement(query);
	    return preparedStatement.executeQuery();
	}
	return null;
    }

    public void addDataDB(String query) throws ClassNotFoundException, SQLException {
	if (this.isConnected()) {
	    PreparedStatement preparedStatement = this.dbConn.prepareStatement(query);
	    preparedStatement.execute();
	}
    }
}
