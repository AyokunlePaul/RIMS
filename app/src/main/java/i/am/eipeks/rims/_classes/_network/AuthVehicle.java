package i.am.eipeks.rims._classes._network;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class AuthVehicle {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("vehicle_name")
    @Expose
    private String vehicleName;
    @SerializedName("vehicle_make")
    @Expose
    private String vehicleMake;
    @SerializedName("vehicle_capacity")
    @Expose
    private Integer vehicleCapacity;
    @SerializedName("vehicle_weight")
    @Expose
    private String vehicleWeight;
    @SerializedName("vehicle_engine")
    @Expose
    private String vehicleEngine;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("vehicle_rt_sss")
    @Expose
    private String vehicleRtSss;
    @SerializedName("vehicle_registration_number")
    @Expose
    private String vehicleRegistrationNumber;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
}