package me.sungbin.demojsp;

import java.time.LocalDateTime;

/**
 * packageName : me.sungbin.demojsp
 * fileName : Event
 * author : rovert
 * date : 2022/01/30
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/01/30       rovert         최초 생성
 */

public class Event {

    private String name;

    private LocalDateTime starts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStarts() {
        return starts;
    }

    public void setStarts(LocalDateTime starts) {
        this.starts = starts;
    }
}
