package myproject.myshop.service;

import lombok.RequiredArgsConstructor;
import myproject.myshop.domain.member.Member;
import myproject.myshop.repository.CartRepository;
import myproject.myshop.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;

    //회원 가입
    public Member save(Member member) {
        //기존에 있는 아이디인지 확인
        if(!memberRepository.findByLoginId(member.getLoginId()).isPresent()) {
            //회원가입 성공처리
            cartRepository.saveCartList(member.getCartList());
            return memberRepository.save(member);
        } else {
            return null;
        }
    }

    //회원 조회
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id);
    }

    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).orElse(null);
    }

    //회원 삭제
    public void deleteMember(Long id) {
        memberRepository.deleteMember(id);
    }

    //로그인
    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
