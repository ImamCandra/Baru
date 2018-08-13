package com.example.home.baru;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.home.baru.utils.Constant;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = getPreferences(0);
        initFragment();
    }

    private void initFragment() {
        Fragment mFragment;
        if (mSharedPreferences.getBoolean(Constant.IS_LOGGED_IN, false)) {
            mFragment = new RegisterFragment();
        } else {
            mFragment = new LoginFragment();
        }
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_frame, mFragment);
        fragmentTransaction.commit();
    }
}
