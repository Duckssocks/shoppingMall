package com.example.shopingmall.controller.Member;

import com.example.shopingmall.domain.Member;
import com.example.shopingmall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j @RequestMapping("member/signup") @RequiredArgsConstructor
public class ShoppingMallMemberJoinController {
    private final MemberService memberService;

    // Get 방식으로 요청한 경우에 실행. join/joinPage를 반환
    @GetMapping
    public String JoinPage(@ModelAttribute Member member, Model model) {
        // addAttribute : 사용자가 join form에 입력한 내용을 유지하면서 redirect하기 위함.
        model.addAttribute("member", member);
        return "join/joinPage";
    }
    // Post 방식으로 요청했을 때 실행, 사용자가 입력한 내용에 대해 submit를 누른 경우, 실행
    @PostMapping
    public String CreateMember(@ModelAttribute Member member, BindingResult bindingResult) {
        // 검증
        if (!StringUtils.hasText(member.getName())) {
            bindingResult.addError((new FieldError("member", "name", "문자를 적으셔야 합니다.")));
        }
        if (!StringUtils.hasText(member.getLoginId()) || member.getLoginId().length()<3 || member.getLoginId().length() > 10) {
            bindingResult.addError(new FieldError("member", "loginId", "올바르지 않은 ID: 문자가 있어야 하며, 아이디의 길이는 3자 이상 10자 이하여야 합니다."));
        }
        if(member.getPassword().length() < 3 || member.getPassword().length() > 10) {
            bindingResult.addError(new FieldError("member", "password", "올바르지 않은 PASSWORD: 비밀번호의 길이는 3자 이상 10자 이하여야 합니다."));
        }
        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);
            return "join/joinPage";
        }
        memberService.join(member);
        log.info("member={}", member);
        return "redirect:/";
    }
}
