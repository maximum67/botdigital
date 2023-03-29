package com.example.botdigital.service;


import com.example.botdigital.models.BotSetting;

import java.io.FileReader;
import java.io.IOException;

import static java.lang.String.valueOf;

public class PathDocument {
//    private String path ="";
    private BotSetting botSetting;
    PathDocument(BotSetting botSetting){
        this.botSetting = botSetting;
    }

    public String getPathDocument() {
//       path ="";
//        try (FileReader reader = new FileReader("notes4.txt")) {
//            int c;
//            while ((c = reader.read()) != -1) {
//                path = path + valueOf((char) c);
//            }
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        }
        return botSetting.getFileName();
    }
}