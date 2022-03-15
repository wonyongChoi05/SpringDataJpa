package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Team;

@EnableJpaRepositories
public interface TeamRepository extends JpaRepository<Team, Long> {

}