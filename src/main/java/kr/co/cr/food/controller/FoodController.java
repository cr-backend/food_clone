package kr.co.cr.food.controller;

import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import kr.co.cr.food.common.APIResponse;
import kr.co.cr.food.common.BaseController;
import kr.co.cr.food.dto.common.PagingResponse;
import kr.co.cr.food.dto.food.FoodDetailRes;
import kr.co.cr.food.dto.food.SearchFoodReq;
import kr.co.cr.food.dto.food.SearchFoodRes;
import kr.co.cr.food.exception.FoodException;
import kr.co.cr.food.exception.errorcodes.ErrorCode;
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
        return APIResponse.ok(searchFoodRes);
    }


    @Operation(summary = "음식 상세 조회 요청", description = "음식의 자세한 정보가 반환됩니다.", tags = { "Food Controller" })
    @ApiResponse(code = 200, message = "요청완료", response = FoodDetailRes.class)
    @GetMapping("/{id}")
    public APIResponse<FoodDetailRes> getDetail(@PathVariable Long id, @RequestHeader("Authorization") String accessToken) {
        Long memberId = retrieveMemberId(accessToken);
        if (memberId == null)
            throw new FoodException(ErrorCode.UNAUTHORIZED); // 임시처리
        FoodDetailRes foodDetail = foodService.getFoodDetail(id, memberId);
        return APIResponse.ok(foodDetail);
    }


    @PatchMapping("/{id}/{type}")
    public APIResponse<Boolean> voteForFood(@PathVariable Long id, @PathVariable String type, @RequestHeader("Authorization") String accessToken) {
        if (accessToken == null)
            throw new FoodException(ErrorCode.UNAUTHORIZED);
        Long memberId = retrieveMemberId(accessToken);
        if (memberId == null)
            throw new FoodException(ErrorCode.UNAUTHORIZED); // 임시처리
        Boolean voteResult = foodService.vote(id, type, memberId);
        return APIResponse.ok(voteResult);
    }
}
