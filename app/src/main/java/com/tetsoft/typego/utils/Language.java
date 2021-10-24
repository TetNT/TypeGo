package com.tetsoft.typego.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import com.tetsoft.typego.R;
import java.io.Serializable;
import java.util.ArrayList;

public class Language implements Serializable {
    private final String identifier;
    private final int languageNameResourceId;
    private final String name;

    public Language(String identifier, int languageNameResourceId, @NonNull Context context) {
        this.identifier = identifier;
        this.languageNameResourceId = languageNameResourceId;
        name = context.getResources().getString(languageNameResourceId);
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName(Context context) {
        try {
            return context.getResources().getString(getResourceIdByName(context));
        }
        catch (Exception e) {
            Log.i("EX", "Failed to get resource: " + e.getMessage());
            return toString();
        }
    }

    private int getResourceIdByName(Context context) {
        return context.getResources().getIdentifier(identifier, "string", context.getPackageName());
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    /*Returns list of languages available for testing.
      New languages are adding here.
    */
    public static ArrayList<Language> getAvailableLanguages(Context context) {
        ArrayList<Language> languages = new ArrayList<>();
        languages.add(new Language(StringKeys.LANGUAGE_EN, R.string.EN, context));
        languages.add(new Language(StringKeys.LANGUAGE_FR, R.string.FR, context));
        languages.add(new Language(StringKeys.LANGUAGE_DE, R.string.DE, context));
        languages.add(new Language(StringKeys.LANGUAGE_IT, R.string.IT, context));
        languages.add(new Language(StringKeys.LANGUAGE_KR, R.string.KR, context));
        languages.add(new Language(StringKeys.LANGUAGE_RU, R.string.RU, context));
        languages.add(new Language(StringKeys.LANGUAGE_ES, R.string.ES, context));
        languages.add(new Language(StringKeys.LANGUAGE_BG, R.string.BG, context));
        languages.add(new Language(StringKeys.LANGUAGE_UA, R.string.UA, context));
        return languages;
    }

}
