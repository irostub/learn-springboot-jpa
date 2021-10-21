package com.irostub.learnspringbootjpa.service;

import com.irostub.learnspringbootjpa.domain.Member;
import com.irostub.learnspringbootjpa.controller.form.MemberForm;
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
        MemberForm memberForm = new MemberForm();
        memberForm.setName("irostub");
        memberForm.setCity("city");
        memberForm.setStreet("street");
        memberForm.setZipcode("zipcode");

        //when
        Long memberId = memberService.join(memberForm.getName(), memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        //then
        assertNotNull(memberId);
    }

    @Test
    @DisplayName("맴버 가입 실패(중복 회원)")
    void joinFail() {
        //given
        MemberForm memberForm = new MemberForm();
        memberForm.setName("irostub");
        memberForm.setCity("city");
        memberForm.setStreet("street");
        memberForm.setZipcode("zipcode");
        memberService.join(memberForm.getName(), memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        //when
        MemberForm memberForm2 = new MemberForm();
        memberForm2.setName("irostub");
        memberForm2.setCity("city");
        memberForm2.setStreet("street");
        memberForm2.setZipcode("zipcode");

        //then
        assertThrows(IllegalStateException.class, () -> memberService.join(memberForm2.getName(), memberForm2.getCity(), memberForm2.getStreet(), memberForm2.getZipcode()));
    }

    @Test
    @DisplayName("맴버 전체 검색")
    void findMembers() {
        //given
        MemberForm memberForm1 = new MemberForm();
        memberForm1.setName("irostub");
        memberForm1.setCity("city");
        memberForm1.setStreet("street");
        memberForm1.setZipcode("zipcode");
        MemberForm memberForm2 = new MemberForm();
        memberForm2.setName("iro");
        memberForm2.setCity("city");
        memberForm2.setStreet("street");
        memberForm2.setZipcode("zipcode");

        memberService.join(memberForm1.getName(), memberForm1.getCity(), memberForm1.getStreet(), memberForm1.getZipcode());
        memberService.join(memberForm2.getName(), memberForm2.getCity(), memberForm2.getStreet(), memberForm2.getZipcode());

        //when
        List<Member> members = memberService.findMembers();

        //then
        assertEquals(members.size(), 2);
    }

    @Test
    @DisplayName("맴버 단일 조회")
    void findOne() {
        MemberForm memberForm1 = new MemberForm();
        memberForm1.setName("irostub");
        memberForm1.setCity("city");
        memberForm1.setStreet("street");
        memberForm1.setZipcode("zipcode");
        Long joinMemberId = memberService.join(memberForm1.getName(), memberForm1.getCity(), memberForm1.getStreet(), memberForm1.getZipcode());

        Member findMember = memberService.findOne(joinMemberId);
        assertNotNull(findMember);
    }
}