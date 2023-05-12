package project.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    public List<Item> findByNameContaining(String keyword);

    public List<Item> findByFeatureContaining(String keyword);

}
