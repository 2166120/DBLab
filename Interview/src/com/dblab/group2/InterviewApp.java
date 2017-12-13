package com.dblab.group2;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.*;


public class InterviewApp {
	
	private final String DBIP = "54.169.15.228";
	private final String DBURL = "jdbc:mysql://" + DBIP + ":3306/Group2";
	private final String USER = "Group2";
	private final String PASSWORD  = "mybirthday";
	
	private Connection con;
	private Statement sqlStmnt;
	
	private static InterviewApp app;
	
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

	public void disconnectFromDb() throws SQLException{
		con.close();
	}
	
	public static void main(String[] args) {
		app = new InterviewApp();
		displayMenu();
<<<<<<< HEAD
		try {
			
			app.addNewApplicant();
		} catch (SQLException e) {
			// TODO handle all sql exceptions
			e.printStackTrace();
=======
		
		Scanner s = new Scanner(System.in);
		
		while(true){
			try {
				handleUserRequest();
			} catch (SQLException e) {
				System.out.println("Something went wrong\nPress enter to continue");
				s.nextLine();
				continue;
			}
>>>>>>> 82b0d43f9be84294eb05a69f6edf4a60e396d86e
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
	
	public static void handleUserRequest() throws SQLException{
		Scanner s = new Scanner(System.in);
		int choice = 0;
		String buffer = "";
		
		while(true){
			displayMenu();
			System.out.print("Enter your choice : ");
			buffer = s.next();
			try{
				choice = Integer.parseInt(buffer);
				break;
			}catch (Exception e) {
				System.out.println("That's an invalid input!");
				continue;
			}
		}
		
		switch(choice){
			case 0:
				System.out.println("Good bye :(");
				app.disconnectFromDb();
				System.exit(0);
				break;
			case 1:
				app.showInterviewers();
				break;
			case 2:
				//TODO add showscheds
				break;
			case 3:
				app.showApplicants();
				break;
			case 4:
				app.addNewInterviewer();
				break;
			case 5:
				app.addNewInterviewSchedule();
				break;
			case 6:
				//TODO addapplicant
				break;
			case 7:
				app.editInterviewerInfo();
				break;
			case 8:
				app.editInterviewSchedule();
				break;
			case 9:
				app.editApplicatInfo();
				break;
			case 10:
				app.removeInterviewer();
				break;
			case 11:
				app.removeInterviewSchedule();
				break;
			case 12:
				app.removeApplicantInfo();
				break;
			default:
				System.out.println("That is not in the choices!");
		}

		
	}

	public void showInterviewers() throws SQLException {
		
		ResultSet res = sqlStmnt.executeQuery("SELECT * FROM Group2.interviewer");
		System.out.printf("%5s	%-20s%-20s%-20s%n","ID","First Name", "Last Name", "Availability");
		
		while(res.next()){
			
			System.out.printf("%5d	%-20s%-20s%-20s%n",res.getInt(1),res.getString(2),res.getString(3),
					res.getString(4).equals("T")?"Available":"Not Available");
			
		}
		
		System.out.print("Would you like to show an interviewer's schedule ? <y/*> ");
		Scanner s = new Scanner(System.in);
		String answer = s.nextLine();
		
		while( answer.equals("y") || answer.equals("Y") ){
			System.out.print("Enter the interviewer ID : ");
			int id = s.nextInt();
			
			res = sqlStmnt.executeQuery("SELECT `first_name`,`last_name`,`time`,`date` "
					+ "FROM interviewer NATURAL JOIN panel NATURAL JOIN interviewsched WHERE interviewerid=" 
					+ id);
			
			res.first();
			System.out.println("The schedule for " + res.getString(1) + " " + res.getString(2) + " : ");
			res.beforeFirst();
			System.out.printf("%20s%20s%n","Time","Date");
			while(res.next()){
				System.out.printf("%20s%20s%n",res.getString(3),res.getDate(4));
			}
			
			System.out.print("Display another? <y/*> ");
			answer = s.nextLine();
		}
		
	}	

	
	public void showApplicants() throws SQLException {
		ResultSet res = sqlStmnt.executeQuery("SELECT * FROM Group2.applicant");
		System.out.printf("%5s	%-20s%-20s%-20s%n","ID","First Name", "Last Name", "Resume");
		
		while(res.next()){
			System.out.printf("%5d	%-20s%-20s%-20s%n",res.getInt(1),res.getString(2),res.getString(3),
					res.getString(4));
		}
	}
	
	public void addNewInterviewer() {
		
	}
	
	public void addNewInterviewSchedule() {
		
	}
	
	public void addNewApplicant() throws SQLException {
		try {
			
			Scanner s = new Scanner(System.in);
			String choice;
			
			do {
				System.out.print("Please enter the first name of the applicant: ");
				String fName = s.nextLine();
				System.out.print("Please enter the last name of the applicant: ");
				String lName = s.nextLine();
				System.out.print("Please enter the URL of the resume of the applicant: ");
				String resu = s.nextLine();
				
				
				String query = " insert into applicant (firstname, lastname, resume)"
				        + " values (?, ?, ?)";
				
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString (1, fName);
			    ps.setString (2, lName);
			    ps.setString (3, resu);
			    
			    ps.execute();
			    
			    System.out.print("Would you like to add another applicant? <y/*> ");
			    choice = s.nextLine();
			}while(choice.equals("y") || choice.equals("Y"));
		}
		catch (Exception e)
	    {
	      System.err.println("Got an exception!");
	      System.err.println(e.getMessage());
	    }
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
