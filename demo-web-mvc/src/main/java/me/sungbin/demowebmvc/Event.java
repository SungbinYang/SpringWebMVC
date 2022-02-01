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

    interface ValidateLimit {}
    interface ValidateName{}

    private Integer id;

    @NotBlank(groups = ValidateName.class)
    private String name;

    @Min(value = 0, groups = ValidateLimit.class)
    private Integer limit;
}
