package com.ssg.mmm.account.helper;

import com.ssg.mmm.account.dto.AccountJoinRequests;
import com.ssg.mmm.account.dto.AccountLoginRequests;
import com.ssg.mmm.account.etc.AccountConstants;
import com.ssg.mmm.common.util.HttpUtils;
import com.ssg.mmm.member.entity.Member;
import com.ssg.mmm.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionAccountHelper implements AccountHelper {

    private final MemberService memberService;

    @Override
    public void join(AccountJoinRequests joinReq) {
        memberService.save(joinReq.getName(), joinReq.getLoginId(), joinReq.getLoginPw());
    }

    @Override
    public String login(AccountLoginRequests loginReq, HttpServletRequest request, HttpServletResponse response) {
        Member member = memberService.find(loginReq.getLoginId(), loginReq.getLoginPw());

        // 회원 데이터가 없다면 null 반환
        if (member == null) {
            return null;
        }
        // 회원 데이터를 가져오면 세션에 남
        HttpUtils.setSession(request, AccountConstants.Member_ID_NAME, member.getId());
        return member.getLoginId().toString();
    }

    @Override
    public Integer getMemberId(HttpServletRequest request) {
        Object memberId = HttpUtils.getSession(request, AccountConstants.Member_ID_NAME);
        if (memberId == null) {
            return null;
        }
        return (Integer) memberId;
    }

    @Override
    public boolean isLoggedIn(HttpServletRequest request) {
        return getMemberId(request) != null;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpUtils.removeSession(request, AccountConstants.Member_ID_NAME);
    }
}
