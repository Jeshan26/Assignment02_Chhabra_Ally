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
import com.example.assignment_2_eg.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = binding.emailET.getText().toString().trim();
                String password = binding.passwordET.getText().toString();
                String confirmPassword = binding.confirmPasswdET.getText().toString();

                // Validate email
                if (email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email is required...", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate password
                if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password is required...", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate confirm password
                if (confirmPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Confirm password cannot be empty...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match...", Toast.LENGTH_SHORT).show();
                    return;
                }

//                displaying toast if fields are validated
                Toast.makeText(getApplicationContext(), "Registering the user ....", Toast.LENGTH_SHORT).show();

                registerUser(email,password);
//                registerUser("kishan@gmail.com","class123");

            }
        });
    }

    private void registerUser(String email, String passwd){

    mAuth.createUserWithEmailAndPassword(email,passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
                //  an Intent to go to the Login Page after successful registeration
                Intent intentObj = new Intent(getApplicationContext(), Login.class);
                startActivity(intentObj);

            }
            else{
                Log.d("tag","createdUserWithEmail:Failed",task.getException());
                Toast.makeText(Register.this,"Auth failed",Toast.LENGTH_SHORT).show();
            }

        }
    });

    }
}