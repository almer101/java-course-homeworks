<%@ page contentType="text/html; charset=UTF-8" session="true" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<body>
	<div align="center">
		<h1>This is the home page - available polls :</h1>
 		<c:forEach var="e" items="${pollEntries}">
 			${e.title}<br>
 			${e.message} -> <a href="glasanje?pollID=${e.id}">link</a>
 			<br><br><br>  
 		</c:forEach>	
	</div> 
</body>

