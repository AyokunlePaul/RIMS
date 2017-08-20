package i.am.eipeks.rims._classes;


public class TripTemporary {

    private Vehicle vehicle;
    private Passenger passenger;
    private Driver driver;
    private String dateAndTime, displacement, numberOfPassengers;

    public TripTemporary(Vehicle vehicle, Passenger passenger, Driver driver,
                         String dateAndTime, String displacement, String numberOfPassengers) {
        this.vehicle = vehicle;
        this.passenger = passenger;
        this.driver = driver;
        this.dateAndTime = this.dateAndTime;
        this.displacement = displacement;
        this.numberOfPassengers = numberOfPassengers;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Driver getDriver() {
        return driver;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public String getDisplacement() {
        return displacement;
    }

    public String getNumberOfPassengers() {
        return numberOfPassengers;
    }
}
