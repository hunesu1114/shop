package project.shop.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(length = 500, nullable = false)
    private String feature;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Item(String name, Integer price, String feature, Member member) {
        this.name = name;
        this.price = price;
        this.feature = feature;
        this.member = member;
    }
}
