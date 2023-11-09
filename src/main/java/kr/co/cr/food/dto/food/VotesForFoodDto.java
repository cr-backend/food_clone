package kr.co.cr.food.dto.food;

import kr.co.cr.food.entity.Vote;
import kr.co.cr.food.enums.VoteAvgType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VotesForFoodDto {
  private Integer likeCnt;
  private Integer badCnt;
  private VoteAvgType avgType;

  public VotesForFoodDto(Vote vote) {
    if (vote != null) {
      this.likeCnt = vote.getLikeCount();
      this.badCnt = vote.getBadCount();
      this.avgType = getAvg();
    } else {
      this.likeCnt = 0;
      this.badCnt = 0;
      this.avgType = VoteAvgType.SOSO;
    }
  }

  private VoteAvgType getAvg() {
    if (this.likeCnt == null) this.likeCnt = 0;
    if (this.badCnt == null) this.badCnt = 0;

    if (this.likeCnt > this.badCnt) {
      return VoteAvgType.GOOD;
    } else if (this.likeCnt < this.badCnt) {
      return VoteAvgType.BAD;
    } else {
      return VoteAvgType.SOSO;
    }
  }
}
