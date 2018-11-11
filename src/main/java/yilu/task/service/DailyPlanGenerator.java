package yilu.task.service;

import yilu.task.dao.AirplaneRepository;
import yilu.task.dao.ScheduleRepository;
import yilu.task.entity.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import yilu.task.exceptions.NoValidSchedulePlanException;

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

    private Set<Airplane> airplaneSet;
    private List<Schedule> scheduleList;
    private Map<String, List<FlightPlan>> flightPlanMap;

    private Map<String, List<OperationsPlan>> operationsPlanMap;
    private Map<String, Airport> airportMap;

    private void init() {
        airplaneSet = airplaneRepository.getAirplane();
        scheduleList = new ArrayList<Schedule>(scheduleRepository.getSchedule());
        Collections.sort(scheduleList,
                (o1, o2) -> (int) (o1.getDepatureTime() - o2.getDepatureTime()));
        airportMap = airportController.initAirport(airplaneSet);
        flightPlanMap = new HashMap<>();
        operationsPlanMap = new HashMap<>();
    }

    public void generatePlan() {
        init();

        boolean result = dfs(scheduleList);
        if (!result) {
            throw new NoValidSchedulePlanException();
        }
    }

    /**
     * This method applies depth first search to find the first valid plans.
     * plans are valid such as 1) each plane's last journey is going home
     * 2) all schedule have been consumed
     * @param scheduleList a list of schedule will be consume
     * @return true if there is a valid plan; false otherwise.
     */
    private boolean dfs(List<Schedule> scheduleList) {
        if (scheduleList.size() == 0) {
            if (airplaneAtHome(operationsPlanMap))
                return true;
            else
                return false;
        }
        Schedule schedule = scheduleList.remove(0);
        List<Airplane> candidatePlanList = calCandidatePlane(schedule);
        if (candidatePlanList.size() == 0) {
            scheduleList.add(0, schedule);
            return false;
        }

        Airport originAirport = airportMap.get(schedule.getOrigin());
        Airport destAirport = airportMap.get(schedule.getDestination());
        for (Airplane airplane : candidatePlanList) {
            AirplaneStatus takeOffPlaneStatus = airportController.takeOff(originAirport, airplane);
            AirplaneStatus noticeDestPlaneStatus =airportController.noticeDest(schedule, airplane, destAirport);

            operationsPlanMap.computeIfAbsent(airplane.getRegistration(),
                    key -> new ArrayList<>()).add(generatePlan(schedule, originAirport.getIdentity(),
                    destAirport.getIdentity()));
            flightPlanMap.computeIfAbsent(originAirport.getIdentity(),
                    key -> new ArrayList<>()).add(generatePlan(schedule, airplane));
            if (dfs(scheduleList)) {
                return true;
            } else {
                // reverse takeOff, reverse noticeDest, reverse FlightPlan, reverse OperationsPlan
                airportController.reverseTakeOff(originAirport, takeOffPlaneStatus);
                airportController.reverseNoticeDest(destAirport, noticeDestPlaneStatus);
                operationsPlanMap.get(airplane.getRegistration())
                        .remove(operationsPlanMap.get(airplane.getRegistration()).size() - 1);
                flightPlanMap.get(originAirport.getIdentity())
                        .remove(flightPlanMap.get(originAirport.getIdentity()).size() - 1);
            }
        }
        // reverse schedule
        scheduleList.add(0, schedule);
        return false;
    }

    /**
     * This method check whether all airplanes are at home
     * @param operationsPlanMap map contains all airplanes plans
     * @return true if they are all at home; false otherwise.
     */
    private boolean airplaneAtHome(Map<String, List<OperationsPlan>> operationsPlanMap) {
        for (Map.Entry<String, List<OperationsPlan>> entry : operationsPlanMap.entrySet()) {
            List<OperationsPlan> list = entry.getValue();
            Airplane airplane = airplaneSet.stream().filter(airplane1 ->
                    airplane1.getRegistration().equals(entry.getKey())).findFirst().get();
            if (!list.isEmpty()) {
                OperationsPlan lastPlanOfPlane = list.get(list.size() - 1);
                if (!lastPlanOfPlane.getDestination().equals(airplane.getBase())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method calculate candidate airplanes based on current status of all airports and
     * the schedule
     * @param schedule to carry out
     * @return planes can consume the schedule
     */
    public List<Airplane> calCandidatePlane(Schedule schedule) {
        Airport origin = airportMap.get(schedule.getOrigin());
        // get airplane status
        // arrival time < deparure
        long departureTime = schedule.getDepatureTime();
        String destName = schedule.getDestination();
        List<Airplane> candidatePlaneList = origin.getAirplanes().stream().filter(airplaneStatus ->
                airplaneStatus.getArrival() < departureTime)
                .map(airplaneStatus -> airplaneStatus.getAirplane())
                .collect(Collectors.toList());
        return candidatePlaneList;
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
