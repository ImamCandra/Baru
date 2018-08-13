package com.example.home.baru;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.home.baru.models.Response;
import com.example.home.baru.network.NetworkUtil;
import com.example.home.baru.network.RetrofitInterface;
import com.example.home.baru.utils.Constant;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.example.home.baru.utils.Validation.validateEmail;
import static com.example.home.baru.utils.Validation.validateFields;

public class LoginFragment extends Fragment {

    private AppCompatButton mBtLogin;
    private EditText mEtEmail;
    private EditText mEtPassword;
    private ProgressBar mProgressBar;
    private SharedPreferences mSharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mSharedPreferences = getActivity().getPreferences(0);
        mBtLogin = (AppCompatButton)view.findViewById(R.id.btn_login);
        mEtEmail = (EditText)view.findViewById(R.id.et_email);
        mEtPassword = (EditText)view.findViewById(R.id.et_password);
        mProgressBar = (ProgressBar)view.findViewById(R.id.progress);
        TextView mTvRegister = (TextView)view.findViewById(R.id.tv_register);

        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });

    }

    private void setError() {
        mEtEmail.setError(null);
        mEtPassword.setError(null);
    }

    private void login() {
        setError();

        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();

        int err = 0;

        if (!validateEmail(email)) {
            err++;
            mEtEmail.setError("Email should be valid !");
        }

        if (!validateFields(password)) {
            err++;
            mEtPassword.setError("Password should not be empty !");
        }

        if (err == 0) {

            loginProcess(email, password);
            mProgressBar.setVisibility(View.VISIBLE);

        } else {
            showSnackBarMessage("Enter valid Details !");
        }
    }

    private void loginProcess(String email, String password) {
        RetrofitInterface service = NetworkUtil.getRetrofitLogin(email, password).create(RetrofitInterface.class);
        String key = "access_token:CcwYJYiaQo7Am9Z6Yk6FsswFiOpP60Da";
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), key);
        Call<Response> call = service.userLogin(body);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                showSnackBarMessage("BERHASIL");
                mProgressBar.setVisibility(View.GONE);
                showSnackBarMessage(response.body().getToken());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                showSnackBarMessage("GAGAL");
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void goToRegister() {
        Fragment registerFragment = new RegisterFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_frame, registerFragment);
        fragmentTransaction.commit();
    }

    private void goToProfile() {
        //Fragment profileFragment = new ProfileActivity();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        //fragmentTransaction.replace(R.id.fragment_frame, profileFragment);
        fragmentTransaction.commit();
    }

    private void showSnackBarMessage(String message) {
        if (getView() != null) {
            Snackbar.make(getView(),message,Snackbar.LENGTH_SHORT).show();
        }
    }
}
