package com.example.demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG ="TAG" ;
    ImageView imageView;
    Button register,login;
    EditText name, email, phone, password, cpassword;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageReference;
    String userID,Profileimageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        imageView = findViewById(R.id.imageView);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        cpassword = findViewById(R.id.cpassword);
        register = findViewById(R.id.register);
        login=findViewById(R.id.login);
        fAuth=FirebaseAuth.getInstance();
        fStore =FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent opengalleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(opengalleryintent,2000);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "Welcome to login Page", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();
                String Name = name.getText().toString();
                String Cpassword = cpassword.getText().toString().trim();
                String Phone =phone.getText().toString();
                if (TextUtils.isEmpty(Email)) {
                    email.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(Password)) {
                    password.setError("Password is required");
                    return;
                }
                if (Password.length() < 6) {
                    password.setError("Password must be of 6 character or greater than 6");
                    return;
                }

                if (TextUtils.isEmpty(Cpassword)) {
                    cpassword.setError("Please Confirm Your Password");
                    return;
                }

                if (TextUtils.isEmpty(Name)) {
                    name.setError("Please your name");
                    return;
                }

                if (Password.equals(Cpassword))
                {
                    fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                                userID= fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference =fStore.collection("User").document(userID);
                                Map<String,Object> User=new HashMap<>();
                                User.put("Fname",Name);
                                User.put("Email",Email);
                                User.put("MobileNo",Phone);
                                User.put("ProfileImage",Profileimageuri);
                                documentReference.set(User).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG,"ON Success : user profile is created for "+userID );
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(),ContactActivity.class));
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "Error!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                else {
                    cpassword.setError("Please enter the same password");
                    return;
                }



            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2000)

        {
            if (resultCode== Activity.RESULT_OK)
            {
                Uri imageuri= data.getData();
                imageView.setImageURI(imageuri);

//                upload image in storage
                StorageReference fileRef= storageReference.child("Profile/profile").child(fAuth.getCurrentUser().getUid());
                fileRef.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(RegisterActivity.this, "Image upload", Toast.LENGTH_SHORT).show();
                          fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                              @Override
                              public void onSuccess(Uri uri) {
                                  Picasso.get().load(uri).into(imageView);
                                  Profileimageuri= uri.toString();

                              }
                          });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}