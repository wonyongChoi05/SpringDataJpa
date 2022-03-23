package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;

import javax.persistence.QueryHint;
import java.util.List;

@EnableJpaRepositories
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    public List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    /** Member 엔티티의 참조변수를 m으로 선언하고 m의 username과 age가 입력받은 값과 같은 m의 모든 속성을 조회해라.*/
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);
    /**
     * Member - 단건
     * List<Member> - 컬렉션
     * Optional<Member> - 단건 Optional
     * paging query
     * Slice<Member> findByAge(int age, Pageable pageable);
     * */

}