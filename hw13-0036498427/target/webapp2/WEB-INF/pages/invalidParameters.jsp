<%@ page contentType="text/html; charset=UTF-8" session="true" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% String s = (String)session.getAttribute("pickedBgColor");%>
<style>
   	body {background-color: #<%if(s == null) out.print("FFFFFF"); else out.print(s);%>;}
</style>

<h1 align="center">Invalid parameters</h1><br><br>
<div align="center">
	<h3>The entered parameters are invalid, please enter a as an integer </h3><br>
	<h3>
		in [-100,100], b as an integer in [-100,100] and n such that it is 
		larger than 0 and less than 6. 
	</h3><br>
	
	<br><br><br>
	<a href="/webapp2/">Home page</a><br><br>
</div>


