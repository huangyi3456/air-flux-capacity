package yilu.task.entity;

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
}
