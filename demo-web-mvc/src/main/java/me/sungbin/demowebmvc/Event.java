package me.sungbin.demowebmvc;

import lombok.Getter;
import lombok.Setter;

/**
 * packageName : me.sungbin.demowebmvc
 * fileName : Event
 * author : rovert
 * date : 2022/02/01
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/01       rovert         최초 생성
 */

@Getter @Setter
public class Event {

    private Integer id;

    private String name;

    private Integer limit;
}
