package com.fx.cloudmessagehandler.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class Price {
    @Id
    private Integer id;

    private String name;

    private Double bid;

    private Double ask;

    private LocalDateTime timeStamp;
}
