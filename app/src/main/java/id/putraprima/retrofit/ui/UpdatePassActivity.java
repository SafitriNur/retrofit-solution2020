package id.putraprima.retrofit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.PasswordRequest;
import id.putraprima.retrofit.api.models.PasswordResponse;
import id.putraprima.retrofit.api.models.Session;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePassActivity extends AppCompatActivity {

    private EditText txtPass, txtConPass;
    private Session s;
    private String pswd, cpswd, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass);

        txtPass = findViewById(R.id.paswd);
        txtConPass = findViewById(R.id.cpaswd);
        s = new Session(this);
        token = s.getTokenType() + " " + s.getToken();
    }

    public void handleUpdateProcess(View view) {
        pswd = txtPass.getText().toString();
        cpswd = txtConPass.getText().toString();
        boolean check = pswd.equals("") && cpswd.equals("");
        boolean confirm = pswd.equals(cpswd);
        boolean dataLength = pswd.length() < 8 && cpswd.length() < 8;

        if (check) {
            txtPass.setError("Entry Your Password!");
            txtConPass.setError("Entry Your New Password!");
            if (confirm) {
                Toast.makeText(UpdatePassActivity.this, "the confirmation is not the same", Toast.LENGTH_SHORT).show();
            }
            if (dataLength) {
                Toast.makeText(UpdatePassActivity.this, "Data length min 8 characters", Toast.LENGTH_SHORT).show();
            }
        } else {
            updatePass();
        }

    }

    private void updatePass() {
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<PasswordResponse> call = service.doUpdatePassword(token, new PasswordRequest(pswd, cpswd));
        call.enqueue(new Callback<PasswordResponse>() {
            @Override
            public void onResponse(Call<PasswordResponse> call, Response<PasswordResponse> response) {
                if (response.body() != null) {
                    Toast.makeText(UpdatePassActivity.this, "Update Successfull", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(UpdatePassActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(UpdatePassActivity.this, "Update Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PasswordResponse> call, Throwable t) {
                Toast.makeText(UpdatePassActivity.this, "Update Failed!", Toast.LENGTH_SHORT).show();
            }

        });
    }
}

