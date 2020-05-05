<%@page import="com.Appointment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="Views/css/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src=Components/appointment.js></script>
<title>Appointment Management</title>
<style>
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h2 style="text-align:center">Appointment Management</h2>
				<br>
				<form id="formAppointment" name="formAppointment" method="post" action="appointment.jsp">
				  	Appointment ID:
				  	<input id="id" name="id" type="text" 
				 	         class="form-control form-control-sm">
				  	<br> Hospital ID:
				  	<input id="hospitalid" name="hospitalid" type="text"
				         	 class="form-control form-control-sm">
				   	<br> Patient ID:
				   	<input id="patientid" name="patientid" type="text"
				           		class="form-control form-control-sm">
				   	<br> Date:
				   	<input id="date" name="date" type="text"
				           	  class="form-control form-control-sm">
				    <br> Time:
				   	<input id="time" name="time" type="text"
				           		class="form-control form-control-sm">
				     <br>  Description:
				   	<input id="description" name="description" type="text"
				           		class="form-control form-control-sm">
					<br>
				    <p>Status:</p>
					<input type="radio" id="status" name="status" value="Yes">
					<label for="Yes">Yes</label>
					<input type="radio" id="status" name="status" value="No">
					<label for="No">No</label>
					<br>
					<br> 
				    <input id="btnSave" name="btnSave" type="button" value="Save"
				          class="btn btn-primary">
				    <input type="hidden" id="hidAppIDSave" name="hidAppIDSave" value="">
				  </form>
				  
				  <div id="alertSuccess" class="alert alert-success"></div>
				  <div id="alertError" class="alert alert-danger"></div>
				  
				  <br>
				  <div id = "divAppGrid">
				  <%
				  	Appointment appObj = new Appointment();
				    out.print(appObj.readAppointment());
				  %>
				  </div>
			  </div>
		  </div>
	  </div>
</body>
</html>