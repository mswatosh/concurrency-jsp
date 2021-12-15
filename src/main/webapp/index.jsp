<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.transaction.UserTransaction,javax.naming.InitialContext" %>
<html>
    <head>
        <title>Sample JSP</title>
    </head>
    <body>
        <p>Servlet Invoke: <a href="SimpleServlet">here</a></p>
        <% 
        String[] arr = {"What's up?", "Hello", "It's a nice day today!"}; 
         String greetings = arr[(int)(Math.random() * arr.length)];	

         UserTransaction tx = InitialContext.doLookup("java:comp/UserTransaction");
        %>
        <p><%= greetings %></p>
    </body>
</html>