package id.putraprima.retrofit.api.models;

public class LoginRequest {
    public String UserId, password;

    public LoginRequest(String userId, String password) {
        UserId = userId;
        this.password = password;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
