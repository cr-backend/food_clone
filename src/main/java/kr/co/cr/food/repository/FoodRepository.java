package kr.co.cr.food.repository;

import kr.co.cr.food.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
  Page<Food> findByNameContainsOrderByIdDesc(String name, Pageable pageable);
}
