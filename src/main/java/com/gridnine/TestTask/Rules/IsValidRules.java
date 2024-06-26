package com.gridnine.TestTask.Rules;

import com.gridnine.TestTask.Entity.*;

import java.util.List;

public class IsValidRules implements Rules{
    @Override
    public List<Flight> doRules(List<Flight> flights) {
        return flights.stream().filter(IsValidRules::isFlightValid).toList();
    }

    private static boolean isFlightValid(Flight flight) {

        for (Segment segment : flight.getSegments()) {
            if (!isSegmentValid(segment)) {
                return false;
            }
        }

        var segmentsSorted = RulesUtils.getSegmentsSorted(flight.getSegments());

        for (int i = 0; i < segmentsSorted.size() - 1; i++) {
            if (segmentsSorted.get(i).getDateArrival().isAfter(segmentsSorted.get(i + 1).getDateDeparture())) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSegmentValid(Segment segment) {
        return segment.getDateDeparture().isBefore(segment.getDateArrival());
    }
}
