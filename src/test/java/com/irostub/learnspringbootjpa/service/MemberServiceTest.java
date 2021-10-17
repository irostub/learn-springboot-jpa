package com.irostub.learnspringbootjpa.service;

import com.irostub.learnspringbootjpa.domain.Member;
import com.irostub.learnspringbootjpa.dto.MemberDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
        MemberDto memberDto = new MemberDto();
        memberDto.setName("irostub");

        //when
        Long memberId = memberService.join(memberDto);

        //then
        assertNotNull(memberId);
    }

    @Test
    @DisplayName("맴버 가입 실패(중복 회원)")
    void joinFail() {
        //given
        MemberDto memberDto = new MemberDto();
        memberDto.setName("irostub");
        memberService.join(memberDto);

        //when
        MemberDto memberDto1 = new MemberDto();
        memberDto1.setName("irostub");

        //then
        assertThrows(IllegalStateException.class,()->memberService.join(memberDto1));
    }

    @Test
    @DisplayName("맴버 전체 검색")
    void findMembers() {
        //given
        MemberDto memberDto1 = new MemberDto();
        memberDto1.setName("irostub");
        MemberDto memberDto2 = new MemberDto();
        memberDto2.setName("iro");

        memberService.join(memberDto1);
        memberService.join(memberDto2);

        //when
        List<Member> members = memberService.findMembers();

        //then
        assertEquals(members.size(), 2);
    }

    @Test
    @DisplayName("맴버 단일 조회")
    void findOne() {
        MemberDto memberDto1 = new MemberDto();
        memberDto1.setName("irostub");
        Long joinMemberId = memberService.join(memberDto1);

        Member findMember = memberService.findOne(joinMemberId);
        assertNotNull(findMember);
    }
}