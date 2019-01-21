package net.maxqfz.superkassa;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FlightCombiner {
    private List<Flight> flights1NNN = new LinkedList<Flight>();
    private List<Flight> flightsN2NN = new LinkedList<Flight>();
    private List<Flight> flightsNN3N = new LinkedList<Flight>();
    private List<Flight> flightsNNN4 = new LinkedList<Flight>();
    private List<Flight> flights12NN = new LinkedList<Flight>();
    private List<Flight> flights1N3N = new LinkedList<Flight>();
    private List<Flight> flights1NN4 = new LinkedList<Flight>();
    private List<Flight> flightsN23N = new LinkedList<Flight>();
    private List<Flight> flightsN2N4 = new LinkedList<Flight>();
    private List<Flight> flightsNN34 = new LinkedList<Flight>();
    private List<Flight> flights123N = new LinkedList<Flight>();
    private List<Flight> flights12N4 = new LinkedList<Flight>();
    private List<Flight> flights1N34 = new LinkedList<Flight>();
    private List<Flight> flightsN234 = new LinkedList<Flight>();
    private List<Flight> flights1234 = new LinkedList<Flight>();

    public FlightCombiner(List<Flight> flights) {
        for(Flight flight : flights) {
            if(flight.getE1() != null) {
                if (flight.getE2() != null) {
                    if (flight.getE3() != null) {
                        if (flight.getE4() != null)
                            flights1234.add(flight);
                        else
                            flights123N.add(flight);
                    } else {
                        if (flight.getE4() != null)
                            flights12N4.add(flight);
                        else
                            flights12NN.add(flight);
                    }
                } else {
                    if (flight.getE3() != null) {
                        if (flight.getE4() != null)
                            flights1N34.add(flight);
                        else
                            flights1N3N.add(flight);
                    } else {
                        if (flight.getE4() != null)
                            flights1NN4.add(flight);
                        else
                            flights1NNN.add(flight);
                    }
                }
            } else {
                if (flight.getE2() != null) {
                    if (flight.getE3() != null) {
                        if (flight.getE4() != null)
                            flightsN234.add(flight);
                        else
                            flightsN23N.add(flight);
                    } else {
                        if (flight.getE4() != null)
                            flightsN2N4.add(flight);
                        else
                            flightsN2NN.add(flight);
                    }
                } else {
                    if (flight.getE3() != null) {
                        if (flight.getE4() != null)
                            flightsNN34.add(flight);
                        else
                            flightsNN3N.add(flight);
                    } else {
                        if (flight.getE4() != null)
                            flightsNNN4.add(flight);
                        //else NNNN
                    }
                }
            }
        }
    }

    public List<Flight> combineFlights() {
        //Combine solo's
        flights1234.addAll(multiplyLists(flights1NNN, flightsN2NN, flightsNN3N, flightsNNN4));
        //Combine solo flights with pairs to get new triplets
        flights123N.addAll(multiplyLists(flights1NNN, flightsN23N));
        flights12N4.addAll(multiplyLists(flights1NNN, flightsN2N4));
        flights1N34.addAll(multiplyLists(flights1NNN, flightsNN34));
        flights123N.addAll(multiplyLists(flightsN2NN, flights1N3N));
        flights12N4.addAll(multiplyLists(flightsN2NN, flights1NN4));
        flightsN234.addAll(multiplyLists(flightsN2NN, flightsNN34));
        flights123N.addAll(multiplyLists(flightsNN3N, flights12NN));
        flights1N34.addAll(multiplyLists(flightsNN3N, flights1NN4));
        flightsN234.addAll(multiplyLists(flightsNN3N, flightsN2N4));
        flights12N4.addAll(multiplyLists(flightsNNN4, flights12NN));
        flights1N34.addAll(multiplyLists(flightsNNN4, flights1N3N));
        flightsN234.addAll(multiplyLists(flightsNNN4, flightsN23N));
        //Combine pairs with pairs
        flights1234.addAll(multiplyLists(flights12NN, flightsNN34));
        flights1234.addAll(multiplyLists(flights1N3N, flightsN2N4));
        flights1234.addAll(multiplyLists(flights1NN4, flightsN23N));
        //Combine triplets with solo
        flights1234.addAll(multiplyLists(flights123N, flightsNNN4));
        flights1234.addAll(multiplyLists(flights12N4, flightsNN3N));
        flights1234.addAll(multiplyLists(flights1N34, flightsN2NN));
        flights1234.addAll(multiplyLists(flightsN234, flights1NNN));
        //Return result
        return flights1234;
    }

    public static List<Flight> multiplyLists(List<Flight> listA, List<Flight> listB) {
        List<Flight> joined = new ArrayList<Flight>(listA.size() * listB.size());
        for (Flight flightA : listA) {
            for (Flight flightB : listB) {
                joined.add(new Flight(
                        coalesce(flightA.getE1(), flightB.getE1()),
                        coalesce(flightA.getE2(), flightB.getE2()),
                        coalesce(flightA.getE3(), flightB.getE3()),
                        coalesce(flightA.getE4(), flightB.getE4())
                ));
            }
        }
        return joined;
    }

    public static List<Flight> multiplyLists(List<Flight> listA, List<Flight> listB, List<Flight> listC, List<Flight> listD) {
        return multiplyLists(multiplyLists(listA, listB), multiplyLists(listC, listD));
    }

    public static <T> T coalesce(T a, T b) {
        return a != null ? a : b;
    }
}
