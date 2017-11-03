package i.am.eipeks.rims._classes._auth_class._json_response._register;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import i.am.eipeks.rims._classes._auth_class._auth_pojo.AuthTrip;
import i.am.eipeks.rims._classes._auth_class._auth_pojo._register.AuthTripRegister;

public class JSONResponseTripRegister {

    @Expose
    @SerializedName("status")
    private Integer status;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("trip")
    private AuthTripRegister trip;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AuthTripRegister getTrip() {
        return trip;
    }

    public void setTrip(AuthTripRegister trip) {
        this.trip = trip;
    }
}
