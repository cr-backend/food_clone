package kr.co.cr.food.service.application;

import kr.co.cr.food.dto.FoodDetailRes;
import kr.co.cr.food.dto.SearchFoodReq;
import kr.co.cr.food.dto.SearchFoodRes;
import kr.co.cr.food.entity.Food;
import kr.co.cr.food.repository.FoodRepository;
import kr.co.cr.food.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

  @Autowired
  FoodRepository foodRepository;

  @Override
  public List<SearchFoodRes> searchFoods(SearchFoodReq searchFoodReq) {
    return null;
  }

  @Override
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
