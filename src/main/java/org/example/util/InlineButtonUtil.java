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

    public static InlineKeyboardMarkup aviaMenu() {

        InlineKeyboardButton button = InlineButtonUtil.button("TASHKENT -> BUXORO", "12_callback", "💳➕");
        List<InlineKeyboardButton> row = InlineButtonUtil.row(button);

        InlineKeyboardButton button1 = InlineButtonUtil.button("TASHKENT -> ANDIJON", "13_callback", "💰");
        List<InlineKeyboardButton> row1 = InlineButtonUtil.row(button1);

        InlineKeyboardButton button2 = InlineButtonUtil.button("TASHKENT -> SAMARQAND", "14_callback", "📤");
        List<InlineKeyboardButton> row2 = InlineButtonUtil.row(button2);

        InlineKeyboardButton button3 = InlineButtonUtil.button("TASHKENT -> NAVOIY", "15_callback", "💲");
        List<InlineKeyboardButton> row3 = InlineButtonUtil.row(button3);

        InlineKeyboardButton button4 = InlineButtonUtil.button("BUXORO -> TOSHKENT", "16_callback", "💼");
        List<InlineKeyboardButton> row4 = InlineButtonUtil.row(button4);

        InlineKeyboardButton button5 = InlineButtonUtil.button("ANDIJON -> TASHKENT", "17_callback", "📉📈");
        List<InlineKeyboardButton> row5 = InlineButtonUtil.row(button5);

        InlineKeyboardButton button6 = InlineButtonUtil.button("SAMARQAND -> TASHKENT  ", "18_callback", "📰");
        List<InlineKeyboardButton> row6 = InlineButtonUtil.row(button6);

        InlineKeyboardButton button7 = InlineButtonUtil.button("NAVOIY -> TASHKENT  ", "19_callback", "📰");
        List<InlineKeyboardButton> row7 = InlineButtonUtil.row(button7);

        List<List<InlineKeyboardButton>> rowList = InlineButtonUtil.rowList(row, row1, row2, row3, row4, row5,row6,row7);
        return keyboard(rowList);
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

        InlineKeyboardButton button6 = InlineButtonUtil.button("BOT haqida", "bot1_callback", "📰");
        List<InlineKeyboardButton> row6 = InlineButtonUtil.row(button6);

        List<List<InlineKeyboardButton>> rowList = InlineButtonUtil.rowList(row, row1, row2, row3, row4, row5,row6);
        return keyboard(rowList);
    }
    public static InlineKeyboardMarkup servisMenu(){

        InlineKeyboardButton button1 = InlineButtonUtil.button("PeyMe Avia", "avia_callback", "✈");
        List<InlineKeyboardButton> row1 = InlineButtonUtil.row(button1);

        InlineKeyboardButton button2 = InlineButtonUtil.button("YXX Jarimalar", "yxx_callback", "\uD83E\uDDFE");
        List<InlineKeyboardButton> row2 = InlineButtonUtil.row(button2);

        InlineKeyboardButton button3 = InlineButtonUtil.button("Xayria", "xayriya_callback", "🗳");
        List<InlineKeyboardButton> row3 = InlineButtonUtil.row(button3);

        List<List<InlineKeyboardButton>> rowList = InlineButtonUtil.rowList( row1, row2, row3);
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
