package com.ssg.mmm.member.repository;

import com.ssg.mmm.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByLoginIdAndLoginPw(String loginId, String loginPw);
}
