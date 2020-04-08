package com.ndodla.connectedroads.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class represents a City along with the connected cities
 * City names are not case sensitive in computations
 */
public class City implements Comparable<City>{
	
    private String name;

    private Set<City> directConnected = new HashSet<>();

    private City(String name) {
        this.name = name.trim().toUpperCase();
    }

    private City() {
    }

    public static City build(String name) {
        return new City(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City addDirectConnected(City city) {
    	directConnected.add(city);
        return this;
    }

    public Set<City> getDirectConnected() {
        return directConnected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return Objects.equals(name, city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    
    @Override
    public String toString() {

        return "City{" +
                "name='" + name + "'" +
                ", directConnected='" + getDirectConnectedCities() +
                "'}";
    }

    public String getDirectConnectedCities() {
        return directConnected
                .stream()
                .map(City::getName)
                .collect(Collectors.joining(","));
    }
    
    @Override
	public int compareTo(City city) {
		return this.getName().compareTo(city.getName());
	}

}
