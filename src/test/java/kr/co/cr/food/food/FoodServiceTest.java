package kr.co.cr.food.food;

import kr.co.cr.food.dto.common.PagingResponse;
import kr.co.cr.food.dto.food.FoodDetailRes;
import kr.co.cr.food.dto.food.SearchFoodReq;
import kr.co.cr.food.dto.food.SearchFoodRes;
import kr.co.cr.food.entity.Food;
import kr.co.cr.food.entity.Vote;
import kr.co.cr.food.repository.FoodRepository;
import kr.co.cr.food.repository.VoteRepository;
import kr.co.cr.food.service.FoodService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FoodServiceTest {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private FoodService foodService;

    @BeforeAll
    void init() {
        Food food = Food.builder()
              .id(1L)
              .name("테스트음식")
              .code("TEST")
              .carbs(1.0)
              .kcal(1.0)
              .col(1.0)
              .fat(1.0)
              .servingSize(1.0)
              .unit("g")
              .build();
        foodRepository.save(food);

        voteRepository.save(new Vote(1L));
    }

    @Test
    void vote() {
        foodService.vote(1L, "LIKE");

        Vote vote = voteRepository.findById(1L).orElseThrow();
        assertThat(vote.getLikeCount()).isEqualTo(1);
    }



    @Test
    void getDetail() {
        FoodDetailRes foodDetail = foodService.getFoodDetail(1L);

        System.out.println(foodDetail.toString());
        assertThat(foodDetail.getId()).isEqualTo(1L);
    }

    @Test
    void getAll() {
        SearchFoodReq searchFoodReq = new SearchFoodReq();
        searchFoodReq.setName("테스트");
        PagingResponse<SearchFoodRes> searchFoodRes = foodService.searchFoods(Pageable.ofSize(10).first(), searchFoodReq);

        System.out.println("page : " + searchFoodRes.getMetaData().getPage());
        assertThat(searchFoodRes.getData().size()).isEqualTo(1);
        searchFoodRes.getData().forEach(f -> {
            System.out.println(f.toString());
        });
    }
}