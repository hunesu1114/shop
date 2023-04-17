package project.shop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.shop.entity.Item;
import project.shop.entity.Member;
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
        Member member = Member.builder().email("kewcxea@naver.com").build();
        memberService.save(member);

        Item item=Item.builder().name("상품명1").price(10000).feature("상품설명1").build();
        itemService.save(item);
    }
}
