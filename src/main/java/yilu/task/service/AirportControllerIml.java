package yilu.task.service;

import org.springframework.stereotype.Component;
import yilu.task.entity.*;

import java.util.*;

/**
 * This class has method to create airport objects and a method instruct flight to take off.
 * This implementation has an assumption that there is only one flight on each airport.
 */
@Component
public class AirportControllerIml implements AirportController {
    /**
     * This method build airports from set of airplanes.
     * @param airplaneList all existing airplanes
     * @return an map, key: Airport:: identity, value: airplanes are on the way to it or already arrive there.
     */
    public Map<String, Airport> initAirport(Set<Airplane> airplaneList) {
        Map<String, Airport> map = new HashMap<>();
        airplaneList.stream().map(airplane -> {
            AirplaneStatus airplaneStatus = new AirplaneStatus();
            airplaneStatus.setAirplane(airplane);
            airplaneStatus.setArrival(0L);
            return airplaneStatus;
        }).forEach(airplaneStatus -> {
            String airpostName = airplaneStatus.getAirplane().getBase();
            map.computeIfAbsent(airpostName, key -> {
                Airport airport = new Airport();
                airport.setIdentity(key);
                airport.setAirplanes(new ArrayList<>());
                return airport;
            }).getAirplanes().add(airplaneStatus);
        });
        return map;
    }

    /**
     * This method changes the status of a airplane.
     * @param origin departure from
     * @param airplane airplane which takes off;
     * @return the updated airplane
     */
    public AirplaneStatus takeOff(Airport origin, Airplane airplane) {
        Iterator<AirplaneStatus> iterator =origin.getAirplanes().iterator();
        while (iterator.hasNext()) {
            AirplaneStatus airplaneStatus = iterator.next();
            if (airplaneStatus.getAirplane().equals(airplane)) {
                iterator.remove();
                return airplaneStatus;
            }
        }
        return null;
    }

    /**
     *
     * @param schedule
     * @param airplane
     * @param destAirport
     * @return
     */
    public AirplaneStatus noticeDest(Schedule schedule, Airplane airplane, Airport destAirport) {
        AirplaneStatus airplaneStatus = new AirplaneStatus();
        airplaneStatus.setAirplane(airplane);
        airplaneStatus.setArrival(schedule.getDepatureTime() + schedule.getFlightTime());
        destAirport.getAirplanes().add(airplaneStatus);
        return airplaneStatus;
    }

    /**
     * This method is used to put an airplane back to the airport
     * @param origin departure from which airport
     * @param airplaneStatus the airplane need to be revoked
     */
    public void reverseTakeOff(Airport origin, AirplaneStatus airplaneStatus) {
        origin.getAirplanes().add(airplaneStatus);
    }

    /**
     * This method remove an plane from its destination airport
     * @param destAirport coming to which airport
     * @param airplaneStatus the airplane need to be revoked
     */
    public void reverseNoticeDest(Airport destAirport, AirplaneStatus airplaneStatus) {
        destAirport.getAirplanes().remove(airplaneStatus);
    }

}
