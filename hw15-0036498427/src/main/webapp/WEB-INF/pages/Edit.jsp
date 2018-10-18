<%@page import="hr.fer.zemris.java.hw15.model.NewEntryForm"%>
<%@page import="hr.fer.zemris.java.hw15.model.BlogEntry"%>
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
			String firstName = (String)session.getAttribute("current.user.fn");
			String lastName = (String)session.getAttribute("current.user.ln");
			String nick = (String)session.getAttribute("current.user.nick");
			String path = request.getContextPath().toString();
		%>
		<% if (nick == null) { %>
				Not logged in
				<br><a href="<%out.print(path);%>/servleti/main">Login</a> 
		<% } else { %>
				Logged in as : <%out.print(firstName);%> <%out.print(lastName);%>
				<br><a href="<%out.print(path);%>/servleti/logout">Logout</a> 
		<% } %>
		<br>
		
		<div align="center">
			<h3>Edit Blog Post - <c:out value="${editEntry.title}"></c:out></h3> 
			<form action="<%out.print(path);%>/servleti/editEntry" method="get">
			
				<c:choose>
					<c:when test="${editEntry!=null}">
						Title:<input type="text" name="title" value="${editEntry.title}" style="width:800px;"><br>
						<div class="error"><c:out value="${editEntry.getError('title')}"/></div>
					</c:when>
					<c:when test="${editEntry==null}">
						Text:<input type="text" name="title" value="" style="width:800px;"><br>
					</c:when>
				</c:choose>		
				<c:choose>
					<c:when test="${editEntry!=null}">
						Text:<input type="text" name="text" value="${editEntry.text}" style="height:200px;width:800px;"><br>
						<div class="error"><c:out value="${editEntry.getError('text')}"/></div>
					</c:when>
					<c:when test="${editEntry==null}">
						Text:<input type="text" name="text" value="" style="height:200px;width:800px;"><br>
					</c:when>
				</c:choose>				
				<br><br>
				<input type="submit" value="Submit Entry"> 
				<input type="hidden" name="id" value="${editEntry.id}">
				
			</form>
		</div>
		<br><br>
			<a href="<%out.print(path);%>">Back To Home Page</a>
	</body>
</html>