package id.putraprima.retrofit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.RegisterRequest;
import id.putraprima.retrofit.api.models.RegisterResponse;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    RegisterRequest registerRequest;
    private EditText editMail;
    private EditText editPasswd;
    private EditText editUsername;
    private EditText editPasswdConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editMail = findViewById(R.id.edit_email);
        editPasswd = findViewById(R.id.edit_password);
        editUsername = findViewById(R.id.edit_username);
        editPasswdConfirm = findViewById(R.id.edit_passwordconfirm);

    }


    public void register(){
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<RegisterResponse> call = service.doRegister(registerRequest);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    Toast.makeText(RegisterActivity.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Gagal terhubung dengan server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void handlerRegister(View view) {
        String name = editUsername.getText().toString();
        String email = editMail.getText().toString();
        String password = editPasswd.getText().toString();
        String passwordConfirm = editPasswdConfirm.getText().toString();


        if (name.isEmpty()) {
            Toast.makeText(this, "Name is Empty!", Toast.LENGTH_SHORT).show();
            return;
        } else if(email.isEmpty()) {
            Toast.makeText(this, "Email is Empty!", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Password is Empty!", Toast.LENGTH_SHORT).show();
            return;
        } else if (passwordConfirm.isEmpty()) {
            Toast.makeText(this, "Password Confirmation is Empty!", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.length() < 8) {
            Toast.makeText(this, "Password limit 8", Toast.LENGTH_SHORT).show();
            return;
        } else if (!password.equals(passwordConfirm)) {
            Toast.makeText(this, "Confirm Password not Same!", Toast.LENGTH_SHORT).show();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Email Not Valid", Toast.LENGTH_SHORT).show();
            return;
        }
        registerRequest = new RegisterRequest(name, email, password, passwordConfirm);
            register();
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
    }
}
