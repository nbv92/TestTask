package com.gridnine.TestTask;

import com.gridnine.TestTask.Enum.DateFiltering;
import com.gridnine.TestTask.Enum.DurationFiltering;
import com.gridnine.TestTask.Rules.IsValidRules;
import com.gridnine.TestTask.Rules.Rules;
import com.gridnine.TestTask.Rules.FlightDateDepartureRules;
import com.gridnine.TestTask.Entity.Flight;
import com.gridnine.TestTask.Entity.FlightBulder;
import com.gridnine.TestTask.Rules.TransferTimeRules;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MainTests {
    @Test
    public void testFilterFlights_validFilter() {
        RulesService rulesService = new RulesService();
        List flights = FlightBulder.createFlights();

        Rules rulesValid = new IsValidRules();
        List<Flight> result = rulesService.FilterFlights(flights, rulesValid);

        for(Flight flight : result) {
            assertTrue(flight.getSegments().stream().allMatch(segment -> segment.getDateDeparture().isAfter(LocalDateTime.now())));
        }
    }

    @Test
    public void testFilterFlights_fromNowFilter() {
        RulesService rulesService = new RulesService();
        List flights = FlightBulder.createFlights();

        Rules rulesFromNow = new FlightDateDepartureRules(LocalDateTime.now(), DateFiltering.AFTER);
        List<Flight> result = rulesService.FilterFlights(flights, rulesFromNow);

        for(Flight flight : result) {
            assertTrue(flight.getSegments().stream().allMatch(segment -> segment.getDateDeparture().isAfter(LocalDateTime.now())));
        }

    }

}
