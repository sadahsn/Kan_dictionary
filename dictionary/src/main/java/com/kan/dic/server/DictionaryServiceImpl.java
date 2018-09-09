package com.kan.dic.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

import com.kan.dic.client.service.DictionaryService;
import com.kan.dic.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DictionaryServiceImpl extends RemoteServiceServlet implements
		DictionaryService {

	public DictionaryServiceImpl() {
		
	}
	public String getLocalSuggestList(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 1 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");
		try {
			getThreadLocalRequest().setCharacterEncoding("utf-8");
			getThreadLocalRequest().setAttribute("Content-Type","application/x-www-form-urlencoded");
			System.out.println("conte type:"+getThreadLocalRequest().getContentType());
			getThreadLocalResponse().setContentType("text/plain");
			getThreadLocalResponse().setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		ResultSet result = selectQuery("select * from LETTER");
		String letter = "";
		try {
			while (result.next()) {
				System.out.println(result.getString("LETTER"));
				letter = result.getString("LETTER");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Hello, " + letter + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
	
	
	private ResultSet selectQuery(String query){
		
		// Read properties file.
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("jdbc.properties");

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
		
		Logger.getLogger("Server:").info("driverClass:"+driverClass + "\n"+
				"url:"+url + "\n"+
				"username:"+username + "\n"+
				"password:"+password);
		
		/*String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url = "jdbc:sqlserver://localhost:1433;databaseName=kandic";
		String username = "mbiis";
		String password = "JoesGot2YellowBalls!";*/
		
		try {
			Class.forName(driverClass).newInstance();
			Connection conn = DriverManager.getConnection(url,username,password);
			Statement stmt = conn.createStatement();
			
			if(stmt.execute(query)){
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
