package com.ndodla.connectedroads;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.ndodla.connectedroads.controller.LoadingResource;
import com.ndodla.connectedroads.model.City;
import com.ndodla.connectedroads.service.Connecter;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class CityRoadsApplicationTests {

    @Autowired
    LoadingResource resource;
    
    @Autowired
    Connecter connecter;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void fileLoad() {
        Assert.assertFalse("File load failed", resource.getCityMap().isEmpty());
    }

    @Test
    public void sameCity() {
        City city = City.build("New York");
        Assert.assertEquals(connecter.connected(city, city),"yes");
    }

    @Test
    public void neighbours() {
    	City cityA = resource.getCity("New York".toUpperCase());
    	City cityB = resource.getCity("Boston".toUpperCase());
                
        Assert.assertNotNull("Invalid test data. City not found: A", cityB);
        Assert.assertNotNull("Invalid test data. City not found: F", cityA);

        Assert.assertEquals(connecter.connected(cityA, cityB),"yes");
    }

    @Test
    public void distantConnected() {
        City cityA = resource.getCity("New York".toUpperCase());
        City cityB = resource.getCity("Philadelphia".toUpperCase());

        Assert.assertNotNull("Invalid test data. City not found: F", cityA);
        Assert.assertNotNull("Invalid test data. City not found: A", cityB);

        Assert.assertEquals(connecter.connected(cityA, cityB),"yes");
    }

    @Test
    public void restConnectedIT() {

        Map<String, String> params = new HashMap<>();
        params.put("origin", "New York");
        params.put("destination", "Philadelphia");

        String body = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, params);
        Assert.assertEquals("yes", body);
    }

    @Test
    public void restNotConnectedIT() {

        Map<String, String> params = new HashMap<>();
        params.put("origin", "New York");
        params.put("destination", "Trenton");

        String body = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, params);
        Assert.assertEquals("no", body);
    }

    @Test
    public void badRequestIT() {
    	 Map<String, String> params = new HashMap<>();
         params.put("origin", "none");
         params.put("destination", "none");

         String body = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, params);
         Assert.assertEquals("no", body);
    }

}
