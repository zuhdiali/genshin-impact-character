/*
 * *
 *  * Created by zuhdi on 12/22/22, 8:35 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 12/19/22, 4:05 PM
 *
 */

package com.example.karaktergenshinimpact.request;

import com.example.karaktergenshinimpact.response.AddCharacterResponse;
import com.example.karaktergenshinimpact.response.CharacterResponse;
import com.example.karaktergenshinimpact.response.LoginResponse;
import com.example.karaktergenshinimpact.response.AccountResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIInterface {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> getUserInformation(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<AccountResponse> register(@Field("email") String email,
                                   @Field("username") String username,
                                   @Field("nama_lengkap") String namaLengkap,
                                   @Field("password") String password,
                                   @Field("confpassword") String confPassword);

    @FormUrlEncoded
    @POST("update-user/{id}")
    Call<AccountResponse> updateUser(@Path("id") String id,
                                     @Header("Authorization") String token,
                                     @Field("email") String email,
                                     @Field("nama_lengkap") String fullName,
                                     @Field("username") String username,
                                     @Field("password_lama") String oldPassword,
                                     @Field("password_baru") String newPassword,
                                     @Field("confpassword_baru") String confNewPassword);

    @GET("karakter")
    Call<List<CharacterResponse>> getAllKarakter(@Header("Authorization") String token);

    @Multipart
    @POST("karakter")
    Call<AddCharacterResponse> addCharacter(@Header("Authorization") String token,
                                            @Part MultipartBody.Part cardImg,
                                            @Part MultipartBody.Part avatarImg,
                                            @Part("nama")RequestBody nama,
                                            @Part("asal")RequestBody asal,
                                            @Part("vision")RequestBody vision,
                                            @Part("rarity")RequestBody rarity,
                                            @Part("senjata")RequestBody senjata,
                                            @Part("deskripsi")RequestBody deskripsi);


    @DELETE("delete/{id}")
    Call<AddCharacterResponse> deleteCharacter(@Header("Authorization") String token,
                                               @Path("id") String id);

    @Multipart
    @POST("update/{id}")
    Call<AddCharacterResponse> editCharacter00(@Header("Authorization") String token,
                                            @Path("id") String id,
                                            @Part("nama")RequestBody nama,
                                            @Part("asal")RequestBody asal,
                                            @Part("vision")RequestBody vision,
                                            @Part("rarity")RequestBody rarity,
                                            @Part("senjata")RequestBody senjata,
                                            @Part("deskripsi")RequestBody deskripsi);

    @Multipart
    @POST("update/{id}")
    Call<AddCharacterResponse> editCharacter10(@Header("Authorization") String token,
                                               @Path("id") String id,
                                               @Part MultipartBody.Part cardImg,
                                               @Part("nama")RequestBody nama,
                                               @Part("asal")RequestBody asal,
                                               @Part("vision")RequestBody vision,
                                               @Part("rarity")RequestBody rarity,
                                               @Part("senjata")RequestBody senjata,
                                               @Part("deskripsi")RequestBody deskripsi);

    @Multipart
    @POST("update/{id}")
    Call<AddCharacterResponse> editCharacter01(@Header("Authorization") String token,
                                               @Path("id") String id,
                                               @Part MultipartBody.Part avatarImg,
                                               @Part("nama")RequestBody nama,
                                               @Part("asal")RequestBody asal,
                                               @Part("vision")RequestBody vision,
                                               @Part("rarity")RequestBody rarity,
                                               @Part("senjata")RequestBody senjata,
                                               @Part("deskripsi")RequestBody deskripsi);

    @Multipart
    @POST("update/{id}")
    Call<AddCharacterResponse> editCharacter11(@Header("Authorization") String token,
                                               @Path("id") String id,
                                               @Part MultipartBody.Part cardImg,
                                               @Part MultipartBody.Part avatarImg,
                                               @Part("nama")RequestBody nama,
                                               @Part("asal")RequestBody asal,
                                               @Part("vision")RequestBody vision,
                                               @Part("rarity")RequestBody rarity,
                                               @Part("senjata")RequestBody senjata,
                                               @Part("deskripsi")RequestBody deskripsi);
}
