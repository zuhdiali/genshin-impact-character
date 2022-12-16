package com.example.karaktergenshinimpact.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.karaktergenshinimpact.R;
import com.example.karaktergenshinimpact.Utils.DownloadImageTask;
import com.example.karaktergenshinimpact.Utils.URL;

public class CharacterDetailActivity extends AppCompatActivity {
    private TextView nama, asal, vision, weapon, rarity, deskripsi;
    private ImageView cardImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_character);

        nama = findViewById(R.id.nama_character_detail);
        asal = findViewById(R.id.asal_character_detail);
        vision = findViewById(R.id.vision_character_detail);
        weapon = findViewById(R.id.senjata_character_detail);
        rarity = findViewById(R.id.rarity_character_detail);
        deskripsi = findViewById(R.id.description_character_detail);
        cardImg = findViewById(R.id.card_karakter);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        nama.setText(extras.getString("NAMA"));
        asal.setText(extras.getString("ASAL"));
        vision.setText(extras.getString("VISION"));
        weapon.setText(extras.getString("SENJATA"));
        rarity.setText(extras.getString("RARITY"));
        deskripsi.setText(extras.getString("DESKRIPSI"));

        new DownloadImageTask(cardImg).execute(URL.urlCardImg + extras.getString("CARD_IMG"));
    }
}