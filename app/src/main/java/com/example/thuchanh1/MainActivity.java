package com.example.thuchanh1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText email, password;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        // KHAI BAO
        auth = FirebaseAuth.getInstance();


        email = (EditText) findViewById(R.id.textEmail);
        password = (EditText) findViewById(R.id.textPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        // SU KIEN NUT LOGIN
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valid()) {
                    // LAY TEXT TU PLANTEXT SO SANH VOI TAI KHOAN CO SAN
                    String emailValue = email.getText().toString().trim();
                    String passvalue = password.getText().toString().trim();

                    auth.signInWithEmailAndPassword(emailValue, passvalue)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(MainActivity.this, DataActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(MainActivity.this, "????ng nh???p kh??ng th??nh c??ng: "
                                                + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
    private boolean valid() {
        String emailValue = email.getText().toString().trim();
        String passvalue = password.getText().toString().trim();

        if (emailValue.equals("")) {
            email.setError("Email kh??ng ???????c ????? tr???ng");
            return false;
        }
        if (passvalue.equals("")) {
            password.setError("M???t kh???u kh??ng ???????c ????? tr???ng");
            return false;
        }

        return true;
    }
}