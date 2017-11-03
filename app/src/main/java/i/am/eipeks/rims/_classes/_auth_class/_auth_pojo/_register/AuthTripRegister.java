package i.am.eipeks.rims._classes._auth_class._auth_pojo._register;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


//@SuppressWarnings("unused")
public class AuthTripRegister {

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
