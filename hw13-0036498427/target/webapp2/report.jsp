<%@ page contentType="text/html; charset=UTF-8" session="true" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% String s = (String)session.getAttribute("pickedBgColor");%>
<style>
   	body {background-color: #<%if(s == null) out.print("FFFFFF"); else out.print(s);%>;}
</style>

<h1 align="center">OS Usage</h1>
<h2>Results</h2>
<p>Here are the results of OS usage in survey that we completed.</p>
<br>
<img alt="Pie Chart" src="reportImage" width="500" height="300">

<br><br><br>
<div align="center">
	<a href="/webapp2/">Home page</a><br><br>
</div>
