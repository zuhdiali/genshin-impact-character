/*
 * *
 *  * Created by zuhdi on 12/22/22, 8:35 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 12/20/22, 4:57 PM
 *
 */

package com.example.karaktergenshinimpact.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.karaktergenshinimpact.R;

public class ProfileActivity extends AppCompatActivity {
    private TextView email, username, fullName;
    private Button editProfile;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE);

        email = findViewById(R.id.email_profile);
        username = findViewById(R.id.username_profile);
        fullName = findViewById(R.id.fullname_profile);
        editProfile = findViewById(R.id.edit_btn_profile);

        email.setText(sharedPreferences.getString("EMAIL",""));
        username.setText(sharedPreferences.getString("USERNAME",""));
        fullName.setText(sharedPreferences.getString("FULL_NAME",""));

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}