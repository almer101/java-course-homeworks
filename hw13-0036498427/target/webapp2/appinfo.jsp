<%@page import="hr.fer.zemris.java.servlets.Util.ServletUtil"%>
<%@ page contentType="text/html; charset=UTF-8" session="true" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% String s = (String)session.getAttribute("pickedBgColor");%>
<style>
   	body {background-color: #<%if(s == null) out.print("FFFFFF"); else out.print(s);%>;}
</style>

<%
	long currentMillis = System.currentTimeMillis();
	long timeStarted = (Long)request.getSession().getServletContext().getAttribute("time");
	
	long timeElapsed = currentMillis - timeStarted;
	String elapsedString = ServletUtil.getElapsedString(timeElapsed);
%>

<br><br><br>
<h2>The web application has been running for: <% out.print(elapsedString); %> </h2>

<br><br><br>
<div align="center">
	<a href="/webapp2/">Home page</a><br><br>
</div>

