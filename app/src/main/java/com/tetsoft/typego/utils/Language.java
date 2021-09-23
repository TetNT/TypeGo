package com.tetsoft.typego.utils;

import android.content.Context;
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
        return context.getResources().getString(languageNameResourceId);
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
        languages.add(new Language(StringKeys.LANGUAGE_EN, R.string.english, context));
        languages.add(new Language(StringKeys.LANGUAGE_FR, R.string.french, context));
        languages.add(new Language(StringKeys.LANGUAGE_DE, R.string.german, context));
        languages.add(new Language(StringKeys.LANGUAGE_IT, R.string.italian, context));
        languages.add(new Language(StringKeys.LANGUAGE_KR, R.string.korean, context));
        languages.add(new Language(StringKeys.LANGUAGE_RU, R.string.russian, context));
        languages.add(new Language(StringKeys.LANGUAGE_ES, R.string.spanish, context));
        return languages;
    }

}
