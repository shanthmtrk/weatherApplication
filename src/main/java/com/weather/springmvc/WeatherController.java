package com.weather.springmvc;

//import org.springframework.web.bind.annotation.PathVariable;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.weather.wsdl.GetCitiesByCountryResponse;
import com.weather.wsdl.GetWeatherResponse;

@RestController
public class WeatherController {
	
	private static final Logger logger = Logger.getLogger(WeatherController.class);
	
	@RequestMapping(value = "/weather/{countryString}/{cityString}", method = RequestMethod.POST)
	public String getWeather(@PathVariable("countryString") String countryString, @PathVariable("cityString") String cityString) {
		GetWeatherResponse response=new GetWeatherResponse();
		try{
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
	    ctx.register(WeatherAppConfig.class);
	    ctx.refresh();
		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		response = weatherClient.getWeatherInfo(cityString,countryString);
		logger.info("getWeather"+response.getGetWeatherResult());
		}
		catch(Exception ex){
			logger.error("Error in getWeather", ex);
			return "Unable to process your request";
		}
	    return response.getGetWeatherResult();
	}
	
	
	@RequestMapping(value = "/city/{countryString}", method = RequestMethod.POST)
	public Object[] getCityByCountry(@PathVariable("countryString") String countryString) throws ParserConfigurationException,IOException,SAXException {
		
		Set<String> citySet=new HashSet<String>();
		try{
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
	    ctx.register(WeatherAppConfig.class);
	    ctx.refresh();
	    WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		GetCitiesByCountryResponse response = weatherClient.getCountry(countryString);
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource src = new InputSource();
		src.setCharacterStream(new StringReader(response.getGetCitiesByCountryResult()));
		Document doc = builder.parse(src);
		NodeList nodeList = doc.getElementsByTagName("City");
		
		logger.info("Number of cities"+nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {

			citySet.add(nodeList.item(i).getTextContent());
	      
		}
		}
		catch(Exception ex){
			logger.error("Error in getCityByCountry", ex);
			return null;
		}
		return citySet.toArray();
	}
	
	@RequestMapping(value = "/country/{countryString}", method = RequestMethod.POST)
	public Object[] getCountry(@PathVariable("countryString") String countryString) throws ParserConfigurationException,IOException,SAXException{
		System.out.println("getcountry");
		Set<String> countrySet=new HashSet<String>();
		try{
			System.out.println("test");
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
	    ctx.register(WeatherAppConfig.class);
	    ctx.refresh();
		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		GetCitiesByCountryResponse response = weatherClient.getCountry(countryString);
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource src = new InputSource();
		src.setCharacterStream(new StringReader(response.getGetCitiesByCountryResult()));
		Document doc = builder.parse(src);
		NodeList nodeList = doc.getElementsByTagName("Country");

		
		for (int i = 0; i < nodeList.getLength(); i++) {

		    countrySet.add(nodeList.item(i).getTextContent());
	      
		}
		System.out.println("testing");
		}
		catch(Exception ex){
			logger.error("Error in getCountry", ex);
			return null;
		}
		return countrySet.toArray();
		
	}
}
