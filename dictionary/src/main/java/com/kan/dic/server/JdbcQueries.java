package com.kan.dic.server;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

public class JdbcQueries {

	public ResultSet selectQuery(String query) {

		// Read properties file.
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("jdbc.properties");

		Properties properties = new Properties();

		// load the inputStream using the Properties
		try {
			properties.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String driverClass = properties.getProperty("jdbc.driverClassName");
		String url = properties.getProperty("jdbc.url");
		String username = properties.getProperty("jdbc.username");
		String password = properties.getProperty("jdbc.password");

		Logger.getLogger("Server:").info(
				"driverClass:" + driverClass + "\n" + "url:" + url + "\n"
						+ "username:" + username + "\n" + "password:"
						+ password);

		try {
			Class.forName(driverClass).newInstance();
			Connection conn = DriverManager.getConnection(url, username,
					password);
			Statement stmt = conn.createStatement();

			if (stmt.execute(query)) {
				return stmt.getResultSet();
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
