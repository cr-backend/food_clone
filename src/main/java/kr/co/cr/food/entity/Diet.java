package kr.co.cr.food.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Diet {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Long count; // 제공량 횟수

    @Column(name = "diet_date")
    private LocalDateTime dietDate;

    @Enumerated(EnumType.STRING)
    private MealTime mealTime;

    // 양방향 편의 메서드
    public void changeMember(Member member){
        this.member = member;
        member.getDiets().add(this);
    }


    // 식단 변경 메서드
    public void updateDiet(Food food, Long count, LocalDateTime dietDate, MealTime mealTime){
        this.food = food;
        this.count = count;
        this.dietDate = dietDate;
        this.mealTime = mealTime;
    }
}
