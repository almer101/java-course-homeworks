<%@ page contentType="text/html; charset=UTF-8" session="true" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% String s = (String)session.getAttribute("pickedBgColor");%>
<style>
   	body {background-color: #<%if(s == null) out.print("FFFFFF"); else out.print(s);%>;}
</style>

<div align="center">
	<form name="form" action="setcolor" method="get">
		<input type="hidden" name="color" value="FFFFFF">
		<a href="#"  onclick="document.form.color.value='FFFFFF' ; document.form.submit();">WHITE</a><br><br>
		<a href="#"  onclick="document.form.color.value='FF0000' ; document.form.submit();">RED</a><br><br>
		<a href="#"  onclick="document.form.color.value='00FF00' ; document.form.submit();">GREEN</a><br><br>
		<a href="#"  onclick="document.form.color.value='00FFFF' ; document.form.submit();">CYAN</a><br><br>
	</form>
	
	<br><br><br>
	<a href="/webapp2/">Home page</a><br><br>
</div>


