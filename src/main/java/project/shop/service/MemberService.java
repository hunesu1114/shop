package project.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.config.auth.dto.SessionMember;
import project.shop.dto.MemberDto;
import project.shop.entity.Member;
import project.shop.entity.Order;
import project.shop.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    public Optional<Member> getMemberFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SessionMember sessionMember = (SessionMember) session.getAttribute("member");
        Optional<Member> member = memberRepository.findByEmail(sessionMember.getEmail());
        return member;
    }

    public Optional<Member> findByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        return member;
    }

    public MemberDto toMemberDto(Member member) {
        MemberDto dto = MemberDto.builder()
                .email(member.getEmail()).name(member.getName()).picture(member.getPicture()).nickName(member.getNickName())
                .build();
        return dto;
    }
}
