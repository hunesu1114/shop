package project.shop.controller;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import project.shop.dto.ItemDto;
import project.shop.entity.Item;
import project.shop.repository.ItemRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @After
    public void clear() {
        itemRepository.deleteAll();
    }

    @Test
    public void save() {
        String name = "name";
        Integer price = 10000;
        String feature = "feature";

        ItemDto dto = ItemDto.builder().name(name).price(price).feature(feature).build();
        String url = "http://localhost:" + port + "/item/registration/api";
        MockHttpServletRequest requset = new MockHttpServletRequest();
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, dto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Item> all = itemRepository.findAll();
        assertThat(all.get(0).getName()).isEqualTo(name);
        assertThat(all.get(0).getPrice()).isEqualTo(price);
        assertThat(all.get(0).getFeature()).isEqualTo(feature);
    }
}