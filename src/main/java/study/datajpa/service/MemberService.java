package study.datajpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService{

    @PersistenceContext
    private EntityManager em;

    private final MemberRepository memberRepository;

    public Long join(Member member){
        memberRepository.save(member);
        return member.getId();
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(Member member){
        return memberRepository.findAll();
    }

    public void deleteMember(String name) {
        em.createQuery("delete from Member m where m.username = :name")
                .setParameter("name", name)
                .executeUpdate();
        em.clear();
    }

    public void update(Long id, String name) {
        Member member = new Member();
        member.setId(id);
        member.setUsername(name);
        memberRepository.save(member);
    }
}
