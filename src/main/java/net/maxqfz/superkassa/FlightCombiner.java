package net.maxqfz.superkassa;

import java.util.LinkedList;
import java.util.List;

public class FlightCombiner implements IFlightCombiner {
    private List<Flight> combinedFlights;

    public FlightCombiner(List<Flight> flights) {
        combinedFlights = new LinkedList<Flight>();
        Flight fi, fij, fijk, fijkl;
        for (int i = 0; i < flights.size(); ++i) {
            fi = flights.get(i);
            //Checking if i is self-sufficient
            if (checkSelfSufficient(fi)) {
                combinedFlights.add(fi);
                continue;
            }
            //If not, then trying to combine
            for (int j = i + 1; j < flights.size(); ++j) {
                //Combining i + j
                try {
                    fij = combineTwoFlights(fi, flights.get(j));
                } catch (IllegalArgumentException e) {
                    //If combining crosses values, then it is not correct
                    continue;
                }
                //Checking if i + j is self-sufficient
                if (checkSelfSufficient(fij)) {
                    combinedFlights.add(fij);
                    continue;
                }
                //If still not, trying more
                for (int k = j + 1; k < flights.size(); ++k) {
                    try {
                        fijk = combineTwoFlights(fij, flights.get(k));
                    } catch (IllegalArgumentException e) {
                        //If combining crosses values, then it is not correct
                        continue;
                    }
                    //Checking if i + j + k is self-sufficient
                    if (checkSelfSufficient(fijk)) {
                        combinedFlights.add(fijk);
                        continue;
                    }
                    //If still not, trying more
                    for (int l = k + 1; l < flights.size(); ++l) {
                        //Combining i + j + k + l
                        try {
                            fijkl = combineTwoFlights(fijk, flights.get(l));
                        } catch (IllegalArgumentException e) {
                            //If combining crosses values, then it is not correct
                            continue;
                        }
                        //Checking if i + j + k + l is self-sufficient
                        if (checkSelfSufficient(fijkl))
                            combinedFlights.add(fijkl);
                    }
                }
            }
        }
    }

    public List<Flight> getCombinedFlights() {
        return combinedFlights;
    }

    private boolean checkSelfSufficient(Flight flight) {
        return flight.getE1() != null && flight.getE2() != null &&
                flight.getE3() != null && flight.getE4() != null;
    }

    private Flight combineTwoFlights(Flight flightA, Flight flightB) {
        return new Flight(
                coalesce(flightA.getE1(), flightB.getE1()),
                coalesce(flightA.getE2(), flightB.getE2()),
                coalesce(flightA.getE3(), flightB.getE3()),
                coalesce(flightA.getE4(), flightB.getE4())
        );
    }

    private <T> T coalesce(T a, T b) {
        if (a != null) {
            if (b != null)
                throw new IllegalArgumentException();
            else
                return a;
        } else
            return b;
    }
}