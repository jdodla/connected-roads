package com.ndodla.connectedroads.controller;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ndodla.connectedroads.model.City;
import com.ndodla.connectedroads.service.Connecter;


@RestController
public class CityRoadsController {
	
	 private final Log LOG = LogFactory.getLog(getClass());

    @Autowired
    LoadingResource resource;
	
    @Autowired
    Connecter connecter;
    
    @GetMapping(value = "/connected", produces = "text/plain")
    public String isConnected(@RequestParam(required = true) String origin, @RequestParam(required = true) String destination) {

		LOG.info("origin="+origin+" && destination="+destination);
        City originCity = resource.getCity(origin.toUpperCase());
        City destCity = resource.getCity(destination.toUpperCase());

        if(originCity==null || destCity==null)
        	return "no";
        else
        	return String.valueOf(connecter.connected(originCity, destCity));
    }

    @GetMapping(value = "/", produces = "text/html")
    public String info() {

        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html><head><meta charset=\"utf-8\"><title>City Data</title></head><body>")
                .append("<h2>City List</h2><table border='true' style=\"width:100%\"><tr><td><b>City</b></td><td><b>Direct Connected</b></td>");
       
        Collection<City> cities = resource.getCityMap().values();
        for (City city : cities) {
            html.append("<tr><td>")
                    .append(city.getName())
                    .append("</td> <td>")
                    .append(city.getDirectConnectedCities())
                    .append("</td></tr>");
        }
        html.append("</table></body></html>");
        return html.toString();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String cityError() {
        return "Exception while processing the request!!!";
    }

}
