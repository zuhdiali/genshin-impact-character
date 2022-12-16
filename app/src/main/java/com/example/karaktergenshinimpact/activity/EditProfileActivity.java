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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    private EditText email, namaLengkap, username, oldPassword, newPassword, confPassword;
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
                        if (response.code() == 200) {
                            Toast.makeText(EditProfileActivity.this, "Edit account success!!!", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("FULL_NAME", namaLengkap.getText().toString());
                            editor.putString("USERNAME", username.getText().toString());
                            editor.putString("EMAIL", email.getText().toString());
//                            Intent i = new Intent(EditProfileActivity.this, ProfileActivity.class);
//                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(EditProfileActivity.this, "Response code != 200!!!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(EditProfileActivity.this, "Edit unsuccessful!!!", Toast.LENGTH_SHORT).show();
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

}