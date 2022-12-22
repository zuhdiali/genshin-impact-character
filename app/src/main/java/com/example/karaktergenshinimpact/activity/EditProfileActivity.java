/*
 * *
 *  * Created by zuhdi on 12/22/22, 8:35 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 12/18/22, 5:27 PM
 *
 */

package com.example.karaktergenshinimpact.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class EditProfileActivity extends AppCompatActivity {
    private TextInputEditText email, namaLengkap, username, oldPassword, newPassword, confPassword;
    private Button editBtn;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE);

        email = findViewById(R.id.email_editprf);
        namaLengkap = findViewById(R.id.fullname_editprf);
        username = findViewById(R.id.username_editprf);
        oldPassword = findViewById(R.id.old_password_editprf);
        newPassword = findViewById(R.id.new_password_editprf);
        confPassword = findViewById(R.id.confpassword_editprf);
        editBtn = findViewById(R.id.edit_btn_editprf);

        email.setText(sharedPreferences.getString("EMAIL", ""));
        username.setText(sharedPreferences.getString("USERNAME", ""));
        namaLengkap.setText(sharedPreferences.getString("FULL_NAME", ""));
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAccountAction();
            }
        });
    }

    private void editAccountAction() {
        if (isValidate()) {
            Boolean notChangePassword = notChangePassword();
            APIInterface apiInterface = APIService.getRetrofitInstance().create(APIInterface.class);
            Call<AccountResponse> call = apiInterface.updateUser(
                    sharedPreferences.getString("ID_USER", ""),
                    "Bearer " + sharedPreferences.getString("TOKEN", ""),
                    email.getText().toString(),
                    namaLengkap.getText().toString(),
                    username.getText().toString(),
                    (notChangePassword ? sharedPreferences.getString("PASSWORD", "") : oldPassword.getText().toString()),
                    (notChangePassword ? sharedPreferences.getString("PASSWORD", "") : newPassword.getText().toString()),
                    (notChangePassword ? sharedPreferences.getString("PASSWORD", "") : confPassword.getText().toString())
            );
            call.enqueue(new Callback<AccountResponse>() {
                @Override
                public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                    if (response.isSuccessful()) {
//                        if (response.code() == 200) {
                            Toast.makeText(EditProfileActivity.this, "Edit account success!!!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(EditProfileActivity.this, "Back to home page to apply changes", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("FULL_NAME", namaLengkap.getText().toString());
                            editor.putString("USERNAME", username.getText().toString());
                            editor.putString("EMAIL", email.getText().toString());
                            editor.apply();
//                            Intent i = new Intent(EditProfileActivity.this, ProfileActivity.class);
//                            startActivity(i);
                            finish();
//                        } else {
//                            Toast.makeText(EditProfileActivity.this, "Response code != 200!!!", Toast.LENGTH_SHORT).show();
//                        }
                    } else {
                        errorMsg(response);
                    }
                }

                @Override
                public void onFailure(Call<AccountResponse> call, Throwable t) {
                    Toast.makeText(EditProfileActivity.this, "ON FAILURE!!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean isValidate() {
        return true;
    }

    private boolean notChangePassword(){
        if(oldPassword.getText().equals("") && newPassword.getText().equals("") && confPassword.getText().equals("")){
            return true;
        }
        else {
            return false;
        }
    }

    private void errorMsg(Response<AccountResponse> response){
        String showError = "";
        try {
            JSONObject jsonObjectError = new JSONObject(response.errorBody().string());
            JSONObject errorMessages = jsonObjectError.getJSONObject("messages");

            if (!errorMessages.isNull("email")) {
                showError += errorMessages.getString("email");
            }
            if (!errorMessages.isNull("password_lama")) {
                if(!showError.equals("")){
                    showError += "\n";
                }
                showError += errorMessages.getString("password_lama");
            }
            if (!errorMessages.isNull("nama_lengkap")) {
                if(!showError.equals("")){
                    showError += "\n";
                }
                showError += errorMessages.getString("nama_lengkap");
            }
            if (!errorMessages.isNull("username")) {
                if(!showError.equals("")){
                    showError += "\n";
                }
                showError += errorMessages.getString("username");
            }
            if (!errorMessages.isNull("password_baru")) {
                if(!showError.equals("")){
                    showError += "\n";
                }
                showError += errorMessages.getString("password_baru");
            }
            if (!errorMessages.isNull("confpassword_baru")) {
                if(!showError.equals("")){
                    showError += "\n";
                }
                showError += errorMessages.getString("confpassword_baru");
            }
            if (!errorMessages.isNull("error")) {
                if(!showError.equals("")){
                    showError += "\n";
                }
                showError +=errorMessages.getString("error");
            }
            Toast.makeText(getApplicationContext(), showError, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}