package com.tetsoft.typego.utils;

import android.content.Context;
import android.content.res.Resources;

import com.tetsoft.typego.R;

public class Dictionary {
    public enum Type {
        BASIC,
        ENHANCED
    }

    Type type;

    public Dictionary(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public String getNameTranslation(Context context) {
        switch(type) {
            case BASIC:
                return context.getString(R.string.basic);
            case ENHANCED:
                return context.getString(R.string.enhanced);
            default:
                return "";
        }
    }
}
