<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Home page</title>
		
		<style type="text/css">
		.error {
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
		<div align = "center">
			<h1>Register</h1>
		</div>
		<br><br>
		
		<form action="registerUser" method="post">
		
			<div align="center">
			 <div align="left">
			  <span class="formLabel">First Name</span><input type="text" name="firstName" value='<c:out value="${entry.firstName}"/>' size="30">
			 	<c:if test="${entry.hasError('firstName')}">
			 		<div class="error"><c:out value="${entry.getError('firstName')}"/></div>
			 	</c:if>
			 </div>
			</div>
		
			<div align="center">
			 <div align="left">
			  <span class="formLabel">Last Name</span><input type="text" name="lastName" value='<c:out value="${entry.lastName}"/>' size="40">
			  	<c:if test="${entry.hasError('lastName')}">
			 		<div class="error"><c:out value="${entry.getError('lastName')}"/></div>
			 	</c:if>
			 </div>
			</div>
			
			<div align="center">
			 <div align="left">
			  <span class="formLabel">E-mail</span><input type="text" name="email" value='<c:out value="${entry.email}"/>' size="20">
			  <c:if test="${entry.hasError('email')}">
			 		<div class="error"><c:out value="${entry.getError('email')}"/></div>
			 	</c:if>
			 </div>
			</div>
			
			<div align="center">
			 <div align="left">
			  <span class="formLabel">Nickname</span><input type="text" name="nick" value='<c:out value="${entry.nick}"/>' size="20">
			  <c:if test="${entry.hasError('nick')}">
			 		<div class="error"><c:out value="${entry.getError('nick')}"/></div>
			 	</c:if>
			 </div>
			</div>
			
			<div align="center">
			 <div align="left">
			  	<span class="formLabel">Password</span><input type="text" name="password" value="" size="20">
				<c:if test="${entry.hasError('password')}">
			 		<div class="error"><c:out value="${entry.getError('password')}"/></div>
			 	</c:if>
			 </div>
			</div>
	
			<div class="formControls" align="center">
			  <span class="formLabel">&nbsp;</span>
			  <input type="submit" name="method" value="Register">
			  <input type="submit" name="method" value="Back To Home Page">
			</div>
			
		</form>

	</body>
</html>
