package com.example.botdigital.service;

import com.example.botdigital.models.BotSetting;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class BotWork {

    private BotSetting botSetting;


    public void runBot(BotSetting botSetting){
        this.botSetting = botSetting;

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MyTelegramBot(botSetting));
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }

    }


}
