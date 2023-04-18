package project.shop.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import project.shop.service.ItemService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * postMapping 검증해야함
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ItemController.class)
public class ItemControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemService itemService;

    @Test
    public void item리스트_조회() throws Exception {
        mvc.perform(get("/item/list/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("item/list"));
    }

    @Test
    public void item등록() throws Exception {
        mvc.perform(get("/item/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("item/registration"));
    }

    @Test
    public void item수정() throws Exception {
        mvc.perform(get("/item/edit/{id}","1"))
                .andExpect(status().isOk())
                .andExpect(view().name("item/edit"));

    }
}