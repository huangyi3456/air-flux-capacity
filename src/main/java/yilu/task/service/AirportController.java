package yilu.task.service;

import yilu.task.entity.Airplane;
import yilu.task.entity.AirplaneStatus;
import yilu.task.entity.Airport;
import yilu.task.entity.Schedule;

import java.util.Map;
import java.util.Set;

public interface AirportController {
    Map<String, Airport> initAirport(Set<Airplane> airplaneList);
    AirplaneStatus takeOff(Airport origin, Airplane airplane);
    AirplaneStatus noticeDest(Schedule schedule, Airplane airplane, Airport destAirport);
    void reverseTakeOff(Airport origin, AirplaneStatus airplaneStatus);
    void reverseNoticeDest(Airport destAirport, AirplaneStatus airplaneStatus);
}
