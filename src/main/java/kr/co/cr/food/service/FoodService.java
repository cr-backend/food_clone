package kr.co.cr.food.service;

import kr.co.cr.food.dto.food.FoodDetailRes;
import kr.co.cr.food.dto.food.SearchFoodReq;
import kr.co.cr.food.dto.food.SearchFoodRes;
import kr.co.cr.food.entity.Food;
import kr.co.cr.food.entity.Vote;
import kr.co.cr.food.exception.FoodException;
import kr.co.cr.food.exception.errorcodes.ErrorCode;
import kr.co.cr.food.repository.FoodRepository;
import kr.co.cr.food.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService {

  @Autowired
  FoodRepository foodRepository;
  @Autowired
  VoteRepository voteRepository;

  public List<SearchFoodRes> searchFoods(Pageable pageable, SearchFoodReq searchFoodReq) {
    Page<Food> byNameOrderByIdDesc = foodRepository.findByNameContainsOrderByIdDesc(searchFoodReq.getName(), pageable);
    return byNameOrderByIdDesc.stream()
          .map(SearchFoodRes::toDto)
          .collect(Collectors.toList());
  }

  public FoodDetailRes getFoodDetail(Long id) {
    Food food = foodRepository.findById(id).orElseThrow();
    Vote vote = voteRepository.findById(id).orElse(null);
    return new FoodDetailRes(food, vote);
  }

  public Boolean vote(Long id, String type) {
    boolean exists = foodRepository.existsById(id);
    if (!exists) {
      throw new FoodException(ErrorCode.NOT_FOUND);
    }

    Vote vote = voteRepository.findById(id)
          .orElse(voteRepository.save(new Vote(id)));

    vote.vote(type);
    voteRepository.save(vote);

    return true;
  }
}
