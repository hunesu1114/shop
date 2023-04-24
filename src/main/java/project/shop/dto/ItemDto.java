package project.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.shop.entity.Member;

@Getter
@Setter
@NoArgsConstructor
public class ItemDto {
    private String name;
    private Integer price;
    private String feature;
    private Member member;
}
