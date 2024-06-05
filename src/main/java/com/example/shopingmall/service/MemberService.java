package com.example.shopingmall.service;

import com.example.shopingmall.domain.Member;
import com.example.shopingmall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor @Service
public class MemberService {
    private final MemberRepository memberRepository;

    public String join(Member member) {
        // 같은 이름이 존재하는 중복 회원은 불가.
        memberRepository.findByLoginId(member.getLoginId())
                .ifPresent(m->{
                    throw new IllegalStateException();
                });
        memberRepository.save(member);
        return member.getLoginId();
    }
}
