package kr.co.cr.food.controller;

import io.swagger.v3.oas.annotations.Operation;
import kr.co.cr.food.common.ApiResponse;
import kr.co.cr.food.dto.diet.CreateDietRequest;
import kr.co.cr.food.dto.diet.UpdateDietRequest;
import kr.co.cr.food.service.DietService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class DietController {

    private final DietService dietService;

    // 저장, 수정, 삭제
    @Operation(summary = "식단 저장", description = "사용자가 요청한 식단(음식)을 저장합니다.", tags = {"Diet Controller"})
    @io.swagger.annotations.ApiResponse(code = 200, message = "요청완료")
    @PostMapping
    public ApiResponse saveDiet(@RequestBody @Validated CreateDietRequest createDietRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new NullPointerException("입력 값에 NulL값이 들어감");
        }

        return ApiResponse.builder()
                .result("ok")
                .msg("입력된 음식이 저장되었습니다.")
                .data(dietService.inputDiet(createDietRequest))
                .build();
    }

    @Operation(summary = "식단 수정", description = "사용자가 요청한 식단(음식)을 수정합니다.", tags = {"Diet Controller"})
    @io.swagger.annotations.ApiResponse(code = 200, message = "요청완료")
    @PatchMapping("/diet/{id}")
    public ApiResponse modifiedDiet(@PathVariable("id") Long id,
                                    @RequestBody @Validated UpdateDietRequest updateDietRequest,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new NullPointerException("입력 값에 NulL값이 들어감");
        }

        return ApiResponse.builder()
                .result("ok")
                .msg("음식 정보가 수정되었습니다")
                .data(dietService.updateDiet(id, updateDietRequest))
                .build();
    }

    @Operation(summary = "식단 삭제", description = "사용자가 요청한 식단(음식)을 삭제합니다.", tags = {"Diet Controller"})
    @io.swagger.annotations.ApiResponse(code = 200, message = "요청완료")
    @DeleteMapping("/diet/{id}")
    public ApiResponse removeDiet(@PathVariable("id") Long id) {
        return ApiResponse.builder()
                .result("ok")
                .msg("음식 정보가 삭제되었습니다")
                .data(dietService.deleteDiet(id))
                .build();
    }

}
