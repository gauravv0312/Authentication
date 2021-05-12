package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
   Button button;
   EditText address, textpassword;
   FirebaseAuth fAuth;
   FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button=findViewById(R.id.button);
        address=findViewById(R.id.address);
        textpassword=findViewById(R.id.textpassword);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Address= address.getText().toString().trim();
                String TextPassword = textpassword.getText().toString().trim();
                if (TextUtils.isEmpty(Address))
                {
                    address.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(TextPassword))
                {
                    textpassword.setError("Password is required");
                }

                fAuth.signInWithEmailAndPassword(Address,TextPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful())
                       {
                           Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(),ContactActivity.class));
                       }
                       else
                       {
                           Toast.makeText(LoginActivity.this, "Error!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                       }
                    }
                });

            }
        });
    }
}