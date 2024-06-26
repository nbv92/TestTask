package com.gridnine.TestTask.Rules;

import com.gridnine.TestTask.Entity.Segment;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class RulesUtils {
    public static Segment getFirstSegment (List<Segment> segments) {
        return segments.stream()
                .min(Comparator.comparing(Segment::getDateDeparture))
                .orElseThrow(NoSuchElementException::new);
    }

    public static Segment getLastSegment (List<Segment> segments) {
        return segments.stream()
                .max(Comparator.comparing(Segment::getDateArrival))
                .orElseThrow(NoSuchElementException::new);
    }

    public static List<Segment> getSegmentsSorted(List<Segment> segments) {
        return segments.stream()
                .sorted(Comparator.comparing(Segment::getDateDeparture))
                .toList();
    }
}
