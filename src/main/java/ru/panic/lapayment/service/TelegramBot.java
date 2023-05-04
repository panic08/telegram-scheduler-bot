package ru.panic.lapayment.service;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.panic.lapayment.config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.panic.lapayment.model.Admin;
import ru.panic.lapayment.model.Subscriber;
import ru.panic.lapayment.pojo.Stage;
import ru.panic.lapayment.repository.AdminRepository;
import ru.panic.lapayment.repository.SubscriberRepository;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    private BotConfig config;
    @Autowired
    private TelegramBot bot;
    @Autowired
    private SubscriberRepository subscriberRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    public SubscriberService subscriberService;
    private static HashMap<Long, Stage> noticeStages = new HashMap<>();
    private static HashMap<Long, Stage> createStages = new HashMap<>();

    public TelegramBot(BotConfig config) {
        this.config = config;
//        List<BotCommand> listOfCommands = new ArrayList<>();
//        listOfCommands.add(new BotCommand("/start", "get a welcome message"));
//        listOfCommands.add(new BotCommand("/joke", "get a random joke"));
//        listOfCommands.add(new BotCommand("/help", "info how to use this bot"));
//        listOfCommands.add(new BotCommand("/settings", "set your preferences"));
//        try {
//            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
//        } catch (TelegramApiException e) {
//            log.error(Arrays.toString(e.getStackTrace()));
//        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotUserName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            if (createStages.get(chatId) != null){
                switch (createStages.get(chatId).getStage()) {
                    case 0 -> {
                        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
                        inlineKeyboardButton1.setText(EmojiParser.parseToUnicode(":heavy_minus_sign: Назад"));
                        inlineKeyboardButton1.setCallbackData(EmojiParser.parseToUnicode(":heavy_minus_sign: Назад"));

                        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
                        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

                        keyboardButtonsRow1.add(inlineKeyboardButton1);

                        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                        rowList.add(keyboardButtonsRow1);
                        rowList.add(keyboardButtonsRow2);
                        inlineKeyboardMarkup.setKeyboard(rowList);
                        try {
                            Subscriber subscriber = subscriberRepository.findById(Long.valueOf(messageText)).orElseThrow();

                        } catch (Exception e1) {

                            SendMessage sendMessage = new SendMessage();
                            sendMessage.setText("Вы ввели некорректный ID, повторите попытку");
                            sendMessage.setChatId(chatId);
                            sendMessage.setReplyMarkup(inlineKeyboardMarkup);

                            try {
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                log.error(Arrays.toString(e.getStackTrace()));
                            }
                            return;
                        }
                        createStages.get(chatId).setDto(messageText);

                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setText("Придумайте уведомление, которое вы хотите поставить пользователю");
                        sendMessage.setChatId(chatId);
                        sendMessage.setReplyMarkup(inlineKeyboardMarkup);


                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            log.error(Arrays.toString(e.getStackTrace()));
                        }
                        createStages.get(chatId).setStage(1);
                        return;

                    }
                    case 1 -> {
                        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
                        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
                        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
                        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
                        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();
                        InlineKeyboardButton inlineKeyboardButton6 = new InlineKeyboardButton();
                        InlineKeyboardButton inlineKeyboardButton7 = new InlineKeyboardButton();
                        inlineKeyboardButton1.setText(EmojiParser.parseToUnicode(":calendar: Понедельник"));
                        inlineKeyboardButton1.setCallbackData(EmojiParser.parseToUnicode(":calendar: Понедельник"));
                        inlineKeyboardButton2.setText(EmojiParser.parseToUnicode(":calendar: Вторник"));
                        inlineKeyboardButton2.setCallbackData(EmojiParser.parseToUnicode(":calendar: Вторник"));
                        inlineKeyboardButton3.setText(EmojiParser.parseToUnicode(":calendar: Среда"));
                        inlineKeyboardButton3.setCallbackData(EmojiParser.parseToUnicode(":calendar: Среда"));
                        inlineKeyboardButton4.setText(EmojiParser.parseToUnicode(":calendar: Четверг"));
                        inlineKeyboardButton4.setCallbackData(EmojiParser.parseToUnicode(":calendar: Четверг"));
                        inlineKeyboardButton5.setText(EmojiParser.parseToUnicode(":calendar: Пятница"));
                        inlineKeyboardButton5.setCallbackData(EmojiParser.parseToUnicode(":calendar: Пятница"));
                        inlineKeyboardButton6.setText(EmojiParser.parseToUnicode(":calendar: Суббота"));
                        inlineKeyboardButton6.setCallbackData(EmojiParser.parseToUnicode(":calendar: Суббота"));
                        inlineKeyboardButton7.setText(EmojiParser.parseToUnicode(":calendar: Воскресенье"));
                        inlineKeyboardButton7.setCallbackData(EmojiParser.parseToUnicode(":calendar: Воскресенье"));

                        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
                        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

                        keyboardButtonsRow1.add(inlineKeyboardButton1);
                        keyboardButtonsRow1.add(inlineKeyboardButton2);
                        keyboardButtonsRow1.add(inlineKeyboardButton3);
                        keyboardButtonsRow1.add(inlineKeyboardButton4);
                        keyboardButtonsRow2.add(inlineKeyboardButton5);
                        keyboardButtonsRow2.add(inlineKeyboardButton6);
                        keyboardButtonsRow2.add(inlineKeyboardButton7);

                        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                        rowList.add(keyboardButtonsRow1);
                        rowList.add(keyboardButtonsRow2);
                        inlineKeyboardMarkup.setKeyboard(rowList);

                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setText("Выберите, в какой день будет срабатывать оповещение");
                        sendMessage.setChatId(chatId);
                        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
//                        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
//                        sendMessage("Вы успешно обновили уведомление пользователя.", chatId);
                        //жжется
//                        noticeStages.remove(chatId);
                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            log.error(Arrays.toString(e.getStackTrace()));
                        }

                        Subscriber subscriber = subscriberRepository.findById(Long.valueOf(createStages.get(chatId).getDto())).orElseThrow();
                        subscriber.setId(subscriber.getId() + 1);
                        subscriber.setNotice(messageText);
                        subscriberRepository.save(subscriber);
                        createStages.get(chatId).setDto(String.valueOf(subscriber.getId()));
                    }
                }
            }
            if (noticeStages.get(chatId) != null){
                switch (noticeStages.get(chatId).getStage()){
                    case 0 -> {
                        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
                        inlineKeyboardButton1.setText(EmojiParser.parseToUnicode(":heavy_minus_sign: Назад"));
                        inlineKeyboardButton1.setCallbackData(EmojiParser.parseToUnicode(":heavy_minus_sign: Назад"));

                        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
                        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

                        keyboardButtonsRow1.add(inlineKeyboardButton1);

                        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                        rowList.add(keyboardButtonsRow1);
                        rowList.add(keyboardButtonsRow2);
                        inlineKeyboardMarkup.setKeyboard(rowList);
                        try {
                            Subscriber subscriber = subscriberRepository.findById(Long.valueOf(messageText)).orElseThrow();

                        }catch (Exception e1) {

                            SendMessage sendMessage = new SendMessage();
                            sendMessage.setText("Вы ввели некорректный ID, повторите попытку");
                            sendMessage.setChatId(chatId);
                            sendMessage.setReplyMarkup(inlineKeyboardMarkup);

                            try {
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                log.error(Arrays.toString(e.getStackTrace()));
                            }
                            return;
                        }
                        noticeStages.get(chatId).setDto(messageText);

                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setText("Придумайте уведомление, которое вы хотите поставить пользователю");
                        sendMessage.setChatId(chatId);


                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            log.error(Arrays.toString(e.getStackTrace()));
                        }
                        noticeStages.get(chatId).setStage(1);
                        return;
                    }
                    case 1 -> {
                        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
                        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
                        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
                        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
                        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();
                        InlineKeyboardButton inlineKeyboardButton6 = new InlineKeyboardButton();
                        InlineKeyboardButton inlineKeyboardButton7 = new InlineKeyboardButton();
                        inlineKeyboardButton1.setText(EmojiParser.parseToUnicode(":calendar: Понедельник"));
                        inlineKeyboardButton1.setCallbackData(EmojiParser.parseToUnicode(":calendar: Понедельник"));
                        inlineKeyboardButton2.setText(EmojiParser.parseToUnicode(":calendar: Вторник"));
                        inlineKeyboardButton2.setCallbackData(EmojiParser.parseToUnicode(":calendar: Вторник"));
                        inlineKeyboardButton3.setText(EmojiParser.parseToUnicode(":calendar: Среда"));
                        inlineKeyboardButton3.setCallbackData(EmojiParser.parseToUnicode(":calendar: Среда"));
                        inlineKeyboardButton4.setText(EmojiParser.parseToUnicode(":calendar: Четверг"));
                        inlineKeyboardButton4.setCallbackData(EmojiParser.parseToUnicode(":calendar: Четверг"));
                        inlineKeyboardButton5.setText(EmojiParser.parseToUnicode(":calendar: Пятница"));
                        inlineKeyboardButton5.setCallbackData(EmojiParser.parseToUnicode(":calendar: Пятница"));
                        inlineKeyboardButton6.setText(EmojiParser.parseToUnicode(":calendar: Суббота"));
                        inlineKeyboardButton6.setCallbackData(EmojiParser.parseToUnicode(":calendar: Суббота"));
                        inlineKeyboardButton7.setText(EmojiParser.parseToUnicode(":calendar: Воскресенье"));
                        inlineKeyboardButton7.setCallbackData(EmojiParser.parseToUnicode(":calendar: Воскресенье"));

                        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
                        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

                        keyboardButtonsRow1.add(inlineKeyboardButton1);
                        keyboardButtonsRow1.add(inlineKeyboardButton2);
                        keyboardButtonsRow1.add(inlineKeyboardButton3);
                        keyboardButtonsRow1.add(inlineKeyboardButton4);
                        keyboardButtonsRow2.add(inlineKeyboardButton5);
                        keyboardButtonsRow2.add(inlineKeyboardButton6);
                        keyboardButtonsRow2.add(inlineKeyboardButton7);

                        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                        rowList.add(keyboardButtonsRow1);
                        rowList.add(keyboardButtonsRow2);
                        inlineKeyboardMarkup.setKeyboard(rowList);

                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setText("Выберите, в какой день будет срабатывать оповещение");
                        sendMessage.setChatId(chatId);
                        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
//                        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
//                        sendMessage("Вы успешно обновили уведомление пользователя.", chatId);
                        //жжется
//                        noticeStages.remove(chatId);
                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            log.error(Arrays.toString(e.getStackTrace()));
                        }

                            Subscriber subscriber = subscriberRepository.findById(Long.valueOf(noticeStages.get(chatId).getDto()))
                                    .orElseThrow(() -> new RuntimeException("RuntimeException!!!!!!!!!!!"));

                        subscriber.setNotice(messageText);
                        subscriberRepository.save(subscriber);


                        return;
                    }
                    case 2 -> {

                    }

                }
            }

            switch (messageText) {

                case "/start" -> {
                    sendMessage("Hello, world", chatId);
                }
            }

            if (EmojiParser.parseToUnicode(":heavy_plus_sign: Подписаться").equals(messageText)) {
                Subscriber subscriber = new Subscriber();
                subscriber.setChatId(chatId);
                subscriber.setSubscribedAt(System.currentTimeMillis());
                subscriber.setNotice(null);
                subscriber.setDate(null);
                try {
                    subscriberRepository.save(subscriber);
                } catch (Exception e) {
                    log.error("Query did not return a unique result");
                }
//                subscriber.setSubscribedAt(System.currentTimeMillis());
                subscribeStage(chatId, update.getMessage().getChat().getId().toString());
            }
            if (EmojiParser.parseToUnicode(":wavy_dash: Создать уведомление").equals(messageText)) {
                Stage stage = new Stage();
                stage.setDto(null);
                stage.setStage(0);
                createStages.put(chatId, stage);
                sendMessage("Введите ID подписчика, которому хотите добавить еще одно уведомление." +
                        "\nЧтобы его получить, нажмите на кнопку \"Список подписчиков\"", chatId);
            }
            if (EmojiParser.parseToUnicode(":heavy_minus_sign: Отписаться").equals(messageText)) {
                subscriberService.removeByChatId(chatId);
//                subscriber.setSubscribedAt(System.currentTimeMillis());
                describeStage(chatId);
            }
            if (EmojiParser.parseToUnicode(":exclamation: Админ-панель").equals(messageText)){
                Admin admin = new Admin();
                admin.setChatId(chatId);
                admin.setAdminFrom(System.currentTimeMillis());
                adminRepository.save(admin);
                adminStage(chatId);
            }
            if (EmojiParser.parseToUnicode(":black_nib: Изменить уведомление").equals(messageText)){
                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
                inlineKeyboardButton1.setText(EmojiParser.parseToUnicode(":heavy_minus_sign: Назад"));
                inlineKeyboardButton1.setCallbackData(EmojiParser.parseToUnicode(":heavy_minus_sign: Назад"));

                List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
                List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

                keyboardButtonsRow1.add(inlineKeyboardButton1);

                List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                rowList.add(keyboardButtonsRow1);
                rowList.add(keyboardButtonsRow2);
                inlineKeyboardMarkup.setKeyboard(rowList);

                SendMessage sendMessage = new SendMessage();
                sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                sendMessage.setText(EmojiParser.parseToUnicode("Введите ID подписчика, у которого вы хотите изменить уведомление" +
                        "\nЧтобы его получить, нажмите на кнопку \"mega: Список подписчиков\""));
                sendMessage.setChatId(chatId);
                Stage stage = new Stage();
                stage.setStage(0);
                stage.setDto(null);
                noticeStages.put(Long.valueOf(chatId), stage);

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    log.error(Arrays.toString(e.getStackTrace()));
                }

            }
            if (EmojiParser.parseToUnicode(":mega: Список подписчиков").equals(messageText)){
                int i = 0;
                List<Subscriber> subscriberList = subscriberRepository.findAll();
                if (subscriberList != null && !subscriberList.isEmpty()) {
                    String[] roller = new String[subscriberList.size()];
                    for (Subscriber key : subscriberList) {
                        GetChatMember member = new GetChatMember();
                        member.setChatId(key.getChatId());
                        member.setUserId(key.getChatId());
                        ChatMember chatMember;
                        try {
                            chatMember = execute(member);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'года' в HH:mm");
                        LocalDateTime dateTime = Instant.ofEpochMilli(key.getSubscribedAt()).atZone(ZoneId.systemDefault()).toLocalDateTime();
                        String formattedDateTime = dateTime.format(formatter);
                        String dateOfWeekString = switch (key.getDate()) {
                            case "MONDAY" -> "Понедельник";
                            case "TUESDAY" -> "Вторник";
                            case "WEDNESDAY" -> "Среда";
                            case "THURSDAY" -> "Четверг";
                            case "FRIDAY" -> "Пятница";
                            case "SATURDAY" -> "Суббота";
                            case "SUNDAY" -> "Воскресенье";
                            case null, default -> "Не указано";
                        };
                        String a = "ID: " + key.getId() + "\nПодписчик: @" + chatMember.getUser().getUserName() +
                                "\nУведомление: " + (key.getNotice() != null ? key.getNotice() : "Не указано") +
                                "\nВызыв уведомления: " + dateOfWeekString +
                                "\nПодписался: " + formattedDateTime +
                                "\n----------------------------------";
                        roller[i] = a;
                        i++;
                    }
                    String response = String.join("\n", roller);
                    sendMessage(response, chatId);
                }else{
                    sendMessage(EmojiParser.parseToUnicode("Пока еще никто не подписался :sob:"), chatId);
                }
            }

        } else {
            if (update.hasCallbackQuery()) {
                if(update.getCallbackQuery().getData().equals(EmojiParser.parseToUnicode(":heavy_minus_sign: Назад"))){
                    try {
                        noticeStages.remove(update.getCallbackQuery().getMessage().getChatId());
                        createStages.remove(update.getCallbackQuery().getMessage().getChatId());
                    }catch (Exception e){
                    }
                    sendMessage("ты отменил поздр все круто)", update.getCallbackQuery().getMessage().getChatId());
                    return;
                }
                if(update.getCallbackQuery().getData().equals(EmojiParser.parseToUnicode(":calendar: Понедельник")) ||
                        update.getCallbackQuery().getData().equals(EmojiParser.parseToUnicode(":calendar: Вторник")) ||
                        update.getCallbackQuery().getData().equals(EmojiParser.parseToUnicode(":calendar: Среда")) ||
                        update.getCallbackQuery().getData().equals(EmojiParser.parseToUnicode(":calendar: Четверг")) ||
                        update.getCallbackQuery().getData().equals(EmojiParser.parseToUnicode(":calendar: Пятница")) ||
                        update.getCallbackQuery().getData().equals(EmojiParser.parseToUnicode(":calendar: Суббота")) ||
                        update.getCallbackQuery().getData().equals(EmojiParser.parseToUnicode(":calendar: Воскресенье"))) {
                    String dayOfWeekString = update.getCallbackQuery().getData().replaceAll(EmojiParser.parseToUnicode(":calendar: "), "");
                    DayOfWeek dayOfWeek;
                    switch (dayOfWeekString) {
                        case "Понедельник":
                            dayOfWeek = DayOfWeek.MONDAY;
                            break;
                        case "Вторник":
                            dayOfWeek = DayOfWeek.TUESDAY;
                            break;
                        case "Среда":
                            dayOfWeek = DayOfWeek.WEDNESDAY;
                            break;
                        case "Четверг":
                            dayOfWeek = DayOfWeek.THURSDAY;
                            break;
                        case "Пятница":
                            dayOfWeek = DayOfWeek.FRIDAY;
                            break;
                        case "Суббота":
                            dayOfWeek = DayOfWeek.SATURDAY;
                            break;
                        case "Воскресенье":
                            dayOfWeek = DayOfWeek.SUNDAY;
                            break;

                        default:
                            throw new IllegalArgumentException("Invalid day of week: " + dayOfWeekString);
                    }
                    if (createStages.get(update.getCallbackQuery().getMessage().getChatId()) == null) {
                    sendMessage("Вы успешно изменили уведомление у подписчика", update.getCallbackQuery().getMessage().getChatId());

                        Subscriber subscriber = subscriberRepository.findById(Long.valueOf(noticeStages.get(update.getCallbackQuery().getMessage().getChatId()).getDto())).orElseThrow();

                        noticeStages.remove(update.getCallbackQuery().getMessage().getChatId());

                        subscriber.setDate(dayOfWeek.toString());
                        subscriberRepository.save(subscriber);
                    }else{
                        sendMessage("Вы успешно создали новое уведомление для подписчика", update.getCallbackQuery().getMessage().getChatId());
                        System.out.println(createStages.get(update.getCallbackQuery().getMessage().getChatId()).getDto());
                           Subscriber subscriber = subscriberRepository.findById(Long.valueOf(createStages.get(update.getCallbackQuery().getMessage().getChatId()).getDto())).orElseThrow();
                            System.out.println(subscriber.getChatId());

                        createStages.remove(update.getCallbackQuery().getMessage().getChatId());

                        subscriber.setDate(dayOfWeek.toString());
                        subscriberRepository.save(subscriber);
                    }
                    return;

                }
            }
        }
    }


    private void subscribeStage(long chatId, String name) {
        String answer = EmojiParser.parseToUnicode(
                "Вы успешно подписались на рассылку :heavy_check_mark:");
        sendMessage(answer, chatId);
    }

    private void describeStage(long chatId) {
        String answer = EmojiParser.parseToUnicode(
                "Вы отписались от рассылки :x:");
        sendMessage(answer, chatId);
    }
    private void adminStage(long chatId){
        String answer = EmojiParser.parseToUnicode(
                "Вы зашли в админ-панель :curly_loop:");
        sendMessage(answer, chatId);
    }
    private void allSubscribersShow(long chatId){
        String answer = EmojiParser.parseToUnicode(
                "Вы зашли в админ-панель :curly_loop:");
        sendMessage(answer, chatId);
    }

    private void commandNotFound(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(EmojiParser.parseToUnicode(":raise hands: Назад"));
        inlineKeyboardButton1.setCallbackData("Button \"Тык\" has been pressed");
        inlineKeyboardButton2.setText("Тык2");
        inlineKeyboardButton2.setCallbackData("Button \"Тык2\" has been pressed");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);

        keyboardButtonsRow2.add(inlineKeyboardButton2);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        //
        String answer = EmojiParser.parseToUnicode(
                "Нету такой команды дебилоид :heavy_minus_sign:");
