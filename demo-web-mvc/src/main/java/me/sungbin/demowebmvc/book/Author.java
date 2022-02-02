package me.sungbin.demowebmvc.book;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * packageName : me.sungbin.demowebmvc.book
 * fileName : Author
 * author : rovert
 * date : 2022/02/02
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/02       rovert         최초 생성
 */

@Getter @Setter
public class Author {

    @JsonView(BookJsonView.Complex.class)
    private Long id;

    @JsonView(BookJsonView.Complex.class)
    private String firstName;

    @JsonView(BookJsonView.Complex.class)
    private String lastName;

    private String email;

    private String address;

    private Date joinedAt;
}
