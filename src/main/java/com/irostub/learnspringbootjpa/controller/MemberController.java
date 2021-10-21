package com.irostub.learnspringbootjpa.controller;

import com.irostub.learnspringbootjpa.controller.form.MemberForm;
import com.irostub.learnspringbootjpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String joinMemberView(@ModelAttribute("memberForm") MemberForm memberForm) {
        return "members/members";
    }

    @PostMapping("/members/new")
    public String joinMember(@Validated @ModelAttribute MemberForm memberForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.error("error={}", bindingResult);
            return "members/members";
        }

        memberService.join(memberForm.getName(), memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());
        redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다.");
        return "redirect:/";
    }

    @GetMapping("/members")
    public String memberListView(Model model) {
        model.addAttribute("members", memberService.findMembers());
        return "members/memberList";
    }
}
