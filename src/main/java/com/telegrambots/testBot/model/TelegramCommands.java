package com.telegrambots.testBot.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class TelegramCommands {

    private final Handler handler;
    private States state = States.START;

    @Autowired
    public TelegramCommands(Handler handler) {
        this.handler = handler;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return null;
        } else {
            Message message = update.getMessage();
            if (message != null && message.hasText()) {
                return handleInputMessage(message);
            }
        }
        return null;
    }

    public BotApiMethod<?> handleInputMessage(Message message) {
        final String start = "/start";
        final String order = "/order";
        final String help = "/help";

        String text = message.getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));

        if (text.equalsIgnoreCase(start)) {
            return handler.handleStart(message);
        }
        if (text.equalsIgnoreCase(order)) {
            state = States.ORDER;
            return handler.handleOrder(message);
        }
        if (state.equals(States.ORDER)) {
            BotApiMethod<?> botApiMethod = handler.handleAddOrder(message);
            state = States.START;
            return botApiMethod;
        }
        if (text.equalsIgnoreCase(help)) {
            return handler.handleHelp(message);
        }
        sendMessage.setText("Неизвестная команда. Для получения списка команд введите\n " +
                "/help");
        return sendMessage;
    }
}
