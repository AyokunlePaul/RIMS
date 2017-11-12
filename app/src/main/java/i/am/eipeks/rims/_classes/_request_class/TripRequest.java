package i.am.eipeks.rims._classes._request_class;



public class TripRequest {

    private Integer vehicleId;
    private String displacement, travelDate;

    public TripRequest(Integer vehicleId, String displacement, String travelDate) {
        this.vehicleId = vehicleId;
        this.displacement = displacement;
        this.travelDate = travelDate;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public String getDisplacement() {
        return displacement;
    }

    public String getTravelDate() {
        return travelDate;
    }
}
