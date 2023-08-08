package kr.co.cr.food.dto.diet;

import kr.co.cr.food.entity.Diet;
import kr.co.cr.food.entity.Food;
import kr.co.cr.food.enums.MealTime;
import kr.co.cr.food.entity.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateDietRequest {

    private Food food;
    private Member member;
    private Long count; // 몇 인분?
    private LocalDateTime dietDate;
    private MealTime mealTime;

    public Diet toEntity(CreateDietRequest request) {
        return Diet.builder()
                .food(request.food)
                .member(request.member)
                .count(request.count)
                .dietDate(request.dietDate)
                .mealTime(request.mealTime)
                .build();
    }

}
