package com.example.moodybinge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        ImageView aboutLogo = rootView.findViewById(R.id.aboutLogo);

// Load the glow pulse animation
        Animation glowPulse = AnimationUtils.loadAnimation(getContext(), R.anim.pulse);

// Start the animation on the logo (with background glow)
        aboutLogo.startAnimation(glowPulse);


        return rootView;
    }
}
