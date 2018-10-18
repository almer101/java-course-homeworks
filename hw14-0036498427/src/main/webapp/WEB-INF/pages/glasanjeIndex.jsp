<%@ page contentType="text/html; charset=UTF-8" session="true" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<body>
		<h1></h1>
		<p></p>
		<ol>
			<c:forEach var="option" items="${options}">
				<li><a href="glasanje-glasaj?pollID=${option.pollID}&optionID=${option.optionID}">${option.title}</a></li>
			</c:forEach>
		</ol>
	</body>
	
	<br><br><br>
	<div align="center">
		<a href="/voting-app/index.html">Home page</a><br><br>
	</div>
</html>
