package kr.co.cr.food.dto.diet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import kr.co.cr.food.entity.Diet;
import kr.co.cr.food.entity.Food;
import kr.co.cr.food.entity.Member;
import kr.co.cr.food.enums.MealTime;
import kr.co.cr.food.enums.RecordType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor(force = true)
public class CreateDietRequest {

    @NotNull
    private Long foodId;

    @NotNull
    private Long memberId;

    @NotNull
    private Long count; // 몇 인분?

    @NotNull
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate dietDate;

    @NotNull
    private MealTime mealTime;

}
