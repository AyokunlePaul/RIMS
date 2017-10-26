package i.am.eipeks.rims._network;


import java.util.List;

import i.am.eipeks.rims._classes.Passenger;
import i.am.eipeks.rims._classes.Trip;
import i.am.eipeks.rims._classes.Vehicle;
import i.am.eipeks.rims._classes._network.JSONResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Auth {

    @GET("get-vehicle/{vehicleId}?_token=our_token")
    Call<JSONResponse> getVehicle(@Path("vehicleId") String vehicleId);

    @POST("/post/{uuid}")
    Call<Vehicle> postTripInformation(@Path("uuid") String uuid, @Body Vehicle vehicle, @Body List<Passenger> passengers, @Body Trip trip);
}
