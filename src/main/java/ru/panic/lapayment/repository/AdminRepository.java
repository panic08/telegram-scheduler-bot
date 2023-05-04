package ru.panic.lapayment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.panic.lapayment.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByChatId(long chatId);
}
