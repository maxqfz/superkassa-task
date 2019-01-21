package net.maxqfz.superkassa;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    private static JsonHelper parser = new JsonHelper();

    public static void main(String[] args) {
        List<Flight> flights = parser.readData("input.json");
        FlightCombiner combiner = new FlightCombiner(flights);
        parser.writeData("output.json", combiner.combineFlights());
    }
}