package i.am.eipeks.rims._classes._network;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthVehicle {

    public AuthVehicle(){}

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("make")
    @Expose
    private String make;
    @SerializedName("capacity")
    @Expose
    private String capacity;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("engine")
    @Expose
    private String engine;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("rt_sss")
    @Expose
    private String rtSss;
    @SerializedName("registration_number")
    @Expose
    private String registrationNumber;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRtSss() {
        return rtSss;
    }

    public void setRtSss(String rtSss) {
        this.rtSss = rtSss;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

}