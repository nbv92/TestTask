package com.gridnine.TestTask.Rules;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.gridnine.TestTask.Entity.*;
import com.gridnine.TestTask.Enum.*;

public class FlightArrivalDateRules implements Rules{
    private final LocalDateTime time;
    private final DateFiltering type;

    public FlightArrivalDateRules(LocalDateTime time, DateFiltering type) {
        this.time = time;
        this.type = type;
    }

    @Override
    public List<Flight> doRules(List<Flight> flights) {
        switch (type) {
            case BEFORE -> {
                return flights.stream().filter(f -> isArrivingBeforeTime(f.getSegments())).toList();
            }
            case AFTER -> {
                return flights.stream().filter(f -> isArrivingAfterTime(f.getSegments())).toList();
            }
            case EXACT -> {
                return flights.stream().filter(f -> isArrivingAtDay(f.getSegments())).toList();
            }
            default -> {
                return new ArrayList<Flight>();
            }
        }
    }

    private boolean isArrivingBeforeTime(List<Segment> segments) {
        return segments.stream().anyMatch(s -> s.getDateArrival().isBefore(time));
    }

    private boolean isArrivingAfterTime(List<Segment> segments) {
        return segments.stream().anyMatch(s -> s.getDateArrival().isAfter(time));
    }

    private boolean isArrivingAtDay(List<Segment> segments) {
        var lastSegment = RulesUtils.getLastSegment(segments);
        return lastSegment.getDateArrival().truncatedTo(ChronoUnit.DAYS)
                .isEqual(time.truncatedTo(ChronoUnit.DAYS));
    }
}
