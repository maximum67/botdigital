package com.example.botdigital.repositories;

import com.example.botdigital.models.BotSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BotRepository extends JpaRepository<BotSetting, Long> {
    BotSetting findByTokenbot(String tokenbot);
    BotSetting findById(Integer id);
}
