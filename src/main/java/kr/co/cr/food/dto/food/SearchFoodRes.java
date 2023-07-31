package kr.co.cr.food.dto.food;

import kr.co.cr.food.enums.RecordType;
import lombok.Data;

@Data
public class SearchFoodRes {
  private String name;
  private Boolean isUsers;
  private RecordType recordType;
  private Integer kcal;
  private String servingSize;
  private String servingType;
}
