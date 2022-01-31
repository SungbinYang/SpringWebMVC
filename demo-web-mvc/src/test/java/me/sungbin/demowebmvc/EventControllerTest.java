package me.sungbin.demowebmvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * packageName : me.sungbin.demowebmvc
 * fileName : EventControllerTest
 * author : rovert
 * date : 2022/02/01
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/02/01       rovert         최초 생성
 */

@WebMvcTest
class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void 연습문제1() throws Exception {
        mockMvc.perform(get("/events"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("event"));
    }

    @Test
    void 연습문제2() throws Exception {
        mockMvc.perform(get("/events/11"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("event"));

        mockMvc.perform(get("/events/sungbin"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void 연습문제3() throws Exception {
        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("event"));
    }

    @Test
    void 연습문제4() throws Exception {
        mockMvc.perform(delete("/events/11"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("event"));

        mockMvc.perform(delete("/events/sungbin"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void 연습문제5() throws Exception {
        mockMvc.perform(put("/events/11")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("event"));

        mockMvc.perform(put("/events/sungbin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}