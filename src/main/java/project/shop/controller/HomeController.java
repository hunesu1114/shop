package project.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping("")
    public String home() {
        log.info("비회원 홈페이지");
        return "home/home";
    }

    @GetMapping("/login")
    public String home_member() {
        log.info("회원 홈페이지");
        return "home/home_member";
    }
}
