package me.sungbin.demowebmvc;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

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
@ToString
public class Event {

    private Integer id;

    @NotBlank
    private String name;

    @Min(0)
    private Integer limit;
}
