package kr.co.cr.food.service;

import kr.co.cr.food.dto.diet.CreateDietRequest;
import kr.co.cr.food.dto.diet.UpdateDietRequest;
import kr.co.cr.food.entity.Diet;
import kr.co.cr.food.exception.InternalServerErrorException;
import kr.co.cr.food.repository.DietRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DietService {

    private final DietRepository dietRepository;

    public boolean inputDiet(CreateDietRequest createDietRequest) {
        Diet diet = toEntity(createDietRequest);
        diet.changeMember(createDietRequest.getMember());
        diet.updateKcal();
        Diet savedDiet = dietRepository.save(diet);
        if (savedDiet == null)
            throw new InternalServerErrorException("식단을 저장할 수 없음");

        return true;
    }


    @Transactional
    public Long updateDiet(Long id, UpdateDietRequest request) {
        update(id, request);
        Diet findUpdateDiet = dietRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("해당 식단 아이디를 찾을 수 없음"));
        return findUpdateDiet.getId();
    }

    public void update(Long id, UpdateDietRequest request) {
        Diet diet = dietRepository.findById(id)
                .orElseThrow(() ->
                        new NullPointerException("해당 식단 아이디를 찾을 수 없음"));
        diet.updateDiet(request.getFood(), request.getMember(), request.getCount(), request.getDietDate(), request.getMealTime());
        diet.updateKcal();
    }

    public boolean deleteDiet(Long id) {
        Diet diet = dietRepository.findById(id)
                .orElseThrow(() ->
                        new NullPointerException("해당 식단 아이디를 찾을 수 없음"));
        dietRepository.delete(diet);
        return true;
    }

    public Diet toEntity(CreateDietRequest request) {
        return Diet.builder()
                .food(request.getFood())
                .member(request.getMember())
                .count(request.getCount())
                .dietDate(request.getDietDate())
                .mealTime(request.getMealTime())
//                .totalKcal()
//                .totalCarb()
//                .totalPro()
//                .totalFat()
                .build();
    }
}
