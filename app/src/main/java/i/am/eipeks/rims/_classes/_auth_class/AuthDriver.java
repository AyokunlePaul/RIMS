package i.am.eipeks.rims._classes._auth_class;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthDriver {

    @Expose
    @SerializedName("id")
    private Integer id;

    @Expose
    @SerializedName("trip_id")
    private Integer tripId;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("phone")
    private String phone;

    @Expose
    @SerializedName("created_at")
    private String createdAt;

    @Expose
    @SerializedName("updated_at")
    private String updatedAt;

}
