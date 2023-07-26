package kr.co.cr.food.service;

import kr.co.cr.food.dto.FoodDetailRes;
import kr.co.cr.food.dto.SearchFoodReq;
import kr.co.cr.food.dto.SearchFoodRes;

import java.util.List;

public interface FoodService {
  List<SearchFoodRes> searchFoods(SearchFoodReq searchFoodReq);
  FoodDetailRes getFoodDetail(Long id);
}
