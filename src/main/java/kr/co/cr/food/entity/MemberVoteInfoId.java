package kr.co.cr.food.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@Embeddable
public class MemberVoteInfoId implements Serializable {
  @Column(name = "food_id")
  private Long foodId;
  @Column(name = "member_id")
  private Long memberId;
}
