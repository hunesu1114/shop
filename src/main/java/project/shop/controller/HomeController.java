package project.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.shop.config.auth.dto.SessionMember;
import project.shop.entity.Member;
import project.shop.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @GetMapping("")
    public String home(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("member") == null) {
            model.addAttribute("loginStatus", false);
        }else{
            model.addAttribute("loginStatus", true);
            SessionMember member = (SessionMember) session.getAttribute("member");
            String memberName = member.getName();
            model.addAttribute("memberName", memberName);
        }
        return "home/home";

    }

}
