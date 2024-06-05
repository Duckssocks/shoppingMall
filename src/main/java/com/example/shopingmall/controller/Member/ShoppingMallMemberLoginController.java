package com.example.shopingmall.controller.Member;


import com.example.shopingmall.domain.Member;
import com.example.shopingmall.repository.MemberRepository;
import com.example.shopingmall.repository.MemberRepositoryImpl;
import com.example.shopingmall.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
public class ShoppingMallMemberLoginController {
    private final SessionManager sessionManager;
    MemberRepository memberRepository = new MemberRepositoryImpl();
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginPage() {
        return "login/loginPage";
    }

    @PostMapping("/login")
    public String loginV4(@Valid @ModelAttribute LoginForm form, Model model, @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request) {

        Member loginMember = loginService.login(form.getLoginId(),
                form.getPassword());
        log.info("login? {}", loginMember);
        if (loginMember == null) {
            model.addAttribute("loginError", "아이디, 비밀번호가 틀렸습니다. 다시 입력해주세요.");
            return "login/loginPage";
        }
        // 로그인 성공 처리
        // 세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        // 세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        // 세션을 삭제함.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
    // 사용자 페이지 (사용자의 정보를 볼 수 있음)
    @GetMapping("/{loginId}")
    public String userPage(@PathVariable("loginId") String loginId, Model model) {
        Member member = memberRepository.findByLoginId(loginId).orElse(null);
        model.addAttribute("member", member);
        return "userPage";
    }


}
