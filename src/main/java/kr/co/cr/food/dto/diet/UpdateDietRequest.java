package kr.co.cr.food.dto.diet;

import kr.co.cr.food.entity.Food;
import kr.co.cr.food.entity.MealTime;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UpdateDietRequest {

    private Food food;
    private Long count; // 몇 인분?
    private LocalDateTime dietDate;
    private MealTime mealTime;

}
