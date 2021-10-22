package com.irostub.learnspringbootjpa.service;

import com.irostub.learnspringbootjpa.domain.Member;
import com.irostub.learnspringbootjpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    @Transactional
    public Long join(String name, String city, String street, String zipcode) {
        validateDuplicateMember(name);
        Member newMember = Member.createNewMember(name, city, street, zipcode);
        memberRepository.save(newMember);
        return newMember.getId();
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    private void validateDuplicateMember(String name) {
        List<Member> findMember = memberRepository.findByName(name);
        if (!findMember.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    @Transactional
    public void updateMemberName(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.changeMemberName(name);
    }
}
