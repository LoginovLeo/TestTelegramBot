package com.telegrambots.testBot.configuration;

import com.telegrambots.testBot.botconfiguration.TelegramBotConfig;
import com.telegrambots.testBot.model.TelegramBot;
import com.telegrambots.testBot.model.TelegramCommands;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;


@Configuration
public class SpringAppConfiguration {

    private final TelegramBotConfig telegramBotConfig;

    public SpringAppConfiguration(TelegramBotConfig telegramBotConfig) {
        this.telegramBotConfig = telegramBotConfig;
    }

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(telegramBotConfig.getWebHookPath()).build();
    }

    @Bean
    public TelegramBot springWebhookBot(SetWebhook setWebhook, TelegramCommands telegramCommands) {
        TelegramBot telegramBot = new TelegramBot(setWebhook, telegramCommands);
        telegramBot.setBotToken(telegramBotConfig.getBotToken());
        telegramBot.setBotUsername(telegramBotConfig.getUserName());
        telegramBot.setBotPath(telegramBotConfig.getWebHookPath());

        return telegramBot;
    }
}
