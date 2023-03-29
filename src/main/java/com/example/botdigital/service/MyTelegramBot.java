package com.example.botdigital.service;

import com.example.botdigital.models.BotSetting;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;


public class MyTelegramBot extends TelegramLongPollingBot {


        private Update update;
        private long usedChatId;
        public static ArrayList<String> workChats = new ArrayList<>();
        private MyThread thread = new MyThread();
        private int botId;
        private BotSetting botSetting;


    public MyTelegramBot(BotSetting botSetting) {
        this.botSetting = botSetting;
    }

        @Override
        public String getBotUsername(){
          return botSetting.getName();
        }
        @Override
        public String getBotToken(){
            return botSetting.getTokenbot();
        }

        @Override
        public void onRegister() {
            super.onRegister();
        }

        /**
         * This method is called when receiving updates via GetUpdates method
         *
         * @param update Update received
         */
        @Override
        public void onUpdateReceived(Update update) {
            usedChatId = update.getMessage().getChatId();
        /*if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Создайте объект SendMessage с обязательными полями
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText("Передаем файл");
            try {
                execute(message); // Отправляем сообщение
            } catch (TelegramApiException e) {e.printStackTrace();}
        }*/
            if (update.getMessage().getText().equalsIgnoreCase("start bot")) {
                SendMessage message = new SendMessage();
                message.setChatId(update.getMessage().getChatId().toString());
                message.setText("Я телеграм-бот, я ежедневно буду присылать вам отчёт. " +
                        "Если в этом нет необходимости наберите: stop bot");
                //if (!workChats.contains(update.getMessage().getChatId().toString()))
                if (workChats.size()==0) {
                    workChats.add(update.getMessage().getChatId().toString());
                    thread.setName("Thread " + update.getMessage().getChatId().toString());
                    thread.setUsedChatId(usedChatId);
                    thread.setBotSetting(botSetting);
                    if (thread.isInterrupted()) {
                        thread = new MyThread("Thread " + update.getMessage().getChatId().toString(), usedChatId, botSetting);
                        thread.start();
                    }else {
                        thread.start();
                    }
                } else {
                    message.setText("Я уже работаю.");
                }
                try {
                    execute(message); // Отправляем сообщение
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if (update.getMessage().getText().equalsIgnoreCase("stop bot")) {
                if (workChats.contains(update.getMessage().getChatId().toString())) {
                    SendMessage message = new SendMessage();
                    message.setChatId(update.getMessage().getChatId().toString());
                    message.setText("Ну, пока. Я отключаюсь.");
                    try {
                        execute(message);// Отправляем сообщение
                        if (thread.isAlive()) {
                            thread.stopThread();
                            workChats.clear();
                        }
                        //System.exit(0);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (update.getMessage().getText().equals("sending a report")) {
                //PathDocument pathDocument = new PathDocument(botSetting);
                LocalDate date = LocalDate.now().minusDays(1);
                int dayOfMonth = date.getDayOfMonth();
                int year = date.getYear();
                int month = date.getMonthValue();
                String dataFormat = String.format("Отчет за %d.%d.%d \n", dayOfMonth, month, year);
                try {
                   sendDocument(update.getMessage().getChatId().toString(),
                            dataFormat,
                           FileUtils.getFile(botSetting.getFileName()));

                           // FileUtils.getFile(pathDocument.getPathDocument()));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

        public void sendDocument(String chatId, String caption, File sendFile) throws TelegramApiException {
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(chatId);
            sendDocument.setCaption(caption);
            sendDocument.setDocument(new InputFile(sendFile));
            execute(sendDocument);

        }


}
