package yilu.task.entity;

public class Airplane {
    private String type;
    private String base;
    private String registration;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    @Override
    public int hashCode() {
        return type.hashCode() * 13 + base.hashCode() * 7 + registration.hashCode() * 5;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Airplane) {
            Airplane o1 = (Airplane) o;
            return this.registration.equals(o1.registration);
        } else {
            return false;
        }
    }
}
