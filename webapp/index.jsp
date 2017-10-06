<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<c:url var="firstAjax" value="/StartServletClient" scope="request" />
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
				<title>Index page first</title>
			</head>
			<body>
				<div class="formInit" id="formInit"  >
					<h3>Init completato</h3>
				</div>
				<div class="formButtons" >
					<button id="loadButton" class="buttonExec ui-button ui-widget ui-corner-all"  onclick="javascript:getLoadFeed();" >Esegui il processo FEED</button>
					<br>
				</div>
				<div class="formOut" class="ui-dialog-titlebar" id="formOut" >
					
				</div>
			</body>
			
			<!-- JQuery style -->
			<script src="http://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js" type="text/javascript" ></script>
			<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
			<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
			
			<!-- SCRIPT -->
			<script type="text/javascript">
				function getLoadFeed()
				{

					$(".buttonExec ").prop("disabled",true);
					$.ajax({
						type : "GET",
						url : "${firstAjax}",
						data : {
						} ,
						success : function(data) {
							 $('#formOut').html(data);
							
						},
						error : function(e) {
							console.log("ERROR: ", e);
							alert("err--> "+e);
							
						},
						done : function(e) {
							console.log("DONE");
							alert("done--> "+e);
						}
					});

					$(".buttonExec ").prop("disabled",false);
				}
			</script>
		</html>