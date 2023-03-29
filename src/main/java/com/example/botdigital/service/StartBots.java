package com.example.botdigital.service;

import com.example.botdigital.models.BotSetting;
import com.example.botdigital.repositories.BotRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StartBots implements ApplicationRunner {

    private BotWork botWork = new BotWork();
    private final BotRepository botRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (BotSetting bot : new BotService(botRepository).list()){
            if (bot.isActive()){
             //  botWork.runBot(bot);
            }
        }
    }
}
