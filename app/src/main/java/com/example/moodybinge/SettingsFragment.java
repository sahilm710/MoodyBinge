package com.example.moodybinge;

import android.os.Bundle;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        // 🔹 Find the language preference (make sure key = "language" in preferences.xml)
        ListPreference languagePref = findPreference("language");

        if (languagePref != null) {
            // Set current saved language as selected
            String currentLang = LocaleHelper.getSavedLanguage(requireContext());
            languagePref.setValue(currentLang);

            // 🔹 Handle language change
            languagePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String selectedLang = (String) newValue;

                    // Save & apply language
                    LocaleHelper.setLocale(requireContext(), selectedLang);

                    // Restart activity so changes take effect
                    requireActivity().recreate();

                    return true;
                }
            });

        }
    }
}
