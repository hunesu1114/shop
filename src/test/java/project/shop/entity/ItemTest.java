package project.shop.entity;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemTest {

    private Member member;

    @Test
    @DisplayName("Item getter,builder 테스트")
    public void Item_getter_Builder_테스트() {
        Item item = Item.builder()
                .name("이름1")
                .price(10000)
                .feature("상품설명1")
                .build();

        Assertions.assertThat(item.getName()).isEqualTo("이름1");
        Assertions.assertThat(item.getPrice()).isEqualTo(10000);
        Assertions.assertThat(item.getFeature()).isEqualTo("상품설명1");
    }
}