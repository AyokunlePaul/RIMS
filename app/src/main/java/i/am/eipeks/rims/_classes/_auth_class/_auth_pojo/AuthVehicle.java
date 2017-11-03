package i.am.eipeks.rims._classes._auth_class._auth_pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import i.am.eipeks.rims._classes._model_class.Vehicle;

public class AuthVehicle {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String vehicleName;
    @SerializedName("make")
    @Expose
    private String vehicleMake;
    @SerializedName("capacity")
    @Expose
    private Integer vehicleCapacity;
    @SerializedName("weight")
    @Expose
    private String vehicleWeight;
    @SerializedName("engine")
    @Expose
    private String vehicleEngine;
    @SerializedName("rtsss")
    @Expose
    private String vehicleRtSss;
    @SerializedName("reg_number")
    @Expose
    private String vehicleRegistrationNumber;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("trips")
    @Expose
    private List<AuthTrip> trips;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleMake() {
        return vehicleMake;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }

    public Integer getVehicleCapacity() {
        return vehicleCapacity;
    }

    public void setVehicleCapacity(Integer vehicleCapacity) {
        this.vehicleCapacity = vehicleCapacity;
    }

    public String getVehicleWeight() {
        return vehicleWeight;
    }

    public void setVehicleWeight(String vehicleWeight) {
        this.vehicleWeight = vehicleWeight;
    }

    public String getVehicleEngine() {
        return vehicleEngine;
    }

    public void setVehicleEngine(String vehicleEngine) {
        this.vehicleEngine = vehicleEngine;
    }

    public String getVehicleRtSss() {
        return vehicleRtSss;
    }

    public void setVehicleRtSss(String vehicleRtSss) {
        this.vehicleRtSss = vehicleRtSss;
    }

    public String getVehicleRegistrationNumber() {
        return vehicleRegistrationNumber;
    }

    public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<AuthTrip> getTrips() {
        return trips;
    }

    public void setTrips(List<AuthTrip> trips) {
        this.trips = trips;
    }

    public Vehicle getVehicle(){
        return new Vehicle(vehicleName, vehicleMake, String.valueOf(vehicleCapacity), vehicleWeight, vehicleEngine, vehicleRtSss, vehicleRegistrationNumber);
    }
}