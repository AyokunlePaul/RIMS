package i.am.eipeks.rims._classes._model_class;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Passenger {

    @SerializedName("passenger_name")
    @Expose
    private String passengerName;

    @SerializedName("uuid")
    @Expose
    private String uuid;

    @SerializedName("passenger_phone")
    @Expose
    private String passengerPhone;

    @SerializedName("passenger_sex")
    @Expose
    private String passengerSex;

    @SerializedName("passenger_address")
    @Expose
    private String passengerAddress;

    @SerializedName("passenger_next_of_kin")
    @Expose
    private String nextOfKin;

    @SerializedName("passenger_seat")
    @Expose
    private String seatNumber;

    @SerializedName("passenger_next_of_kin_phone")
    @Expose
    private String nextOfKinPhone;

    public Passenger(String passengerName, String passengerPhone, String passengerSex,
                     String passengerAddress, String nextOfKin, String seatNumber, String nextOfKinPhone) {
        this.passengerName = passengerName;
        this.passengerPhone = passengerPhone;
        this.passengerSex = passengerSex;
        this.passengerAddress = passengerAddress;
        this.nextOfKin = nextOfKin;
        this.seatNumber = seatNumber;
        this.nextOfKinPhone = nextOfKinPhone;
    }

    public String getPassengerName() {
        return passengerName.trim();
    }

    public String getPassengerPhone() {
        return passengerPhone.trim();
    }

    public String getPassengerSex() {
        return passengerSex.trim();
    }

    public String getPassengerAddress() {
        return passengerAddress.trim();
    }

    public String getNextOfKin() {
        return nextOfKin.trim();
    }

    public String getSeatNumber() {
        return seatNumber.trim();
    }

    public String getNextOfKinPhone() {
        return nextOfKinPhone.trim();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
