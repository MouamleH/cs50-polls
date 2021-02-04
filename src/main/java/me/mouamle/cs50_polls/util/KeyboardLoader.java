package me.mouamle.cs50_polls.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KeyboardLoader {

    private static final List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

    public static void loadStaticKeyboard() {
        for (String name : Projects.getNames()) {
            final InlineKeyboardButton button = InlineKeyboardButton.builder().text(name).callbackData(name).build();
            keyboard.add(Collections.singletonList(button));
        }
    }

    public static List<List<InlineKeyboardButton>> getKeyboard() {
        return keyboard;
    }

}
