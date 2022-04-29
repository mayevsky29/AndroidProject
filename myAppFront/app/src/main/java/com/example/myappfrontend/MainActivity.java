package com.example.myappfrontend;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myappfrontend.network.account.RegisterActivity;
import com.example.myappfrontend.network.account.UsersActivity;

public class MainActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // завантаження основного актівіті
        setContentView(R.layout.activity_main);
    }




}