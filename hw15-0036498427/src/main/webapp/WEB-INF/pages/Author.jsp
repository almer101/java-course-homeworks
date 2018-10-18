<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
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
	
		<div>
			<h3>List of Author's Blog Posts - Author: <%out.print(request.getAttribute("author")); %></h3>
			<ul>
				<c:forEach var="p" items="${authorPosts}">
					<li><a href="${author}/${p.id}">${p.title}</a></li><br><br>
				</c:forEach></ul>
			<br><br>
			<% 
				Object o1 = session.getAttribute("current.user.nick");
				boolean enable = false;
				if(o1 != null) {
					enable = session.getAttribute("current.user.nick")
							.equals(request.getAttribute("author"));	
				}
				if(enable) { %>
					<form action="<%out.print(nick);%>/new" method="post">
						<input type="submit" value="New entry">
					</form>
			<% } %>
			<br><br>
			<a href="<%out.print(path);%>">Back To Home Page</a>
		</div>
	</body>
</html>