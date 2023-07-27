package kr.co.cr.food.diet;

import kr.co.cr.food.diet.dto.CreateDietRequest;
import kr.co.cr.food.diet.dto.UpdateDietRequest;
import kr.co.cr.food.entity.Diet;
import kr.co.cr.food.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DietService {

    private final DietRepository dietRepository;

    public boolean inputDiet(CreateDietRequest createDietRequest) {
        CreateDietRequest dto = new CreateDietRequest();
        Diet diet = dto.toEntity(createDietRequest);
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
        diet.updateDiet(request.getFood(), request.getCount(), request.getDietDate(), request.getMealTime());
    }

    public boolean deleteDiet(Long id) {
        Diet diet = dietRepository.findById(id)
                .orElseThrow(() ->
                        new NullPointerException("해당 식단 아이디를 찾을 수 없음"));
        dietRepository.delete(diet);
        return true;
    }
}
