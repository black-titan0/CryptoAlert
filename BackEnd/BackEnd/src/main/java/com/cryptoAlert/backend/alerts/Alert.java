package com.cryptoAlert.backend.alerts;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    public Long id;
    public LocalDateTime time;
    public String name;
    public String marketName;

    public Alert(LocalDateTime time, String name, String marketName) {
        this.time = time;
        this.name = name;
        this.marketName = marketName;
    }

    public Alert() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "id=" + id +
                ", time=" + time +
                ", name='" + name + '\'' +
                ", marketName='" + marketName + '\'' +
                '}';
    }
}
