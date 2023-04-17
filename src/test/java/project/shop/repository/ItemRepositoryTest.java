package project.shop.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import project.shop.entity.Item;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @AfterEach
    public void deleteAll() {
        itemRepository.deleteAll();
    }

    @Test
    public void Item_save_findAll_테스트() {
        String name = "상품이름1";
        Integer price = 10000;
        String feature = "상품설명1";

        itemRepository.save(Item.builder()
                .name(name)
                .price(price)
                .feature(feature)
                .build());

        List<Item> itemList = itemRepository.findAll();
        Item findItem = itemList.get(0);

        Assertions.assertThat(findItem.getName()).isEqualTo(name);
        Assertions.assertThat(findItem.getPrice()).isEqualTo(price);
        Assertions.assertThat(findItem.getFeature()).isEqualTo(feature);
    }


}