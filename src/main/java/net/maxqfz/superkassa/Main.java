package net.maxqfz.superkassa;

import java.util.List;

public class Main {
    private static JsonHelper parser = new JsonHelper();

    public static void main(String[] args) {
        List<Flight> flights = parser.readData("input.json");
        IFlightCombiner combiner = new FlightCombinerFast(flights);
        parser.writeData("output.json", combiner.getCombinedFlights());
    }
}