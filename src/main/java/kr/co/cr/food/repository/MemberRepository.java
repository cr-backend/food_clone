package kr.co.cr.food.repository;

import kr.co.cr.food.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

   Optional<Member> findByEmail(String email);

}
