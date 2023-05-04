package ru.panic.lapayment.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.panic.lapayment.config.BotConfig;
import ru.panic.lapayment.model.Subscriber;
import ru.panic.lapayment.repository.SubscriberRepository;
import ru.panic.lapayment.service.TelegramBot;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Component
public class NoticeScheduler {
    public NoticeScheduler(SubscriberRepository repository, TelegramBot telegramBot) {
        this.repository = repository;
        this.telegramBot = telegramBot;
    }

    private SubscriberRepository repository;

    private TelegramBot telegramBot;

    @Scheduled(cron = "0 0 14 * * ?", zone = "Europe/Moscow")
    public void noticeSchedule(){
        List<Subscriber> subscribers = repository.findAll();
        LocalDate cur = LocalDate.now();
        DayOfWeek dayOfWeek = cur.getDayOfWeek();
        SendMessage message = new SendMessage();
        for(Subscriber key : subscribers){
            if (key.getDate() != null && key.getNotice() != null && DayOfWeek.valueOf(key.getDate()) == dayOfWeek){
                message.setText("Ваши обязанности на сегодня:" + "\n" + key.getNotice());
                message.setChatId(key.getChatId());
                try {
                    telegramBot.execute(message);
                }catch (Exception e){

                }
            }
        }

    }
}
