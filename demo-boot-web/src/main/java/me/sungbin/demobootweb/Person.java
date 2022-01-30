package me.sungbin.demobootweb;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * packageName : me.sungbin.demobootweb
 * fileName : Person
 * author : rovert
 * date : 2022/01/30
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/01/30       rovert         최초 생성
 */

@Entity
@Getter
@Setter
public class Person {

    @Id @GeneratedValue
    private Long id;

    private String name;
}
