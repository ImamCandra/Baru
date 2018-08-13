package com.example.home.baru;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.home.baru.models.Response;
import com.example.home.baru.models.User;
import com.example.home.baru.network.NetworkUtil;
import com.example.home.baru.network.RetrofitInterface;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;

import static com.example.home.baru.utils.Validation.validateEmail;
import static com.example.home.baru.utils.Validation.validateFields;

public class RegisterFragment extends Fragment implements View.OnClickListener{

    private AppCompatButton mBtRegister;
    private EditText mEtEmail;
    private EditText mEtPassword;
    private EditText mEtUsername;
    private ProgressBar mProgressBar;
   // @BindView(R.id.et_name) EditText mEtEmail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mBtRegister = (AppCompatButton)view.findViewById(R.id.btn_register);
        mEtEmail = (EditText)view.findViewById(R.id.et_email);
        mEtPassword = (EditText)view.findViewById(R.id.et_password);
        mEtUsername = (EditText)view.findViewById(R.id.et_name);
        mProgressBar = (ProgressBar)view.findViewById(R.id.progress);

        mBtRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
       register();
    }

    private void setError() {
        mEtUsername.setError(null);
        mEtEmail.setError(null);
        mEtPassword.setError(null);
    }

    private void register() {
        setError();

        String username = mEtUsername.getText().toString();
        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();

        int err = 0;

        if (!validateEmail(email)) {
            err++;
            mEtEmail.setError("Email should be valid !");
        }

        if (!validateFields(username)) {
            err++;
            mEtPassword.setError("Username should not be empty !");
        }

        if (!validateFields(password)) {
            err++;
            mEtPassword.setError("Password should not be empty !");
        }

        if (err == 0) {

            registerProcess(username, email, password);
            mProgressBar.setVisibility(View.VISIBLE);

        } else {
            showSnackBarMessage("Enter valid Details !");
        }
    }

    private void registerProcess(String username, String email, String password) {


        RetrofitInterface service = NetworkUtil.getRetrofit().create(RetrofitInterface.class);

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        String key = "access_token=CcwYJYiaQo7Am9Z6Yk6FsswFiOpP60Da";

        Call<Response> call = service.userRegister(user);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                mProgressBar.setVisibility(View.GONE);
                showSnackBarMessage("BERHASIL");
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                showSnackBarMessage("GAGAL");
            }
        });
    }

    private void goToLogin() {
        Fragment loginFragment =  new LoginFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_frame, loginFragment);
        fragmentTransaction.commit();
    }

    private void showSnackBarMessage(String message) {

        if (getView() != null) {

            Snackbar.make(getView(),message,Snackbar.LENGTH_SHORT).show();
        }
    }
}
