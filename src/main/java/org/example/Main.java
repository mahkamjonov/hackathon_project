package org.example;

import org.example.repo.DataBase;
import org.example.temp.Temp;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class Main {

    public static void main(String[] args) throws TelegramApiException {

        DataBase dataBase = Temp.dataBase;
        dataBase.addCard();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new MyBot());

    }

}