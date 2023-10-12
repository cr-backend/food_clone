package kr.co.cr.food.controller;

import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import kr.co.cr.food.common.APIResponse;
import kr.co.cr.food.common.BaseController;
import kr.co.cr.food.dto.common.PagingResponse;
import kr.co.cr.food.dto.food.FoodDetailRes;
import kr.co.cr.food.dto.food.SearchFoodReq;
import kr.co.cr.food.dto.food.SearchFoodRes;
import kr.co.cr.food.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/food", produces = "application/json; charset=utf-8")
public class FoodController extends BaseController {

    @Autowired
    FoodService foodService;

    @Operation(summary = "음식 전체 조회 요청", description = "검색어를 포함한 음식의 전체 목록이 반환됩니다.")
    @ApiResponse(code = 200, message = "요청완료", response = SearchFoodRes.class)
    @GetMapping("/")
    public APIResponse<PagingResponse<SearchFoodRes>> getAll(Pageable pageable, SearchFoodReq searchFoodReq) {
        PagingResponse<SearchFoodRes> searchFoodRes = foodService.searchFoods(pageable, searchFoodReq);
        return ok(searchFoodRes);
    }


    @Operation(summary = "음식 상세 조회 요청", description = "음식의 자세한 정보가 반환됩니다.", tags = { "Food Controller" })
    @ApiResponse(code = 200, message = "요청완료", response = FoodDetailRes.class)
    @GetMapping("/{id}")
    public APIResponse<FoodDetailRes> getDetail(@PathVariable Long id) {
        FoodDetailRes foodDetail = foodService.getFoodDetail(id);
        return ok(foodDetail);
    }


    @PatchMapping("/{id}/{type}")
    public APIResponse<Boolean> voteForFood(@PathVariable Long id, @PathVariable String type) {
        Boolean voteResult = foodService.vote(id, type);
        return ok(voteResult);
    }
}
