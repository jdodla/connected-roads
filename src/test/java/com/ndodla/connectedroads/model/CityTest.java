package com.ndodla.connectedroads.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class CityTest {

    @Test
    public void build() {
        City city = City.build("New York");
        Assert.assertEquals("NEW YORK", city.getName());
    }

    @Test
    public void buildWithNeighbours() {
        City city = City.build("New York");
        city.addDirectConnected(City.build("Boston"))
                .addDirectConnected(City.build("Albany"));

        Set<City> directConnect = city.getDirectConnected();
        Assert.assertEquals(2, directConnect.size());
        Assert.assertTrue(directConnect.contains(City.build("Albany")));
    }


    @Test
    public void addNearby() {
        City city = City.build("New York");
        city.addDirectConnected(City.build("Boston"))
                .addDirectConnected(City.build("Albany"));

        Assert.assertEquals(2, city.getDirectConnected().size());
    }

    @Test
    public void addNearbyDuplicates() {
        City city = City.build("New York");
        city.addDirectConnected(City.build("Boston"))
                .addDirectConnected(City.build("BOSTON"))
                .addDirectConnected(City.build("  boston"));

        Assert.assertEquals(1, city.getDirectConnected().size());
    }


}