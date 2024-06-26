package com.gridnine.TestTask.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Segment {
    private final LocalDateTime dateDeparture;

    private final LocalDateTime dateArrival;

    @Override
    public String toString() {
        DateTimeFormatter segString =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return "Отправление " + dateDeparture.format(segString) + ", Прибытие " + dateArrival.format(segString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return Objects.equals(dateDeparture, segment.dateDeparture) && Objects.equals(dateArrival, segment.dateArrival);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateDeparture, dateArrival);
    }

    public LocalDateTime getDateDeparture() {
        return dateDeparture;
    }

    public LocalDateTime getDateArrival() {
        return dateArrival;
    }

    public Segment(LocalDateTime dateDeparture, LocalDateTime dateArrival) {
        this.dateDeparture = dateDeparture;
        this.dateArrival = dateArrival;
    }
}
