package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void Member(){
        Member member = new Member("choi");
        Member savedMember = memberJpaRepository.save(member);

        Optional<Member> findMember = Optional.ofNullable(memberJpaRepository.find(savedMember.getId()));

        assertThat(findMember).isEqualTo(member);
    }
}