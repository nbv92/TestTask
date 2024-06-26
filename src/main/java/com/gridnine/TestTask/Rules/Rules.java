package com.gridnine.TestTask.Rules;

import com.gridnine.TestTask.Entity.Flight;

import java.util.List;

public interface Rules {
    List<Flight> doRules(List<Flight> flights);
}
