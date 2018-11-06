/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucr;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Harsh Kumar Singh
 */
public class loginData {

    String username;
    String password;
    String userlevel;

    public void newUser(String uname, String pass, String ulevel) {
	username = uname;
	password = pass;
	switch (ulevel) {
	    case "Coordinator":
		userlevel = "A";
		break;
	    case "Volunteer":
		userlevel = "U";
		break;
	    default:
		userlevel = null;
		break;
	}
    }

    public byte checkLoginDetails() throws SQLException, ClassNotFoundException {
	connectDB dbConn = new connectDB();
	byte finalresult = 0;
	String query = "select * from logindata where username='" + this.username + "'";
	ResultSet resultSet = dbConn.getDataDB(query);
	if (resultSet != null) {
	    if (resultSet.next()) {
		finalresult = 2;
		if (resultSet.getString("password").equals(this.password)) {
		    finalresult = 3;
		    if (resultSet.getString("userlevel").equals(this.userlevel)) {
			finalresult = 4;
		    } else if (resultSet.getString("userlevel").equals("A")) {
			finalresult = 4;
		    }
		}
		resultSet.close();
	    } else {
		finalresult = 1;
	    }
	}
	return finalresult;
    }
}
