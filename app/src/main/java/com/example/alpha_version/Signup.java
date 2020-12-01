package com.example.alpha_version;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    EditText ETName,ETMail,ETPass;
    private FirebaseAuth fAuth;
    Button SignUp,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ETMail = (EditText) findViewById(R.id.ETMail);
        ETPass = (EditText) findViewById(R.id.ETPass);
        ETName = (EditText) findViewById(R.id.ETName);
        fAuth = FirebaseAuth.getInstance();
        SignUp = (Button) findViewById(R.id.SignUp);
        SignUp.setOnClickListener(this);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    /**
     * @since 1/11/2020
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent si = new Intent (this, MainActivity.class);
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.SignUp:
                registerUser();
                break;
        }
    }

    private void registerUser(){
        String email = ETMail.getText().toString();
        String Name = ETName.getText().toString();
        String password = ETPass.getText().toString();
        if(Name.isEmpty()){
            ETName.setError("Full name is required");
            ETName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            ETMail.setError("Email is required");
            ETMail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            ETPass.setError("Password is required");
            ETPass.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            ETMail.setError("Please provide valid email");
            ETMail.requestFocus();
            return;
        }

        if(password.length()<6){
            ETPass.setError("Password is should be 6 Characters!");
            ETPass.requestFocus();
            return;
        }

        fAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Users user = new Users(Name , email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Signup.this, "User has been registered successfully!", Toast.LENGTH_SHORT).show();
                                        //Move the user to the login activity
                                    }
                                    else {
                                        Toast.makeText(Signup.this, "Failed to sign up", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(Signup.this, "Failed to sign up", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}