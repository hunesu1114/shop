package project.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.dto.ItemDto;
import project.shop.entity.Item;
import project.shop.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public Item findById(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        return item.orElseThrow();
    }

    public List<Item> searchItem(String keyword) {
        List<Item> rs1 = itemRepository.findByNameContaining(keyword);
        List<Item> rs2 = itemRepository.findByFeatureContaining(keyword);
        List<Item> resultSet = new ArrayList<>();
        resultSet.addAll(rs1);
        resultSet.addAll(rs2);
        return resultSet;
    }

    public Item update(Long id, ItemDto dto) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id=" + id));
        item.update(dto);
        return item;
    }

    public List<Item> findAllByPage(Pageable pageable) {
        return itemRepository.findAll(pageable).getContent();
    }

    public int itemCount() {
        return Long.valueOf(itemRepository.count()).intValue();
        //count()의 리턴값이 long이라서 Long으로 변환 후 int로
    }

    public void deleteItem(Item item) {
        itemRepository.delete(item);
    }

    public void clear() {
        itemRepository.deleteAll();
    }
}
