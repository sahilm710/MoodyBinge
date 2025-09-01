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

public class ChangePasswordFragment extends Fragment {

    private EditText currentPass, newPass, confirmPass;
    private Button updatePassBtn;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        // 🔹 Setup Toolbar with Back Button
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Change Password");
        toolbar.setNavigationIcon(R.drawable.ic_back); // Use the same ic_back.xml drawable
        toolbar.setNavigationOnClickListener(v -> requireActivity()
                .getSupportFragmentManager()
                .popBackStack());

        // 🔹 Initialize views
        currentPass = view.findViewById(R.id.currentPass);
        newPass = view.findViewById(R.id.newPass);
        confirmPass = view.findViewById(R.id.confirmPass);
        updatePassBtn = view.findViewById(R.id.updatePassBtn);

        // 🔹 Button click logic
        updatePassBtn.setOnClickListener(v -> {
            String current = currentPass.getText().toString().trim();
            String newPassword = newPass.getText().toString().trim();
            String confirm = confirmPass.getText().toString().trim();

            if (current.isEmpty() || newPassword.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (!newPassword.equals(confirm)) {
                Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                // TODO: Update password with Firebase/Auth
                Toast.makeText(getContext(), "Password updated!", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack(); // Go back
            }
        });

        return view;
    }
}
