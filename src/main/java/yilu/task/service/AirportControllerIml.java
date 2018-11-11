package yilu.task.service;

import org.springframework.stereotype.Component;
import yilu.task.entity.*;
import yilu.task.exceptions.NoAvaliablePlaneExeption;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class has method to create airport objects and a method instruct flight to take off.
 * This implementation has an assumption that there is only one flight on each airport.
 */
@Component
public class AirportControllerIml implements AirportController {
    public Map<String, Airport> initAirport(List<Airplane> airplaneList) {
        return airplaneList.stream().map(Airplane::getBase).distinct()
                .map(airportName -> {
                    Airport airport = new Airport();
                    airport.setIdentity(airportName);
                    airport.setAirplanes(new ArrayList<>());
                    return airport;
                }).collect(Collectors.toMap(Airport::getIdentity, airport -> airport));
    }

    public Airplane takeOff(Schedule schedule, Airport origin) {
            // get airplane status
            // 1, arrival time < deparure
            // 2, either base here or base the destination airport
            String destName = schedule.getDestination();
            Optional<AirplaneStatus> airplaneDepature = origin.getAirplanes().stream().filter(airplaneStatus -> {
                return airplaneStatus.getAirplane().getBase() == origin.getIdentity()
                        || airplaneStatus.getAirplane().getBase() == destName;
            }).min((o1, o2) -> (int) (o1.getArrival() - o2.getArrival()));

            if (!airplaneDepature.isPresent()) {
                throw new NoAvaliablePlaneExeption(schedule);
            }
            origin.getAirplanes().remove(airplaneDepature.get().getAirplane().getBase());
            return airplaneDepature.get().getAirplane();
    }

    public void noticeDest(Schedule schedule, Airplane airplane, Airport destAirport) {
        AirplaneStatus airplaneStatus = new AirplaneStatus();
        airplaneStatus.setAirplane(airplane);
        airplaneStatus.setArrival(schedule.getDepatureTime() + schedule.getFlightTime());
        destAirport.getAirplanes().add(airplaneStatus);
    }

}
