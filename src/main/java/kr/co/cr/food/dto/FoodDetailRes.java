package kr.co.cr.food.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FoodDetailRes {
  private Double carb;
  private Double protein;
  private Double fat;
  private Double kcal;
  private Double servingSize;
  private String unit;
}
