package ru.panic.lapayment.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.panic.lapayment.repository.SubscriberRepository;

@Service
public class SubscriberService {
    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    SubscriberRepository subscriberRepository;
    @Transactional
    public void removeByChatId(long chatId){
        subscriberRepository.removeByChatId(chatId);

    }

}