//
        SendMessage message = new SendMessage(); // Create a message object object
        message.setReplyMarkup(inlineKeyboardMarkup);
        message.setChatId(String.valueOf(chatId));
        message.setText(answer);
//        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }

    }

    private void sendMessage(String textToSend, long chatId) {
        Subscriber subscriber = subscriberRepository.findByChatId(chatId);
        Admin admin = adminRepository.findByChatId(chatId);
        SendMessage message = new SendMessage();
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        // Добавляем кнопки "Подписаться" и "Отписаться" для всех пользователей
        KeyboardButton subscribeButton = new KeyboardButton(EmojiParser.parseToUnicode(":heavy_plus_sign: Подписаться"));
        if (subscriber != null) {
            subscribeButton = new KeyboardButton(EmojiParser.parseToUnicode(":heavy_minus_sign: Отписаться"));
        }
        subscribeButton.setRequestLocation(false);
        subscribeButton.setRequestContact(false);
        row.add(subscribeButton);

        if (admin != null) {
            // Добавляем 6 кнопок для администратора
            KeyboardButton subscribersButton = new KeyboardButton(EmojiParser.parseToUnicode(":wavy_dash: Создать уведомление"));
            subscribersButton.setRequestLocation(false);
            subscribersButton.setRequestContact(false);
            row.add(subscribersButton);


//            KeyboardButton promoButton = new KeyboardButton(EmojiParser.parseToUnicode(":moneybag: Рассылка промокодов"));
//            promoButton.setRequestLocation(false);
//            promoButton.setRequestContact(false);
//            row.add(promoButton);

            KeyboardButton adminModeButton = new KeyboardButton(EmojiParser.parseToUnicode(":black_nib: Изменить уведомление"));
            adminModeButton.setRequestLocation(false);
            adminModeButton.setRequestContact(false);
            row.add(adminModeButton);

            KeyboardButton broadcastButton = new KeyboardButton(EmojiParser.parseToUnicode(":mega: Список подписчиков"));
            broadcastButton.setRequestLocation(false);
            broadcastButton.setRequestContact(false);
            row.add(broadcastButton);

//            KeyboardButton feedbackButton = new KeyboardButton(EmojiParser.parseToUnicode(":envelope_with_arrow: Отзывы"));
//            feedbackButton.setRequestLocation(false);
//            feedbackButton.setRequestContact(false);
//            row.add(feedbackButton);

            KeyboardButton settingsButton = new KeyboardButton(EmojiParser.parseToUnicode(":gear: Настройки"));
            settingsButton.setRequestLocation(false);
            settingsButton.setRequestContact(false);
            row.add(settingsButton);
        } else {
            // Добавляем кнопку "Стать администратором" только для не-администраторов
            KeyboardButton adminButton = new KeyboardButton(EmojiParser.parseToUnicode(":exclamation: Админ-панель"));
            adminButton.setRequestContact(false);
            adminButton.setRequestLocation(false);
            row.add(adminButton);
        }

        rows.add(row);
        keyboardMarkup.setKeyboard(rows);
        message.setReplyMarkup(keyboardMarkup);
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }





}
