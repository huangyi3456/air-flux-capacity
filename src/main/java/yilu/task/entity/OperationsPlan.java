package yilu.task.entity;

public class OperationsPlan {
    private String origin;
    private String destination;
    private long departure;

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

    @Override
    public int hashCode() {
        return (int) (origin.hashCode() * 13 + destination.hashCode() * 7 + departure * 3);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof OperationsPlan) {
            OperationsPlan o1 = (OperationsPlan) o;
            return this.origin.equals(o1.origin) && this.destination.equals(o1.destination)
                    && this.departure == o1.departure;
        }
        return false;
    }
}
