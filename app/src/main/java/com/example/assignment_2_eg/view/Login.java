package com.example.assignment_2_eg.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignment_2_eg.R;
import com.example.assignment_2_eg.databinding.ActivityLoginBinding;
import com.example.assignment_2_eg.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();



    binding.btnLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            loginUser("kishan@gmail.com","class123");
            String email = binding.emailET.getText().toString();
//             Validate email
                if (email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email is required...", Toast.LENGTH_SHORT).show();
                    return;
                }
            String password = binding.passwordET.getText().toString();
//            Validate password
            if (password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Password is required...", Toast.LENGTH_SHORT).show();
                return;
            }

            loginUser(email,password);
        }
    });
    binding.btnRegister.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentObj = new Intent(getApplicationContext(), Register.class);
            startActivity(intentObj);
            finish();
        }
    });
    }
    private void loginUser(String email, String passwd){

        mAuth.signInWithEmailAndPassword(email,passwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //  an Intent
                            Intent intentObj = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intentObj);
                            finish();

                        }
                        else{
                            Log.d("tag","Login failed ",task.getException());
                            Toast.makeText(Login.this,"Either email or password does not match",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}