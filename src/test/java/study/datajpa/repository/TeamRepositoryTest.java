package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class TeamRepositoryTest {

    @Autowired
    MemberRepository MemberRepository;

    @Test
    public void beaterThan(){
        Member member = new Member("AAA",17);
        MemberRepository.save(member);

        List<Member> result = MemberRepository.findByUsernameAndAgeGreaterThan("AAA", 10);

        Assertions.assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        Assertions.assertThat(result.get(0).getAge()).isEqualTo(17);
        Assertions.assertThat(result.size()).isEqualTo(1);

    }
}