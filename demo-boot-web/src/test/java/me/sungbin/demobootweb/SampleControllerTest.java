package me.sungbin.demobootweb;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * packageName : me.sungbin.demobootweb
 * fileName : SampleControllerTest
 * author : rovert
 * date : 2022/01/30
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/01/30       rovert         최초 생성
 */

@SpringBootTest
@AutoConfigureMockMvc
class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;

    @Test
    void hello() throws Exception {
        Person person = new Person();
        person.setName("sungbin");
        Person savedPerson = personRepository.save(person);

        this.mockMvc.perform(get("/hello").param("id", savedPerson.getId().toString()))
                .andDo(print())
                .andExpect(content().string("hello sungbin"));
    }

    @Test
    void 정적리소스테스트() throws Exception {
        this.mockMvc.perform(get("/index.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("hello index")));
    }

    @Test
    void 정적리소스핸들러테스트() throws Exception {
        this.mockMvc.perform(get("/mobile/index.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("Hello Mobile")))
                .andExpect(header().exists(HttpHeaders.CACHE_CONTROL));
    }
}