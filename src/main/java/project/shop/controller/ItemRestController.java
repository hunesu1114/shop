package project.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.shop.config.auth.dto.SessionMember;
import project.shop.dto.ItemDto;
import project.shop.entity.Item;
import project.shop.entity.Member;
import project.shop.entity.Order;
import project.shop.service.ItemService;
import project.shop.service.MemberService;
import project.shop.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemRestController {

    private final ItemService itemService;
    private final MemberService memberService;
    private final OrderService orderService;

    @PostMapping("/registration/api")
    public Long save(@RequestBody ItemDto itemDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        SessionMember sessionMember = (SessionMember) session.getAttribute("member");

        Item item = itemService.save(itemDto.toEntity());
        item.setMember(memberService.findByEmail(sessionMember.getEmail()).orElseThrow());

        return item.getId();
    }

    @PutMapping("/edit/{id}")
    public Item update(@PathVariable Long id, @RequestBody ItemDto dto) {
        return itemService.update(id, dto);
    }

    @PostMapping("/{id}")
    public void addItemToCart(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Member member = memberService.getMemberFromSession(request).orElseThrow();
        Order order = orderService.orderItem(member.getId(), id, 1);
        String redirectUrl="/member/"+member.getId()+"/cart?orderId="+order.getId();
        response.sendRedirect(redirectUrl);
    }

    @PostMapping("/list/{page}")
    public void mainPage(@RequestParam String keyword, HttpServletResponse response) throws IOException {
        //sendRedirect 쓸 때, 인코딩 해줘야 함
        response.sendRedirect("/item/list/search?search=" + URLEncoder.encode(keyword, "UTF-8"));
    }

    @PostMapping("/list/search")
    public void searchMain(@RequestParam String keyword, HttpServletResponse response) throws IOException {
        response.sendRedirect("/item/list/search?search=" + URLEncoder.encode(keyword, "UTF-8"));
    }

}
