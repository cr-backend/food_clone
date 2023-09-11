package kr.co.cr.food.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Vote {
    @Id
    private Long foodId;
    private Integer likeCount;
    private Integer badCount;

    public Vote(Long foodId) {
        this.foodId = foodId;
        this.likeCount = 0;
        this.badCount = 0;
    }

    public void vote(String type) {
        if (type == null) return;

        if (type.equalsIgnoreCase("LIKE")) {
            like();
        } else {
            bad();
        }
    }


    private void like() {
        ++ this.likeCount;
    }

    private void bad() {
        ++ this.badCount;
    }
}
