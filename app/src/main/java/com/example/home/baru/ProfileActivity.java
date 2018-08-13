package com.example.home.baru;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.home.baru.models.Response;
import com.example.home.baru.network.NetworkUtil;
import com.example.home.baru.network.RetrofitInterface;

import retrofit2.Call;

public class ProfileActivity extends AppCompatActivity {

    private TextView mTvName;
    private TextView mTvEmail;
    private Button mBtLogout;
    private ProgressBar mProgressbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvEmail = (TextView) findViewById(R.id.tv_email);
        mBtLogout = (Button) findViewById(R.id.btn_logout);
        mProgressbar = (ProgressBar) findViewById(R.id.progress);
    }

    private void loadUser() {
        RetrofitInterface service = NetworkUtil.getRetrofit().create(RetrofitInterface.class);
       // Call<Response> call = service.getProfile();
    }
}
