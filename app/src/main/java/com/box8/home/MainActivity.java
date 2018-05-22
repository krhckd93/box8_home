package com.box8.home;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.box8.home.fragments.HomeFragment;
import com.box8.home.helpers.NetworkHelper;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!NetworkHelper.isConnected(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Active internet connection is required.", Toast.LENGTH_SHORT).show();
        }
        HomeFragment homeFragment = new HomeFragment();
        getFragmentManager().beginTransaction().replace(R.id.frag_container, homeFragment).addToBackStack("homeFragment").commit();
    }

}