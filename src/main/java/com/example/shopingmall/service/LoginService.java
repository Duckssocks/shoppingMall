package com.example.shopingmall.service;

import com.example.shopingmall.domain.Member;
import com.example.shopingmall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    public Member login(String loginId, String password) {
        Optional<Member> member = memberRepository.findByLoginId(loginId);
        
        // 입력된 아이디 비밀번호를 토대로 멤버를 리턴함. 만약 멤버가 없으면 null을 반환
        return member.filter(m -> m.getPassword().equals(password)).orElse(null);
    }
}
