package com.example;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/SimpleServlet")
public class SimpleServlet extends HttpServlet {
    
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream out = response.getOutputStream();
        out.println("(" + Thread.currentThread().getId() + ") getUserPrincipal: " + request.getUserPrincipal());

		ExecutorService executor = Executors.newFixedThreadPool(3);

		Future<String> future = executor.submit(() -> {
				return new String("(" + Thread.currentThread().getId() + ") getUserPrincipal: " + request.getUserPrincipal());
		});
		try {
			out.println(future.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
    }
}