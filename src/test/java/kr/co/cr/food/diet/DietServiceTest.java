package kr.co.cr.food.diet;

import kr.co.cr.food.dto.diet.CreateDietRequest;
import kr.co.cr.food.dto.diet.UpdateDietRequest;
import kr.co.cr.food.entity.Food;
import kr.co.cr.food.entity.MealTime;
import kr.co.cr.food.entity.Member;
import kr.co.cr.food.repository.MemberRepository;
import kr.co.cr.food.repository.FoodRepository;
import kr.co.cr.food.service.DietService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

@SpringBootTest
class DietServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private DietService service;

    @Test
//    @Rollback(value = false)
    void createDiet() {
        Member savedMember = getMember();
        Food savedFood = getFood();

        CreateDietRequest request = new CreateDietRequest();
        request.setMember(savedMember);
        request.setFood(savedFood);
        request.setDietDate(LocalDateTime.now());
        request.setCount(1L);
        request.setMealTime(MealTime.LUNCH);

        service.inputDiet(request);
    }

    @Test
    @Rollback(value = false)
    void updateDiet() {
        Food savedFood = getFood();

        UpdateDietRequest request = new UpdateDietRequest();
        request.setFood(savedFood);
        request.setCount(5L);
        request.setMealTime(MealTime.BREAKFAST);

        service.updateDiet(2L,request);
    }

    private Food getFood() {
        Food food = Food.builder()
                .id(1L)
                .name("흑미")
                .carbs(55.0)
                .fat(8.0)
                .protein(7.0)
                .kcal(300.0)
                .servingSize(100.0)
                .build();

        Food savedFood = foodRepository.save(food);
        return savedFood;
    }

    private Member getMember() {
        Member member = Member.builder()
                .id(1L)
                .email("kkkkk@naver.com")
                .nickname("member")
                .build();

        Member savedMember = memberRepository.save(member);
        return savedMember;
    }


}