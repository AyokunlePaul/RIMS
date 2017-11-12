package i.am.eipeks.rims._network;


import i.am.eipeks.rims._classes._auth_class._json_response.JSONResponsePassenger;
import i.am.eipeks.rims._classes._auth_class._json_response.JSONResponseTrip;
import i.am.eipeks.rims._classes._auth_class._json_response.JSONResponseUser;
import i.am.eipeks.rims._classes._auth_class._json_response.JSONResponseVehicle;
import i.am.eipeks.rims._classes._auth_class._json_response._register.JSONResponseTripRegister;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Auth {

    @Multipart
    @POST("passenger")
    Call<JSONResponsePassenger> sendPassenger(@Part("name") String passengerName, @Part("phone") String passengerPhone,
                             @Part("sex") String passengerSex, @Part("address") String passengerAddress,
                             @Part("next_of_kin") String passengerNextOfKin, @Part("seat") Integer passengerSeat,
                             @Part("next_of_kin_phone") String nextOfKinPhone, @Part("trip_id") Integer tripId,
                             @Header("Authorization") String authorization);

    @GET("passenger/{uuid}")
    Call<Void> getPassenger(@Path("uuid") String uuid);

    @DELETE("passenger/{uuid}")
    Call<Void> deletePassenger(@Path("uuid") String uuid);

    @GET("vehicle/{vehicleId}")
    Call<JSONResponseVehicle> getVehicle(@Path("vehicleId") Integer vehicleId, @Header("Authorization") String authorization);

    @DELETE("vehicle/{registration_number}")
    Call<Void> deleteVehicle(@Path("registration_number") String registrationNumber);

    @Multipart
    @POST("vehicle")
    Call<Void> addVehicle(@Part("name") String vehicleName, @Part("make") String vehicleMake,
                          @Part("capacity") String vehicleCapacity, @Part("weight") String vehicleWeight,
                          @Part("engine") String vehicleEngine, @Part("rtsss") String vehicleRtSSS,
                          @Part("reg_number") String registrationNumber, @Header("Authorization") String authorization);

    @POST("trip")
    @FormUrlEncoded
    Call<JSONResponseTripRegister> addTrip(@Header("Authorization") String header,
                                           @Field("vehicle_id") Integer id, @Field("displacement") String displacement,
                                           @Field("departure_time") String departureTime);

    @DELETE("trip/{trip_id}")
    Call<Void> deleteTrip(@Path("trip_id") Integer tripId, @Header("Authorization") String authorization);

    @GET("trip/{trip_id}")
    Call<JSONResponseTrip> getTrip(@Path("trip") Integer tripId, @Header("Authorization") String authorization);

    @Multipart
    @POST("driver")
    Call<Void> sendDriver(@Part("name") String driverName, @Part("phone") String driverPhone,
                          @Header("Authorization") String authorization, @Part("trip_id") Integer id);

    @GET("get-driver/{uuid}")
    Call<Void> getDriver(@Path("uuid") String uuid, @Header("Authorization") String authorization);

    @DELETE("driver/{uuid}")
    Call<Void> deleteDriver(@Path("uuid") String uuid, @Header("Authorization") String authorization);

    @POST("auth/login")
    Call<JSONResponseUser> authenticateUser(@Query("user_id") String username, @Query("password") String password);
}
