package com.example.bookinginternationalcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class SignUp extends AppCompatActivity {
    EditText texname, textEmail ,textPhone,textpass;
    Button register;
    TextView imageView21;
    FirebaseAuth fAuth;
    TextView loginhere;
    ProgressBar progressBar;
    //DatabaseReference reff;
    //Guest member;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        texname = findViewById(R.id.name);
        textEmail =findViewById(R.id.Email);
        textPhone =findViewById(R.id.Phone);
        textpass =  findViewById(R.id.Password);
        loginhere = findViewById(R.id.textView4);
        imageView21 = findViewById(R.id.textView17);
        register = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);
        //reff = FirebaseDatabase.getInstance().getReference().child("Guest");
        /*member = new Guest();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                member.setName(texname.getText().toString().trim());
                member.setEmail(textEmail.getText().toString().trim());
                member.setPhone(textPhone.getText().toString().trim());
                reff.push().setValue(member);
                Toast.makeText(signup.this,"data inserted successfully",Toast.LENGTH_LONG).show();
            }
        });*/

        //assigning variables




        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),SignUp.class));
            finish();
        }

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = textEmail.getText().toString().trim();
                String password = textpass.getText().toString().trim();
                String phone = textPhone.getText().toString().trim();
                String name = texname.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    texname.setError("Name required");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    textPhone.setError("Phone Required");
                    return;
                }
                if(phone.length()!=10){
                    textPhone.setError("Phone number must be ten");
                    return;
                }


                if (TextUtils.isEmpty(email)) {
                    textEmail.setError("email required");
                    return;

                }
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(textEmail.getText().toString()).matches()){
                    textEmail.setError("Enter Valid email");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    textpass.setError("password is empty");
                    return;

                }
                if (password.length() < 6) {
                    textpass.setError("password must be 6 characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                // register user
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "user created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Login.class));
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(SignUp.this, "error occured" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });
            }
        });
        loginhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));

            }
        });
    }

}