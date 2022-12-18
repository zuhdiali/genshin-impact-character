package com.example.karaktergenshinimpact.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.karaktergenshinimpact.R;
import com.example.karaktergenshinimpact.Utils.RealPathUtil;
import com.example.karaktergenshinimpact.model.CharacterModel;
import com.example.karaktergenshinimpact.request.APIInterface;
import com.example.karaktergenshinimpact.request.APIService;
import com.example.karaktergenshinimpact.response.AddCharacterResponse;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCharacterActivity extends AppCompatActivity {
    private AutoCompleteTextView asal, vision, weapon, rarity;
    private TextInputEditText nama, deskripsi;
    private ImageView cardImg, avatarImg;
    private Button selectCardImg, selectAvatarImg, addCharacterButton;
    private String pathCardImg, pathAvatarImg, asalString, visionString, weaponString, rarityString;
    private SharedPreferences sharedPreferences;
    private ArrayAdapter<String> adapterItems;
    private static final String TAG = "AddCharacterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_character);

        nama = findViewById(R.id.nama_character_add);
        asal = findViewById(R.id.asal_character_add);
        vision = findViewById(R.id.vision_character_add);
        weapon = findViewById(R.id.senjata_character_add);
        rarity = findViewById(R.id.rarity_character_add);
        deskripsi = findViewById(R.id.description_character_add);
        cardImg = (ImageView) findViewById(R.id.card_img_add);
        avatarImg = (ImageView) findViewById(R.id.avatar_img_add);
        selectCardImg = (Button) findViewById(R.id.card_img_button);
        selectAvatarImg = (Button) findViewById(R.id.avatar_img_button);
        addCharacterButton = (Button) findViewById(R.id.add_char_btn_add);

        getSupportActionBar().setTitle("Add Character");
        initializeDropdownMenus();

        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE);

        selectCardImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 10);
                } else {
                    ActivityCompat.requestPermissions(AddCharacterActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }
        });

        selectAvatarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 11);
                } else {
                    ActivityCompat.requestPermissions(AddCharacterActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }
        });

        addCharacterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (isValid()) {
                        addCharacter(new CharacterModel(
                                nama.getText().toString(),
                                asalString,
                                visionString,
                                weaponString,
                                rarityString,
                                deskripsi.getText().toString(),
                                pathCardImg,
                                pathAvatarImg
                        ));
                    } else {
                        Toast.makeText(AddCharacterActivity.this, "There is an empty field in the form", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Origin isnull: " + (asalString == null ? "true" : "false"));
                    Toast.makeText(AddCharacterActivity.this, "Please select image correctly", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean isValid() {
        if (nama.getText().toString().equals("")) {
            return false;
        }
        if (asalString == null) {
            return false;
        }
        if (visionString == null) {
            return false;
        }
        if (weaponString == null) {
            return false;
        }
        if (rarityString == null) {
            return false;
        }
        if (deskripsi.getText().toString().equals("")) {
            return false;
        }
        return true;
    }

    private void initializeDropdownMenus() {
        String[] items = {"Mondstadt", "Liyue", "Inazuma", "Sumeru", "Fontaine", "Natlan", "Snezhnaya"};
        adapterItems = new ArrayAdapter<String>(this, R.layout.item_list, items);
        asal.setAdapter(adapterItems);
        asal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                asalString = adapterView.getItemAtPosition(i).toString();
                Log.e(TAG, "Origin:  " + adapterView.getItemAtPosition(i).toString());
            }
        });

        items = new String[]{"Anemo", "Geo", "Electro", "Dendro", "Hydro", "Pyro", "Cryo"};
        adapterItems = new ArrayAdapter<String>(this, R.layout.item_list, items);
        vision.setAdapter(adapterItems);
        vision.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                visionString = adapterView.getItemAtPosition(i).toString();
                Log.e(TAG, "Vision:  " + adapterView.getItemAtPosition(i).toString());
            }
        });

        items = new String[]{"Sword", "Bow", "Catalyst", "Claymore", "Polearm"};
        adapterItems = new ArrayAdapter<String>(this, R.layout.item_list, items);
        weapon.setAdapter(adapterItems);
        weapon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                weaponString = adapterView.getItemAtPosition(i).toString();
                Log.e(TAG, "Weapon:  " + adapterView.getItemAtPosition(i).toString());
            }
        });

        items = new String[]{"5 stars", "4 stars"};
        adapterItems = new ArrayAdapter<String>(this, R.layout.item_list, items);
        rarity.setAdapter(adapterItems);
        rarity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                rarityString = adapterView.getItemAtPosition(i).toString();
                Log.e(TAG, "Rarity:  " + adapterView.getItemAtPosition(i).toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Context context = AddCharacterActivity.this;
            pathCardImg = RealPathUtil.getRealPath(context, uri);

            Bitmap bitmap = BitmapFactory.decodeFile(pathCardImg);
            cardImg.setImageBitmap(bitmap);
        } else if (requestCode == 11 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Context context = AddCharacterActivity.this;
            pathAvatarImg = RealPathUtil.getRealPath(context, uri);

            Bitmap bitmap = BitmapFactory.decodeFile(pathAvatarImg);
            avatarImg.setImageBitmap(bitmap);
        }
    }

    public void addCharacter(CharacterModel characterModel) {
        File cardImgFile = new File(pathCardImg);
        RequestBody cardFileRequest = RequestBody.create(MediaType.parse("multipart/form-data"), cardImgFile);
        MultipartBody.Part cardFileBody = MultipartBody.Part.createFormData("card_img", cardImgFile.getName(), cardFileRequest);

        File avatarImgFile = new File(pathAvatarImg);
        RequestBody avatarFileRequest = RequestBody.create(MediaType.parse("multipart/form-data"), avatarImgFile);
        MultipartBody.Part avatarFileBody = MultipartBody.Part.createFormData("avatar_img", avatarImgFile.getName(), avatarFileRequest);

        RequestBody namaBody = RequestBody.create(MediaType.parse("multipart/form-data"), characterModel.getNama());
        RequestBody asalBody = RequestBody.create(MediaType.parse("multipart/form-data"), characterModel.getAsal());
        RequestBody visionBody = RequestBody.create(MediaType.parse("multipart/form-data"), characterModel.getVision());
        RequestBody senjataBody = RequestBody.create(MediaType.parse("multipart/form-data"), characterModel.getSenjata());
        RequestBody rarityBody = RequestBody.create(MediaType.parse("multipart/form-data"), characterModel.getRarity());
        RequestBody deskripsiBody = RequestBody.create(MediaType.parse("multipart/form-data"), characterModel.getDeskripsi());

        APIInterface apiInterface = APIService.getRetrofitInstance().create(APIInterface.class);

        Call<AddCharacterResponse> call = apiInterface.addCharacter("Bearer " +
                        sharedPreferences.getString("TOKEN", ""),
                cardFileBody,
                avatarFileBody,
                namaBody,
                asalBody,
                visionBody,
                rarityBody,
                senjataBody,
                deskripsiBody
        );

        call.enqueue(new Callback<AddCharacterResponse>() {
            @Override
            public void onResponse(Call<AddCharacterResponse> call, Response<AddCharacterResponse> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "masuk response successful");
                    if (response.code() == 200) {
                        Log.e(TAG, "masuk response code 200");
                        Toast.makeText(getApplicationContext(), "Character added!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Log.e(TAG, "masuk response selain 200: " + Integer.toString(response.code()));
                        Toast.makeText(getApplicationContext(), "Your input is invalid or character already exist!!!", Toast.LENGTH_SHORT).show();
//                        switch (response.code()) {
//                            case 401:
//                                Toast.makeText(AddCharacterActivity.this, "unauthorized", Toast.LENGTH_SHORT).show();
//                                break;
//                            case 404:
//                                Toast.makeText(AddCharacterActivity.this, "not found", Toast.LENGTH_SHORT).show();
//                                break;
//                            case 500:
//                                Toast.makeText(AddCharacterActivity.this, "server broken", Toast.LENGTH_SHORT).show();
//                                break;
//                            default:
//                                Toast.makeText(AddCharacterActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
//                                break;
//                        }
                    }
                } else {
                    Log.e(TAG, "masuk response unsucceful");
                    errorMsg(response);
                }
            }

            @Override
            public void onFailure(Call<AddCharacterResponse> call, Throwable t) {
                Log.e(TAG, "masuk onFailure");
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void errorMsg(Response<AddCharacterResponse> response) {
        String showError = "";
        try {
            JSONObject jsonObjectError = new JSONObject(response.errorBody().string());
            JSONObject errorMessages = jsonObjectError.getJSONObject("messages");

            if (!errorMessages.isNull("nama")) {
                showError += errorMessages.getString("nama");
            }
            if (!errorMessages.isNull("asal")) {
                if (!showError.equals("")) {
                    showError += "\n";
                }
                showError += errorMessages.getString("asal");
            }
            if (!errorMessages.isNull("username")) {
                if (!showError.equals("")) {
                    showError += "\n";
                }
                showError += errorMessages.getString("username");
            }
            if (!errorMessages.isNull("vision")) {
                if (!showError.equals("")) {
                    showError += "\n";
                }
                showError += errorMessages.getString("vision");
            }
            if (!errorMessages.isNull("senjata")) {
                if (!showError.equals("")) {
                    showError += "\n";
                }
                showError += errorMessages.getString("senjata");
            }
            if (!errorMessages.isNull("rarity")) {
                if (!showError.equals("")) {
                    showError += "\n";
                }
                showError += errorMessages.getString("rarity");
            }
            if (!errorMessages.isNull("avatar_img")) {
                if (!showError.equals("")) {
                    showError += "\n";
                }
                showError += errorMessages.getString("avatar_img");
            }
            if (!errorMessages.isNull("card_img")) {
                if (!showError.equals("")) {
                    showError += "\n";
                }
                showError += errorMessages.getString("card_img");
            }
            if (!errorMessages.isNull("deskripsi")) {
                if (!showError.equals("")) {
                    showError += "\n";
                }
                showError += errorMessages.getString("deskripsi");
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