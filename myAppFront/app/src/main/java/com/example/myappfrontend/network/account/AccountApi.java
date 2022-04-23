package com.example.myappfrontend.network.account;



import com.example.myappfrontend.network.account.dto.AccountResponseDTO;
import com.example.myappfrontend.network.account.dto.RegisterDTO;
import com.example.myappfrontend.network.account.usercard.UserDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AccountApi {
    // куди буде йти запит
    @POST("/api/account/register")
    public Call<AccountResponseDTO> register(@Body RegisterDTO model);

    @GET("/api/account/users")
    public Call<List<UserDTO>> users();
}
