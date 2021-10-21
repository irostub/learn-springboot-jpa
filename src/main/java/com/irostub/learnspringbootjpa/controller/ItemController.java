package com.irostub.learnspringbootjpa.controller;

import com.irostub.learnspringbootjpa.controller.form.BookCreateForm;
import com.irostub.learnspringbootjpa.controller.form.BookUpdateForm;
import com.irostub.learnspringbootjpa.domain.item.Book;
import com.irostub.learnspringbootjpa.domain.item.Item;
import com.irostub.learnspringbootjpa.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String addItemView(@ModelAttribute("form") BookCreateForm bookCreateForm) {
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String addItem(@Validated @ModelAttribute("form") BookCreateForm bookCreateForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.error("error={}", bindingResult);
            return "items/createItemForm";
        }

        itemService.saveItem(
                bookCreateForm.getName(),
                bookCreateForm.getPrice(),
                bookCreateForm.getStockQuantity(),
                bookCreateForm.getAuthor(),
                bookCreateForm.getIsbn());

        redirectAttributes.addFlashAttribute("message", "물품이 정상적으로 등록되었습니다.");
        return "redirect:/";
    }

    @GetMapping("/items")
    public String itemListView(Model model) {
        model.addAttribute("items", itemService.findItems());
        return "items/itemList";
    }

    @GetMapping("/items/{id}/edit")
    public String updateItemView(@PathVariable Long id, Model model) {
        Book one = (Book)itemService.findOne(id);
        BookUpdateForm bookUpdateForm = new BookUpdateForm(one);

        model.addAttribute("form", bookUpdateForm);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{id}/edit")
    public String updateItem(@PathVariable Long id,
                             @Validated @ModelAttribute BookUpdateForm bookUpdateForm,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.error("error={}", bindingResult);
            return "items/updateItemForm";
        }

        itemService.updateItem(id, bookUpdateForm);

        redirectAttributes.addFlashAttribute("message", "아이템이 수정되었습니다.");
        return "redirect:/";
    }
}
