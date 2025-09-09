package com.example.moodybinge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountFragment extends Fragment {

    private TextView userName, userEmail;
    private Button editProfileBtn, changePasswordBtn, logoutBtn;
    private FirebaseAuth auth;

    public AccountFragment() {
        // Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        // 🔹 Initialize Firebase
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        // 🔹 Initialize views
        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        editProfileBtn = view.findViewById(R.id.editProfileBtn);
        changePasswordBtn = view.findViewById(R.id.changePasswordBtn);
        logoutBtn = view.findViewById(R.id.logoutBtn);

        // 🔹 Load from SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String savedName = prefs.getString("name", "No Name");
        String savedEmail = prefs.getString("email", null);

        // 🔹 Show email (prefer SharedPreferences, fallback to Firebase)
        if (savedEmail != null) {
            userEmail.setText(savedEmail);
        } else if (user != null && user.getEmail() != null) {
            userEmail.setText(user.getEmail());
        } else {
            userEmail.setText("No Email");
        }

        // 🔹 Show name (from SharedPreferences only)
        userName.setText(savedName);

        // 🔹 Open Edit Profile Fragment
        editProfileBtn.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new EditProfileFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // 🔹 Open Change Password Fragment
        changePasswordBtn.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ChangePasswordFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // 🔹 Logout
        logoutBtn.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            requireActivity().finish();
        });

        return view;
    }
}
