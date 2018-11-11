package yilu.task.service;

import yilu.task.entity.FlightPlan;
import yilu.task.entity.OperationsPlan;

import java.util.List;

public interface PlanService {
    List<FlightPlan> getFlightPlan();
    List<FlightPlan> getFlightPlanByAirport(String airport);
    List<OperationsPlan> getOperationsPlanByFlight(String registration);
}
