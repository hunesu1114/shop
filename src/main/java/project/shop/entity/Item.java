package project.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.shop.dto.ItemDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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


    //판매시
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore //이거 안해주면 stackoverflow(무한참조)
    private Member member;

    @Transient  //column으로 안만듦
    private String createDateTimeString;

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

    public void setTimeString() {
        LocalDateTime source = this.getCreatedDate();
        int month = source.getMonth().getValue();
        int day = source.getDayOfMonth();
        int hour = source.getHour();
        int minute = source.getMinute();
        StringBuilder string = new StringBuilder();
        string.append(month);
        string.append("월");
        string.append(day);
        string.append("일 ");
        string.append(hour);
        string.append(":");
        string.append(minute);
        this.createDateTimeString = string.toString();
        log.info("=========CDTS : {}",this.createDateTimeString);
    }



}
