package com.dblab.group2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InterviewApp {
	
	private final String DBIP = "54.169.15.228";
	private final String DBURL = "jdbc:mysql://" + DBIP + ":3306/Group2";
	private final String USER = "Group2";
	private final String PASSWORD  = "mybirthday";
	
	private Connection con;
	private Statement sqlStmnt;
	
	public InterviewApp(){
		
		try {
			connectToDb();
			sqlStmnt = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void connectToDb() throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(DBURL,USER,PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		InterviewApp app = new InterviewApp();
		displayMenu();
		try {
			app.showInterviewers();
		} catch (SQLException e) {
			// TODO handle all sql exceptions
			e.printStackTrace();
		}
	}
	
	public static void displayMenu() {
		System.out.println("-------Read-------");
		System.out.println("<1> Show Interviewers");
		System.out.println("<2> Show Interview Schedules");
		System.out.println("<3> Show Applicants");
		System.out.println("------Create------");
		System.out.println("<4> Add new Interviewer");
		System.out.println("<5> Add new Interview Schedule");
		System.out.println("<6> Add new Applicant");
		System.out.println("------Update------");
		System.out.println("<7> Edit Interviewer Info");
		System.out.println("<8> Edit Interview Schedule");
		System.out.println("<9> Edit Applicant Info");
		System.out.println("------Delete-----");
		System.out.println("<10> Remove Interviewer");
		System.out.println("<11> Remove Interview Schedule");
		System.out.println("<12> Remove Applicant");
		System.out.println("<0> Exit ");
	}

	public void showInterviewers() throws SQLException {
		
		ResultSet res = sqlStmnt.executeQuery("SELECT * FROM Group2.interviewer");
		System.out.printf("%5s	%-20s%-20s%-20s%n","ID","First Name", "Last Name", "Availability");
		
		while(res.next()){
			System.out.printf("%5d	%-20s%-20s%-20s%n",res.getInt(1),res.getString(2),res.getString(3),
					res.getString(4).equals("T")?"Available":"Not Available");
		}
	}
	
	public void showInterviewSchedules() {
		
	}
	
	public void showApplicants() throws SQLException {
		ResultSet res = sqlStmnt.executeQuery("SELECT * FROM Group2.applicant");
		System.out.printf("%5s	%-20s%-20s%-20s%n","ID","First Name", "Last Name", "Resume");
		
		while(res.next()){
			System.out.printf("%5d%-20s%-20s%-20s%n",res.getInt(1),res.getString(2),res.getString(3),
					res.getString(4));
		}
	}
	
	public void addNewInterviewer() {
		
	}
	
	public void addNewInterviewSchedule() {
		
	}
	
	public void editInterviewerInfo() {
		
	}
	
	public void editInterviewSchedule() {
		
	}
	
	public void editApplicatInfo() {
		
	}
	
	public void removeInterviewer() {
		
	}
	
	public void removeInterviewSchedule() {
		
	}
	
	public void removeApplicantInfo() {
		
	}
}
