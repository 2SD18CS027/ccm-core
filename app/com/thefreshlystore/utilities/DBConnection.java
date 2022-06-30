package com.thefreshlystore.utilities;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	private static Connection connection = null;
	private static String devPassword = "root1234";
	
	public static Connection getConnection() {
		createConnection();
		return connection;
	}
	
	public static void changePassword(String password) {
		devPassword = password;
		try {
			connection.close();
		} catch(Exception e) { e.printStackTrace(); }
		connection = null;
	}
	
	public static void createConnection() {
		try {

			if(connection == null) {
				Class.forName("com.mysql.jdbc.Driver");
				String devJDBCUrl = "jdbc:mysql://database-1.cbksmbmjyfb0.ap-south-1.rds.amazonaws.com:3306/the-freshly-store-local";
				String devUserName = "root";
				
				
				connection = DriverManager.getConnection(devJDBCUrl,
						devUserName, devPassword);
				connection.setAutoCommit(false);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
