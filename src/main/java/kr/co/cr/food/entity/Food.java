package kr.co.cr.food.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Food {
    @Id
    private Long id;
    private String code;
    private String type;
    private String name;
    @Column(name = "serving_size")
    private Double servingSize;
    private String unit; // g/ ml
    private Double kcal; // 500 g
    private Double carbs; // 0.3 g
    private Double protein; // 0.1 g
    private Double fat;
    private Double sugar;
    private Double nat;
    private Double col;
    @Column(name = "saturated_fat")
    private Double saturatedFat;
    @Column(name = "trans_fat")
    private Double transFat;
}
