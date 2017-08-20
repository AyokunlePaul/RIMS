package i.am.eipeks.rims._classes;



public class SubTrip {

    private Trip trip;
    private Driver driver;

    public SubTrip(Trip trip, Driver driver) {
        this.trip = trip;
        this.driver = driver;
    }

    public Trip getTrip() {
        return trip;
    }

    public Driver getDriver() {
        return driver;
    }
}
