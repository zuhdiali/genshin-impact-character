package com.example.karaktergenshinimpact.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karaktergenshinimpact.R;
import com.example.karaktergenshinimpact.Utils.AppUtils;
import com.example.karaktergenshinimpact.Utils.DownloadImageTask;
import com.example.karaktergenshinimpact.activity.CharacterDetailActivity;
import com.example.karaktergenshinimpact.response.CharacterResponse;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.HolderKarakter> {
    List<CharacterResponse> listKarakter;
    LayoutInflater inflater;

    public CharacterAdapter(Context context, List<CharacterResponse> listKarakter) {
        this.listKarakter = listKarakter;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public HolderKarakter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_rv_karakter, parent, false);
        return new HolderKarakter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderKarakter holder, @SuppressLint("RecyclerView") int position) {
        holder.namaKarakter.setText(listKarakter.get(position).getNama());
        new DownloadImageTask(holder.avatarKarakter).execute(AppUtils.urlAvatarImg + listKarakter.get(position).getAvatarImg());
        setBackgroundItem(holder,listKarakter.get(position).getVision());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(), "Karakter: " + listKarakter.get(position).getNama(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(view.getContext(), CharacterDetailActivity.class);
                Bundle extras = new Bundle();
                extras.putString("ID", listKarakter.get(position).getId());
                extras.putString("NAMA", listKarakter.get(position).getNama());
                extras.putString("VISION", listKarakter.get(position).getVision());
                extras.putString("ASAL", listKarakter.get(position).getAsal());
                extras.putString("SENJATA", listKarakter.get(position).getSenjata());
                extras.putString("RARITY", listKarakter.get(position).getRarity());
                extras.putString("DESKRIPSI", listKarakter.get(position).getDeskripsi());
                extras.putString("CARD_IMG", listKarakter.get(position).getCardImg());
                extras.putString("AVATAR_IMG", listKarakter.get(position).getAvatarImg());
                i.putExtras(extras);
                view.getContext().startActivity(i);
            }
        });
    }

    private void setBackgroundItem(HolderKarakter holder,String vision) {
        switch (vision){
            case "Anemo":
                holder.itemView.setBackgroundResource(R.drawable.background_anemo);
                holder.visionKarakter.setImageResource(R.drawable.anemo_vision);
                break;
            case "Pyro":
                holder.itemView.setBackgroundResource(R.drawable.background_pyro);
                holder.visionKarakter.setImageResource(R.drawable.pyro_vision);
                break;
            case "Dendro":
                holder.itemView.setBackgroundResource(R.drawable.background_dendro);
                holder.visionKarakter.setImageResource(R.drawable.dendro_vision);
                break;
            case "Cryo":
                holder.itemView.setBackgroundResource(R.drawable.background_cryo);
                holder.visionKarakter.setImageResource(R.drawable.cryo_vision);
                break;
            case "Geo":
                holder.itemView.setBackgroundResource(R.drawable.background_geo);
                holder.visionKarakter.setImageResource(R.drawable.geo_vision);
                break;
            case "Hydro":
                holder.itemView.setBackgroundResource(R.drawable.background_hydro);
                holder.visionKarakter.setImageResource(R.drawable.hydro_vision);
                break;
            case "Electro":
                holder.itemView.setBackgroundResource(R.drawable.background_electro);
                holder.visionKarakter.setImageResource(R.drawable.electro_vision);
                break;
            default:
//                holder.itemView.setBackgroundResource(R.drawable.background_hydro);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return listKarakter.size();
    }

    public class HolderKarakter extends RecyclerView.ViewHolder {
        TextView namaKarakter;
        ImageView avatarKarakter, visionKarakter;

        public HolderKarakter(@NonNull View itemView) {
            super(itemView);

            namaKarakter = itemView.findViewById(R.id.nama_karakter_rv);
            avatarKarakter = itemView.findViewById(R.id.avatar_rv);
            visionKarakter = itemView.findViewById(R.id.vision_rv);

        }
    }
}
