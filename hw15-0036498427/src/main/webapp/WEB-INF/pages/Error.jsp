<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<style type="text/css">
			.error {
		   font-family: sans-serif;
		   font-weight: bold;
		   font-size: 0.9em;
		   color: #FF0000;
		   padding-left: 110px;
		}
		</style>
	</head>
	<body>
	
		<%
			String path = request.getContextPath().toString();
		%>
		<div align="center">
			<h2>You cannot access the wanted page because you have no permission!</h2>
			<br><br>
			<a href="<%out.print(path);%>">Back To Home Page</a>
		</div>
	</body>
</html>