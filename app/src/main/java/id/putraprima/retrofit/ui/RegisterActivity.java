package id.putraprima.retrofit.ui;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.Envelope;
import id.putraprima.retrofit.api.models.RegisterRequest;
import id.putraprima.retrofit.api.models.RegisterResponse;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    public RegisterRequest registerRequest;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mPasswordConfirm;
    private EditText mEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mPasswordConfirm = findViewById(R.id.password_confirm);
        mEmail = findViewById(R.id.email);
    }

    public void register() {
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<Envelope<RegisterResponse>> call = service.doRegister(registerRequest);
        call.enqueue(new Callback<Envelope<RegisterResponse>>() {

            @Override
            public void onResponse(Call<Envelope<RegisterResponse>> call, Response<Envelope<RegisterResponse>> response) {
                if (response.code() == 302) {
                    Toast.makeText(RegisterActivity.this, "Registrasi Gagal, User Sudah Digunakan", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 201) {
                    Toast.makeText(RegisterActivity.this, response.body().getData().getName(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterActivity.this, response.body().getData().getEmail(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterActivity.this, "Registrasi Anda Telah Berhasil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Envelope<RegisterResponse>> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error Request", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void handlerRegistProcess(View view) {
        String name = mUsername.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String password_confirm = mPasswordConfirm.getText().toString();
        registerRequest = new RegisterRequest(name, email, password, password_confirm);

        boolean check;
        if (name.equals("")) {
            Toast.makeText(this, "Input Your Name!", Toast.LENGTH_SHORT).show();
            check = false;
        } else if (email.equals("")) {
            Toast.makeText(this, "Input Your Email!", Toast.LENGTH_SHORT).show();
            check = false;
        } else if (password.equals("")) {
            Toast.makeText(this, "Input Your Password!", Toast.LENGTH_SHORT).show();
            check = false;
        } else if (password.length() < 8) {
            Toast.makeText(this, "Your Password is Too Short", Toast.LENGTH_SHORT).show();
            check = false;
        } else if (!password_confirm.equals(password)) {
            Toast.makeText(this, "Your Confirm Password Is Not Same!", Toast.LENGTH_SHORT).show();
            check = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Your Email Is Not Valid!", Toast.LENGTH_SHORT).show();
            check = false;
        } else {
            check = true;
        }
        if (check == true) {
            register();
        }
    }
}
