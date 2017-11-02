package i.am.eipeks.rims._classes._auth_class;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JSONResponseTrip {

    @Expose
    @SerializedName("status")
    private Integer status;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("trip")
    private AuthTrip trip;

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

    public AuthTrip getTrip() {
        return trip;
    }

    public void setTrip(AuthTrip trip) {
        this.trip = trip;
    }
}
