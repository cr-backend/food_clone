package kr.co.cr.food.entity;

import kr.co.cr.food.enums.MealTime;
import kr.co.cr.food.enums.RecordType;
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

    @Column(name = "total_kcal")
    private Long totalKcal;

    @Column(name = "total_carb")
    private Long totalCarb;

    @Column(name = "total_pro")
    private Long totalPro;

    @Column(name = "total_fat")
    private Long totalFat;


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

//    public void setTotal(){
//        totalKcal *= count;
//        totalCarb *= count;
//        totalPro *= count;
//        totalFat *= count;
//    }
}
