package com.example;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

@WebServlet("/SimpleServlet")
public class SimpleServlet extends HttpServlet {
    
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream out = response.getOutputStream();

		out.println("<h1>SimpleServlet</h1>");
		out.println("getAuthType: " + request.getAuthType());
        out.println("getRemoteUser: " + request.getRemoteUser());
        out.println("getUserPrincipal: " + request.getUserPrincipal());

		try {
			UserTransaction tx = InitialContext.doLookup("java:comp/UserTransaction");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}