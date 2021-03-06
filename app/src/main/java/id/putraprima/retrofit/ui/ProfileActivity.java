package id.putraprima.retrofit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.Data;
import id.putraprima.retrofit.api.models.Session;
import id.putraprima.retrofit.api.models.UserInfo;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    //    public Context context;
    private TextView nameText, emailText;
    private String txtToken, txtTokenType;
    private Session s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        s = new Session(this);
        nameText = findViewById(R.id.Name);
        emailText = findViewById(R.id.Email);
        txtToken = s.getToken();
        txtTokenType = s.getTokenType();
        getDatas();
    }

    public void getDatas() {
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<Data<UserInfo>> call = service.me(txtTokenType + " " + txtToken);
        call.enqueue(new Callback<Data<UserInfo>>() {
            @Override
            public void onResponse(Call<Data<UserInfo>> call, Response<Data<UserInfo>> response) {
                if (response.body() != null) {
                    Toast.makeText(ProfileActivity.this, response.body().data.getEmail(), Toast.LENGTH_SHORT).show();
                    nameText.setText(response.body().data.name);
                    emailText.setText(response.body().data.email);
                }
            }

            @Override
            public void onFailure(Call<Data<UserInfo>> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Error Request", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void handleUpdate(View view) {
        Intent i = new Intent(ProfileActivity.this, UpdateActivity.class);
        startActivity(i);
    }

    public void handleUpdatePass(View view) {
        Intent i = new Intent(ProfileActivity.this, UpdatePassActivity.class);
        startActivity(i);
    }

}
