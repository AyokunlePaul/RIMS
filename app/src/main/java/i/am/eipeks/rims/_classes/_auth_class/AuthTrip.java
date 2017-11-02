package i.am.eipeks.rims._classes._auth_class;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//@SuppressWarnings("unused")
public class AuthTrip {

    @Expose
    @SerializedName("id")
    private Integer tripId;

    @Expose
    @SerializedName("vehicle_id")
    private Integer vehicleId;

    @Expose
    @SerializedName("departure_time")
    private String departureTime;

    @Expose
    @SerializedName("displacement")
    private String displacement;

    @Expose
    @SerializedName("created_at")
    private String createAt;

    @Expose
    @SerializedName("update_at")
    private String updatedAt;

    @Expose
    @SerializedName("driver")
    private AuthDriver driver;

    @Expose
    @SerializedName("passengers")
    private List<AuthPassenger> passengers;

    public AuthDriver getDriver() {
        return driver;
    }

    public void setDriver(AuthDriver driver) {
        this.driver = driver;
    }

    public List<AuthPassenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<AuthPassenger> passengers) {
        this.passengers = passengers;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
