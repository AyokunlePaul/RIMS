package i.am.eipeks.rims._classes._auth_class._json_response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JSONResponsePassenger {

    @Expose
    @SerializedName("status")
    private Integer status;

    @Expose
    @SerializedName("message")
    private String message;

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
}
