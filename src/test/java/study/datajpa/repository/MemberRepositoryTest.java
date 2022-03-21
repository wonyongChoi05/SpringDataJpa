package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;
import study.datajpa.service.MemberService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@WebAppConfiguration
@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MemberService memberService;

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
        members.forEach(member -> {
            System.out.println("member=" + member.getUsername());
            System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
        });
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
    public void 레파지토리_회원가입() throws Exception{

        // given
        Member member1 = new Member();
        Member member2 = new Member();

        member1.setUsername("adsf");
        member1.setAge(14);

        member2.setUsername("fasdf");
        member2.setAge(15);

        // when
        memberRepository.save(member1);
        memberRepository.save(member2);

        // then
        Assertions.assertThat(member1.getUsername()).isEqualTo("adsf");
        Assertions.assertThat(member2.getUsername()).isEqualTo("fasdf");
    }
    
    @Test
    public void 레파지토리_회원조회() throws Exception{
        // given
        Member member1 = new Member("choi", 17);
        Member member2 = new Member("kim", 18);

        // when
        memberRepository.save(member1);
        memberRepository.save(member2);

        // then
        List<Member> findMembers = memberRepository.findUser("choi", 17);

        Member result1 = findMembers.get(0);

        Assertions.assertThat(member1).isEqualTo(result1);
    }

    @Test
    public void 레파지토리_회원수정() throws Exception{
        // given
        Member member = new Member("choi", 18);

        // when
        memberRepository.save(member);
        List<Member> findUsers1 = memberRepository.findUser("choi", 18);
        System.out.println("findUsers1 = " + findUsers1);

        member.setAge(20);

        List<Member> findUsers2 = memberRepository.findUser("choi", 18);
        // then
        System.out.println("findUsers2 = " + findUsers2);

        List<Member> findUsers3 = memberRepository.findUser("choi", 20);
        // then
        System.out.println("findUsers3 = " + findUsers3);
    }

    @Test
    public void 레파지토리_회원삭제() throws Exception{
        // given
        Member member1 = new Member("choi", 18);
        
        memberRepository.save(member1);

        // when
        List<Member> findUsers1 = memberRepository.findUser("choi", 18);
        // then
        System.out.println("findUsers1 = " + findUsers1);
        
        memberRepository.delete(member1);

        // when
        List<Member> findUsers2 = memberRepository.findUser("choi", 18);
        //then
        System.out.println("findUsers2 = " + findUsers2);
    }

    @Test
    public void 서비스_회원가입() throws Exception{

        // given
        Member member1 = new Member();
        Member member2 = new Member();

        member1.setUsername("adsf");
        member1.setAge(14);

        member2.setUsername("fasdf");
        member2.setAge(15);

        // when
        memberService.join(member1);
        memberService.join(member2);

        Member one = memberService.findOne(member1.getId());
        Member one1 = memberService.findOne(member2.getId());

        // then
        Assertions.assertThat(one).isEqualTo(member1);
        Assertions.assertThat(one1).isEqualTo(member2);
    }

    @Test
    public void 서비스_전체_회원_조회(){

        // given
        Member member = new Member("choi");
        memberService.join(member);

        // when
        List<Member> findMember = memberService.findAll(member);

        // then
        assertThat(findMember.get(0)).isEqualTo(member);

    }

    @Test
    public void 서비스_회원_삭제(){
        
        // given
        Member member = new Member();
        member.setUsername("userA");

        System.out.println("member.getUsername() = " + member.getUsername());
        
        // when
        memberService.deleteMember("userA");

        List<Member> findMembers = memberService.findAll(member);

        // then
        System.out.println("findMembers = " + findMembers);
    }
}