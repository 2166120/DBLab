package com.dblab.group2;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.io.IOException;
import java.sql.*;


public class InterviewApp {
	
	private final String DBIP = "54.169.15.228";
	private final String DBURL = "jdbc:mysql://" + DBIP + ":3306/Group2";
	private final String USER = "Group2";
	private final String PASSWORD  = "mybirthday";
	
	private Connection con;
	private Statement sqlStmnt;
	private PreparedStatement ps; 
	
	private static InterviewApp app;
	private static Scanner kbd = new Scanner(System.in);
	
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
		Scanner s = new Scanner(System.in);
		
		while(true){
			try {
				handleUserRequest();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				System.err.println("Something went wrong\nPress enter to continue");
				app.pauseStream();
				continue;
			}
		}
	}
	
	public static void displayMenu() {
		System.out.println("-------Read-------");
		System.out.println("<1> Show Interviewers");
		System.out.println("<2> Show Interview Schedules");
		System.out.println("<3> Show Applicants");
		System.out.println("<4> Show Panel");
		System.out.println("------Create------");
		System.out.println("<5> Add new Interviewer");
		System.out.println("<6> Add new Interview Schedule");
		System.out.println("<7> Add new Applicant");
		System.out.println("<8> Add new Panel");
		System.out.println("------Update------");
		System.out.println("<9> Edit Interviewer Info");
		System.out.println("<10> Edit Interview Schedule");
		System.out.println("<11> Edit Applicant Info");
		System.out.println("<12> Add Score");
		System.out.println("------Delete-----");
		System.out.println("<13> Remove Interviewer");
		System.out.println("<14> Remove Interview Schedule");
		System.out.println("<15> Remove Applicant");
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
				app.showInterviewSchedules();
				break;
			case 3:
				app.showApplicants();
				break;
			case 4:
				app.showPanel();
				break;
			case 5:
				app.addNewInterviewer();
				break;
			case 6:
				app.addNewInterviewSchedule();
				break;
			case 7:
				app.addNewApplicant();
				break;
			case 8:
				app.addNewPanel();
				break;
			case 9:
				app.editInterviewerInfo();
				break;
			case 10:
				app.editInterviewSchedule();
				break;
			case 11:
				app.editApplicatInfo();
				break;
			case 12:
				app.addScore();
				break;
			case 13:
				app.removeInterviewer();
				break;
			case 14:
				app.removeInterviewSchedule();
				break;
			case 15:
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
		String answer = kbd.next();
		
		while( answer.equals("y") || answer.equals("Y") ){
			System.out.print("Enter the interviewer ID : ");
			int id = kbd.nextInt();
			
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
			answer = kbd.next();
		}
		
	}
	
	public void showInterviewSchedules() throws SQLException {
		ResultSet res = sqlStmnt.executeQuery("SELECT * FROM Group2.interviewsched");
		System.out.printf("%5s	%-20s%-20s%-20s%n","Sched ID","Time", "Date", "Applicant ID");
		
		while(res.next()){
			System.out.printf("%1d	%-25s%-25s%-28d%n",res.getInt(1),res.getString(2),res.getString(3),
					res.getInt(4));
		}
		
		System.out.println("Press enter to continue");
		pauseStream();
	}
	
	public void showApplicants() throws SQLException {
		ResultSet res = sqlStmnt.executeQuery("SELECT * FROM Group2.applicant");
		System.out.printf("%5s	%-20s%-20s%-20s%n","ID","First Name", "Last Name", "Resume");
		
		while(res.next()){
			System.out.printf("%5d	%-20s%-20s%-20s%n",res.getInt(1),res.getString(2),res.getString(3),
					res.getString(4));
		}
		System.out.println("Press enter to continue");
		pauseStream();
	}
	
	public void showPanel() throws SQLException {
		System.out.print("Enter the Schedule ID of the panel you want to view: ");
		int id = kbd.nextInt();
		ResultSet res = sqlStmnt.executeQuery(
				"SELECT interviewerid, first_name, last_name, time, "
				+ "date FROM panel NATURAL JOIN interviewer NATURAL JOIN interviewsched WHERE schedid = "+ id + ";");
		
		System.out.println("Showing the panel for Schedule ID "+id+":");
		res.next();
		System.out.println("Time: "+res.getString(4));
		System.out.println("Date: "+res.getString(5));
		res.previous();
		System.out.printf("%15s  %-20s%-20s%n", "Interviewer ID", "First Name", "Last Name");
		
		while (res.next()) {
			System.out.printf("%15s  %-20s%-20s%n", res.getInt(1), res.getString(2), res.getString(3));
		}
		System.out.println("Press enter to continue");
		pauseStream();
		
	}
	
	public void addNewInterviewer() throws SQLException{
		String firstname;
		String lastname;
		char avail;
		String choice; 
	
		do{
			System.out.print("Please enter the first name of the interviewer: ");
			firstname = kbd.next();
			System.out.print("Please enter the last name of the interviewer: ");
			lastname = kbd.next();
			System.out.print("Please enter the availability of the interviewer: ");
			avail = kbd.next().charAt(0);
			
			String stmnt = "INSERT INTO Group2.interviewer(first_name,last_name,availability) values(?,?,?)";
			
			PreparedStatement ps = con.prepareStatement(stmnt);
			ps.setString(1, firstname);
			ps.setString(2, lastname);
			ps.setString(3, String.valueOf(avail));
			ps.executeUpdate();
					
			System.out.println( firstname + " " + lastname + " was successfully added as an interviewer.");
			System.out.print("Would you like to add another applicant? <y/*> ");
			choice = kbd.next().toLowerCase();
		}while(choice.equals("y"));
	}
	
	public void addNewInterviewSchedule() {
		try {
			String choice;
			
			do {
				System.out.print("Please enter the time of interview (ex: 8:00-9:00): ");
				String time = kbd.nextLine();
				System.out.print("Please enter the date of interview (ex: 2017-12-13): ");
				String date = kbd.nextLine();
				
				
				String query = " insert into Group2.interviewsched (time,date)"
				        + " values (?, ?)";
				
				PreparedStatement ps = con.prepareStatement(query);
			    ps.setString (1, time);
			    ps.setString (2, date);
			    
			    ps.execute();
			    
			    System.out.print("Would you like to add another schedule? <y/*> ");
			    choice = kbd.nextLine();
			}while(choice.equals("y") || choice.equals("Y"));
		}
		catch (Exception e){
	      System.err.println("Got an exception!");
	      System.err.println(e.getMessage());
	    }
		
		
	}
	
	public void addNewApplicant() throws SQLException {
		try {
			String choice;
			
			do {
				System.out.print("Please enter the first name of the applicant: ");
				String fName = kbd.next();
				System.out.print("Please enter the last name of the applicant: ");
				String lName = kbd.next();
				System.out.print("Please enter the URL of the resume of the applicant: ");
				String resu = kbd.next();
				
				
				String query = " insert into Group2.applicant (firstname, lastname, resume)"
				        + " values (?, ?, ?)";
				
				PreparedStatement ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
				ps.setString (1, fName);
			    ps.setString (2, lName);
			    ps.setString (3, resu);
			    
			    ps.execute();
			    
			    ResultSet res = ps.getGeneratedKeys();
			    int applicantID = 0;
			    if(res.next()){
			    	applicantID = res.getInt(1);
			    }
			    
			    System.out.print("Please enter the schedule id for the new Applicant : ");
			    //TODO verify
			    int schedId = kbd.nextInt();
			    
			    query = "update Group2.interviewsched set applicantid = ? where schedid = ?";
			    
			    ps = con.prepareStatement(query);
			    ps.setInt(1, applicantID);
			    ps.setInt(2, schedId);
			    
			    ps.execute();
			    
			    System.out.print("Would you like to add another applicant? <y/*> ");
			    choice = kbd.next();
			}while(choice.equals("y") || choice.equals("Y"));
		}
		catch (Exception e){
	      System.err.println("Got an exception!");
	      System.err.println(e.getMessage());
	    }
	}
	
	public void addNewPanel() throws SQLException {
		System.out.print("Enter the schedule ID : ");
		Scanner s = new Scanner(System.in);
		int schedID = s.nextInt();
		String answer = "Y";
		String query = "";
		
		while(answer.equalsIgnoreCase("Y")){
			System.out.print("Enter Interviewer ID : ");
			int id = s.nextInt();
			
			if(interviewerExits(id)){
				query = "INSERT INTO `Group2`.`panel` (`schedid`, `interviewerid`) VALUES (?, ?)";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setInt(1, schedID);
				ps.setInt(2, id);
				ps.execute();
			}else{
				System.out.println("can't find interviewer on database");
			}
			
			System.out.print("Add another ? <y/*> ");
			answer = s.next();
			
		}
		
	}
	
	public boolean interviewerExits(int id) throws SQLException{
		
		String query = "select * from interviewer where interviewerid = " + id;
		
		return sqlStmnt.executeQuery(query).next();
		
	}
	
	public void editInterviewerInfo() throws SQLException{
		String query = null;
		try {
	    	System.out.print("Please enter the ID of the interviewer you want to edit: ");
	    	int id = kbd.nextInt();
	    	System.out.println("Which column would you like to edit?");
	    	System.out.println("1. first name");
	    	System.out.println("2. last name");
	    	System.out.println("3. availability"); 	
	    	System.out.print("Enter choice: ");
	    	int choice = kbd.nextInt();
	    	
	    	switch(choice) {
	    	case 1:
	    		String newFirstName;
	    		System.out.print("Enter new first name: ");
	    		newFirstName = kbd.next();
	    		query = "update Group2.interviewer  set first_name = ? where interviewerid = ?";
				ps = con.prepareStatement(query);
				ps.setString(1, newFirstName);
				ps.setString(2, String.valueOf(id));  
				ps.executeUpdate();
	    		System.out.println("Successfuly edited!");
	    		break;
	    	case 2:
	    		String newLastName;
	    		System.out.print("Enter new last name: ");
	    		newLastName = kbd.next();
	    		query = "update Group2.interviewer  set first_name = ? where interviewerid = ?";
				ps = con.prepareStatement(query);
				ps.setString(1, newLastName);
				ps.setString(2, String.valueOf(id));  
				ps.executeUpdate();
	    		System.out.println("Successfuly edited!");
	    		break;
	    	case 3:
	    		char avail;
	    		System.out.print("Enter availabilty status: ");
	    		avail = kbd.next().charAt(0);
	    		query = "update Group2.interviewer  set availability = ? where interviewerid = ?";
				ps = con.prepareStatement(query);
				ps.setString(1, String.valueOf(avail));
				ps.setString(2, String.valueOf(id));  
				ps.executeUpdate();
	    		System.out.println("Successfuly edited!");
	    		break;
	    	}
	    }
	    catch(Exception e) {
	    	System.err.println("Got an exception!");
	    	System.err.println(e.getMessage());
	    }
	}
	
	public void editInterviewSchedule() {
		String query;
		try {
			System.out.print("Please enter the interview schedule ID you want to edit: ");
			int id = kbd.nextInt();
			System.out.println("Which column would you like to edit?");
			System.out.println("1. time");
			System.out.println("2. date");
			System.out.println("3. applicant");
			System.out.print("Enter choice: ");
	    	int choice = kbd.nextInt();
	    	
	    	switch(choice) {
	    	case 1:
	    		String newTime;
	    		System.out.print("Enter new time: ");
	    		newTime = kbd.next();
	    		query = "update Group2.interviewsched set time = ? where schedid = ?";
				ps = con.prepareStatement(query);
				ps.setString(1, newTime);
				ps.setString(2, String.valueOf(id));  
				ps.executeUpdate();
	    		System.out.println("Successfuly edited!");
	    		break;
	    	case 2:
	    		String newDate;
	    		System.out.print("Enter new date: ");
	    		newDate = kbd.next();
	    		query = "update Group2.interviewsched  set date = ? where schedid = ?";
				ps = con.prepareStatement(query);
				ps.setString(1, newDate);
				ps.setString(2, String.valueOf(id));  
				ps.executeUpdate();
	    		System.out.println("Successfuly edited!");
	    		break;
	    	case 3:
	    		String newApplicant;
	    		System.out.print("Enter new applicant ID: ");
	    		newApplicant = kbd.next();
	    		query = "update Group2.interviewsched  set applicantid = ? where schedid = ?";
				ps = con.prepareStatement(query);
				ps.setString(1, newApplicant);
				ps.setString(2, String.valueOf(id));  
				ps.executeUpdate();
	    		System.out.println("Successfuly edited!");
	    		break;
	    	}
	    }
		catch(Exception e) {
	    	System.err.println("Got an exception!");
	    	System.err.println(e.getMessage());
	    }
	}
	
	public void editApplicatInfo() {
		String query;
	    try {
	    	System.out.print("Please enter the ID of the applicant you want to edit: ");
	    	int id = kbd.nextInt();
	    	System.out.println("Which column would you like to edit?");
	    	System.out.println("1. first name");
	    	System.out.println("2. last name");
	    	System.out.println("3. resume"); 	
	    	System.out.print("Enter choice: ");
	    	int choice = kbd.nextInt();
	    	
	    	switch(choice) {
	    	case 1:
	    		String newFirstName;
	    		System.out.print("Enter new first name: ");
	    		newFirstName = kbd.next();
	    		query = "update Group2.applicant  set firstname = ? where applicantid = ?";
				ps = con.prepareStatement(query);
				ps.setString(1, newFirstName);
				ps.setString(2, String.valueOf(id));  
				ps.executeUpdate();
	    		System.out.println("Successfuly edited!");
	    		break;
	    	case 2:
	    		String newLastName;
	    		System.out.print("Enter new last name: ");
	    		newLastName = kbd.next();
	    		query = "update Group2.applicant  set lastname = ? where applicantid = ?";
				ps = con.prepareStatement(query);
				ps.setString(1, newLastName);
				ps.setString(2, String.valueOf(id));  
				ps.executeUpdate();
	    		System.out.println("Successfuly edited!");
	    		break;
	    	case 3:
	    		String newResume;
	    		System.out.print("Enter new resume link: ");
	    		newResume = kbd.next();
	    		query = "update Group2.applicant  set resume = ? where applicantid = ?";
				ps = con.prepareStatement(query);
				ps.setString(1, newResume);
				ps.setString(2, String.valueOf(id));  
				ps.executeUpdate();
	    		System.out.println("Successfuly edited!");
	    		break;
	    	}
	    }
	    catch(Exception e) {
	    	System.err.println("Got an exception!");
	    	System.err.println(e.getMessage());
	    }
	}

	
	public void addScore() throws SQLException{
		
		Scanner s = new Scanner(System.in);
		System.out.print("Please enter the schedule ID : ");
		int schedID = s.nextInt();
		System.out.print("Please enter the interviewer ID : ");
		int interviewerID = s.nextInt();
		System.out.print("Please enter the score out of 10 : ");
		int score = s.nextInt();
		
		String query = "UPDATE `Group2`.`panel` SET `score`=? WHERE `schedid`=? and`interviewerid`=?";
		ps = con.prepareStatement(query);
		ps.setInt(1, score);
		ps.setInt(2, schedID);
		ps.setInt(3, interviewerID);
		
		ps.execute();
		
		System.out.print("Successfully Updated score! Press enter to continue");
		
		
	}
	
	public void removeInterviewer() {
		try {
			System.out.print("Please enter the ID of the interviewer you want to remove: ");
			int id = kbd.nextInt();
				
			String query = "delete from Group2.interviewer where interviewerid = ?";
			ps = con.prepareStatement(query);
			ps.setInt(1, id);  
			ps.execute();
				
		}	
		catch (Exception e){
		      System.err.println("Got an exception!");
		      System.err.println(e.getMessage());
		}
	}
	
	public void removeInterviewSchedule() {
		try {
			System.out.print("Please enter the ID of the interview schedule you want to remove: ");
			int id = kbd.nextInt();
				
			String query = "delete from Group2.interviewsched where schedid = ?";
			ps = con.prepareStatement(query);
			ps.setInt(1, id);  
			ps.execute();
				
		}	
		catch (Exception e){
		      System.err.println("Got an exception!");
		      System.err.println(e.getMessage());
		}
	}
	
	public void removeApplicantInfo() {
		try {		
			System.out.print("Please enter the ID of the applicant you want to remove: ");
			int id = kbd.nextInt();
				
			String query = "delete from applicant where applicantid = ?";
			ps = con.prepareStatement(query);
			ps.setInt(1, id);  
			ps.execute();
				
		}	
		catch (Exception e){
		      System.err.println("Got an exception!");
		      System.err.println(e.getMessage());
		}
	}
	
	private void pauseStream(){
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
