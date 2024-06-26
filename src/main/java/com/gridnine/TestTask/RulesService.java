package com.gridnine.TestTask;

import com.gridnine.TestTask.Entity.Flight;
import com.gridnine.TestTask.Rules.Rules;

import java.util.List;

public class RulesService {
    public List<Flight> FilterFlights(List<Flight> flights, Rules... rules ) {
        List<Flight> result = List.copyOf(flights);
        for (var rule : rules) {
            result = rule.doRules(result);
        }
        return result;
    }
}
