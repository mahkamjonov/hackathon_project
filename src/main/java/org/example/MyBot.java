package org.example;

import org.example.entity.CardEntity;
import org.example.entity.TolovEntity;
import org.example.entity.TransferEntity;
import org.example.entity.USerEntity;
import org.example.enums.Step;
import org.example.repo.DataBase;
import org.example.temp.Temp;
import org.example.util.HashMapUtil;
import org.example.util.InlineButtonUtil;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MyBot extends TelegramLongPollingBot {

    private DataBase dataBase = Temp.dataBase;

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {

            Message message = update.getMessage();
            System.out.print(message.getChatId());
            System.out.println("   " + message.getFrom().getUserName());
            if (message.hasText()) {
                messageHandler(message);
            }
        } else if (update.hasCallbackQuery()) {
            Message message = (Message) update.getCallbackQuery().getMessage();
            User user = update.getCallbackQuery().getFrom();
            String text = update.getCallbackQuery().getData(); // kirim_calback
            callBackHandler(message, user, text);
        }

    }

    @Override
    public String getBotUsername() {
        return "t.me/NAI_PAY_bot";
    }

    @Override
    public String getBotToken() {
        return "7701179353:AAGvXx349YVr8srmhScqp6fapnzX70F6pbg";
    }

    public void send(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    private void messageHandler(Message message) {
        String text = message.getText();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());

        if (text.equals("/start")) {
            sendMessage.setText(message.getFrom().getFirstName() + " menu tanlang ");

            sendMessage.setReplyMarkup(InlineButtonUtil.mainMenu());
            send(sendMessage);

        } else if (HashMapUtil.hashMap.get(message.getChatId()) != null || HashMapUtil.transferHashMap.get(message.getChatId()) != null || HashMapUtil.tolovHashMap.get(message.getChatId()) != null) {
            handleText(message);
        }
    }

    private void handleText(Message message) {
        USerEntity entity1 = HashMapUtil.hashMap.get(message.getChatId());
        TransferEntity entity2 = HashMapUtil.transferHashMap.get(message.getChatId());
        TolovEntity tolovEntity = HashMapUtil.tolovHashMap.get(message.getChatId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());

        if (entity1 != null && entity1.getStep().equals(Step.ADD_CARD)) {
            if (dataBase.findCard(message.getText(), message.getChatId())) {
                sendMessage.setText("Karta qo'shildi 💳✔");
                HashMapUtil.hashMap.remove(message.getChatId());
            } else {
                sendMessage.setText("Karta topilmadi 💳❌");
            }
            send(sendMessage);
        } else if (entity2 != null && entity2.getStep().equals(Step.CARD_NUMBER)) {
            CardEntity card = dataBase.getCardByCardNumber(message.getText());
            if (card != null) {
                sendMessage.setText("Summani kiriting 💵");
                entity2.setStep(Step.AMOUNT);
                entity2.setGetterCardNumber(message.getText());
                HashMapUtil.transferHashMap.put(message.getChatId(), entity2);
            } else {
                sendMessage.setText("Karta topilmadi 💳❌");
            }
            send(sendMessage);
        } else if (entity2 != null && entity2.getStep().equals(Step.AMOUNT)) {
            CardEntity senderCard = dataBase.getCardByUserId(message.getChatId());
            CardEntity getterCard = dataBase.getCardByCardNumber(entity2.getGetterCardNumber());
            double amount = Double.parseDouble(message.getText());
            if (senderCard.getBalance() > amount) {
                sendMessage.setText("Pul o'tkazildi✔");
                entity2.setSenderCardNumber(senderCard.getNumber());
                entity2.setAmount(amount);
                entity2.setDate(LocalDateTime.now().toString().substring(0, 19));
                getterCard.setBalance(getterCard.getBalance() + amount);
                amount += (amount / 100);
                senderCard.setBalance(senderCard.getBalance() - amount);
                dataBase.saveTransfer(senderCard);
                dataBase.saveTransfer(getterCard);
                dataBase.setTransfer(entity2);
                HashMapUtil.transferHashMap.remove(message.getChatId());
            }

        } else if (tolovEntity != null && tolovEntity.getStep().equals(Step.TOLOV_PHONE_NUMBER)) {
            boolean checkPhoneNumber = checkPhoneNumber(message);
            if (checkPhoneNumber) {
                tolovEntity.setStep(Step.MOBILE_OPERATOR_AMOUNT);
                tolovEntity.setPhoneNumber(message.getText());
                HashMapUtil.tolovHashMap.put(message.getChatId(), tolovEntity);

                sendMessage.setText("Summa kiriting💸");
            } else {
                sendMessage.setText("Raqam notog'ri kiritildi, qayta kiriting❌📞");
            }
            send(sendMessage);
        } else if (tolovEntity != null && tolovEntity.getStep().equals(Step.MOBILE_OPERATOR_AMOUNT)) {
            CardEntity senderCard = dataBase.getCardByUserId(message.getChatId());

            double amount = Double.parseDouble(message.getText());

            if (senderCard.getBalance() > amount) {
                sendMessage.setText("Mobile operator to'lovi muvaffaqiyatli amalga oshirildi✔");
                tolovEntity.setSenderCardNumber(senderCard.getNumber());
                tolovEntity.setAmount(amount);
                tolovEntity.setDate(LocalDateTime.now().toString().substring(0, 19));

                amount += (amount / 100);
                senderCard.setBalance(senderCard.getBalance() - amount);

                dataBase.setMobileOperator(tolovEntity);
                dataBase.saveTransfer(senderCard);
                HashMapUtil.tolovHashMap.remove(message.getChatId(), tolovEntity);
            } else {
                sendMessage.setText("Mablag' yetarli emas❌");
            }
            send(sendMessage);
        }
    }

    public void callBackHandler(Message message, User user, String text) {
        if (text.equals("add_callback")) {
            addNewCard(message);
        } else if (text.equals("balance_callback")) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText("Balance💰: " + dataBase.getBalance(message.getChatId()));
            send(sendMessage);
        } else if (text.equals("otkazma_callback")) {
            transfer(message);
        } else if (text.equals("monitoring_callback")) {
            monitoring(message);
        } else if (text.equals("servis_callback")) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText("Menu tanlang");
            sendMessage.setReplyMarkup(InlineButtonUtil.servisMenu());
            send(sendMessage);

        } else if (text.equals("avia_callback")) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText("MANZILNI TANLANG VA CHIPTA SOTIB OLING");
            sendMessage.setReplyMarkup(InlineButtonUtil.aviaMenu());
            send(sendMessage);

        } else if (text.equals("tolov_callback")) {
            tolov(message);
        } else if (text.equals("mobile_callback")) {
            mobileTolov(message);
        } else if (text.equals("kamunal_callback")) {

            komunalTolov(message);

        } else if (text.equals("12_callback")) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText("TOSHKENT dan BUXOROGA chiptalar✈\n \n https://www.aviasales.uz/search/TAS2001BHK21011?marker=16022.UZ_TAStoAZN.secondsearch&origin_iata=TAS&destination_iata=AZN&depart_date=2025-01-19&return_date=&oneway=true&adults=1&children=0&infants=0&trip_class=0&currency=uzs&locale=ru&market=uz ");

        } else if (text.equals("13_callback")) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText("TOSHKENT dan ANDIJONGA chiptalar✈\n \nhttps://www.aviasales.uz/search/TAS1901AZN1?marker=16022.UZ_TAStoAZN.secondsearch&origin_iata=TAS&destination_iata=AZN&depart_date=2025-01-19&return_date=&oneway=true&adults=1&children=0&infants=0&trip_class=0&currency=uzs&locale=ru&market=uz");

        } else if (text.equals("14_callback")) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText("TOSHKENT dan SAMARQANDGA chiptalar✈\n\nhttps://www.aviasales.uz/search/TAS2001SKD21011?marker=16022.UZ_TAStoAZN.secondsearch&origin_iata=TAS&destination_iata=AZN&depart_date=2025-01-19&return_date=&oneway=true&adults=1&children=0&infants=0&trip_class=0&currency=uzs&locale=ru&market=uz");


        } else if (text.equals("15_callback")) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText("TOSHKENT dan SAMARQANDGA chiptalar✈\n\nhttps://www.aviasales.uz/search/TAS2001NVI21011?marker=16022.UZ_TAStoAZN.secondsearch&origin_iata=TAS&destination_iata=AZN&depart_date=2025-01-19&return_date=&oneway=true&adults=1&children=0&infants=0&trip_class=0&currency=uzs&locale=ru&market=uz");

        } else if (text.equals("16_callback")) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText("BUXORO dan TOSHKENTGA chiptalar✈\n\n https://www.aviasales.uz/search/BHK2001TAS21011?marker=16022.UZ_TAStoAZN.secondsearch&origin_iata=TAS&destination_iata=AZN&depart_date=2025-01-19&return_date=&oneway=true&adults=1&children=0&infants=0&trip_class=0&currency=uzs&locale=ru&market=uz ");

        } else if (text.equals("17_callback")) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText("ANDIJON dan TOSHKENTGA chiptalar✈\n\n https://www.aviasales.uz/search/AZN2001TAS21011?marker=16022.UZ_TAStoAZN.secondsearch&origin_iata=TAS&destination_iata=AZN&depart_date=2025-01-19&return_date=&oneway=true&adults=1&children=0&infants=0&trip_class=0&currency=uzs&locale=ru&market=uz");

        } else if (text.equals("18_callback")) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText("SAMARQAND dan TOSHKENTGA chiptalar✈\n\n https://www.aviasales.uz/search/SKD2001TAS21011?marker=16022.UZ_TAStoAZN.secondsearch&origin_iata=TAS&destination_iata=AZN&depart_date=2025-01-19&return_date=&oneway=true&adults=1&children=0&infants=0&trip_class=0&currency=uzs&locale=ru&market=uz");

        } else if (text.equals("19_callback")) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText("NAVOIY dan TOSHKENTGA chiptalar✈\n\n https://www.aviasales.uz/search/NVI2001TAS21011?marker=16022.UZ_TAStoAZN.secondsearch&origin_iata=TAS&destination_iata=AZN&depart_date=2025-01-19&return_date=&oneway=true&adults=1&children=0&infants=0&trip_class=0&currency=uzs&locale=ru&market=uz");

        } else if (text.equals("talim_callback")) {
            talimTolov(message);
        } else if (text.equals("bot1_callback")) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText("Bu botda\n◾pul otkazish\n◾telefonga pul o'tkazish\n◾xayriya qilish\n◾jarimalaringizni to'lash\nva boshqa xizmatlardan foydalanishingiz mumkin⚙");
            send(sendMessage);
        }
    }

    private void tolov(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Menu tanlang");
        sendMessage.setReplyMarkup(InlineButtonUtil.tolovMenu());
        send(sendMessage);


    }

    private void talimTolov(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        TolovEntity entity = new TolovEntity();
        entity.setUserId(message.getChatId());
        entity.setStep(Step.TALIM_ID);
        sendMessage.setText("invoice raqam kiriting");
        HashMapUtil.tolovHashMap.put(message.getChatId(), entity);
        send(sendMessage);

    }

    private void komunalTolov(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());

        TolovEntity tolov = new TolovEntity();
        tolov.setUserId(message.getChatId());
        tolov.setStep(Step.KOMUNAL_ID);
        sendMessage.setText("Komunal id kiriting🆔");
        HashMapUtil.tolovHashMap.put(message.getChatId(), tolov);
        send(sendMessage);
    }

    private void mobileTolov(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());

        TolovEntity tolov = new TolovEntity();

        tolov.setUserId(message.getChatId());
        tolov.setStep(Step.TOLOV_PHONE_NUMBER);
        HashMapUtil.tolovHashMap.put(message.getChatId(), tolov);
        sendMessage.setText("Telefon raqam kiriting📞 +998 xx xxx-xx-xx");
        send(sendMessage);
    }

    private void monitoring(Message message) {
        ArrayList<TransferEntity> transferStory = dataBase.getMonitoring(message.getChatId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        StringBuilder builder = new StringBuilder();
        builder.append(" __--* Story *--__");
        for (TransferEntity entity : transferStory) {
            builder.append("\n--------------------");
            builder.append("\n Sender Card📤: " + entity.getSenderCardNumber());
            builder.append("\n Getter Card📥: " + entity.getGetterCardNumber());
            builder.append("\n Amount💸: " + entity.getAmount());
            builder.append("\n Date⏰: " + entity.getDate());
            builder.append("\n--------------------");
        }
        sendMessage.setText(builder.toString());
        send(sendMessage);
    }

    private void transfer(Message message) {
        TransferEntity entity = new TransferEntity();
        entity.setUserId(message.getChatId());
        entity.setStep(Step.CARD_NUMBER);
        HashMapUtil.transferHashMap.put(message.getChatId(), entity);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Karta raqam kiriting💳");
        send(sendMessage);
    }

    private void addNewCard(Message message) {
        USerEntity entity = new USerEntity();
        entity.setId(message.getChatId());
        entity.setStep(Step.ADD_CARD);
        HashMapUtil.hashMap.put(message.getChatId(), entity);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Karta raqam kiriting💳");
        send(sendMessage);
    }

    private boolean checkPhoneNumber(Message message) {
        String[] validCodes = {"90", "91", "93", "94", "95", "97", "98", "99", "33", "88"};

        if (!message.getText().startsWith("+998")) {
            return false;
        }

        if (message.getText().length() != 17) {
            return false;
        }

        for (String validCode : validCodes) {
            if (validCode.equals(message.getText().substring(5, 7))) {
                return true;
            }
        }

        return false;
    }


}
