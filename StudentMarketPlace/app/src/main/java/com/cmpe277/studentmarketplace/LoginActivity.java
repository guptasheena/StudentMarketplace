package com.cmpe277.studentmarketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText email_input, pwd_input;
    Button login_btn;
    TextView signup_link;
    Database db;
    private static final int REQUEST_SIGNUP = 0;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email_input = (EditText) findViewById(R.id.input_email);
        pwd_input = (EditText) findViewById(R.id.input_password);
        login_btn = (Button) findViewById(R.id.btn_login);
        signup_link = (TextView) findViewById(R.id.link_signup);
        db = new Database(this);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        sp = getApplicationContext().getSharedPreferences(getString(R.string.app_pref), MODE_PRIVATE);
        if (sp.getBoolean("logged", false)) {
            Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homeIntent);
        }
        signup_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {

        if (!validate()) {
            onLoginFailed("Login Failed");
            return;
        }

        login_btn.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        String email = email_input.getText().toString();
                        String password = pwd_input.getText().toString();
                        DbResult result = db.validUser(email, password);
                        // On complete call either onLoginSuccess or onLoginFailed
                        if (result.getStatus()) {
                            onLoginSuccess(email_input.getText().toString());
                        } else onLoginFailed(result.getMessage());
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                Bundle b = data.getExtras();
                String email = b.getString("email");
                onLoginSuccess(email);
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(String email) {
        sp.edit().putString("email", email).apply();
        sp.edit().putBoolean("logged", true).apply();
        login_btn.setEnabled(true);
        Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeIntent);
    }

    public void onLoginFailed(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();

        login_btn.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = email_input.getText().toString();
        String password = pwd_input.getText().toString();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_input.setError("enter a valid email address");
            valid = false;
        } else {
            email_input.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            pwd_input.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            pwd_input.setError(null);
        }

        return valid;
    }
}

