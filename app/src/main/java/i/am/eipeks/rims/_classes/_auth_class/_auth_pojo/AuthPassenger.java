package i.am.eipeks.rims._classes._auth_class._auth_pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import i.am.eipeks.rims._classes._model_class.Passenger;

public class AuthPassenger {

    @Expose
    @SerializedName("id")
    private Integer id;

    @Expose
    @SerializedName("trip_id")
    private Integer tripId;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("phone")
    private String phone;

    @Expose
    @SerializedName("sex")
    private String sex;

    @Expose
    @SerializedName("address")
    private String address;

    @Expose
    @SerializedName("next_of_kin")
    private String nextOfKin;

    @Expose
    @SerializedName("next_of_kin_phone")
    private String nextOfKinPhone;

    @Expose
    @SerializedName("seat")
    private Integer seat;

    @Expose
    @SerializedName("created_at")
    private String createdAt;

    @Expose
    @SerializedName("updated_at")
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(String nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    public String getNextOfKinPhone() {
        return nextOfKinPhone;
    }

    public void setNextOfKinPhone(String nextOfKinPhone) {
        this.nextOfKinPhone = nextOfKinPhone;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
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

    public Passenger getPassenger(){
        return new Passenger(name, phone, sex, address, nextOfKin, String.valueOf(seat), nextOfKinPhone);
    }
}
