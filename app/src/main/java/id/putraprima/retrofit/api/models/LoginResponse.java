package id.putraprima.retrofit.api.models;

public class LoginResponse {

    public String email, password;
    public String token, token_type,expires_in;

    public LoginResponse(String token, String token_type, String expires_in) {
        this.token = token;
        this.token_type = token_type;
        this.expires_in = expires_in;
    }
    public boolean  error;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }



}
