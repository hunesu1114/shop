package project.shop.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.shop.dto.ItemDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Item extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(length = 500, nullable = false)
    private String feature;

    //구매시
    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems=new ArrayList<>();

    //판매시
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Item(String name, Integer price, String feature, Member member) {
        this.name = name;
        this.price = price;
        this.feature = feature;
        this.member = member;
    }

    //연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getSellingItems().add(this);
    }

    public ItemDto toDto() {
        return ItemDto.builder().name(this.name).price(this.price).feature(this.feature).member(this.member).build();
    }

    public Item update(ItemDto dto) {
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.feature = dto.getFeature();
        return this;
    }

}
