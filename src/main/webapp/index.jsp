<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.concurrent.ExecutorService,java.util.concurrent.Executors,java.util.concurrent.Future,java.util.concurrent.ExecutionException" %>
<html>
    <head>
        <title>Sample JSP</title>
    </head>
    <body>
        <p>Servlet Invoke: <a href="SimpleServlet">here</a></p>
        <% 
        String s1 = new String("(" + Thread.currentThread().getId() + ") getUserPrincipal: " + request.getUserPrincipal());
        String s2 = "";

		ExecutorService executor = Executors.newFixedThreadPool(3);

		Future<String> future = executor.submit(() -> {
				return new String("(" + Thread.currentThread().getId() + ") getUserPrincipal: " + request.getUserPrincipal());
		});
		try {
			s2 = future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
        %>
        <p><%= s1 %></p>
        <p><%= s2 %></p>
    </body>
</html>