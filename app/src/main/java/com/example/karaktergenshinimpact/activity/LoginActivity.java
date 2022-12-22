/*
 * *
 *  * Created by zuhdi on 12/22/22, 8:35 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 12/21/22, 11:23 PM
 *
 */

package com.example.karaktergenshinimpact.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText email, password;
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

        if(isValidate()){
            APIInterface apiInterface = APIService.getRetrofitInstance().create(APIInterface.class);
            Call<LoginResponse> call = apiInterface.getUserInformation(emailString, passwordString);
            call.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            editor.putString("TOKEN", response.body().getToken());
                            editor.putString("FULL_NAME", response.body().getNamaLengkap());
                            editor.putString("ROLE", response.body().getRole());
                            editor.putString("USERNAME", response.body().getUsername());
                            editor.putString("EMAIL", response.body().getEmail());
                            editor.putString("ID_USER", response.body().getIdUser());
                            editor.apply();
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Log.e(TAG, "onResponse: " + response.code());
                            Toast.makeText(LoginActivity.this, "Error Code", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e(TAG, "onResponse: " + response.code());

                        try {
                            JSONObject jsonObjectError = new JSONObject(response.errorBody().string());
                            JSONObject errorMessages = jsonObjectError.getJSONObject("messages");
                            String showError = "";
                            if (!errorMessages.isNull("email")) {
                                showError += errorMessages.getString("email");
                            }
                            if (!errorMessages.isNull("password")) {
                                if(!showError.equals("")){
                                    showError += "\n";
                                }
                                showError += errorMessages.getString("password");
                            }
                            if (!errorMessages.isNull("error")) {
                                if(!showError.equals("")){
                                    showError += "\n";
                                }
                                showError +=errorMessages.getString("error");
                            }
//                            Toast.makeText(getApplicationContext(), showError, Toast.LENGTH_LONG).show(); // kodingan ini jika ingin menampilkan kesalahan dari email atau password
                            Toast.makeText(getApplicationContext(), "Email or Password is incorrect", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.e(TAG, "masuk sini juga");
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    Toast.makeText(LoginActivity.this, "No Internet Connection / Server Error", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private boolean isValidate() {
        Boolean isValid = true;
        if (email.getText().toString().equals("")){
            email.setError("Email is required");
            isValid = false;
        }
        if(password.length()<6){
            password.setError("Password must be at least 6 characters long");
            isValid = false;
        }
        return isValid;
    }

}