package com.gridnine.TestTask.Rules;

import com.gridnine.TestTask.Entity.*;
import com.gridnine.TestTask.Enum.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class FlightDateDepartureRules implements Rules{
    private final LocalDateTime time;
    private final DateFiltering type;

    public FlightDateDepartureRules(LocalDateTime time, DateFiltering type) {
        this.time = time;
        this.type = type;
    }


    @Override
    public List<Flight> doRules(List<Flight> flights) {
        switch (type) {
            case BEFORE -> {
                return flights.stream().filter(f -> isDepartingBeforeTime(f.getSegments())).toList();
            }
            case AFTER -> {
                return flights.stream().filter(f -> isDepartingAfterTime(f.getSegments())).toList();
            }
            case EXACT -> {
                return flights.stream().filter(f -> isDepartingAtDay(f.getSegments())).toList();
            }
            default -> {
                return new ArrayList<Flight>();
            }
        }
    }

    private boolean isDepartingBeforeTime(List<Segment> segments) {
        return segments.stream().anyMatch(s -> s.getDateDeparture().isBefore(time));
    }

    private boolean isDepartingAfterTime(List<Segment> segments) {
        return segments.stream().anyMatch(s -> s.getDateDeparture().isAfter(time));
    }

    private boolean isDepartingAtDay(List<Segment> segments) {
        var firstSegment = RulesUtils.getFirstSegment(segments);

        return firstSegment.getDateDeparture().truncatedTo(ChronoUnit.DAYS)
                .isEqual(time.truncatedTo(ChronoUnit.DAYS));
    }
}
