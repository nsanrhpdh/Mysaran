package com.annisa.mysaran;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class LogActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;
    private TextView buttonRegister;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);

        buttonLogin = findViewById(R.id.login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = editTextUsername.getText().toString();
                final String password = editTextPassword.getText().toString();

                class Login extends AsyncTask<Void, Void, String>{
                    ProgressDialog loading;

                    @Override
                    protected void onPreExecute(){
                        super.onPreExecute();
                        loading = ProgressDialog.show(LogActivity.this,
                                "Login..", "Mohon Tunggu", false, false);
                    }

                    @Override
                    protected void onPostExecute(String response){
                        super.onPostExecute(response);
                        loading.dismiss();
                        if(response.equals("Proceed")){
                            Intent intent = new Intent(LogActivity.this, TambahData.class);
                            intent.putExtra("username", username);
                            intent.putExtra("password", password);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(LogActivity.this, response, Toast.LENGTH_LONG).show();
                            editTextPassword.setText("");
                        }
                    }

                    @Override
                    protected String doInBackground(Void... voids) {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("username", username);
                        params.put("password", password);

                        RequestHandler requestHandler = new RequestHandler();
                        String response = requestHandler.sendPostRequest(Konfigurasi.URL_LOGIN, params);
                        return response;
                    }
                }
                Login login = new Login();
                login.execute();
            }
        });

        buttonRegister = findViewById(R.id.register);
        buttonRegister.setOnClickListener(view -> {
            Intent register = new Intent(this, RegisterActivity.class);
            startActivity(register);
        });
    }
}