<%@ page contentType="text/html; charset=UTF-8" session="true" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% String s = (String)session.getAttribute("pickedBgColor");%>
<style>
   	body {background-color: #<%if(s == null) out.print("FFFFFF"); else out.print(s);%>;}
</style>
<html>
	<body>
		<h1>Voting for your favourite band</h1>
		<p>From the following bands which one is your favourite. Click to vote.</p>
		<ol>
			<c:forEach var="choice" items="${votingChoices}">
				<li><a href="glasanje-glasaj?id=${choice.bandID}">${choice.bandName}</a></li>
			</c:forEach>
		</ol>
	</body>
	
	<br><br><br>
	<div align="center">
		<a href="/webapp2/">Home page</a><br><br>
	</div>
</html>