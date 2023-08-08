package kr.co.cr.food.controller;

import kr.co.cr.food.common.APIResponse;
import kr.co.cr.food.service.DietService;
import kr.co.cr.food.dto.diet.CreateDietRequest;
import kr.co.cr.food.dto.diet.UpdateDietRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class DietController {

    private final DietService dietService;

    // 저장, 수정, 삭제

    @PostMapping
    public APIResponse saveDiet(CreateDietRequest createDietRequest){
        return APIResponse.builder()
                .result("ok")
                .msg("입력된 음식이 저장되었습니다.")
                .data(dietService.inputDiet(createDietRequest))
                .build();
    }

    @PatchMapping("/diet/{id}")
    public APIResponse modifiedDiet(@PathVariable("id")Long id, UpdateDietRequest updateDietRequest){
        return APIResponse.builder()
                .result("ok")
                .msg("음식 정보가 수정되었습니다")
                .data(dietService.updateDiet(id, updateDietRequest))
                .build();
    }

    @DeleteMapping("/diet/{id}")
    public APIResponse removeDiet(@PathVariable("id")Long id){
        return APIResponse.builder()
                .result("ok")
                .msg("음식 정보가 삭제되었습니다")
                .data(dietService.deleteDiet(id))
                .build();
    }

}
