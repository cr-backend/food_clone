package kr.co.cr.food.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Food {
    @Id
    private Long id;
    private String code;
    private String type;
    private String name;
    @Column(name = "serving_size")
    private Double servingSize;
    private Double kcal;
    private Double carbs;
    private Double protein;
    private Double fat;
    private Double sugar;
    private Double nat;
    private Double col;
    @Column(name = "saturated_fat")
    private Double saturatedFat;
    @Column(name = "trans_fat")
    private Double transFat;
}
