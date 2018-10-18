<%@ page contentType="text/html; charset=UTF-8" session="true" pageEncoding="UTF-8"%>

<% String s = (String)session.getAttribute("pickedBgColor");%>
<style>
   body {background-color: #<%if(s == null) out.print("FFFFFF"); else out.print(s);%>;}
</style>

<body >
	<div align="center">
		<a href="/webapp2/colors.jsp">Background color chooser</a><br><br>
		
		<form action="trigonometric" method="GET">
	 		Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
	 		Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
	 		<input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
 		</form>	
 		<br>
 		<form name="trigonometricForm"action="trigonometric" method="GET">
 			<input type="hidden" name="a" value="0">
 			<input type="hidden" name="b" value="90">
 			<a href="#" onclick="trigonometricForm.submit()">Calculated sin and cos for angles 0-90 degrees</a>
 		</form>
 		<br>
 		<form name="form1" action="stories/funny.jsp">
 			<a href="#" onclick="form1.submit()">Funny story</a>
 		</form>
 		<br>
 		<form name="form2" action="report.jsp">
 			<a href="#" onclick="form2.submit()">Report</a>
 		</form>
 		<br>
 		<form name="form3" action="powers">
 			<input type="hidden" name="a" value="1">
 			<input type="hidden" name="b" value="100">
 			<input type="hidden" name="n" value="3">
 			<a href="#" onclick="form3.submit()">Create an Excel file</a>
 		</form>
 		<br>
 		<form name="form4" action="appinfo.jsp">
 			<a href="#" onclick="form4.submit()">App Info</a>
 		</form>
 		<br>
 		<form name="form5" action="glasanje">
 			<a href="#" onclick="form5.submit()">Voting</a>
 		</form>
 		
 		
	</div>
	
	
	 
</body>

