package com.gridnine.TestTask.Rules;

import com.gridnine.TestTask.Entity.Flight;
import com.gridnine.TestTask.Entity.Segment;
import com.gridnine.TestTask.Enum.DurationFiltering;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransferTimeRules implements Rules{
    private final Duration duration;
    private final DurationFiltering type;

    public TransferTimeRules(Duration duration, DurationFiltering type) {
        this.duration = duration;
        this.type = type;
    }

    @Override
    public List<Flight> doRules(List<Flight> flights) {
        switch (type) {
            case LESS -> {
                return flights.stream().filter(f -> compareTransferTimeWithTarget(f.getSegments()) <= 0).toList();
            }
            case MORE -> {
                return flights.stream().filter(f -> compareTransferTimeWithTarget(f.getSegments()) >= 0).toList();
            }
            case EXACT -> {
                return flights.stream().filter(f -> compareTransferTimeWithTarget(f.getSegments()) == 0).collect(Collectors.toList());
            }
            default -> {
                return new ArrayList<Flight>();
            }
        }
    }

    private int compareTransferTimeWithTarget(List<Segment> segments) { //
        if(segments.size() == 1) return -1;
        var sortedSegments = RulesUtils.getSegmentsSorted(segments);
        Duration timeInTransfer = Duration.of(0, ChronoUnit.HOURS);
        for(int i = 0; i < sortedSegments.size() - 1; i++) {
            timeInTransfer = timeInTransfer.plus(Duration.between(
                    sortedSegments.get(i).getDateArrival(),
                    sortedSegments.get(i + 1).getDateDeparture())
            );
        }
        return timeInTransfer.compareTo(duration);
    }

}
