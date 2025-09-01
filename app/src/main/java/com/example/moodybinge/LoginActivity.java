package com.example.moodybinge;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginBtn, goToSignupBtn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        auth = FirebaseAuth.getInstance();

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginBtn = findViewById(R.id.loginBtn);
        goToSignupBtn = findViewById(R.id.signupBtn);
        progressBar = findViewById(R.id.progressBar);

        loginBtn.setOnClickListener(v -> doLogin());
        goToSignupBtn.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, SignupActivity.class)));
    }

    private void doLogin() {
        String email = emailInput.getText().toString().trim();
        String pwd   = passwordInput.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailInput.setError("Email required");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            passwordInput.setError("Password required");
            return;
        }

        setLoading(true);
        auth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(task -> {
            setLoading(false);
            if (task.isSuccessful()) {
                Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                String msg = task.getException() != null
                        ? task.getException().getMessage()
                        : "Login failed";
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        loginBtn.setEnabled(!loading);
        goToSignupBtn.setEnabled(!loading);
    }
}
