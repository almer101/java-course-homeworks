<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" session="true" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
       		<thead><tr><th>Option</th><th>Number of votes</th></tr></thead>
       		<tbody>
       			<c:forEach var="option" items="${options}">
					<tr><td>${option.title}</td><td>${option.votesCount}</td></tr>
				</c:forEach>
			</tbody>
		</table>
		<h2> Results visualization</h2>
		<img alt="Pie-chart" src="glasanje-grafika?pollID=${pollID}" width="500" height="320" /> 
		<h2>Results in xls format</h2>
		<p>Results are available in XLS format<a href="glasanje-xls?pollID=${pollID}"> here</a></p>
		<h2>Other</h2>
		<p>Links to winners: </p>
		<ul>
			<c:forEach var="winner" items="${winners}">
				<li><a href="${winner.link}" target="_blank">${winner.title}</a></li>
			</c:forEach>	
		</ul>
		
		<br><br><br>
		<div align="center">
			<a href="/voting-app/index.html">Home page</a><br><br>
		</div>
	</body>
</html>