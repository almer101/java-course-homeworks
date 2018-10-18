<%@ page contentType="text/html; charset=UTF-8" session="true" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% String s = (String)session.getAttribute("pickedBgColor");%>
<style>
   	body {background-color: #<%if(s == null) out.print("FFFFFF"); else out.print(s);%>;}
   	table {
    		border-collapse: collapse;
	}

	table, th, td {
  	  border: 1px solid black;
	}
</style>


<h1 align="center">Trigonometric values in the specified range</h1>
<body>
	<div align="center">
		<table>
			<thead>
				<tr><th>Number</th><th>sin</th><th>cos</th></tr>
			</thead>
			<tbody>
				<c:forEach var="entry" items="${entryTable}">
					<tr><td>${entry.number}</td><td>${entry.sin}</td><td>${entry.cos}</td></tr>
				</c:forEach>
			</tbody>
		</table>
		
		<br><br><br>
		<a href="/webapp2/">Home page</a><br><br>
	</div>
</body>
