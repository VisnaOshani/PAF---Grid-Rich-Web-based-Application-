package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Appointment {

		//A common method to connect to the DB
		private Connection connect()
		{
			Connection con = null;
			String url = "jdbc:mysql://localhost:3306/pafdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				//Provide the correct details: DBServer/DBName, username, password
				con = DriverManager.getConnection(url, "root", "");
			}
			catch (Exception e)
			{e.printStackTrace();}
			return con;
		}

		// insert function
		public String insertAppointment(String id, String hospitalid, String patientid, String date, String time, String description, boolean status)
		{
			String output = "";
			try
			{
				Connection con = connect();
					if (con == null)
					{return "Error while connecting to the database for inserting."; }
					
					// create a prepared statement
					String query = " insert into appointments(`id`,`hospitalid`,`patientid`,`date`,`time`,`description`,`status`)"
							+ " values (?,?,?,?,?,?,?)";
					
					PreparedStatement preparedStmt = con.prepareStatement(query);
					
					// binding values
					preparedStmt.setString(1, id);
					preparedStmt.setString(2, hospitalid);
					preparedStmt.setString(3, patientid);
					preparedStmt.setString(4, date);
					preparedStmt.setString(5, time);
					preparedStmt.setString(6, description);
					preparedStmt.setBoolean(7, status);

					// execute the statement
					preparedStmt.execute();
					con.close();
					
					String newAppointment = readAppointment();
					output = "{\"status\":\"success\", \"data\": \"" + newAppointment + "\"}";
				}
				catch (Exception e)
				{
					output = "{\"status\":\"error\", \"data\": \"Error while inserting the appointment details. \"}";
					System.err.println(e.getMessage());
				}
				
				return output;
			}
		
		
			// read function
			public String readAppointment()
			{
				String output = "";
					
				try
				{
					Connection con = connect();
					if (con == null)
					{return "Error while connecting to the database for reading."; }
					
					// Prepare the html table to be displayed
					output = "<table border=\'1\'><tr><th>ID</th><th>Hospital ID</th><th>Patient ID</th><th>Date</th><th>Time</th><th>Description</th><th>Status</th><th>Update</th><th>Remove</th></tr>";
					
					String query = "select * from appointments";
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					
					// iterate through the rows in the result set
					while (rs.next())
					{
						String id = rs.getString("id");
						String hospitalid = rs.getString("hospitalid");
						String patientid = rs.getString("patientid");
						String date =rs.getString("date");
						String time = rs.getString("time");
						String description = rs.getString("description");
						String status = Boolean.toString(rs.getBoolean("status"));
					
						// Add into the html table
						output += "<tr><td><input id=\'hidAppIDUpdate\' name=\'hidAppIDUpdate\' type=\'hidden\' value=\'' + id + '\'>" + id + "</td>";
						output += "<td>" + hospitalid + "</td>";
						output += "<td>" + patientid + "</td>";
						output += "<td>" + date + "</td>";
						output += "<td>" + time + "</td>";
						output += "<td>" + description + "</td>";
						output += "<td>" + status + "</td>";

					
						// buttons
						output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
								+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-id='"
										 + id + "'>" + "</td></tr>";
								
					}
					
					con.close();
					
					// Complete the html table
					output += "</table>";
				}
				catch (Exception e)
				{
					output = "Error while reading the appointment details.";
					System.err.println(e.getMessage());
				}
				return output;
			}
	 		
			
			// Update function
			public String updateAppointment(String hospitalid, String patientid, String date, String time, String description, String status, String id)
			{
			String output = "";
			
			try
			{
					Connection con = connect();
					
					if (con == null)
					{return "Error while connecting to the database for updating."; }
			
					// create a prepared statement
					String query = "update appointments set hospitalid=?, patientid=?, date=?, time=?, description=?, status=? where id=?;";
									
					PreparedStatement preparedStmt = con.prepareStatement(query);
			
					// binding values
					
					preparedStmt.setString(1, hospitalid);
					preparedStmt.setString(2, patientid);
					preparedStmt.setString(3, date);
					preparedStmt.setString(4, time);
					preparedStmt.setString(5, description);
					preparedStmt.setString(6, status);
					preparedStmt.setString(7, id);
			
					System.out.println("update method called: " + id);
					
					// execute the statement
					preparedStmt.execute();
					con.close();
			
					String newAppointment = readAppointment();
					output = "{\"status\":\"success\", \"data\": \"" + newAppointment + "\"}";
			}
			catch (Exception e)
			{
				output = "{\"status\":\"error\", \"data\": \"Error while updating the appointment details. \"}";
				System.err.println(e.getMessage());
			}
				return output;
		}
			
			
		//Delete function
		public String deleteAppointment(String id)
		{
			String output = "";
			
			try
			{
				Connection con = connect();
				
				if (con == null)
				{return "Error while connecting to the database for deleting."; }
			
				// create a prepared statement
				String query = "delete from appointments where id=?";
			
				PreparedStatement preparedStmt = con.prepareStatement(query);
			
				// binding values
				preparedStmt.setString(1, id);
			
				// execute the statement
				preparedStmt.execute();
				con.close();
			
				String newAppointment = readAppointment();
				output = "{\"status\":\"success\", \"data\": \"" + newAppointment + "\"}";
			}
			catch (Exception e)
			{
				output = "{\"status\":\"error\", \"data\":\"Error while deleting the appointment.\"}"; 		
				System.err.println(e.getMessage());
			}
			
			return output;
			}
}