package project.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.shop.config.auth.dto.SessionMember;
import project.shop.dto.MemberDto;
import project.shop.entity.Member;
import project.shop.entity.Order;
import project.shop.repository.MemberRepository;
import project.shop.service.MemberService;
import project.shop.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

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
        return "redirect:/home";
    }

    @GetMapping("/registration")
    public String registration() {
        return "member/registration";
    }

    @GetMapping("/mypage")
    public String myPageRedir(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        SessionMember sessionMember = (SessionMember) session.getAttribute("member");
        Member member = memberService.findByEmail(sessionMember.getEmail());
        redirectAttributes.addAttribute("id", member.getId());
        return "redirect:/member/mypage/{id}";
    }

    @GetMapping("/mypage/{id}")
    public String myPage(@PathVariable Long id, Model model) {
        Member member = memberService.findById(id);
        MemberDto dto = memberService.toMemberDto(member);
        model.addAttribute("member", dto);
        return "member/mypage";
    }

    @PostMapping("/mypage/{id}")
    public String myPage(@PathVariable Long id, @ModelAttribute MemberDto dto, RedirectAttributes redirectAttributes) {
        Member member = memberService.findById(id);
        member.updateNickName(dto.getNickName());
        redirectAttributes.addAttribute("id", id);
        redirectAttributes.addAttribute("edited", true);
        return "redirect:/member/mypage/{id}";
    }


    @GetMapping("/paymentLogic/{memberId}")
    public String paymentPage(@PathVariable Long memberId, @RequestParam("orderId") Long orderId, Model model) {
        Member member = memberService.findById(memberId);
        model.addAttribute("orderId", orderId);
        model.addAttribute("member", member);
        return "member/payment";
    }

    @PostMapping("/paymentLogic/{memberId}")
    public String paymentPage(@PathVariable Long memberId, @ModelAttribute Long orderId, RedirectAttributes redirectAttributes) {
        orderService.order(orderId);
        redirectAttributes.addAttribute("memberId", memberId);
        return "redirect:/member/mypage/{memberId}";
    }
}
