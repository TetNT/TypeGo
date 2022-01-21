package com.tetsoft.typego.data;

import java.util.Random;

public class Emoji {
    // emojis that are used in results
    public static final int COLLISION = 0x1F4A5;
    public static final int HUNDRED_POINTS = 0x1F4AF;
    public static final int OK_HAND = 0x1F44C;
    public static final int THUMBS_UP = 0x1F44D;
    public static final int FLEXED_BICEPS = 0x1F4AA;
    public static final int BABY = 0x1F476;
    public static final int POLICE_CAR = 0x1F694;
    public static final int ROCKET = 0x1F680;
    public static final int UFO = 0x1F6F8;
    public static final int FIRE = 0x1F525;
    public static final int MEDAL = 0x1F3C5;
    public static final int CROWN = 0x1F451;
    public static final int MOAI = 0x1F5FF;

    public static String getEmoji(int unicode) {
        try {
            return new String(Character.toChars(unicode));
        } catch (Exception e) {
            return "";
        }
    }
}
