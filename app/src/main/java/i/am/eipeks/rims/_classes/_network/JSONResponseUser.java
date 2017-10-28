package i.am.eipeks.rims._classes._network;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JSONResponseUser {
    @SerializedName("user")
    @Expose
    private AuthUser user;
    @SerializedName("token")
    @Expose
    private String token;

    public AuthUser getUser() {
        return user;
    }

    public void setUser(AuthUser user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
