package kr.co.cr.food.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Food {
    @Id @GeneratedValue
    private Long id;
    @Column(unique = true)
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
