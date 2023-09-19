package kr.co.cr.food.entity;

import kr.co.cr.food.dto.water.CreateWaterRequest;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Water extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Long amount;

    public void changeMember(Member member) {
        this.member = member;
        member.getWater().add(this);
    }

    public void updateWater(Long amount){
        this.amount = amount;
    }

}
