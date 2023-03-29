package com.example.botdigital.service;

import com.example.botdigital.models.BotSetting;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class MyThread extends Thread {

    private long usedChatId;
    private BotSetting botSetting;

    MyThread(String name,long usedChatId, BotSetting botSetting) {
        super(name);
        this.usedChatId = usedChatId;
        this.botSetting = botSetting;
    }
    MyThread(){
    }

    public void setUsedChatId(long usedChatId) {
        this.usedChatId = usedChatId;
    }
    public void setBotSetting(BotSetting botSetting){
        this.botSetting = botSetting;
    }

    public void stopThread() {
        this.interrupt();
    }

    public void run(){

        while (!Thread.currentThread().isInterrupted()) {
            //System.out.printf("%s started... \n", Thread.currentThread().getName());
            //System.out.printf("%s started... \n", Thread.currentThread().getId());
            long timesleep = 100000;
            long timestart = 32700000; //09:05
            Date date = new Date();

            long timestart1 = botSetting.getTimeTask().toSecondOfDay();
            timestart1=timestart1*1000;

            //System.out.println(date.getTime()%86400000);
            if (date.getTime() % 86400000 + 10800000 < timestart1) {
                timesleep = timestart1 - (date.getTime() % 86400000 + 10800000);
            } else {
                timesleep = 86400000 - (date.getTime() % 86400000 + 10800000) + timestart1;
            }
            //System.out.println(timestart1);
            //System.out.println(botSetting.getFileName());

            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                // System.out.println("Thread has been interrupted");
                this.interrupt();
                return;
            }
            //System.out.printf("%s finished... \n", Thread.currentThread().getName());
            //System.out.printf("%s finished... \n", Thread.currentThread().getId());
            Update document = new Update();
            Message text = new Message();
            Chat chat = new Chat();
            chat.setId(usedChatId);
            //System.out.println(usedChatId);
            text.setText("sending a report");
            text.setChat(chat);
            document.setMessage(text);
            MyTelegramBot documentBot = new MyTelegramBot(botSetting);
            documentBot.onUpdateReceived(document);
        }
    }
}
