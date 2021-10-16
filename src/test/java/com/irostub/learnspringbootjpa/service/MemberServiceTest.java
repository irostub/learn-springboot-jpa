package com.irostub.learnspringbootjpa.service;

import com.irostub.learnspringbootjpa.domain.Member;
import com.irostub.learnspringbootjpa.dto.MemberFormDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("맴버 가입")
    void join() {
        //given
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setName("irostub");

        //when
        Long memberId = memberService.join(memberFormDto);

        //then
        assertNotNull(memberId);
    }

    @Test
    @DisplayName("맴버 가입 실패(중복 회원)")
    void joinFail() {
        //given
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setName("irostub");
        memberService.join(memberFormDto);

        //when
        MemberFormDto memberFormDto1 = new MemberFormDto();
        memberFormDto1.setName("irostub");

        //then
        assertThrows(IllegalStateException.class,()->memberService.join(memberFormDto1));
    }

    @Test
    @DisplayName("맴버 전체 검색")
    void findMembers() {
        //given
        MemberFormDto memberFormDto1 = new MemberFormDto();
        memberFormDto1.setName("irostub");
        MemberFormDto memberFormDto2 = new MemberFormDto();
        memberFormDto2.setName("iro");

        memberService.join(memberFormDto1);
        memberService.join(memberFormDto2);

        //when
        List<Member> members = memberService.findMembers();

        //then
        assertEquals(members.size(), 2);
    }

    @Test
    @DisplayName("맴버 단일 조회")
    void findOne() {
        MemberFormDto memberFormDto1 = new MemberFormDto();
        memberFormDto1.setName("irostub");
        Long joinMemberId = memberService.join(memberFormDto1);

        Member findMember = memberService.findOne(joinMemberId);
        assertNotNull(findMember);
    }
}