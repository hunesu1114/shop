package project.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.dto.MemberDto;
import project.shop.entity.Member;
import project.shop.entity.Order;
import project.shop.repository.MemberRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public Member findById(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.orElseThrow();
    }

    public Member findByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.orElseThrow();
    }

    public MemberDto toMemberDto(Member member) {
        MemberDto dto = MemberDto.builder().email(member.getEmail()).name(member.getName()).picture(member.getPicture()).build();
        return dto;
    }
}
