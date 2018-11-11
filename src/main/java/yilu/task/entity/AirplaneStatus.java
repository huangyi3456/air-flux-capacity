package yilu.task.entity;

import yilu.task.entity.Airplane;

public class AirplaneStatus {
    private Airplane airplane;
    private long arrival;

    public void update(long arrival) {
        setArrival(arrival);
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public long getArrival() {
        return arrival;
    }

    public void setArrival(long arrival) {
        this.arrival = arrival;
    }
}
