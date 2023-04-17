package project.shop.controller;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HomeController.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void 비회원_홈페이지_접속_테스트() throws Exception {
        mvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/home"));
    }

    @Test
    public void 회원_홈페이지_접속_테스트() throws Exception {
        mvc.perform(get("/home/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/home_member"));
    }
}