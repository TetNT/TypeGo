package com.example.typego.utils;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.example.typego.R;

import java.io.Serializable;
import java.util.ArrayList;

public class Language implements Serializable {
    private final String identifier;
    private final String name;

    public Language(String identifier, String name) {
        this.identifier = identifier;
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }


     /*Returns list of languages available for testing.
       New languages are adding here. */
    public static ArrayList<Language> getAvailableLanguages(Context context) {
        ArrayList<Language> languages = new ArrayList<>();
        Resources resources = context.getResources();
        languages.add(new Language(StringKeys.LANGUAGE_EN, resources.getString(R.string.english)));
        languages.add(new Language(StringKeys.LANGUAGE_RU, resources.getString(R.string.russian)));
        return languages;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
