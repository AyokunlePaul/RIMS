package i.am.eipeks.rims._classes._network;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class JSONResponseVehicle {
    @SerializedName("vehicle")
    @Expose
    private AuthVehicle authVehicle;

    @SerializedName("status")
    @Expose
    private Integer status;

    public AuthVehicle getAuthVehicle() {
        return authVehicle;
    }

    public Integer getStatus(){ return status; }
}
