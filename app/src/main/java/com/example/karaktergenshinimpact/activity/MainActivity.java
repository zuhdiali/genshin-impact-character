package com.example.karaktergenshinimpact.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karaktergenshinimpact.R;
import com.example.karaktergenshinimpact.adapter.CharacterAdapter;
import com.example.karaktergenshinimpact.response.CharacterResponse;
import com.example.karaktergenshinimpact.request.APIInterface;
import com.example.karaktergenshinimpact.request.APIService;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ExtendedFloatingActionButton tambahKarakter;
    private CharacterAdapter characterAdapter;
    private List<CharacterResponse> listKarakter;
    private TextView userInfoLogin;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_beranda);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        tambahKarakter = findViewById(R.id.add_char_btn);
        userInfoLogin = findViewById(R.id.user_login);

        tambahKarakter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddCharacterActivity.class);
                startActivity(i);
            }
        });

        sharedPreferences = getSharedPreferences("USER_LOGIN",MODE_PRIVATE);
        if(!sharedPreferences.contains("TOKEN")){
            Log.e(TAG,"masuk sini boy");
            Intent i = new Intent(this,LoginActivity.class);
            startActivity(i);
            finish();
        }else{
            userInfoLogin.setText("Welcome, "+sharedPreferences.getString("FULL_NAME",""));
            refreshCharacters();
        }

    }

    private void refreshCharacters(){
        listKarakter = new ArrayList<>();
        APIInterface apiInterface = APIService.getRetrofitInstance().create(APIInterface.class);
        Call<List<CharacterResponse>> call = apiInterface.getAllKarakter(sharedPreferences.getString("TOKEN",""));
        call.enqueue(new Callback<List<CharacterResponse>>() {
            @Override
            public void onResponse(Call<List<CharacterResponse>> call, Response<List<CharacterResponse>> response) {
                for(CharacterResponse characterResponse : response.body()){
                    listKarakter.add(characterResponse);
                    Log.e(TAG,"nama karakter: "+ characterResponse.getNama());
                }
                characterAdapter = new CharacterAdapter(MainActivity.this,listKarakter);
                recyclerView.setAdapter(characterAdapter);
            }

            @Override
            public void onFailure(Call<List<CharacterResponse>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh:
                refreshCharacters();
                return true;
            case R.id.profil:
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                sharedPreferences.edit().clear().commit();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                return true;
            case R.id.setting:
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}