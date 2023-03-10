/*
 * *
 *  * Created by zuhdi on 12/22/22, 8:35 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 12/22/22, 8:34 AM
 *
 */

package com.example.karaktergenshinimpact.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.karaktergenshinimpact.R;
import com.example.karaktergenshinimpact.request.APIInterface;
import com.example.karaktergenshinimpact.request.APIService;
import com.example.karaktergenshinimpact.response.AccountResponse;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText email, fullName, username, password, confPassword;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email_reg);
        fullName = findViewById(R.id.fullname_reg);
        username = findViewById(R.id.username_reg);
        password = findViewById(R.id.password_reg);
        confPassword = findViewById(R.id.confpassword_reg);
        registerButton = findViewById(R.id.register_btn_reg);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerAction();
            }
        });
    }

    private void registerAction() {
        if (isValidate()) {
            APIInterface apiInterface = APIService.getRetrofitInstance().create(APIInterface.class);
            Call<AccountResponse> call = apiInterface.register(
                    email.getText().toString(),
                    username.getText().toString(),
                    fullName.getText().toString(),
                    password.getText().toString(),
                    confPassword.getText().toString()
            );
            call.enqueue(new Callback<AccountResponse>() {
                @Override
                public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Register account success!!!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        errorMsg(response);
                    }
                }

                @Override
                public void onFailure(Call<AccountResponse> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "ON FAILURE!!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean isValidate() {
        Boolean isValid = true;
        if (email.getText().toString().equals("")) {
            email.setError("Email is empty");
            isValid = false;
        }
        if (username.getText().toString().equals("")) {
            username.setError("Username is empty");
            isValid = false;
        }
        if (fullName.getText().toString().equals("")) {
            fullName.setError("Full name is empty");
            isValid = false;
        }
        if (password.length() < 6) {
            password.setError("Password must be at least 6 characters long");
            isValid = false;
        }
        if (confPassword.length() < 6) {
            confPassword.setError("Confirm password must be at least 6 characters long");
            isValid = false;
        }
        if (!password.getText().toString().equals(confPassword.getText().toString())) {
            confPassword.setError("Confirm password does not match with Password");
            isValid = false;
        }
        return isValid;
    }

    private void errorMsg(Response<AccountResponse> response) {
        String showError = "";
        try {
            JSONObject jsonObjectError = new JSONObject(response.errorBody().string());
            JSONObject errorMessages = jsonObjectError.getJSONObject("messages");

            if (!errorMessages.isNull("email")) {
                showError += errorMessages.getString("email");
            }
            if (!errorMessages.isNull("nama_lengkap")) {
                if (!showError.equals("")) {
                    showError += "\n";
                }
                showError += "Full Name Field is required";
            }
            if (!errorMessages.isNull("username")) {
                if (!showError.equals("")) {
                    showError += "\n";
                }
                showError += errorMessages.getString("username");
            }
            if (!errorMessages.isNull("password")) {
                if (!showError.equals("")) {
                    showError += "\n";
                }
                showError += errorMessages.getString("password");
            }
            if (!errorMessages.isNull("confpassword")) {
                if (!showError.equals("")) {
                    showError += "\n";
                }
                showError += errorMessages.getString("confpassword");
            }
            if (!errorMessages.isNull("error")) {
                if (!showError.equals("")) {
                    showError += "\n";
                }
                showError += errorMessages.getString("error");
            }
            Toast.makeText(getApplicationContext(), showError, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}