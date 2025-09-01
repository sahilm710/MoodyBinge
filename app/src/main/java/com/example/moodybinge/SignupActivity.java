package com.example.moodybinge;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupActivity extends AppCompatActivity {

    private EditText nameInput, emailInput, passwordInput, confirmPasswordInput;
    private Button signupBtn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        auth = FirebaseAuth.getInstance();

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        signupBtn = findViewById(R.id.signupBtn);
        progressBar = findViewById(R.id.progressBar);

        signupBtn.setOnClickListener(v -> doSignup());
    }

    private void doSignup() {
        String name  = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String pwd   = passwordInput.getText().toString();
        String cpwd  = confirmPasswordInput.getText().toString();

        if (TextUtils.isEmpty(name))  { nameInput.setError("Name required"); return; }
        if (TextUtils.isEmpty(email)) { emailInput.setError("Email required"); return; }
        if (TextUtils.isEmpty(pwd))   { passwordInput.setError("Password required"); return; }
        if (pwd.length() < 6)         { passwordInput.setError("Min 6 chars"); return; }
        if (!pwd.equals(cpwd))        { confirmPasswordInput.setError("Passwords do not match"); return; }

        setLoading(true);
        auth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(task -> {
            setLoading(false);
            if (task.isSuccessful()) {
                FirebaseUser user = auth.getCurrentUser();
                if (user != null) {
                    // ✅ Save the display name into FirebaseAuth profile
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build();
                    user.updateProfile(profileUpdates);
                }

                Toast.makeText(this, "Account created! Please log in.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            } else {
                String msg = task.getException() != null ? task.getException().getMessage() : "Signup failed";
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        signupBtn.setEnabled(!loading);
    }
}
