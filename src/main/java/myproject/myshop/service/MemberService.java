package myproject.myshop.service;

import lombok.RequiredArgsConstructor;
import myproject.myshop.domain.member.LoginForm;
import myproject.myshop.domain.member.Member;
import myproject.myshop.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 가입
    public Member save(Member member) {
        //기존에 있는 아이디인지 확인
        Member savedMember = memberRepository.findByLoginId(member.getLoginId());
        if(savedMember != null) {
            return null;
        }
        //기존에 해당 아이디의 회원이 없는 경우에만 회원 저장
        return memberRepository.save(member);
    }

    //회원 조회
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id);
    }

    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }

    //회원 삭제
    public void deleteMember(Long id) {
        memberRepository.deleteMember(id);
    }

    //로그인
    public Member login(String loginId, String password) {
        Member member = memberRepository.findByLoginId(loginId);

        if(!member.getPassword().equals(password) || member == null) {
            return null;
        }

        return member;
    }
}
