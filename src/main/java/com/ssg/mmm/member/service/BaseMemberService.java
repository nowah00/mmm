package com.ssg.mmm.member.service;

import com.ssg.mmm.member.entity.Member;
import com.ssg.mmm.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseMemberService implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void save(String name, String loginId, String loginPw) {
        memberRepository.save(new Member(name, loginId, loginPw));
    }

    @Override
    public Member find(String loginId, String loginPw) {
        Optional<Member> member = memberRepository.findByLoginIdAndLoginPw(loginId, loginPw);
        return member.orElse(null);
    }
}
