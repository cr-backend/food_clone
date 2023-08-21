package kr.co.cr.food.dto.diet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import kr.co.cr.food.entity.Food;
import kr.co.cr.food.entity.Member;
import kr.co.cr.food.enums.MealTime;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor(force = true)
public class UpdateDietRequest {

    @NotNull
    private Member member;

    @NotNull
    private Food food;

    @NotNull
    private Long count; // 몇 인분?

    @NotNull
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate dietDate;

    @NotNull
    private MealTime mealTime;

}
