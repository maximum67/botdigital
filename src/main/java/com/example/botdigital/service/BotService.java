package com.example.botdigital.service;

import com.example.botdigital.models.BotSetting;
import com.example.botdigital.repositories.BotRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor

public class BotService {
    private final BotRepository botRepository;
    //private final PasswordEncoder passwordEncoder;

    public boolean createBot(BotSetting botSetting) {
        String botSettingToken = botSetting.getTokenbot();
        if (botRepository.findByTokenbot(botSettingToken) != null) return false;
        botSetting.setActive(false);
      //botSetting.setTokenbot(passwordEncoder.encode(botSetting.getTokenbot()));

          log.info("Saving new Bot with name: {}", botSettingToken);
          botRepository.save(botSetting);
          return true;
    }
    public List<BotSetting> list() {
        return botRepository.findAll();
    }

    public BotSetting listById(Integer id) {
        return botRepository.findById(id);
    }

    public void deleteBotById(Integer id){
        botRepository.deleteById(Long.valueOf(id));
    }

    public void updateBotSetting(Integer id){
        BotSetting botSetting = botRepository.findById(id);
         if (botSetting.isActive()){
             botSetting.setActive(false);
         } else {
             botSetting.setActive(true);
         }
        botRepository.save(botSetting);
    }

    public void updateAllSetting(Integer id, BotSetting botSetting){
        BotSetting botSetting1 = botRepository.findById(id);
        botSetting1.setChatId(botSetting.getChatId());
        botSetting1.setFileName(botSetting.getFileName());
        botSetting1.setTimeTask(botSetting.getTimeTask());
        botRepository.save(botSetting1);
    }


//    public void banUser(Long id) {
//        User user = userRepository.findById(id).orElse(null);
//        if (user != null) {
//            if (user.isActive()) {
//                user.setActive(false);
//                log.info("Ban user with id = {}, name: {}", user.getId(), user.getName());
//            } else {
//                user.setActive(true);
//                log.info("Unban user with id = {}, name: {}", user.getId(), user.getName());
//            }
//        }
//        userRepository.save(user);
//    }
//    public void changeUserRoles(User user, Map<String, String> form) {
//        Set<String> roles= Arrays.stream(Role.values())
//                .map(Role::name)
//                .collect(Collectors.toSet());
//        user.getRoles().clear();
//        for (String key : form.keySet()) {
//            if (roles.contains(key)) {
//                user.getRoles().add(Role.valueOf(key));
//            }
//            userRepository.save(user);
//        }
//    }
}