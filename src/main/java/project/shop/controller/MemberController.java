package project.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.shop.config.auth.dto.SessionMember;
import project.shop.dto.MemberDto;
import project.shop.entity.Member;
import project.shop.entity.Order;
import project.shop.entity.OrderItem;
import project.shop.repository.MemberRepository;
import project.shop.service.MemberService;
import project.shop.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

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

    /**
     * 모델을 DTO로 주고 받아야 함..
     * quantity 변경해도 order.html 페이지에 반영 x   -> ajax 좀 배워야 처리 가능 할 듯
     */
    @PostMapping("/{memberId}/cart")
    public String cart(@PathVariable Long memberId, @RequestParam("orderId") Long orderId,
                       @ModelAttribute Order order, RedirectAttributes redirectAttributes) {
        Order orderEntity = orderService.findById(orderId);
        orderEntity.setOrderItems(order.getOrderItems());
        Order savedOrder = orderService.save(orderEntity);

        redirectAttributes.addAttribute("memberId", memberId);
        redirectAttributes.addAttribute("orderId", savedOrder.getId());

        for (OrderItem oi : savedOrder.getOrderItems()) {
            log.info("======orderItem Name : {}", oi.getItem().getName());
            log.info("======orderItem Quantity: {}", oi.getQuantity());
        }
        return "redirect:/member/{memberId}/order?orderId={orderId}";
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
        return "order/payment";
    }

    @PostMapping("/paymentLogic/{memberId}")
    public String paymentPage(@PathVariable Long memberId, @RequestParam("orderId") Long orderId, RedirectAttributes redirectAttributes) {
        orderService.order(orderId);
        redirectAttributes.addAttribute("memberId", memberId);
        return "redirect:/member/mypage/{memberId}";
    }
}
