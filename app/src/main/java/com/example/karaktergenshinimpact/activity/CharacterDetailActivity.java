package com.example.karaktergenshinimpact.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karaktergenshinimpact.R;
import com.example.karaktergenshinimpact.Utils.DownloadImageTask;
import com.example.karaktergenshinimpact.Utils.AppURL;
import com.example.karaktergenshinimpact.request.APIInterface;
import com.example.karaktergenshinimpact.request.APIService;
import com.example.karaktergenshinimpact.response.AddCharacterResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailActivity extends AppCompatActivity {
    private TextView nama, asal, vision, weapon, rarity;
    private TextInputEditText deskripsi;
    private ImageView cardImg, avatarImg;
    private MaterialButton deleteBtn, seeCard, editBtn;
    private SharedPreferences sharedPreferences;
    private Dialog dialog;

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
        avatarImg = findViewById(R.id.avatar_karakter);
        deleteBtn = findViewById(R.id.delete_char_detail);
        editBtn = findViewById(R.id.edit_char_detail);
        seeCard = findViewById(R.id.download_card);
        dialog = new Dialog(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        new DownloadImageTask(avatarImg).execute(AppURL.urlAvatarImg + extras.getString("AVATAR_IMG"));
//        new DownloadImageTask(cardImg).execute(AppURL.urlCardImg+ extras.getString("CARD_IMG"));
        getSupportActionBar().setTitle(extras.getString("NAMA"));

        nama.setText(extras.getString("NAMA"));
        asal.setText(extras.getString("ASAL"));
        vision.setText(extras.getString("VISION"));
        weapon.setText(extras.getString("SENJATA"));
        rarity.setText(extras.getString("RARITY"));
        deskripsi.setText(extras.getString("DESKRIPSI"));

        seeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FloatingActionButton closePopUp;
                ImageView cardCharacter;
                dialog.setContentView(R.layout.popup_card_character_card);
                cardCharacter = dialog.findViewById(R.id.popup_card);
                closePopUp = dialog.findViewById(R.id.close_popup);
                closePopUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                new DownloadImageTask(cardCharacter).execute(AppURL.urlCardImg + extras.getString("CARD_IMG"));
                dialog.show();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CharacterDetailActivity.this, EditCharacterActivity.class);
                intent1.putExtras(extras);
                startActivity(intent1);
                finish();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder confirmDelete = new AlertDialog.Builder(CharacterDetailActivity.this);
                confirmDelete.setTitle("Delete Character");
                confirmDelete.setMessage("Are you sure you want to delete " + extras.getString("NAMA") + "?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
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
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
            }
        });
        if(sharedPreferences.getString("ROLE","").equals("admin")){
            editBtn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.VISIBLE);
        }
        else{
            editBtn.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.GONE);
        }
    }
}