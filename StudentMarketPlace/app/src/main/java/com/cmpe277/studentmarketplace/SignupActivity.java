package com.cmpe277.studentmarketplace;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    EditText email_input, pwd_input,name_input;
    Button signup_btn;
    TextView login_link;
    Database db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name_input = (EditText)findViewById(R.id.input_name);
        email_input = (EditText)findViewById(R.id.input_email);
        pwd_input = (EditText)findViewById(R.id.input_password);
        signup_btn = (Button)findViewById(R.id.btn_signup);
        login_link = (TextView)findViewById(R.id.link_login);
        db = new Database(this);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {

        if (!validate()) {
            onSignupFailed("SignUp Failed");
            return;
        }

        signup_btn.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = name_input.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        String email = email_input.getText().toString();
                        String password = pwd_input.getText().toString();
                        DbResult result = db.insertNewUser(email,password);
                        if(result.getStatus())
                            onSignupSuccess();
                        else
                            onSignupFailed(result.getMessage());
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        signup_btn.setEnabled(true);
        setResult(RESULT_OK, null);
        Toast.makeText(getBaseContext(), "SignUp Success", Toast.LENGTH_LONG).show();
    }

    public void onSignupFailed(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();

        signup_btn.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = name_input.getText().toString();
        String email = email_input.getText().toString();
        String password = pwd_input.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            name_input.setError("at least 3 characters");
            valid = false;
        } else {
            name_input.setError(null);
        }

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
