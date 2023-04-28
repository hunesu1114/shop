package project.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.shop.entity.Member;
import project.shop.entity.Order;
import project.shop.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final MemberService memberService;

    @GetMapping("/{memberId}/{itemId}")
    public void orderItem(@PathVariable("memberId") Long memberId, @PathVariable("itemId") Long itemId) {
        Member member = memberService.findById(memberId);
        int lastIndex = member.getOrders().size()-1;
        Order latestOrder = member.getOrders().get(lastIndex);
        if (latestOrder.getDelivery().equals("DONE")) {
            //createOrder
        } else {    //delivery : READY
            //latestOrder에 해당 아이템 추가
        }
    }
}
