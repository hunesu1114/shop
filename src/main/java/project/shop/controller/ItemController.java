package project.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.shop.config.auth.dto.SessionMember;
import project.shop.dto.ItemDto;
import project.shop.dto.MemberDto;
import project.shop.entity.Item;
import project.shop.entity.Member;
import project.shop.entity.Order;
import project.shop.pagination.Pagination;
import project.shop.pagination.PagingConst;
import project.shop.repository.ItemRepository;
import project.shop.service.ItemService;
import project.shop.service.MemberService;
import project.shop.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final MemberService memberService;
    private final OrderService orderService;

    @GetMapping("/list/{page}")
    public String itemList(@PathVariable int page, Model model) {
        Pageable pageable = PageRequest.of(page - 1, PagingConst.POST_CNT_PER_PAGE, Sort.by("id").descending());

        List<Item> items = itemService.findAllByPage(pageable);
        Pagination pagination = new Pagination(itemService.itemCount(), page);

        //LocalDateTime 깔끔하게 세팅
        for (Item item : items) {
            item.setTimeString();
        }

        model.addAttribute("pagination", pagination);
        model.addAttribute("pagesInCurrentBlock", pagination.pagesInCurrentBlock());
        model.addAttribute("items", items);
        model.addAttribute("keyword", new StringBuilder());

        return "item/list";
    }

    @GetMapping("/registration")
    public String registration(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        SessionMember sessionMember = (SessionMember)session.getAttribute("member");

        Optional<Member> member = memberService.findByEmail(sessionMember.getEmail());
        if(member.isEmpty()){
            return "redirect:/member/login";
        }
        MemberDto memberDto = memberService.toMemberDto(member.orElseThrow());

        log.info("{}", memberDto.getEmail());
        log.info("{}", memberDto.getName());
        log.info("{}", memberDto.getPicture());

        model.addAttribute("item", new ItemDto());
        model.addAttribute("member", memberDto);

        return "item/registration";
    }

    @GetMapping("/{id}")
    public String item(@PathVariable Long id,Model model,HttpServletRequest request) {
        Item item = itemService.findById(id);
        Member seller = item.getMember();

        Optional<Member> member = memberService.getMemberFromSession(request);
        if (member.isEmpty()) {
            model.addAttribute("isSeller", false);
            return "redirect:/member/login";
        } else{
            if (seller.getEmail().equals(member.orElseThrow().getEmail())) {
                model.addAttribute("isSeller", true);
            } else {
                model.addAttribute("isSeller", false);
            }
        }

        model.addAttribute("item", item);
        model.addAttribute("member", member.get());
        return "item/item";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Item item = itemService.findById(id);
        model.addAttribute("item", item.toDto());
        model.addAttribute("id", id);
        return "item/edit";
    }



    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Item item = itemService.findById(id);
        itemService.deleteItem(item);
        return "redirect:/item/list/1";
    }



    @GetMapping("/list/search")
    public String searchMain(@RequestParam("search") String keyword, Model model) {
        List<Item> rs = itemService.searchItem(keyword);

        model.addAttribute("items", rs);
        model.addAttribute("keyword", keyword);
        return "item/search";
    }


}
