package i.am.eipeks.rims._network;


import i.am.eipeks.rims._classes._network.AuthVehicle;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Auth {

    @GET("/get-vehicle/{vehicleId}?_token = our_token")
    Call<AuthVehicle> getVehicle(@Path("vehicleId") String vehicleId);
}
