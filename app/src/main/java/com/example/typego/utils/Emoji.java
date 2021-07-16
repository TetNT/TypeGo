package com.example.typego.utils;

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

    public static String getEmojiOfWpm(int WPM) {
        StringBuilder emojiSet = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            if (WPM <= 15) emojiSet.append(getEmoji(BABY));
            else if (WPM<=20) emojiSet.append(getEmoji(OK_HAND));
            else if (WPM<=30) emojiSet.append(getEmoji(THUMBS_UP));
            else if (WPM<=35) emojiSet.append(getEmoji(FLEXED_BICEPS));
            else if (WPM<=45) emojiSet.append(getRandomEmoji());
            else if (WPM<=50) emojiSet.append(getEmoji(MEDAL));
            else if (WPM<=55) emojiSet.append(getEmoji(CROWN));
            else if (WPM<=60) emojiSet.append(getEmoji(MOAI));
            else if (WPM<=65) emojiSet.append(getEmoji(POLICE_CAR));
            else if (WPM<=70) emojiSet.append(getEmoji(UFO));
            else if (WPM>=100) emojiSet.append(getEmoji(HUNDRED_POINTS));
            else emojiSet.append(getEmoji(MOAI));
        }
        return emojiSet.toString();
    }

    public static String getRandomEmoji() {
        Random rnd = new Random();
        int pick = rnd.nextInt(3);
        switch (pick) {
            case 0:
                return getEmoji(FIRE);
            case 1:
                return getEmoji(COLLISION);
            default:
                return getEmoji(ROCKET);
        }
    }
}
