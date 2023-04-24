package project.shop.service;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.shop.dto.MemberDto;
import project.shop.entity.Member;
import project.shop.entity.Role;
import project.shop.repository.MemberRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional

public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void save_FBEmail_FBId() {
        Member member = Member.builder().email("email1")
                .name("name1")
                .picture("picture1")
                .role(Role.GUEST)
                .build();
        memberService.save(member);
        Member byEmailMember = memberService.findByEmail(member.getEmail());
        Member byIdMember = memberService.findById(member.getId());
        assertThat(member.getId()).isEqualTo(byEmailMember.getId());
        assertThat(member.getId()).isEqualTo(byIdMember.getId());
        assertThat(member.getName()).isEqualTo(byEmailMember.getName());
        assertThat(member.getName()).isEqualTo(byIdMember.getName());
        assertThat(member.getPicture()).isEqualTo(byEmailMember.getPicture());
        assertThat(member.getPicture()).isEqualTo(byIdMember.getPicture());
    }

    @Test
    public void toMemberDto() {
        Member member = Member.builder().email("email1").name("name1").picture("pic1").build();
        MemberDto dto = memberService.toMemberDto(member);
        assertThat(dto.getEmail()).isEqualTo(member.getEmail());
        assertThat(dto.getName()).isEqualTo(member.getName());
        assertThat(dto.getPicture()).isEqualTo(member.getPicture());
    }





}