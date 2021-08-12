package com.telegrambots.testBot.model;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;


public class TelegramBot extends SpringWebhookBot {

    private String botPath;
    private String botUsername;
    private String botToken;

    private final TelegramCommands telegramCommands;

    public TelegramBot(SetWebhook setWebhook, TelegramCommands telegramCommands) {
        super(setWebhook);
        this.telegramCommands = telegramCommands;
    }

    public TelegramBot(DefaultBotOptions options, SetWebhook setWebhook, TelegramCommands telegramCommands) {
        super(options, setWebhook);
        this.telegramCommands = telegramCommands;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return telegramCommands.handleUpdate(update);
    }

    @Override
    public String getBotPath() {
        return botPath;
    }

    public void setBotPath(String botPath) {
        this.botPath = botPath;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    public void setBotUsername(String botUsername) {
        this.botUsername = botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }
}
