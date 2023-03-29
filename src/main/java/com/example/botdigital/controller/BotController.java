package com.example.botdigital.controller;

import com.example.botdigital.models.BotSetting;
import com.example.botdigital.repositories.BotRepository;
import com.example.botdigital.service.BotService;
import com.example.botdigital.service.BotWork;
import com.example.botdigital.service.MyTelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class BotController {
    private final BotService botService;
//    private final List<BotSetting> botSettings;
//    = Stream.of(
//            new BotSetting(1,"telegabot1","rrrtttyyy", false),
//            new BotSetting(2,"telegabot2","yyytttrrr", true),
//            new BotSetting(3,"telegabot3","iiiuuuyyy", false)
//    ).collect(Collectors.toList());

    @GetMapping("/home")
    public String getHomePage(Model model) {
        model.addAttribute("title", "Главная страница!!");
        return "home";
    }
    @GetMapping("/bots")
    public String getBotPage(BotSetting botSetting,Model model) {
        model.addAttribute("title", "Главная страница");
        model.addAttribute("botSettings",botService.list());
        return "bots";
    }
    @GetMapping("/bot-info/{id}")
    public String getPageBotInfo(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("title","bot_"+id);
        model.addAttribute("botSetting", botService.listById(id));
        return "bot-info";
    }
    @PostMapping("/botsetting/create")
    public String createBot(BotSetting botSetting, Model model) throws IOException {
        if (!botService.createBot(botSetting)) {
            model.addAttribute("errorMessage", "Бот с таким токеном: "+botSetting.getTokenbot()+ " уже существует");
            return "/creatingbot";
        }else{
        botService.createBot(botSetting);
        return "redirect:/bots";}
    }
    @GetMapping("/creatingbot")
    public String createBot(Model model) {
        model.addAttribute("title", "Создание бота");
        return "creatingbot";
    }
    @PostMapping("/botsetting/delete/{id}")
    public String deleteBot(@PathVariable ("id") Integer id){
        botService.deleteBotById(id);
        return "redirect:/bots";
    }
    @PostMapping("/botsetting/update/{id}")
    public String updateBotSetting(@PathVariable("id") Integer id){
        botService.updateBotSetting(id);
        Update update = new Update();
        Message text = new Message();
        Chat chat = new Chat();
        if (botService.listById(id).isActive()){
            BotWork botWork = new BotWork();
            botWork.runBot(botService.listById(id));
        }else{
            chat.setId(botService.listById(id).getChatId());
            text.setText("stop bot");
            text.setChat(chat);
            update.setMessage(text);
            MyTelegramBot documentBot = new MyTelegramBot(botService.listById(id));
            documentBot.onUpdateReceived(update);
        }
        return "redirect:/bots";
    }

    @PostMapping("/botsetting/allupdate/{id}")
    public String updateAlltSetting(@PathVariable("id") Integer id, BotSetting botSetting){
        botService.updateAllSetting(id, botSetting);
        return "redirect:/bots";
    }

}
