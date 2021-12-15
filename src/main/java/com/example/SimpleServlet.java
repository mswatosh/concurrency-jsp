package com.example;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
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
		//JSP code - test principal propagation

		String s1 = new String("(" + Thread.currentThread().getId() + ") getUserPrincipal: " + request.getUserPrincipal());
        String s2 = "";

		//Use a managedScheduledExecutor
		ExecutorService executor = Executors.newFixedThreadPool(3);

		Future<String> future = executor.submit(() -> {
				return new String("(" + Thread.currentThread().getId() + ") getUserPrincipal: " + request.getUserPrincipal());
		});
		try {
			s2 = future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		//end JSP code

		ServletOutputStream out = response.getOutputStream();
        out.println(s1);
		out.println(s2);
		out.println("");

		//JSP code - test clearing a principal
		String s3 = "(" + Thread.currentThread().getId() + ")" + " Name: " + request.getUserPrincipal().getName();
		CompletableFuture<String> cf = CompletableFuture.completedFuture("");

		//Submit this as a contextual function...
		CompletableFuture<String> cf2 = cf.thenApply(s -> {
			return s + request.getUserPrincipal() == null ? "Security: cleared" : "(" + Thread.currentThread().getId() + ")" + " Name: " + request.getUserPrincipal().getName();
		});
		String s4 = cf2.join();
		//end JSP code

		out.println(s3);
		out.println(s4);
    }
}