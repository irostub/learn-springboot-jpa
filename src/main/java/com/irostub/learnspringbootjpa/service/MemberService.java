package com.irostub.learnspringbootjpa.service;

import com.irostub.learnspringbootjpa.domain.Member;
import com.irostub.learnspringbootjpa.dto.MemberFormDto;
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
    public Long join(MemberFormDto memberFormDto) {
        validateDuplicateMember(memberFormDto);
        Member member = new Member();
        member.setName(memberFormDto.getName());
        memberRepository.save(member);
        return member.getId();
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    private void validateDuplicateMember(MemberFormDto memberFormDto) {
        List<Member> findMember = memberRepository.findByName(memberFormDto.getName());
        if (!findMember.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }
}
