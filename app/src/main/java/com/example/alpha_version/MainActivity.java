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
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * @author Dvir Dadon
 */
public class MainActivity extends AppCompatActivity {
    EditText ETMail,ETPass;
    private FirebaseAuth fAuth;
    Button SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ETMail = (EditText) findViewById(R.id.ETMail);
        ETPass = (EditText) findViewById(R.id.ETPass);
        fAuth = FirebaseAuth.getInstance();

    }



    public void SignUp(View view) {
        Intent si = new Intent(this , Signup.class);
        startActivity(si);
    }

    public void SignIn(View view) {
        userLogin();
    }

    /**
     * @since  12/11/2020
     * This method helps the user log into the system if he is correct it will send him
     * to the start activity of the application
     */
    private void userLogin() {
        String email = ETMail.getText().toString().trim();
        String password =ETPass.getText().toString().trim();

        if(email.isEmpty()){
            ETMail.setError("Email is required");
            ETMail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            ETPass.setError("Email is required");
            ETPass.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            ETMail.setError("Please enter a valid email!");
            ETMail.requestFocus();
                return;
        }
        if(password.length()<6){
            ETPass.setError("Min password length is 6 characters!");
            ETPass.requestFocus();
            return;
        }
        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this , Storage.class));
                }
                else{
                    Toast.makeText(MainActivity.this, "Failed to login! please check your credentials", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}