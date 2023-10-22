package kr.co.cr.food.service;

import kr.co.cr.food.dto.diet.CreateDietRequest;
import kr.co.cr.food.dto.diet.UpdateDietRequest;
import kr.co.cr.food.entity.Diet;
import kr.co.cr.food.entity.Food;
import kr.co.cr.food.entity.Member;
import kr.co.cr.food.exception.NotFoundException;
import kr.co.cr.food.exception.InternalServerErrorException;
import kr.co.cr.food.repository.DietRepository;
import kr.co.cr.food.repository.FoodRepository;
import kr.co.cr.food.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DietService {

    private final DietRepository dietRepository;
    private final MemberRepository memberRepository;
    private final FoodRepository foodRepository;

    public Long inputDiet(CreateDietRequest createDietRequest) {
        // member 아이디 찾기
        Member member = memberRepository.findById(createDietRequest.getMemberId())
                .orElseThrow(() -> new NotFoundException("해당 멤버를 찾을 수 없습니다."));

        // food 아이디 찾기
        Food food = foodRepository.findById(createDietRequest.getFoodId())
                .orElseThrow(() -> new NotFoundException("해당 음식을 찾을 수 없습니다."));

        // dto -> entity
        Diet diet = toEntity(createDietRequest, member, food);

        // 양방향 관계 업데이트
        diet.changeMember(member);

        // total 값 계산
        diet.updateKcal();

        // 저장
        Diet savedDiet = dietRepository.save(diet);

        if (savedDiet == null)
            throw new InternalServerErrorException("식단을 저장할 수 없음");

        return savedDiet.getId();
    }


    @Transactional
    public Long updateDiet(Long id, UpdateDietRequest request) {
        update(id, request);
        Diet findUpdateDiet = dietRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당 식단 아이디를 찾을 수 없음"));
        return findUpdateDiet.getId();
    }

    public void update(Long id, UpdateDietRequest request) {
        // member 아이디 찾기
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new NotFoundException("해당 멤버를 찾을 수 없습니다."));

        // food 아이디 찾기
        Food food = foodRepository.findById(request.getFoodId())
                .orElseThrow(() -> new NotFoundException("해당 음식을 찾을 수 없습니다."));

        // 식단 찾기
        Diet diet = dietRepository.findById(id)
                .orElseThrow(() ->
                        new NullPointerException("해당 식단 아이디를 찾을 수 없음"));

        diet.updateDiet(food, member, request.getCount(), request.getDietDate(), request.getMealTime());
        diet.updateKcal();
    }

    public boolean deleteDiet(Long id) {
        Diet diet = dietRepository.findById(id)
                .orElseThrow(() ->
                        new NullPointerException("해당 식단 아이디를 찾을 수 없음"));
        dietRepository.delete(diet);
        return true;
    }

    public Diet toEntity(CreateDietRequest request, Member member, Food food) {
        return Diet.builder()
                .food(food)
                .member(member)
                .count(request.getCount())
                .dietDate(request.getDietDate())
                .mealTime(request.getMealTime())
                .build();
    }
}
