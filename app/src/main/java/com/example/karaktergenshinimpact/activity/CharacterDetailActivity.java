package com.example.karaktergenshinimpact.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karaktergenshinimpact.R;
import com.example.karaktergenshinimpact.Utils.DownloadImageTask;
import com.example.karaktergenshinimpact.Utils.URL;
import com.example.karaktergenshinimpact.request.APIInterface;
import com.example.karaktergenshinimpact.request.APIService;
import com.example.karaktergenshinimpact.response.AddCharacterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailActivity extends AppCompatActivity {
    private TextView nama, asal, vision, weapon, rarity, deskripsi;
    private ImageView cardImg;
    private Button deleteBtn;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_character);

        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE);

        nama = findViewById(R.id.nama_character_detail);
        asal = findViewById(R.id.asal_character_detail);
        vision = findViewById(R.id.vision_character_detail);
        weapon = findViewById(R.id.senjata_character_detail);
        rarity = findViewById(R.id.rarity_character_detail);
        deskripsi = findViewById(R.id.description_character_detail);
        cardImg = findViewById(R.id.card_karakter);
        deleteBtn = (Button) findViewById(R.id.delete_char_detail);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        getSupportActionBar().setTitle(extras.getString("NAMA"));

        nama.setText(extras.getString("NAMA"));
        asal.setText(extras.getString("ASAL"));
        vision.setText(extras.getString("VISION"));
        weapon.setText(extras.getString("SENJATA"));
        rarity.setText(extras.getString("RARITY"));
        deskripsi.setText(extras.getString("DESKRIPSI"));

        new DownloadImageTask(cardImg).execute(URL.urlCardImg + extras.getString("CARD_IMG"));

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIInterface apiInterface = APIService.getRetrofitInstance().create(APIInterface.class);
                Call<AddCharacterResponse> call = apiInterface.deleteCharacter(
                        "Bearer " + sharedPreferences.getString("TOKEN", ""),
                        extras.getString("ID")
                );
                call.enqueue(new Callback<AddCharacterResponse>() {
                    @Override
                    public void onResponse(Call<AddCharacterResponse> call, Response<AddCharacterResponse> response) {
                        Toast.makeText(getApplicationContext(), "Character " + extras.getString("NAMA") + " deleted successfully!!!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<AddCharacterResponse> call, Throwable t) {

                    }
                });
            }
        });
    }
}