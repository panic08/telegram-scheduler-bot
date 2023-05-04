package ru.panic.lapayment.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "subscribers")
@Data
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private String notice;
    private String date;
    private Long subscribedAt;
}
