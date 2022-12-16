package com.example.karaktergenshinimpact.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.karaktergenshinimpact.R;
import com.example.karaktergenshinimpact.request.APIInterface;
import com.example.karaktergenshinimpact.request.APIService;
import com.example.karaktergenshinimpact.response.AccountResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText email, namaLengkap, username, password, confPassword;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email_reg);
        namaLengkap = findViewById(R.id.fullname_reg);
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
        if(isValidate()){
            APIInterface apiInterface = APIService.getRetrofitInstance().create(APIInterface.class);
            Call<AccountResponse> call = apiInterface.register(
                    email.getText().toString(),
                    username.getText().toString(),
                    namaLengkap.getText().toString(),
                    password.getText().toString(),
                    confPassword.getText().toString()
            );
            call.enqueue(new Callback<AccountResponse>() {
                @Override
                public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                    if(response.isSuccessful()){
                        if(response.code()==200){
                            Toast.makeText(RegisterActivity.this,"Register account success!!!",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(RegisterActivity.this,"Response code != 200!!!",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this,"Register unsuccessful!!!",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AccountResponse> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this,"ON FAILURE!!!",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean isValidate() {
        return true;
    }
}