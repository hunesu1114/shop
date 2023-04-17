package project.shop.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import project.shop.service.MemberService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * postMapping 검증해야함
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MemberService memberService;

    @Test
    public void member_로그인_페이지() throws Exception {
        mvc.perform(get("/member/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/login"));
    }

    @Test
    public void member_회원가입_페이지() throws Exception {
        mvc.perform(get("/member/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/registration"));
    }

    @Test
    public void member_개인_페이지() throws Exception {
        mvc.perform(get("/member/mypage/{id}","1"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/mypage"));
    }

    @Test
    public void member_회원수정_페이지() throws Exception {
        mvc.perform(get("/member/edit/{id}","1"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/edit"));
    }
}