<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Home page</title>
		
		<style type="text/css">
		.greska {
		   font-family: sans-serif;
		   font-weight: bold;
		   font-size: 0.9em;
		   color: #FF0000;
		   padding-left: 110px;
		}
		.formLabel {
		   display: inline-block;
		   width: 100px;
                   font-weight: bold;
		   text-align: right;
                   padding-right: 10px;
		}
		.formControls {
		  margin-top: 10px;
		}
		</style>
	</head>
	
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
		<div align = "center">
			<h1>HOME PAGE</h1>
		</div>
		<br><br>
		<% 
			String o1 = (String)session.getAttribute("current.user.nick");
			boolean enable = o1 == null;
		
			if(enable) { %>
				<form action="main" method="post">
				
					<div align="center">
					 <div>
					  <span class="formLabel">Nickname</span><input type="text" name="nick" value='<c:out value="${user.nick}"/>' size="20">
					 </div>
					</div>
					
					<div align="center">
					 <div>
					  <span class="formLabel">Password</span><input type="text" name="password" value="" size="20">
					 </div>
					</div>
					
					<div align="center">
						<c:if test="${loginError!=null}">
							<div class="greska"><c:out value="${loginError}"/></div>
						</c:if>
					</div>
			
					<div class="formControls" align="center">
					  <span class="formLabel">&nbsp;</span>
					  <input type="submit" name="method" value="Login">
					</div>
				
				</form>
		<% } else { %>
				<div align="center">
					<h3>Login will be enabled once you logout :)</h3>
				</div>
				
		<% } %>
		
		
		<br>
		<div align="center">
			<p>Don't have an account yet? Register <a href="register">here</a></p>
		</div>
		
		
		<div align="center">
			<h3>List of registered authors</h3>
			<c:forEach var="u" items="${userList}">
					<a href="author/${u.nick}">${u.nick}</a><br>
			</c:forEach>
		</div>

	</body>
</html>