<%@page import="hr.fer.zemris.java.hw15.model.BlogEntry"%>
<%@page import="hr.fer.zemris.java.hw15.model.BlogComment"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
		<style>
			.border
			{
				border-style:solid;
				border-color:#000000;
			}
		</style>
	<body>

		<%
			String firstName = (String) session.getAttribute("current.user.fn");
			String lastName = (String) session.getAttribute("current.user.ln");
			String nick = (String) session.getAttribute("current.user.nick");
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
	
		<c:choose>
			<c:when test="${blogEntry==null}">
	      No such entry!
	    </c:when>
			<c:otherwise>
				<h3><c:out value="${blogEntry.title}"></c:out> - <c:out value="ID = ${blogEntry.id}"></c:out></h3>
				<br>
				<p class="border">
					<c:out value="${blogEntry.text}" />
				</p>
				<%
					BlogEntry entry = (BlogEntry) request.getAttribute("blogEntry");
					List<BlogComment> comments = entry.getComments();
					
					Object o1 = session.getAttribute("current.user.nick");
					boolean enable = false;
					if(o1 != null) {
						enable = session.getAttribute("current.user.nick")
								.equals(request.getAttribute("author"));	
					}
					if(enable) { %>
						<form action="<%out.print(path);%>/servleti/author/<%out.print(nick);%>/edit" method="post">
							<input type="submit" value="Edit entry">
							<input type="hidden" name="editEntryID" value="${blogEntry.id}">
						</form>
				<% 	} %>
				
				<form action="<%out.print(path);%>/servleti/comment" method="post">
					<span style="width:200px;">Comment </span>
					<input type="text" name="comment" value="${commented}" size="30">
					<c:out value="${commentError}"></c:out>
					<% if (nick == null) { %>
						<br><span style="width:200px;">Email </span>
						<input type="text" name="email" size="50">	
					<% } %>
					<input type="submit" value="Submit">
					<% 
						String error = (String)request.getAttribute("emailError");
						if(error != null) out.print(error); 
					%>
					<input type="hidden" name="entryID" value="${blogEntry.id}">
				</form>
				<c:if test="${!comments.isEmpty()}">
					<ul>
						<c:forEach var="e" items="${blogEntry.comments}">
							<li>
							<div style="font-weight: bold">[User=<c:out value="${e.usersEMail}" />]<c:out value="${e.postedOn}" /></div>
							<div style="padding-left: 10px;"><c:out value="${e.message}" /></div>
							</li>
						</c:forEach>
					</ul>
				</c:if>
			</c:otherwise>
		</c:choose>
		<br><br>
			<a href="<%out.print(path);%>">Back To Home Page</a>
	</body>
</html>
