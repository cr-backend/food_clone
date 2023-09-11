package kr.co.cr.food.dto.food;

import kr.co.cr.food.entity.Food;
import kr.co.cr.food.entity.Vote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FoodDetailRes {
  private Long id;
  private String name;
  private Double carb;
  private Double protein;
  private Double fat;
  private Double kcal;
  private Double servingSize;
  private String servingUnit;
  private VotesForFoodDto voteInfo;

  public FoodDetailRes(Food food, Vote vote) {
    this.id = food.getId();
    this.name = food.getName();
    this.carb = food.getCarbs();
    this.protein = food.getProtein();
    this.fat = food.getFat();
    this.kcal = food.getKcal();
    this.servingSize = food.getServingSize();
    this.servingUnit = food.getUnit();
    this.voteInfo = new VotesForFoodDto(vote);
  }
}
