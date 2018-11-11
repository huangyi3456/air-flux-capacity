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
/*        return airplaneList.stream().map(Airplane::getBase).distinct()
                .map(airportName -> {
                    Airport airport = new Airport();
                    airport.setIdentity(airportName);
                    airport.setAirplanes(new ArrayList<>());
                    return airport;
                }).collect(Collectors.toMap(Airport::getIdentity, airport -> airport));*/
    }


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

    public AirplaneStatus noticeDest(Schedule schedule, Airplane airplane, Airport destAirport) {
        AirplaneStatus airplaneStatus = new AirplaneStatus();
        airplaneStatus.setAirplane(airplane);
        airplaneStatus.setArrival(schedule.getDepatureTime() + schedule.getFlightTime());
        destAirport.getAirplanes().add(airplaneStatus);
        return airplaneStatus;
    }

    public void reverseTakeOff(Airport origin, AirplaneStatus airplaneStatus) {
        origin.getAirplanes().add(airplaneStatus);
    }

    public void reverseNoticeDest(Airport destAirport, AirplaneStatus airplaneStatus) {
        destAirport.getAirplanes().remove(airplaneStatus);
    }

}
