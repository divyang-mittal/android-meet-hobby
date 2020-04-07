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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private Button RegisterButton;
    private EditText UserEmail, UserPassword;
    private TextView LoginLink;
    private FirebaseAuth myAuth;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        InitializeFields();
        myAuth = FirebaseAuth.getInstance();
        LoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToLoginActivity();
            }
        });
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });
    }

    private void CreateNewAccount() {
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
            loadingBar.setTitle("Creating Account");
            loadingBar.setMessage("Please Wait");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();
            myAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        SendUserToLoginActivity();
                        Toast.makeText(RegisterActivity.this, "Accout created",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                    else
                    {
                        String message = task.getException().toString();
                        Toast.makeText(RegisterActivity.this, "Error:"+message,Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }

                }
            });
        }
    }

    private void InitializeFields() {
        RegisterButton = (Button) findViewById(R.id.register_button);
        UserEmail = (EditText) findViewById(R.id.register_email);
        UserPassword = (EditText) findViewById(R.id.register_password);
        LoginLink = (TextView) findViewById(R.id.login_link);
        loadingBar = new ProgressDialog(this);
    }

    private void SendUserToMainActivity() {
        Intent MainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(MainIntent);

    }

    private void SendUserToLoginActivity() {
        Intent LoginIntent = new Intent(RegisterActivity.this, loginActivity.class);
        startActivity(LoginIntent);

    }
}
