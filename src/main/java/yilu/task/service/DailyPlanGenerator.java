package yilu.task.service;

import yilu.task.dao.AirplaneRepository;
import yilu.task.dao.ScheduleRepository;
import yilu.task.entity.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DailyPlanGenerator {
    @Autowired
    private AirplaneRepository airplaneRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private AirportController airportController;

    private List<Airplane> airplaneList;
    private List<Schedule> scheduleList;
    private Map<String, List<FlightPlan>> flightPlanMap;

    private Map<String, List<OperationsPlan>> operationsPlanMap;
    private Map<String, Airport> airportMap;

    private void init() {
        airplaneList = airplaneRepository.getAirplane();
        scheduleList = scheduleRepository.getSchedule();
        airportMap = airportController.initAirport(airplaneList);
    }

    public void generatePlan() {
        init();
        for (Schedule schedule : scheduleList) {
            // TODO airport does not exist?
            Airport originAirport = airportMap.get(schedule.getOrigin());
            Airport destAirport = airportMap.get(schedule.getDestination());
            Airplane onDutyAirplane = airportController.takeOff(schedule, originAirport);
            airportController.noticeDest(schedule, onDutyAirplane, destAirport);

            operationsPlanMap.getOrDefault(onDutyAirplane.getRegistration(),
                    new ArrayList<>()).add(generatePlan(schedule, originAirport.getIdentity(),
                    destAirport.getIdentity()));

            flightPlanMap.getOrDefault(originAirport.getIdentity(),
                    new ArrayList<>()).add(generatePlan(schedule, onDutyAirplane));
        }
    }

    public OperationsPlan generatePlan(Schedule schedule, String origin, String destination) {
        OperationsPlan operationsPlan = new OperationsPlan();
        operationsPlan.setOrigin(origin);
        operationsPlan.setDestination(destination);
        operationsPlan.setDeparture(schedule.getDepatureTime());
        return operationsPlan;
    }

    public FlightPlan generatePlan(Schedule schedule, Airplane airplane) {
        FlightPlan flightPlan = new FlightPlan();
        flightPlan.setArrival(schedule.getDepatureTime() + schedule.getFlightTime());
        flightPlan.setDeparture(schedule.getDepatureTime());
        flightPlan.setDestination(schedule.getDestination());
        flightPlan.setEquipment(airplane.getType());
        flightPlan.setOrigin(schedule.getOrigin());
        return flightPlan;
    }

    public Map<String, List<FlightPlan>> getFlightPlanMap() {
        return flightPlanMap;
    }

    public Map<String, List<OperationsPlan>> getOperationsPlanMap() {
        return operationsPlanMap;
    }
}
