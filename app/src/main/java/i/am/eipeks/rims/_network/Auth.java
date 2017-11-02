package i.am.eipeks.rims._network;


import i.am.eipeks.rims._classes._auth_class.AuthPassenger;
import i.am.eipeks.rims._classes._auth_class.JSONResponseTrip;
import i.am.eipeks.rims._classes._auth_class.JSONResponseUser;
import i.am.eipeks.rims._classes._auth_class.JSONResponseVehicle;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Auth {

    @Multipart
    @POST("passenger")
    Call<Void> sendPassenger(@Part("name") String passengerName, @Part("phone") String passengerPhone,
                             @Part("sex") String passengerSex, @Part("address") String passengerAddress,
                             @Part("next_of_kin") String passengerNextOfKin, @Part("seat") Integer passengerSeat,
                             @Part("passenger_next_of_kin_phone") String nextOfKinPhone, @Part("trip_id") String tripId,
                             @Header("Authorization") String authorization);

    @GET("passenger/{uuid}")
    Call<AuthPassenger> getPassenger(@Path("uuid") String uuid);

    @DELETE("passenger/{uuid}")
    Call<Void> deletePassenger(@Path("uuid") String uuid);

    @GET("vehicle/{vehicleId}")
    Call<JSONResponseVehicle> getVehicle(@Path("vehicleId") String vehicleId, @Header("Authorization") String authorization);

    @DELETE("vehicle/{registration_number}")
    Call<Void> deleteVehicle(@Path("registration_number") String registrationNumber);

    @Multipart
    @POST("vehicle")
    Call<Void> addVehicle(@Part("vehicle_name") String vehicleName, @Part("vehicle_make") String vehicleMake,
                          @Part("vehicle_capacity") String vehicleCapacity, @Part("vehicle_weight") String vehicleWeight,
                          @Part("vehicle_engine") String vehicleEngine, @Part("vehicle_rt_sss") String vehicleRtSSS,
                          @Part("vehicle_rt_sss") String registrationNumber, @Header("Authorization") String authorization);

    @Multipart
    @POST("trip")
    Call<JSONResponseTrip> addTrip(@Part("date_and_time") String dateAndTime, @Part("displacement") String displacement,
                                   @Part("departure_information") String departureInformation, @Part("calendar_date") String date,
                                   @Part("calendar_month") String month, @Part("calendar_year") String year,
                                   @Part("uuid") String uuid, @Part("total_number_of_passengers") String passengers,
                                   @Header("Authorization") String header);

    @DELETE("trip/{uuid}")
    Call<Void> deleteTrip(@Path("uuid") String uuid, @Header("Authorization") String authorization);

    @GET("trip/{uuid}")
    Call<Void> getTrip(@Path("uuid") String uuid, @Header("Authorization") String authorization);

    @Multipart
    @POST("driver")
    Call<Void> sendDriver(@Part("driver_name") String driverName, @Part("driver_phone") String driverPhone,
                        @Path("uuid") String uuid, @Header("Authorization") String authorization);

    @GET("get-driver/{uuid}")
    Call<Void> getDriver(@Path("uuid") String uuid, @Header("Authorization") String authorization);

    @DELETE("driver/{uuid}")
    Call<Void> deleteDriver(@Path("uuid") String uuid, @Header("Authorization") String authorization);

    @POST("auth/login")
    Call<JSONResponseUser> authenticateUser(@Query("user_id") String username, @Query("password") String password);
}
