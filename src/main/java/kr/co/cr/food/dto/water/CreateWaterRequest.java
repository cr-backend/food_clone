package kr.co.cr.food.dto.water;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
public class CreateWaterRequest {

    @NotNull
    private Long memberId;

    @NotNull
    // limit 값 설정하기 10.00L
    private Long amount;

}
