package me.sungbin.demobootweb;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * packageName : me.sungbin.demobootweb
 * fileName : PersonFormatter
 * author : rovert
 * date : 2022/01/30
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/01/30       rovert         최초 생성
 */

@Component
public class PersonFormatter implements Formatter<Person> {

    @Override
    public Person parse(String text, Locale locale) throws ParseException {
        Person person = new Person();
        person.setName(text);

        return person;
    }

    @Override
    public String print(Person object, Locale locale) {
        return object.toString();
    }
}
