<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.concurrent.ExecutorService,java.util.concurrent.Executors,java.util.concurrent.Future,java.util.concurrent.ExecutionException,java.util.concurrent.CompletableFuture" %>
<html>
	<head>
		<title>Concurrency JSP</title>
	</head>
	<body>
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

		String s3 = "(" + Thread.currentThread().getId() + ")" + " Name: " + request.getUserPrincipal().getName();
		CompletableFuture<String> cf = CompletableFuture.completedFuture("");


		CompletableFuture<String> cf2 = cf.thenApply(s -> {
			return s + request.getUserPrincipal() == null ? "Security: cleared" : "(" + Thread.currentThread().getId() + ")" + " Name: " + request.getUserPrincipal().getName();
		});
		String s4 = cf2.join();
		%>
		<p><%= s1 %></p>
		<p><%= s2 %></p>

		<p><%= s3 %></p>
		<p><%= s4 %></p>
	</body>
</html>