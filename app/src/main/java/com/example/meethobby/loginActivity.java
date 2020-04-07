package com.example.meethobby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {

    private Button LoginButton;
    private EditText UserEmail, UserPassword;
    private TextView RegisterLink;
    private ProgressDialog loadingBar;
    private FirebaseAuth myAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitializeFields();

        myAuth = FirebaseAuth.getInstance();
        RegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToRegisterActivity();
            }
        });
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginAccount();
            }
        });
    }

    private void LoginAccount() {
        String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Enter an email",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Enter a password",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Logging in Account");
            loadingBar.setMessage("Please Wait");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();
            myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        SendUserToMainActivity();
                        Toast.makeText(loginActivity.this, "Logged in",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                    else
                    {
                        String message = task.getException().toString();
                        Toast.makeText(loginActivity.this, "Error:"+message,Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }
    }

    private void InitializeFields() {
        LoginButton = (Button) findViewById(R.id.login_button);
        UserEmail = (EditText) findViewById(R.id.login_email);
        UserPassword = (EditText) findViewById(R.id.login_password);
        RegisterLink = (TextView) findViewById(R.id.register_link);
        loadingBar = new ProgressDialog(this);
    }



    private void SendUserToMainActivity() {
        Intent MainIntent = new Intent(loginActivity.this, MainActivity.class);
        startActivity(MainIntent);

    }

    private void SendUserToRegisterActivity() {
        Intent MainIntent = new Intent(loginActivity.this, RegisterActivity.class);
        startActivity(MainIntent);

    }
}
