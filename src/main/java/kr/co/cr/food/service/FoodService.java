package kr.co.cr.food.service;

import kr.co.cr.food.dto.food.FoodDetailRes;
import kr.co.cr.food.dto.food.SearchFoodReq;
import kr.co.cr.food.dto.food.SearchFoodRes;
import kr.co.cr.food.entity.Food;
import kr.co.cr.food.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

  @Autowired
  FoodRepository foodRepository;

  public List<SearchFoodRes> searchFoods(SearchFoodReq searchFoodReq) {
    return null;
  }

  public FoodDetailRes getFoodDetail(Long id) {
    Food food = foodRepository.findById(id).orElseThrow();
    return FoodDetailRes.builder()
          .protein(food.getProtein())
          .carb(food.getCarbs())
          .fat(food.getFat())
          .kcal(food.getKcal())
          .servingSize(food.getServingSize())
          .unit(food.getUnit())
          .build();
  }
}
