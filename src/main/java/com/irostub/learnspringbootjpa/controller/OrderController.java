package com.irostub.learnspringbootjpa.controller;

import com.irostub.learnspringbootjpa.controller.form.OrderForm;
import com.irostub.learnspringbootjpa.controller.form.OrderSearch;
import com.irostub.learnspringbootjpa.domain.Member;
import com.irostub.learnspringbootjpa.domain.Order;
import com.irostub.learnspringbootjpa.domain.item.Item;
import com.irostub.learnspringbootjpa.service.ItemService;
import com.irostub.learnspringbootjpa.service.MemberService;
import com.irostub.learnspringbootjpa.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OrderController {
    private final ItemService itemService;
    private final MemberService memberService;
    private final OrderService orderService;

    @GetMapping("/order")
    public String addOrderView(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String addOrder(@ModelAttribute OrderForm orderForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "order/orderForm";
        }
        orderService.order(orderForm.getMemberId(), orderForm.getItemId(), orderForm.getCount());
        redirectAttributes.addFlashAttribute("message", "주문이 완료되었습니다");
        return "redirect:/";
    }

    @GetMapping("/orders")
    public String orderListView(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        log.info("orderSearch={}",orderSearch);

        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @PostMapping("/orders/{id}/cancel")
    public String orderCancel(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return "redirect:/";
    }
}
