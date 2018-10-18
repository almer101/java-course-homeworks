<%@page import="hr.fer.zemris.java.servlets.GlasanjeServlet.Band"%>
<%@page import="hr.fer.zemris.java.servlets.GlasanjeRezultatiServlet.VotingResult"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" session="true" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% String s = (String)session.getAttribute("pickedBgColor");%>
<style>
   	body {background-color: #<%if(s == null) out.print("FFFFFF"); else out.print(s);%>;}
</style>
<html>
	<head>
		<style type="text/css">
			table.rez td {text-align: center;}
     	</style>
	</head>
	<body>
		<h1>Voting results</h1>
		<p>These are the voting results</p>
		<table border="1" cellspacing="0" class="rez">
       		<thead><tr><th>Band</th><th>Number of votes</th></tr></thead>
       		<tbody>
       			<c:forEach var="result" items="${votingResults}">
					<tr><td>${result.bandName}</td><td>${result.votes}</td></tr>
				</c:forEach>
			</tbody>
		</table>
		<h2> Results visualization</h2>
		<img alt="Pie-chart" src="glasanje-grafika" width="500" height="320" /> <h2>Results in xls format</h2>
		<p>Results are available in XLS format<a href="glasanje-xls"> here</a></p>
		<h2>Other</h2>
		<p>Examples of the winner band songs:</p>
		<ul>
			<c:forEach var="winner" items="${winners}">
				<li><a href="${winner.songLink}" target="_blank">${winner.bandName}</a></li>
			</c:forEach>	
		</ul>
		
		<br><br><br>
		<div align="center">
			<a href="/webapp2/glasanje">Voting</a><br><br>
			<a href="/webapp2/">Home page</a><br><br>
		</div>
	</body>
</html>