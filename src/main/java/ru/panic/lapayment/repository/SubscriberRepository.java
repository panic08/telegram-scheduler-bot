package ru.panic.lapayment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.panic.lapayment.model.Subscriber;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    Subscriber findByChatId(long chatId);

    void removeByChatId(long chatId);

}
