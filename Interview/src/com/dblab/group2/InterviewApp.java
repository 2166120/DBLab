package com.dblab.group2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InterviewApp {
	
	final String DBIP = "54.169.15.228";
	final String DBURL = "jdbc:mysql://" + DBIP + ":3306/Group2";
	final String USER = "Group2";
	final String PASSWORD  = "mybirthday";
	
	public InterviewApp(){
		
		connectToDb();
		
	}
	
	public void connectToDb(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DBURL,USER,PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		InterviewApp app = new InterviewApp();
	}

}
