package com.example.meethobby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
{

    private Toolbar myToolbar;
    private FirebaseUser currentUser;
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myAuth = FirebaseAuth.getInstance();
        currentUser = myAuth.getCurrentUser();

        myToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar((myToolbar));
        getSupportActionBar().setTitle("Meet@Hobby");

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if(currentUser == null)
        {
            SendUserToLoginActivity();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.main_logout)
        {
            myAuth.signOut();
            SendUserToLoginActivity();
        }
        return true;
    }

    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, loginActivity.class);
        startActivity(loginIntent);

    }
}
