package project.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.shop.entity.Item;
import project.shop.entity.Member;

@Getter
@Setter
@NoArgsConstructor
public class ItemDto {
    private String name;
    private Integer price;
    private String feature;
    private Member member;

    public Item toEntity() {
        Item item = Item.builder().name(this.name).price(this.price).feature(this.feature).build();
        return item;
    }

    @Builder
    public ItemDto(String name, Integer price, String feature, Member member) {
        this.name = name;
        this.price = price;
        this.feature = feature;
        this.member = member;
    }
}
