<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page isELIgnored="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
     <%@ include file="style.css"%>
</style>
<title>Weather Information</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">

function lookupCountry(){
	var countryString = $( "#searchCountry" ).val();
	
	$("#finalOutput").text("");
	
	var cityCombo = $("#cityInput");
	cityCombo.empty().append(new Option("Select", "Select"));
	
	var countryCombo = $("#countryInput");
	countryCombo.empty().append(new Option("Select", "Select"));
	
	if($("#searchCountry").val().length > 0){
	
		if(/^[a-zA-Z]*$/.test(countryString)){
			$("#error1").text("");
			$( "#searchCountry" ).css('border-color','');
			$.ajax({
				type:"POST",
				url: "http://localhost:9090/weatherApplication/country/"+countryString,
				async:false,
				crossDomain:true,
				success:function(data){
					
					if(null!=data && data.length>0){
						$.each(data, function() {
							countryCombo.append(new Option(this, this));
						});
					}
					else{
						$("#searchCountry" ).css('border-color','red');
						$("#error1").text("No records found!");
						$("#error1").css("color","red");
					}
					
				},
				error: function(xhr,ajaxOptions,thrownError){
					alert(xhr.statusText);
					alert(thrownError);
				}
			});
			}
		else{
			$("#searchCountry" ).css('border-color','red');
			$("#error1").text("Country search allows only characters!");
			$("#error1").css("color","red");
		}
	}
	else{
		$("#searchCountry" ).css('border-color','red');
		$("#error1").text("Please enter country name!");
		$("#error1").css("color","red");
	}
}


function lookupCity(country){
	var countryString = country.value;
	$.ajax({
		type:"POST",
		url: "http://localhost:9090/weatherApplication/city/"+countryString,
		async:false,
		crossDomain:true,
		success:function(data){
			var cityCombo = $("#cityInput");
			cityCombo.empty().append(new Option("Select", "Select"));
			$.each(data, function() {
				cityCombo.append(new Option(this, this));
			});
		},
		error: function(xhr,ajaxOptions,thrownError){
			alert(xhr.statusText);
			alert(thrownError);
		}
	});
}

function getWeather(){
	var countryString = $( "#countryInput" ).value;
	var cityString = $( "#cityInput" ).value;
	
	$.ajax({
		type:"POST",
		url: "http://localhost:9090/weatherApplication/weather/"+countryString+"/"+cityString,
		async:false,
		crossDomain:true,
		success:function(data){
			var finalLabel = $("#finalOutput");
			finalLabel.text(data);
		},
		error: function(xhr,ajaxOptions,thrownError){
			alert(xhr.statusText);
			alert(thrownError);
		}
	});
}

function clearAll(){
	$("#finalOutput").text("");
	$("#cityInput").empty().append(new Option("Select", "Select"));
	$("#countryInput").empty().append(new Option("Select", "Select"));
	$("#searchCountry" ).val("");
	$("#error1").text("");
}
</script>
</head>
<body>
<h1 align="center">
<spring:message code="weather.title" text="default text" />
</h1>
<table align="center">
<tr>
<td>
<label><spring:message code="weather.searchCountryLabel" text="default text" /></label>
</td>
<td>
<input id="searchCountry"></input>
<button class="button" onClick="lookupCountry()"><spring:message code="weather.lookupButton" text="default text" /></button>
</td>

</tr>
<tr>
<td></td>
<td><label id="error1"></label></td>
</tr>

<tr>
<td>
<label><spring:message code="weather.selectCountryLabel" text="default text" /></label>
</td>
<td>
<select id="countryInput" onchange="lookupCity(this);"><option>Select</option></select>
</td>
</tr>

<tr>
<td>
<label><spring:message code="weather.selectCityLabel" text="default text" /></label>
</td>
<td>
<select id="cityInput"><option>Select</option></select>
</td>
</tr>
</table>
<table align="center">
<tr>
<td>
<button class="button" onClick="getWeather()"><spring:message code="weather.getWeatherButton" text="default text" /></button>
</td>
<td>
<button class="button" onClick="clearAll()"><spring:message code="weather.resetButton" text="default text" /></button>
</td>
</tr>
</table>
<p>
<table align="center">
<tr>
<td>
<label id="finalOutput" style="font-size:20px;"></label>
</td>
</tr>
</table>


</body>
</html>