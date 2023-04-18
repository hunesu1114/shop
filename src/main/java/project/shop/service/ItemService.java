package project.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.entity.Item;
import project.shop.repository.ItemRepository;

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

    public List<Item> findAllByPage(Pageable pageable) {
        return itemRepository.findAll(pageable).getContent();
    }

    public int itemCount() {
        return Long.valueOf(itemRepository.count()).intValue();
    }
}
