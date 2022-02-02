package me.sungbin.demowebmvc.book;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * packageName : me.sungbin.demowebmvc.book
 * fileName : BookController
 * author : rovert
 * date : 2022/02/02
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/02       rovert         최초 생성
 */

@RestController
public class BookController {

    @GetMapping("/books")
    @JsonView(BookJsonView.Complex.class)
    public List<Book> getBooks() {
        return books();
    }

    private List<Book> books() {
        Author sungbin = new Author();
        sungbin.setFirstName("sungbin");
        sungbin.setLastName("yang");
        sungbin.setEmail("sungbin@email.com");
        sungbin.setId(100L);
        sungbin.setAddress("대한민국");
        sungbin.setJoinedAt(new Date());

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Spring Boot");
        book.setIsbn("dfsdsfdsfsdsfsfs");
        book.setPublished(new Date());
        book.addAuthor(sungbin);

        return List.of(book);
    }
}
