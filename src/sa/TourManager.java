package sa;

/**
 * Created by deepanshu on 5/26/16.
 * TourManager.java
 * Holds the cities of tour
 */

import java.util.ArrayList;

public class TourManager {

    // Holds our cities
    private static ArrayList destinationCities = new ArrayList<City>();

    // Adds a destination city
    public static void addCity(City city) {
        destinationCities.add(city);
    }

    // Get a city
    public static City getCity(int index) {
        return (City)destinationCities.get(index);
    }

    public static int numberOfCities() {
        return destinationCities.size();
    }
}