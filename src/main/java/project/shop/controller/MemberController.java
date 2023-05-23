package project.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.shop.dto.MemberDto;
import project.shop.entity.Member;
import project.shop.entity.Order;
import project.shop.service.MemberService;
import project.shop.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final OrderService orderService;

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/home?logout=true";
    }

    @GetMapping("/registration")
    public String registration() {
        return "member/registration";
    }

    @GetMapping("/mypage/{id}")
    public String myPage(@PathVariable Long id, Model model) {
        Member member = memberService.findById(id);
        MemberDto dto = memberService.toMemberDto(member);
        model.addAttribute("member", dto);
        model.addAttribute("orders", member.getOrders());
        model.addAttribute("sellingItems", member.getSellingItems());
        model.addAttribute("id", member.getId());
        return "member/mypage";
    }

    @GetMapping("/{memberId}/cart")
    public String cart(@PathVariable Long memberId, @RequestParam("orderId") Long orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        model.addAttribute("memberId", memberId);
        return "member/cart";
    }

    @GetMapping("/{memberId}/order")
    public String OrderPage(@PathVariable Long memberId, @RequestParam("orderId") Long orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "order/order";
    }

    @GetMapping("/paymentLogic/{memberId}")
    public String paymentPage(@PathVariable Long memberId, @RequestParam("orderId") Long orderId, Model model) {
        Member member = memberService.findById(memberId);
        model.addAttribute("member", member);
        model.addAttribute("orderId", orderId);
        return "order/payment";
    }


}
