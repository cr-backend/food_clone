package kr.co.cr.food.entity;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberVoteInfo {
    @EmbeddedId
    private MemberVoteInfoId memberVoteInfoId;
    private String voteType;

    public MemberVoteInfo(Long foodId, Long memberId, String voteType) {
        this.memberVoteInfoId = new MemberVoteInfoId(foodId, memberId);
        this.voteType = voteType;
    }
}
