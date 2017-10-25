package i.am.eipeks.rims._classes;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class Vehicle {

    public Vehicle(){}

    public Vehicle(String vehicleName, String vehicleMake, String vehicleCapacity,
                   String vehicleWeight, String vehicleEngine, String vehicleRTSS, String registrationNumber) {
        this.vehicleName = vehicleName;
        this.vehicleMake = vehicleMake;
        this.vehicleCapacity = vehicleCapacity;
        this.vehicleWeight = vehicleWeight;
        this.vehicleEngine = vehicleEngine;
        this.vehicleRTSS = vehicleRTSS;
        this.registrationNumber = registrationNumber;
    }

    public Vehicle(String vehicleInformation){
        List<String> information = Arrays.asList(vehicleInformation.split("_"));
        this.vehicleName = information.get(0);
        this.vehicleMake = information.get(1);
        this.vehicleCapacity = information.get(2);
        this.vehicleWeight = information.get(3);
        this.vehicleEngine = information.get(4);
        this.vehicleRTSS = information.get(5);
        this.registrationNumber = information.get(6);
    }

    @SerializedName("vehicleName")
    @Expose
    private String vehicleName;

    @SerializedName("vehicleMake")
    @Expose
    private String vehicleMake;

    @SerializedName("vehicleCapacity")
    @Expose
    private String vehicleCapacity;

    @SerializedName("vehicleWeight")
    @Expose
    private String vehicleWeight;

    @SerializedName("vehicleEngine")
    @Expose
    private String vehicleEngine;

    @SerializedName("vehicleRTSS")
    @Expose
    private String vehicleRTSS;

    @SerializedName("registrationNumber")
    @Expose
    private String registrationNumber;


    public String getVehicleName() {
        return vehicleName;
    }

    public String getVehicleMake() {
        return vehicleMake;
    }

    public String getVehicleCapacity() {
        return vehicleCapacity;
    }

    public String getVehicleWeight() {
        return vehicleWeight;
    }

    public String getVehicleEngine() {
        return vehicleEngine;
    }

    public String getVehicleRTSSS() {
        return vehicleRTSS;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    @Override
    public String toString() {
        return getVehicleName() + "\n" + getVehicleMake() + "\n" +
                getVehicleCapacity() + "\n" + getVehicleWeight() + "\n" +
                getVehicleEngine() + "\n" + getVehicleRTSSS() + "\n" + getRegistrationNumber();
    }
}
