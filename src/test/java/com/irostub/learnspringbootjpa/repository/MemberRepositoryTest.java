package com.irostub.learnspringbootjpa.repository;

import com.irostub.learnspringbootjpa.domain.Address;
import com.irostub.learnspringbootjpa.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("맴버 저장")
    void save() {
        //given
        Member member = new Member();
        member.setName("irostub");
        member.setAddress(new Address("seoul", "street", "10000"));

        //when
        Long memberId = memberRepository.save(member);

        //then
        assertNotNull(memberId);
    }

    @Test
    @DisplayName("맴버 찾기")
    @Transactional
    void find() {
        //given
        Member member = new Member();
        member.setName("irostub");
        member.setAddress(new Address("seoul", "street", "10000"));
        Long memberId = memberRepository.save(member);

        //when
        Member findMember = memberRepository.find(memberId);

        //then
        assertEquals(member, findMember);
    }
}