package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void 회원_조회(){
        Member member = new Member();
        member.setUsername("asdf");
        memberJpaRepository.save(member);

        List<Member> findMembers = memberJpaRepository.findAll();

        Assertions.assertThat(findMembers.get(0).getUsername()).isEqualTo("asdf");
    }

}