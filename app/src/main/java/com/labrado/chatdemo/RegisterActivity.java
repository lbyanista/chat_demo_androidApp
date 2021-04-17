package com.labrado.chatdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText username, email, password;
    Button register_btn;

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register_btn = findViewById(R.id.register_btn);
        auth = FirebaseAuth.getInstance();

        register_btn.setOnClickListener(v -> {
            String txt_username = Objects.requireNonNull(username.getText()).toString();
            String txt_email = Objects.requireNonNull(email.getText()).toString();
            String txt_password = Objects.requireNonNull(password.getText()).toString();

            if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password))
            {
                Toast.makeText(RegisterActivity.this, "All fileds are required ", Toast.LENGTH_SHORT).show();
            }
            else if (txt_password.length() < 8)
            {
                Toast.makeText(RegisterActivity.this, "password must be at least 8 characters", Toast.LENGTH_SHORT).show();
            }
            else
            {
                register(txt_username, txt_email, txt_password);
            }
        });
    }

    private void register(String username, String email, String password)
    {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                     if (task.isSuccessful()) {
                         FirebaseUser firebaseUser = auth.getCurrentUser();
                         assert firebaseUser != null;
                         String userid =  firebaseUser.getUid();
                         reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                         HashMap<String, String> hashMap = new HashMap<>();
                         hashMap.put("id", userid);
                         hashMap.put("username", username);
                         hashMap.put("imageURL", "default");

                         reference.setValue(hashMap).addOnCompleteListener(task1 -> {
                             if (task1.isSuccessful()){
                                 Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                 startActivity(intent);
                                 finish();
                             }
                         });
                     } else {
                         Toast.makeText(RegisterActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                     }
                });
    }
}