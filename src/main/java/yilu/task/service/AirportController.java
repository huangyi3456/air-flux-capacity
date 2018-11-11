package yilu.task.service;

import yilu.task.entity.Airplane;
import yilu.task.entity.Airport;
import yilu.task.entity.Schedule;

import java.util.List;
import java.util.Map;

public interface AirportController {
    Map<String, Airport> initAirport(List<Airplane> airplaneList);
    Airplane takeOff(Schedule schedule, Airport origin);
    void noticeDest(Schedule schedule, Airplane airplane, Airport destAirport);
}
