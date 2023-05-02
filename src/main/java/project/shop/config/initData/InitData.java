package project.shop.config.initData;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.shop.entity.Item;
import project.shop.entity.Member;
import project.shop.entity.Role;
import project.shop.service.ItemService;
import project.shop.service.MemberService;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitData {

    private final MemberService memberService;
    private final ItemService itemService;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initData() {
        Member member1 = Member.builder().
                email("TEST-Email-1").
                name("테스트 멤버1").
                picture("TEST-pic-1").
                role(Role.MEMBER).
                build();
        memberService.save(member1);

        Member member2 = Member.builder().
                email("TEST-Email-2").
                name("테스트 멤버2").
                picture("TEST-pic-2").
                role(Role.MEMBER).
                build();
        memberService.save(member2);

        Item item1=Item.builder().name("상품명1").price(10000).feature("상품설명1").build();
        itemService.save(item1);
        item1.setMember(member1);
        itemService.save(item1);

        Item item2=Item.builder().name("상품명2").price(20000).feature("상품설명2").build();
        itemService.save(item2);
        item2.setMember(member1);
        itemService.save(item2);
    }
}
