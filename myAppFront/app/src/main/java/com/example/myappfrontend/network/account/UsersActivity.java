package com.example.myappfrontend.network.account;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myappfrontend.BaseActivity;
import com.example.myappfrontend.R;
import com.example.myappfrontend.network.account.dto.ServerErrorDTO;
import com.example.myappfrontend.network.account.usercard.UserDTO;
import com.example.myappfrontend.network.account.usercard.UsersAdapter;
import com.example.myappfrontend.utils.CommonUtils;
import com.google.gson.Gson;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersActivity extends BaseActivity {

    private UsersAdapter adapter;
    private RecyclerView rcvUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        rcvUsers = findViewById(R.id.rcvUsers);
        rcvUsers.setHasFixedSize(true);
        rcvUsers.setLayoutManager(new GridLayoutManager(this, 2,
                LinearLayoutManager.VERTICAL, false));

        CommonUtils.showLoading(this);
        AccountService.getInstance()
                .jsonApi()
                .users()
                .enqueue(new Callback<List<UserDTO>>() {
                    @Override
                    public void onResponse(Call<List<UserDTO>> call, Response<List<UserDTO>> response) {
                        if(response.isSuccessful())
                        {
                            adapter=new UsersAdapter(response.body());
                            rcvUsers.setAdapter(adapter);
                        }
                        if(response.code()>=500)
                        {
                            try {
                                String json = response.errorBody().string();
                                Gson gson = new Gson();
                                ServerErrorDTO serverError = gson.fromJson(json, ServerErrorDTO.class);
                                String message = serverError.getError();
                                Toast.makeText(UsersActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                            catch(Exception ex) {}

                        }
                        CommonUtils.hideLoading();
                    }

                    @Override
                    public void onFailure(Call<List<UserDTO>> call, Throwable t) {
                        CommonUtils.hideLoading();
                    }
                });

//                .enqueue(new Callback<>() {
//                    @Override
//                    public void onResponse(Call<AccountResponseDTO> call, Response<AccountResponseDTO> response) {
//                        if (response.isSuccessful()) {
//                            AccountResponseDTO data = response.body();
//                            //tvInfo.setText("response is good");
//                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                            startActivity(intent);
//                        } else {
//                            try {
//                                showErrorsServer(response.errorBody().string());
//                            } catch (Exception e) {
//                                System.out.println("------Error response parse body-----");
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<AccountResponseDTO> call, Throwable t) {
//                        String str = t.toString();
//                        int a = 12;
//                    }
//                });
//        List<UserDTO> userDTOS = new ArrayList<>();
//        UserDTO userDTO = new UserDTO();
//        userDTO.setEmail("ss@gg.dd");
//        userDTO.setImage("/images/dmxnsy1u.1ah.jpeg");
//        userDTOS.add(userDTO);
//
//        UserDTO userDTO2 = new UserDTO();
//        userDTO2.setEmail("dd@vv.dd");
//        userDTO2.setImage("/images/gfat5osf.lgr.jpeg");
//        userDTOS.add(userDTO2);
//        adapter=new UsersAdapter(userDTOS);
//        rcvUsers.setAdapter(adapter);

    }
}