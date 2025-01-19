package org.example.util;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class InlineButtonUtil {

    public static InlineKeyboardButton button(String text, String callBack, String emoji) {
        String emojiText= EmojiParser.parseToUnicode(emoji+" "+text);
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(emojiText);
        button.setCallbackData(callBack);
        return button;
    }

    public static List<InlineKeyboardButton> row(InlineKeyboardButton... buttons) {
        return new LinkedList<>(Arrays.asList(buttons));
    }

    public static List<List<InlineKeyboardButton>> rowList(List<InlineKeyboardButton>... rows) {
        return new LinkedList<>(Arrays.asList(rows));
    }

    public static InlineKeyboardMarkup keyboard(List<List<InlineKeyboardButton>> rows) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup menuSingleKeyboard() {
        InlineKeyboardButton button=InlineButtonUtil.button("MENU", "menu_callback", ":dart:");
        List<InlineKeyboardButton> row = InlineButtonUtil.row(button);
        return InlineButtonUtil.keyboard(InlineButtonUtil.rowList(row));
    }


    public static InlineKeyboardMarkup mainMenu(){
        InlineKeyboardButton button = InlineButtonUtil.button("+ Karta qo'shish", "add_callback", "💳➕");
        List<InlineKeyboardButton> row = InlineButtonUtil.row(button);

        InlineKeyboardButton button1 = InlineButtonUtil.button("Balance", "balance_callback", "💰");
        List<InlineKeyboardButton> row1 = InlineButtonUtil.row(button1);

        InlineKeyboardButton button2 = InlineButtonUtil.button("O'tkazma", "otkazma_callback", "📤");
        List<InlineKeyboardButton> row2 = InlineButtonUtil.row(button2);

        InlineKeyboardButton button3 = InlineButtonUtil.button("To'lov", "tolov_callback", "💲");
        List<InlineKeyboardButton> row3 = InlineButtonUtil.row(button3);

        InlineKeyboardButton button4 = InlineButtonUtil.button("Servislar", "servis_callback", "💼");
        List<InlineKeyboardButton> row4 = InlineButtonUtil.row(button4);

        InlineKeyboardButton button5 = InlineButtonUtil.button("Kirim-Chiqim", "monitoring_callback", "📉📈");
        List<InlineKeyboardButton> row5 = InlineButtonUtil.row(button5);

        List<List<InlineKeyboardButton>> rowList = InlineButtonUtil.rowList(row, row1, row2, row3, row4, row5);
        return keyboard(rowList);
    }
    public static InlineKeyboardMarkup servisMenu(){
        InlineKeyboardButton button = InlineButtonUtil.button("Kredit", "add_callback", "📥💰");
        List<InlineKeyboardButton> row = InlineButtonUtil.row(button);

        InlineKeyboardButton button1 = InlineButtonUtil.button("PeyMe Avia", "balance_callback", "✈");
        List<InlineKeyboardButton> row1 = InlineButtonUtil.row(button1);

        InlineKeyboardButton button2 = InlineButtonUtil.button("YXX Jarimalar", "otkazma_callback", "\uD83E\uDDFE");
        List<InlineKeyboardButton> row2 = InlineButtonUtil.row(button2);

        InlineKeyboardButton button3 = InlineButtonUtil.button("Xayria", "tolov_callback", "🗳");
        List<InlineKeyboardButton> row3 = InlineButtonUtil.row(button3);

        List<List<InlineKeyboardButton>> rowList = InlineButtonUtil.rowList(row, row1, row2, row3);
        return keyboard(rowList);

    }
    public static InlineKeyboardMarkup tolovMenu(){
        InlineKeyboardButton button = InlineButtonUtil.button("Mobile operatorlarga tolov", "mobile_callback", "☎");
        List<InlineKeyboardButton> row = InlineButtonUtil.row(button);

        InlineKeyboardButton button1 = InlineButtonUtil.button("Kamunal tolov", "kamunal_callback", "📃");
        List<InlineKeyboardButton> row1 = InlineButtonUtil.row(button1);

        InlineKeyboardButton button2 = InlineButtonUtil.button("Ta'lim uchun", "talim_callback", "💼");
        List<InlineKeyboardButton> row2 = InlineButtonUtil.row(button2);

        List<List<InlineKeyboardButton>> rowList = InlineButtonUtil.rowList(row, row1, row2);
        return keyboard(rowList);
    }
}
