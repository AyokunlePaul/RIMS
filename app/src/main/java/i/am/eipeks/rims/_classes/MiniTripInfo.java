package i.am.eipeks.rims._classes;


public class MiniTripInfo {

    private String vehicleName, uuid;
    private SubTrip subTrip;
    private Vehicle vehicle;

    public MiniTripInfo(Vehicle vehicle, SubTrip subTrip){
        this.vehicleName = vehicle.getVehicleName();
        this.subTrip = subTrip;
        this.uuid = null;
        this.vehicle = vehicle;
    }

//    public MiniTripInfo(String vehicleName, String displacement, String dateAndTime,
//                        String numberOfPassengers, String driversName) {
//        this.vehicleName = vehicleName;
//        this.displacement = displacement;
//        this.dateAndTime = dateAndTime;
//        this.numberOfPassengers = numberOfPassengers;
//        this.driversName = driversName;
//        this.uuid = null;
//    }
//
//    public MiniTripInfo(String vehicleName, String displacement, String dateAndTime,
//                        String numberOfPassengers, String driversName, String uuid) {
//        this.vehicleName = vehicleName;
//        this.displacement = displacement;
//        this.dateAndTime = dateAndTime;
//        this.numberOfPassengers = numberOfPassengers;
//        this.driversName = driversName;
//        this.uuid = uuid;
//    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getDisplacement() {
        return this.subTrip.getTrip().getDisplacement();
    }

    public String getDateAndTime() {
        return this.subTrip.getTrip().getDateAndTime();
    }

    public String getNumberOfPassengers() {
        return subTrip.getTrip().getTotalNumberOfPassengers();
    }

    public String getDriversName() {
        return subTrip.getDriver().getDriverName();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public SubTrip getSubTrip(){
        return this.subTrip;
    }

    public Vehicle getVehicle(){return this.vehicle;}
}
