package com.thread.cdr.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cdr")
public class CallHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "account", length = 100)
    private String account;
    @Column(name = "origen", length = 100)
    private String origen;
    @Column(name = "destiny", length = 100)
    private String destiny;
    @Column(name = "date_time", length = 100)
    private LocalTime date_time;
    @Column(name = "duration", length = 5)
    private int duration;
    @Column(name = "price", length = 5)
    private float price;
    @Column(name = "location", length = 20)
    private String location;
    @Column(name = "procesed_by", length = 20)
    private String procesed_by;
    @Column(name = "consumed_by", length = 20)
    private String consumed_by;
    @Column(name = "time_taked", length = 20)
    private Duration time_taked;
    @Column(name = "date_time_added", length = 100)
    private LocalTime date_time_added;

    public CallHistory(String account, String origen, String destiny, LocalTime date_time, int duration, float price,
                       String location, String procesed_by, String consumed_by, Duration time_taked, LocalTime date_time_added) {
        this.account = account;
        this.origen = origen;
        this.destiny = destiny;
        this.date_time = date_time;
        this.duration = duration;
        this.price = price;
        this.location = location;
        this.procesed_by = procesed_by;
        this.consumed_by = consumed_by;
        this.time_taked = time_taked;
        this.date_time_added = date_time_added;

    }
}
