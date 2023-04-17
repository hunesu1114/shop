package project.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop.entity.Item;

public interface ItemRepository extends JpaRepository<Item,Long> {
}
