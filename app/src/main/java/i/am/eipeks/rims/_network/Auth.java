package i.am.eipeks.rims._network;


import java.util.List;

import i.am.eipeks.rims._classes.Passenger;
import i.am.eipeks.rims._classes.Trip;
import i.am.eipeks.rims._classes.Vehicle;
import i.am.eipeks.rims._classes._network.JSONResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Auth {

    @GET("get-vehicle/{vehicleId}?_token=our_token")
    Call<JSONResponse> getVehicle(@Path("vehicleId") String vehicleId);

    @POST("post/{uuid}")
    Call<Vehicle> postTripInformation(@Path("uuid") String uuid, @Body Vehicle vehicle, @Body List<Passenger> passengers, @Body Trip trip);

    @POST("auth/login")
    @FormUrlEncoded
    Call<Void> authenticateUser(@Field("user_id") String username, @Field("password") String password);

}
