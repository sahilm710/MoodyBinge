package com.example.moodybinge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class EditProfileFragment extends Fragment {

    private EditText editName, editEmail;
    private Button saveBtn;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        // 🔹 Setup Toolbar with Back Button
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Profile");
        toolbar.setNavigationIcon(R.drawable.ic_back); // Add ic_back.xml in res/drawable
        toolbar.setNavigationOnClickListener(v -> requireActivity()
                .getSupportFragmentManager()
                .popBackStack());

        // 🔹 Initialize views
        editName = view.findViewById(R.id.editName);
        editEmail = view.findViewById(R.id.editEmail);
        saveBtn = view.findViewById(R.id.saveBtn);

        // 🔹 Save button logic
        saveBtn.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String email = editEmail.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // TODO: Save user details (Firebase or SharedPreferences)
                Toast.makeText(getContext(), "Profile updated!", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack(); // Go back
            }
        });

        return view;
    }
}
