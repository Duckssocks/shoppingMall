package com.example.shopingmall.repository;


import com.example.shopingmall.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository {
    public Member save(Member member); // 저장소에 저장하기

    public Optional<Member> findByLoginId(String id);

    public Member findById(Long id);

    public List<Member> findAll();
    public void clearStore();

}
