package kr.co.cr.food.entity;

import kr.co.cr.food.enums.MealTime;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Diet {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Long count; // 제공량 횟수

    @Column(name = "diet_date")
    private LocalDate dietDate;

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
    public void changeMember(Member member) {
        this.member = member;
        member.getDiets().add(this);
    }


    // 식단 변경 메서드
    public void updateDiet(Food food, Member member, Long count, LocalDate dietDate, MealTime mealTime) {
        this.food = food;
        this.member = member;
        this.count = count;
        this.dietDate = dietDate;
        this.mealTime = mealTime;
    }

    // 총 칼로리, 탄수화물, 단백질, 지방 업데이트하는 메서드
    public void updateKcal() {
        totalKcal = (long) (food.getKcal() * count);
        totalCarb = (long) (food.getCarbs() * count);
        totalPro = (long) (food.getProtein() * count);
        totalFat = (long) (food.getFat() * count);
    }
}
