package kr.co.cr.food.repository;

import kr.co.cr.food.entity.MemberVoteInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberVoteInfoRepository  extends JpaRepository<MemberVoteInfo, Long> {
  public Optional<MemberVoteInfo> findByFoodIdAndMemberId(Long foodId, Long memberId);
}
