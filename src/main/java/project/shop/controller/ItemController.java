package project.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.shop.entity.Item;
import project.shop.pagination.Pagination;
import project.shop.pagination.PagingConst;
import project.shop.repository.ItemRepository;
import project.shop.service.ItemService;

import java.util.List;

@Controller
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/list/{page}")
    public String itemList(@PathVariable int page, Model model) {
        Pageable pageable = PageRequest.of(page - 1, PagingConst.POST_CNT_PER_PAGE, Sort.by("id").descending());
        List<Item> items = itemService.findAllByPage(pageable);
        Pagination pagination = new Pagination(itemService.itemCount(), page);

        model.addAttribute("pagination", pagination);
        model.addAttribute("pagesInCurrentBlock", pagination.pagesInCurrentBlock());
        model.addAttribute("items", items);
        model.addAttribute("keyword", new StringBuilder());

        return "item/list";
    }

    @GetMapping("/registration")
    public String registration() {
        return "item/registration";
    }

    @GetMapping("/{id}")
    public String item(@PathVariable Long id,Model model) {
        Item item = itemService.findById(id);
        model.addAttribute("item", item);
        return "item/item";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Item item = itemService.findById(id);
        model.addAttribute("item", item);
        return "item/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Item item = itemService.findById(id);
        itemService.deleteItem(item);
        return "redirect:/item/list/1";
    }

}
