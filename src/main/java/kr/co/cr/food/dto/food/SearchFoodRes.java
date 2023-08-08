package kr.co.cr.food.dto.food;

import kr.co.cr.food.entity.Food;
import kr.co.cr.food.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchFoodRes {
  private Long id;
  private String name;
  private Double kcal;
  private Integer servingSize;
  private String servingType;

  public static SearchFoodRes toDto(Food food) {
    return new SearchFoodRes(food.getId(), food.getName(), food.getKcal(), Utils.doubleToInteger(food.getServingSize()), food.getUnit());
  }
}
