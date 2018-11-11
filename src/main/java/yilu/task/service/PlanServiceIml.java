package yilu.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import yilu.task.entity.FlightPlan;
import yilu.task.entity.OperationsPlan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlanServiceIml implements PlanService {
    @Autowired
    DailyPlanGenerator planGenerator;

    public List<FlightPlan> getFlightPlan() {
        return planGenerator.getFlightPlanMap().values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public List<FlightPlan> getFlightPlanByAirport(String airport) {
        return planGenerator.getFlightPlanMap().getOrDefault(airport, new ArrayList<>());
    }

    public List<OperationsPlan> getOperationsPlanByFlight(String registration) {
        return planGenerator.getOperationsPlanMap().getOrDefault(registration, new ArrayList<>());
    }
}
