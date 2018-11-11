package yilu.task.entity;

import java.util.Objects;

public class Schedule {
    private long depatureTime;
    private String origin;
    private String destination;
    private long flightTime;

    public long getDepatureTime() {
        return depatureTime;
    }

    public void setDepatureTime(long depatureTime) {
        this.depatureTime = depatureTime;
    }

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

    public long getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(long flightTime) {
        this.flightTime = flightTime;
    }

    @Override
    public String toString() {
        return "depature: " + depatureTime + ", origin: " + origin + ", destination: " + destination + ", flight time: " + flightTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return depatureTime == schedule.depatureTime &&
                flightTime == schedule.flightTime &&
                Objects.equals(origin, schedule.origin) &&
                Objects.equals(destination, schedule.destination);
    }

    @Override
    public int hashCode() {

        return Objects.hash(depatureTime, origin, destination, flightTime);
    }
}
