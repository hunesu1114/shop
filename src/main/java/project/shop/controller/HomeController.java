package project.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.shop.config.auth.dto.SessionMember;
import project.shop.entity.Member;
import project.shop.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @GetMapping("")
    public String home(Model model, HttpServletRequest request, @Nullable @RequestParam("logout") boolean logout) {
        model.addAttribute("loginStatus", false);
        if (logout == true) {
            return "home/home";
        } else if (request.getSession().getAttribute("member") == null) {
            return "home/home";
        } else if (!memberService.getMemberFromSession(request).isEmpty()) {
            return "redirect:/home/login";
        }
        return "home/home";
    }

    @GetMapping("/login")
    public String loginHome(Model model, HttpServletRequest request) {
        Member member = memberService.getMemberFromSession(request).orElseThrow();
        model.addAttribute("loginStatus", true);
        model.addAttribute("member", member);
        return "home/home";
    }

}
