package id.putraprima.retrofit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.LoginRequest;
import id.putraprima.retrofit.api.models.LoginResponse;
import id.putraprima.retrofit.api.models.MeRequest;
import id.putraprima.retrofit.api.models.MeResponse;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    LoginRequest loginRequest;
    MeRequest meRequest;
    private EditText editMail;
    private EditText editPasswd;
    private TextView Appnamemain;
    private TextView Appversionmain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editMail = findViewById(R.id.edtEmail);
        editPasswd = findViewById(R.id.edtPassword);
        Appnamemain = findViewById(R.id.mainTxtAppName);
        Appversionmain = findViewById(R.id.mainTxtAppVersion);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null ){
            Appnamemain.setText(bundle.getString("APP_NAME"));
            Appversionmain.setText(bundle.getString("APP_VERSION"));
        }
    }

    public void login() {
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<LoginResponse> call = service.dologin(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.body().getToken(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, response.body().getToken_type(), Toast.LENGTH_SHORT).show();
                    String expires_in = Integer.toString(response.body().getExpires_in());
                    Toast.makeText(MainActivity.this, expires_in, Toast.LENGTH_SHORT).show();

                    meRequest = new MeRequest(response.body().getToken());
                    me();
                }else{
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void me(){
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<MeResponse> call = service.doMe(meRequest);
        call.enqueue(new Callback<MeResponse>() {
            @Override
            public void onResponse(Call<MeResponse> call, Response<MeResponse> response) {
                if (response != null){
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<MeResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal terkoneksi dengan Server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void handlerLogin(View view){
        String user = editMail.getText().toString();
        String pass = editPasswd.getText().toString();
        loginRequest = new LoginRequest(user, pass);
        boolean check1, check2;
        if (user.equals("")){
            Toast.makeText(this, "Masukkan email Anda", Toast.LENGTH_SHORT).show();
            check1 = false;
        } else {
            check1 = true;
        }
        if (pass.equals("")){
            Toast.makeText(this, "Masukkan password Anda", Toast.LENGTH_SHORT).show();
            check2 = false;
        }
        else {
            check2 = true;
        }
        if (check1 == true && check2 == true){
            login();
        }
    }

    public void handlerRegister(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
