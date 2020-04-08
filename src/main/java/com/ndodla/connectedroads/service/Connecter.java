package com.ndodla.connectedroads.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ndodla.connectedroads.model.City;

import java.util.*;

@Service
public class Connecter {

    private static final Log LOG = LogFactory.getLog(Connecter.class);

    /**
     * Find if destination city is connected from origin. Will process all the cities
     * on the queue which is built by collecting all the direct connected of a processed place
     * @param origin the origin
     * @param destination the destination
     * @return yes if cities are connected
     */
    public String connected(City origin, City destination) {

        LOG.info("Origin: " + origin.getName() + "& destination: " + destination.getName());

        if (origin.equals(destination)) return "yes";

        if (origin.getDirectConnected().contains(destination)) return "yes";

        /*
         * The origin city was already processed since we have started from it
         */
        Set<City> processed = new HashSet<>(Collections.singleton(origin));

        /*
         * add all the direct connected cities into a queue
         */
        Queue<City> queue = new PriorityQueue<>(origin.getDirectConnected());

        while (!queue.isEmpty()) {

        	LOG.info("processed="+processed);
            City city = queue.element();

            if (city.equals(destination)) return "yes";

            // remove the city from the queue

            // first time process?
            if (!processed.contains(city)) {

            	processed.add(city);

                // add direct connected cities to the queue and
                // remove already processed cities from the list
                queue.addAll(city.getDirectConnected());
                queue.removeAll(processed);

                LOG.info("Processing: ["
                        + city.getName()
                        + "] , connected: ["
                        + (city.getDirectConnectedCities())
                        + "], queue: ["
                        + queue.toString()
                        + "]");
            } else {
                // the city has been processed, so remove it from the queue
            	queue.removeAll(Collections.singleton(city));
            }
        }

        return "no";
    }
}

