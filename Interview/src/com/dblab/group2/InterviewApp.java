package com.dblab.group2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InterviewApp {
	
	private final String DBIP = "54.169.15.228";
	private final String DBURL = "jdbc:mysql://" + DBIP + ":3306/Group2";
	private final String USER = "Group2";
	private final String PASSWORD  = "mybirthday";
	
	private Connection con;
	
	public InterviewApp(){
		
		try {
			connectToDb();
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

	public static void showInterviewers() {
	
	}
	
	public static void showInterviewSchedules() {
		
	}
	
	public static void showApplicants() {
		
	}
	
	public static void addNewInterviewer() {
		
	}
	
	public static void addNewInterviewSchedule() {
		
	}
	
	public static void editInterviewerInfo() {
		
	}
	
	public static void editInterviewSchedule() {
		
	}
	
	public static void editApplicatInfo() {
		
	}
	
	public static void removeInterviewer() {
		
	}
	
	public static void removeInterviewSchedule() {
		
	}
	
	public static void removeApplicantInfo() {
		
	}
}
