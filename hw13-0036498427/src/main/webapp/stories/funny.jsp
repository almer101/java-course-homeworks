<%@page import="java.util.Random"%>
<%@ page contentType="text/html; charset=UTF-8" session="true" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% String s = (String)session.getAttribute("pickedBgColor");%>
<style>
   	body {background-color: #<%if(s == null) out.print("FFFFFF"); else out.print(s);%>;}
</style>
<% 
	Random rand = new Random();
	int randomNumber = rand.nextInt(0xFFFFFF);
%>
<h1 align="center"><font color=#<%out.print(randomNumber); %>>Funny story</font></h1>

<body>
	<div align="center">
		<font color=#<%out.print(randomNumber); %>>
			In a rural area a farmer was tending to his horse named Buddy,<br> 
			and along came a stranger who desperately needed the farmer's help. <br> 
			The stranger had lost control of his vehicle and ran it off into a ditch. <br> 
			The stranger asked the farmer if his horse could somehow pull the <br> 
			vehicle out of the ditch for him and told the farmer that the vehicle <br> 
			was small. The farmer said he would come, bring his horse, and take a look, <br> 
			but could not promise he could help if his horse might be injured in <br> 
			some way from attempting to pull the vehicle out of the ditch. <br> 
			The farmer did see that the stranger was correct and that the <br> 
			vehicle was small, so the farmer took a rope and fixed it so that<br>  
			his horse, Buddy, would be able to pull the vehicle out of the ditch.<br> 
			The farmer then said, "Pull, Casey, Pull," but the horse would not<br> 
			budge. The farmer then said, "Pull, Bailey, Pull," but the horse <br> 
			would not budge again. The farmer then said, "Pull, Mandy, Pull," <br> 
			and again the horse would not move. The farmer then said, "Pull, <br> 
			Buddy, Pull," and the horse pulled until the vehicle was out of the <br> 
			ditch. The stranger was so very grateful, but asked the farmer why <br> 
			he called the horse by different names? The farmer said, "Buddy is <br> 
			blind, and I had to make him think he had help pulling the car out <br> 
			of the ditch or he would not have pulled."<br> 
		</font>
		<br><br><br>
		<a href="/webapp2/">Home page</a><br><br>

	</div>
</body>