package yilu.task.entity;

import java.util.Objects;

public class FlightPlan {
    private String origin;
    private String destination;
    private long departure;
    private long arrival;
    private String equipment;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public long getDeparture() {
        return departure;
    }

    public void setDeparture(long departure) {
        this.departure = departure;
    }

    public long getArrival() {
        return arrival;
    }

    public void setArrival(long arrival) {
        this.arrival = arrival;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightPlan that = (FlightPlan) o;
        return departure == that.departure &&
                arrival == that.arrival &&
                Objects.equals(origin, that.origin) &&
                Objects.equals(destination, that.destination) &&
                Objects.equals(equipment, that.equipment);
    }

    @Override
    public int hashCode() {

        return Objects.hash(origin, destination, departure, arrival, equipment);
    }
}
