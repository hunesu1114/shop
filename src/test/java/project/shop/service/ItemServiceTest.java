package project.shop.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.shop.entity.Item;
import project.shop.pagination.PagingConst;
import project.shop.repository.ItemRepository;
import java.util.List;
import java.util.NoSuchElementException;
import static org.assertj.core.api.Assertions.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @Before
    public void initData() {
        for (int i = 1; i <= 6; i++) {
            Item item = Item.builder().name("item" + i).price(1000+i).feature("feature" + i).build();
            itemService.save(item);
        }
    }

    @After
    public void clear() {
        itemService.clear();
    }

    @Test
    public void save() {
        Item item = Item.builder().name("name").price(100).feature("feat").build();
        Item savedItem = itemService.save(item);
        assertThat(item.getName()).isEqualTo(savedItem.getName());
        assertThat(item.getPrice()).isEqualTo(savedItem.getPrice());
        assertThat(item.getFeature()).isEqualTo(savedItem.getFeature());
    }

    @Test
    public void findById() {
        Item item = Item.builder().name("이름").price(1000).feature("설명").build();
        Item savedItem = itemService.save(item);
        Item findItem = itemService.findById(savedItem.getId());
        assertThat(findItem.getName()).isEqualTo("이름");
    }

    @Test
    public void findAllByPage() {
        int page=1;
        Pageable pageable = PageRequest.of(page - 1, PagingConst.POST_CNT_PER_PAGE, Sort.by("id").descending());
        List<Item> items = itemService.findAllByPage(pageable);
        int itemCnt = items.size();
        for (int i = 1; i <= 6; i++) {
            assertThat(items.get(i - 1).getName()).isEqualTo("item" + (itemCnt - i + 1));
            assertThat(items.get(i - 1).getPrice()).isEqualTo(1000 + (itemCnt - i + 1));
            assertThat(items.get(i - 1).getFeature()).isEqualTo("feature" + (itemCnt - i + 1));
        }
    }

    @Test
    public void itemCount() {
        assertThat(itemService.itemCount()).isEqualTo(6);
    }

    @Test
    public void delete() {
        itemService.deleteItem(itemService.findById(6L));
        assertThatThrownBy(() -> itemService.findById(6L)).isInstanceOf(NoSuchElementException.class);
    }


}