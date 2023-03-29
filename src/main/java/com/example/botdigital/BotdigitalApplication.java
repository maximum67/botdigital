package com.example.botdigital;

import com.example.botdigital.models.BotSetting;
import com.example.botdigital.repositories.BotRepository;
import com.example.botdigital.service.BotService;
import com.example.botdigital.service.BotWork;
import com.example.botdigital.service.StartBots;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BotdigitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotdigitalApplication.class, args);
       }
}
