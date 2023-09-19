package kr.co.cr.food.dto.water;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
public class UpdateWaterRequest {

    @NotNull
    // limit 값 설정하기 10.00L
    private Long amount;
}
