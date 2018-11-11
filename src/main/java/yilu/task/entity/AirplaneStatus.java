package yilu.task.entity;

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

    @Override
    public int hashCode() {
        return airplane.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AirplaneStatus) {
            AirplaneStatus o1 = (AirplaneStatus) o;
            return airplane.equals(o1.getAirplane());
        } else {
            return false;
        }

    }

}
