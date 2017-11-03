package i.am.eipeks.rims._classes._auth_class._json_response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import i.am.eipeks.rims._classes._auth_class._auth_pojo.AuthVehicle;

public class JSONResponseVehicle {

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("vehicle")
    @Expose
    private AuthVehicle authVehicle;

    public void setAuthVehicle(AuthVehicle authVehicle){
        this.authVehicle = authVehicle;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public AuthVehicle getAuthVehicle() {
        return authVehicle;
    }

    public Integer getStatus(){ return status; }
}
