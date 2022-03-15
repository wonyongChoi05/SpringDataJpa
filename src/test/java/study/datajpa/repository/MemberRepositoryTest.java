package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void testMember(){
        Member member = new Member("kim");

        Member savedMember = memberRepository.save(member);

        Optional<Member> byId = memberRepository.findById(savedMember.getId());

        assertThat(byId).isEqualTo(member.getId());
        assertThat(byId.get().getUsername()).isEqualTo(member.getUsername());
        assertThat(byId).isEqualTo(member);

    }

    @Test
    public void QueryMemberTest(){
        Member member = new Member("choi", 17);
        memberRepository.save(member);

        memberRepository.findUser("choi", 17);

        assertThat(member.getUsername()).isEqualTo("choi");
        assertThat(member.getAge()).isEqualTo(17);
    }

    @Test
    public void findMemberLazy(){
        // given
        // member1 -> teamA
        // member2 -> teamB
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamB);

        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            System.out.println("member=" + member.getUsername());
            System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
        }
    }

    @Test
    public void queryHints(){
        Member member = new Member("member1", 18);
        memberRepository.save(member);
        em.flush();
        em.clear();

        Member findMember = memberRepository.findReadOnlyByUsername("member1");
        findMember.setUsername("member2");
        em.flush();
    }

    @Test
    public void callCustom(){
        List<java.lang.reflect.Member> memberCustom = memberRepository.findMemberCustom();
    }
}