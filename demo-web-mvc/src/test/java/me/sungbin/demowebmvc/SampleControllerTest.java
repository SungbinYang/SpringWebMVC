package me.sungbin.demowebmvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * packageName : me.sungbin.demowebmvc
 * fileName : SampleControllerTest
 * author : rovert
 * date : 2022/01/31
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/01/31       rovert         최초 생성
 */

@WebMvcTest(SampleController.class)
class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void PathVariable_테스트() throws Exception {
        mockMvc.perform(get("/event/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1));
    }

    @Test
    void MatrixVariable_테스트() throws Exception {
        mockMvc.perform(get("/event/1;name=sungbin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1));
    }

    @Test
    void RequestParam_테스트_query_parameter() throws Exception {
        mockMvc.perform(post("/event?name=sungbin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("sungbin"));
    }

    @Test
    void RequestParam_테스트_form_data() throws Exception {
        mockMvc.perform(post("/event")
                        .param("name", "sungbin")
                        .param("limit", "5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("sungbin"));
    }

    @Test
    void RequestParam_테스트_form_data2() throws Exception {
        mockMvc.perform(get("/event/form"))
                .andDo(print())
                .andExpect(view().name("events/form"))
                .andExpect(model().attributeExists("event"));
    }

    @Test
    void ModelAttribute_테스트() throws Exception {
        mockMvc.perform(post("/event/name/sungbin")
                        .param("limit", "-10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("sungbin"));
    }

    @Test
    void formSubmitError() throws Exception {
        ResultActions result = mockMvc.perform(post("/event")
                        .param("name", "sungbin")
                        .param("limit", "-10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().hasErrors());

        ModelAndView mav = result.andReturn().getModelAndView();
        assert mav != null;
        Map<String, Object> model = mav.getModel();
        System.out.println(model.size());
    }

    @Test
    void sessionTest() throws Exception {
        MockHttpServletRequest request = mockMvc.perform(get("/event/form"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("event"))
                .andExpect(request().sessionAttribute("event", notNullValue()))
                .andReturn().getRequest();

        Object event = Objects.requireNonNull(request.getSession()).getAttribute("event");
        System.out.println(event);
    }

    @Test
    void FlashAttribute_테스트() throws Exception {
        Event newEvent = new Event();
        newEvent.setName("Winter is gone");
        newEvent.setLimit(10000);

        mockMvc.perform(get("/event/list")
                .sessionAttr("visitTime", LocalDateTime.now())
                .flashAttr("newEvent", newEvent))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("categories"))
                .andExpect(xpath("//p").nodeCount(2));
    }

}