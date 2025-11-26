package com.ssg.mmm.member.service;

import com.ssg.mmm.member.entity.Member;

public interface MemberService {

    void save(String name, String loginId, String loginPw);

    Member find(String loginId, String loginPw);
}
