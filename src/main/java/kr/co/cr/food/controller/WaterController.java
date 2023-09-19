package kr.co.cr.food.controller;

import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import kr.co.cr.food.common.APIResponse;
import kr.co.cr.food.dto.water.CreateWaterRequest;
import kr.co.cr.food.dto.water.UpdateWaterRequest;
import kr.co.cr.food.service.WaterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/water")
public class WaterController {

    private final WaterService waterService;

    @Operation(summary = "물 섭취 추가", description = "섭취한 물의 양을 저장합니다.", tags = {"WaterController"})
    @ApiResponse(code = 200, message = "요청완료")
    @PostMapping
    public APIResponse saveWater(@RequestBody @Validated CreateWaterRequest createWaterRequest){

        return APIResponse.builder()
                .result("ok")
                .msg("섭취한 물의 양이 추가되었습니다.")
                .data(waterService.insert(createWaterRequest))
                .build();
    }


    @Operation(summary = "물 섭취 수정 및 삭제", description = "섭취한 물의 양을 수정 및 삭제합니다.", tags = {"WaterController"})
    @ApiResponse(code = 200, message = "요청완료")
    @PatchMapping("/{waterId}")
    public APIResponse modifiedWater(@PathVariable("waterId") Long id,
                                     @RequestBody @Validated UpdateWaterRequest updateWaterRequest){

        if(updateWaterRequest.getAmount() == 0){
            return APIResponse.builder()
                    .result("ok")
                    .msg("섭취한 물의 양이 삭제되었습니다.")
                    .data(waterService.delete(id))
                    .build();
        }else {
            return APIResponse.builder()
                    .result("ok")
                    .msg("섭취한 물의 양이 수정되었습니다.")
                    .data(waterService.update(id, updateWaterRequest))
                    .build();
        }
    }

}
