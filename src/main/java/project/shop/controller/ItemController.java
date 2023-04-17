package project.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import project.shop.entity.Item;
import project.shop.repository.ItemRepository;
import project.shop.service.ItemService;

@Controller
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/list")
    public String itemList() {
        return "item/list";
    }

    @GetMapping("/registration")
    public String registration() {
        return "item/registration";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Item item = itemService.findById(id);
        model.addAttribute("item", item);
        return "item/edit";
    }
}
