package com.tetsoft.typego.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.ArrayList;

public class Language implements Serializable {
    private final String identifier;
    private final String name;

    public Language(String identifier, @NonNull Context context) {
        this.identifier = identifier;
        name = context.getString(getResourceIdByName(context));
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName(Context context) {
        try {
            return context.getString(getResourceIdByName(context));
        }
        catch (Resources.NotFoundException e) {
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
        languages.add(new Language(StringKeys.LANGUAGE_EN, context));
        languages.add(new Language(StringKeys.LANGUAGE_FR, context));
        languages.add(new Language(StringKeys.LANGUAGE_DE, context));
        languages.add(new Language(StringKeys.LANGUAGE_IT, context));
        languages.add(new Language(StringKeys.LANGUAGE_KR, context));
        languages.add(new Language(StringKeys.LANGUAGE_RU, context));
        languages.add(new Language(StringKeys.LANGUAGE_ES, context));
        languages.add(new Language(StringKeys.LANGUAGE_BG, context));
        languages.add(new Language(StringKeys.LANGUAGE_UA, context));
        return languages;
    }

}
