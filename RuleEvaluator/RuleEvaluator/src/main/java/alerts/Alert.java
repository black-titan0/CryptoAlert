package alerts;

import java.time.LocalDateTime;

public class Alert {

    public int id;
    public LocalDateTime time;
    public String name;
    public String marketName;

    public Alert(LocalDateTime time, String name, String marketName) {
        this.time = time;
        this.name = name;
        this.marketName = marketName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
