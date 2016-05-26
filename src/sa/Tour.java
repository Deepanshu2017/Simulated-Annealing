package sa;

/**
 * Created by deepanshu on 5/26/16.
 * Tour.java
 * Stores a candidate tour through all cities
 */

import java.util.ArrayList;
import java.util.Collections;

public class Tour {

    // Holds our tour of cities
    private ArrayList tour = new ArrayList<City>();
    // Cache
    private int distance = 0;

    // Construct a blank tour
    public Tour() {
        for (int i = 0; i < TourManager.numberOfCities(); ++i) {
            tour.add(null);
        }
    }

    // Construct a tour from another tour
    public Tour(ArrayList tour) {
        this.tour = (ArrayList) tour.clone();
    }

    // Return tour information
    public ArrayList getTour() {
        return this.tour;
    }

    // Create a random individual
    public void generateIndividual() {
        // Loop through all our destination cities and add them to our tour
        for (int cityIndex = 0; cityIndex < TourManager.numberOfCities(); ++cityIndex) {
            setCity(cityIndex, TourManager.getCity(cityIndex));
        }

        // Randomly reorder the tour
        Collections.shuffle(tour);
    }

    // Gets a city form the tour
    public City getCity(int tourPosition) {
        return (City)tour.get(tourPosition);
    }

    // Sets a city in a certain position within a tour
    public void setCity(int tourPosition, City city) {
        tour.set(tourPosition, city);
        // If the tours have been altered we need to reset the fitness and distance
        distance = 0;
    }

    // Gets the total distance of the tour
    public int getDistance() {
        if (distance == 0) {
            int tourDistance = 0;
            // Loop through all our tour's cities
            for (int cityIndex = 0; cityIndex < tourSize(); ++cityIndex) {
                // Get city we're travelling from
                City fromCity = getCity(cityIndex);
                // City we're travelling
                City destinationCity;
                // Check we're not on our tour's last city, if we are set our
                // tour's final destination city to our starting city
                if(cityIndex + 1 < tourSize()) {
                    destinationCity = getCity(cityIndex + 1);
                } else {
                    destinationCity = getCity(0);
                }
                // Get the distance between the two cities
                tourDistance += fromCity.distanceTo(destinationCity);
            }
            distance = tourDistance;
        }
        return distance;
    }

    // Gets the number of cities on our tour
    public int tourSize() {
        return tour.size();
    }

    @Override
    public String toString() {
        String geneString = "|";
        for (int i = 0; i < tourSize(); ++i) {
            geneString += getCity(i) + "|";
        }
        return geneString;
    }
}