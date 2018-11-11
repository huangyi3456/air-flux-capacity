package yilu.task.entity;

import java.util.List;
import java.util.Map;

public class Airport {
    private String identity;
    private List<AirplaneStatus> airplanes;

    public void setAirplanes(List<AirplaneStatus> airplanes) {
        this.airplanes = airplanes;
    }

    public List<AirplaneStatus> getAirplanes() {
        return airplanes;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

}
