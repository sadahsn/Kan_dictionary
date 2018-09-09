package com.kan.dic.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetWordMeaningServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public GetWordMeaningServlet() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		JdbcQueries jdbcQueries = new JdbcQueries();
		ResultSet result = jdbcQueries.selectQuery("select * from SYNONYMOUS  where WORD_ID in (select WORD_ID from WORDS_LEX_INFO where ROOT_WORD like N'"+req.getParameter("input")+"');");
		String words = "";
		try {
			while (result.next()) {
				String word =result.getString("SYNONYMOUS_WORD");
				words = words.isEmpty()? word : words + ","+ word ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader( "Pragma", "no-cache" );
		resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		resp.setDateHeader( "Expires", 0 );
		
		resp.setContentType("text/html");
        PrintWriter printWriter  = resp.getWriter();
       
        printWriter.print(words);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
