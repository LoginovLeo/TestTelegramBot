package com.telegrambots.testBot.model;

import com.telegrambots.testBot.entity.User;
import com.telegrambots.testBot.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class Handler {
    private final String surname = "Фамилия";
    private final String name = "Имя";
    private final String phone = "Телефон";
    private final String address = "Адрес";
    private final String work = "Работы";

    private final String templateOrder = "*" + surname + "*" + ": _ваша фамилия_\n" +
            "*" + name + "*" + ": _ваше имя_\n" +
            "*" + phone + "*" + ": _номер телефона_\n" +
            "*" + address + "*" + ": _адрес офиса_\n" +
            "*" + work + "*" + ": _перечень необходимых работ_ \n" +
            "\n" +
            "*Пример:* \n" +
            "*" + surname + "*" + ": _Петров_\n" +
            "*" + name + "*" + ": _Петр_\n" +
            "*" + phone + "*" + ": _89999999999_\n" +
            "*" + address + "*" + ": _СПб, ул. Улица, д.5, офис 777_\n" +
            "*" + work + "*" + ": _Заменить лампы_ \n";

    private final UserService userService;
    private final MessageValidator messageValidator;


    public Handler(UserService userService, MessageValidator messageValidator) {
        this.userService = userService;
        this.messageValidator = messageValidator;
    }

    public BotApiMethod<?> handleStart(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setParseMode(ParseMode.MARKDOWN);

        String firstName = message.getChat().getFirstName();
        String lastName = message.getChat().getLastName();

        sendMessage.setText("Добро пожаловать " + lastName + " " + firstName + "!\n" +
                "Данный бот позволяет разместить заказ.\n" +
                "Для размещения заказа, пожалуйста введите команду /order\n" +
                "После чего бот будет ожидать от Вас сообщение с информацией о заказе в следующем формате\n" +
                "\n" +
                templateOrder);

        return sendMessage;
    }

    public BotApiMethod<?> handleOrder(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setText("Укажите данные в следующем формате\n" + templateOrder);
        return sendMessage;
    }

    public BotApiMethod<?> handleAddOrder(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setParseMode(ParseMode.MARKDOWN);

        String[] split = message.getText().split("\n");
        Map<String, String> stringStringMap = messageValidator.convertToMap(Arrays.asList(split));

        User user = new User();
        user.setSurname(stringStringMap.get(surname));
        user.setName(stringStringMap.get(name));
        user.setPhone(stringStringMap.get(phone));
        user.setWork(stringStringMap.get(work));
        user.setAddress(stringStringMap.get(address));
        List<String> strings = messageValidator.validateOrderDetails(user);
        if (!strings.get(0).equals("All good")) {
            sendMessage.setText(String.join(".\n", strings) + "\n \n Информация обязательная для заполения \n" + templateOrder +
                    "\n Для продолжения введите /order и после заполните и отправьте обязательную информацию о заказе");
            return sendMessage;
        }
        userService.saveUser(user);

        sendMessage.setText("Заказ успешно размещен");
        return sendMessage;
    }

    public BotApiMethod<?> handleHelp(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText("Список доступных команд \n" +
                "/start \n" +
                "/order");
        return sendMessage;
    }


}
