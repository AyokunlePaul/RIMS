package i.am.eipeks.rims._network;



import i.am.eipeks.rims._classes._network.JSONPassenger;
import i.am.eipeks.rims._classes._network.JSONResponseUser;
import i.am.eipeks.rims._classes._network.JSONResponseVehicle;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Auth {

    @GET("get-vehicle/{vehicleId}")
    Call<JSONResponseVehicle> getVehicle(@Path("vehicleId") String vehicleId, @Header("Authorization") String authorization);

    @Multipart
    @POST("passenger")
    Call<Void> sendPassenger(@Part("passenger_name") String passengerName, @Part("passenger_phone") String passengerPhone,
                             @Part("uuid") String uuid, @Part("passenger_sex") String passengerSex,
                             @Part("passenger_address") String passengerAddress, @Part("passenger_next_of_kin") String passengerNextOfKin,
                             @Part("passenger_seat") String passengerSeat, @Part("passenger_next_of_kin_phone") String nextOfKinPhone,
                             @Header("Authorization") String authorization);

    @POST("auth/login")
    Call<JSONResponseUser> authenticateUser(@Query("user_id") String username, @Query("password") String password);

}
