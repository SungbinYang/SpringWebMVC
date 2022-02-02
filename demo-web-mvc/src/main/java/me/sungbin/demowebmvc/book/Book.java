package me.sungbin.demowebmvc.book;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * packageName : me.sungbin.demowebmvc.book
 * fileName : Book
 * author : rovert
 * date : 2022/02/02
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/02       rovert         최초 생성
 */

@Getter @Setter
public class Book {

    @JsonView(BookJsonView.Simple.class)
    private Long id;

    @JsonView(BookJsonView.Simple.class)
    private String isbn;

    @JsonView(BookJsonView.Complex.class)
    private Date published;

    @JsonView(BookJsonView.Complex.class)
    private Set<Author> authors = new HashSet<>();

    @JsonView(BookJsonView.Simple.class)
    private String title;

    public void addAuthor(Author author) {
        this.authors.add(author);
    }
}
