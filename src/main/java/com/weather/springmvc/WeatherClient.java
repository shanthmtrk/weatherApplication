package com.weather.springmvc;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.weather.wsdl.GetCitiesByCountry;
import com.weather.wsdl.GetCitiesByCountryResponse;
import com.weather.wsdl.GetWeather;
import com.weather.wsdl.GetWeatherResponse;

public class WeatherClient extends WebServiceGatewaySupport  {
	public GetWeatherResponse getWeatherInfo(String cityName, String countryName) {
		GetWeather request = new GetWeather();
		
		request.setCityName(cityName);
		request.setCountryName(countryName);
		GetWeatherResponse response = (GetWeatherResponse) getWebServiceTemplate().marshalSendAndReceive("http://www.webservicex.net/globalweather.asmx",request, new SoapActionCallback("http://www.webserviceX.NET/GetWeather"));
		return response;
		
	}
	
	public GetCitiesByCountryResponse getCountry(String countryName) {
		GetCitiesByCountry request = new GetCitiesByCountry();
	
		request.setCountryName(countryName);
		GetCitiesByCountryResponse response = (GetCitiesByCountryResponse) getWebServiceTemplate().marshalSendAndReceive("http://www.webservicex.net/globalweather.asmx",request, new SoapActionCallback("http://www.webserviceX.NET/GetCitiesByCountry"));
		return response;
	}
}
