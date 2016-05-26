package sa;

/**
 * Created by deepanshu on 5/26/16.
 * City.java
 * Models a city
 */

public class City {
    int x;
    int y;

    // Randomly placed city
    public City() {
        this.x = (int) (Math.random() * 200);
        this.y = (int) (Math.random() * 200);
    }

    // Construct a city at chosen x, y location
    public City(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Get city's x co-ordinate
    public int getX() {
        return this.x;
    }

    // Get city's y co-ordinate
    public int getY() {
        return this.y;
    }

    public double distanceTo(City city) {
        int xDistance = Math.abs(getX() - city.getX());
        int yDistance = Math.abs(getY() - city.getY());
        double distance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));

        return distance;
    }

    @Override
    public String toString(){
        return getX()+", "+getY();
    }
}