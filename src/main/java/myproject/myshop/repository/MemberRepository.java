package myproject.myshop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import myproject.myshop.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private final EntityManager em;

    //회원 가입
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    //회원 조회
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    public Member findByLoginId(String loginId) {
        List<Member> memberList = em.createQuery("select m from Member m where m.loginId = :loginId")
                .setParameter("loginId", loginId)
                .getResultList();

        if(memberList.isEmpty()) {
            return null;
        }

        return memberList.get(0);
    }

    //회원 삭제
    public void deleteMember(Long id) {
        em.remove(em.find(Member.class, id));
    }
}
