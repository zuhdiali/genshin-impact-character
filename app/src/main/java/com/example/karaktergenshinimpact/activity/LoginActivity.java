package com.example.karaktergenshinimpact.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.karaktergenshinimpact.R;
import com.example.karaktergenshinimpact.response.LoginResponse;
import com.example.karaktergenshinimpact.request.APIInterface;
import com.example.karaktergenshinimpact.request.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button loginButton, registerButton;
    private static final String TAG = "LoginActivity";
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editor = getSharedPreferences("USER_LOGIN", MODE_PRIVATE).edit();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginClick();
            }
        });
        registerButton = findViewById(R.id.register_btn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    public void loginClick() {
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();
        Log.e(TAG, "email: " + emailString);
        Log.e(TAG, "password: " + passwordString);
//        Log.e(TAG,"is network permitted: "+is);
        APIInterface apiInterface = APIService.getRetrofitInstance().create(APIInterface.class);
        Call<LoginResponse> call = apiInterface.getUserInformation(emailString, passwordString);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.e(TAG, "onResponse: " + response.code());
                        Log.e(TAG, "onResponse:  id = " + response.body().getIdUser());
                        Log.e(TAG, "onResponse:  token = " + response.body().getToken());
                        Log.e(TAG, "onResponse:  nama lengkap = " + response.body().getNamaLengkap());
                        editor.putString("TOKEN", response.body().getToken());
                        editor.putString("FULL_NAME", response.body().getNamaLengkap());
                        editor.putString("ROLE",response.body().getRole());
                        editor.putString("USERNAME", response.body().getUsername());
                        editor.putString("EMAIL", response.body().getEmail());
                        editor.putString("ID_USER", response.body().getIdUser());
                        editor.putString("PASSWORD", passwordString);
                        editor.apply();
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Log.e(TAG, "onResponse: " + response.code());
//                        Log.e(TAG, "onResponse:  pesanError = " + response.body().getPesanError().getError());
                        Toast.makeText(LoginActivity.this, "Error Code", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Log.e(TAG, "onResponse: " + response.code());
//                Log.e(TAG, "onResponse:  pesanError = " + response.body().getPesanError().getError());
                    Toast.makeText(LoginActivity.this, "Login unsuccessful!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(TAG, "masuk sini juga");
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}