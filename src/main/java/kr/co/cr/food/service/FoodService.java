package kr.co.cr.food.service;

import kr.co.cr.food.dto.common.PagingResponse;
import kr.co.cr.food.dto.food.FoodDetailRes;
import kr.co.cr.food.dto.food.SearchFoodReq;
import kr.co.cr.food.dto.food.SearchFoodRes;
import kr.co.cr.food.entity.Food;
import kr.co.cr.food.entity.MemberVoteInfo;
import kr.co.cr.food.entity.Vote;
import kr.co.cr.food.exception.FoodException;
import kr.co.cr.food.exception.errorcodes.ErrorCode;
import kr.co.cr.food.repository.FoodRepository;
import kr.co.cr.food.repository.MemberVoteInfoRepository;
import kr.co.cr.food.repository.VoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class FoodService {

  @Autowired
  FoodRepository foodRepository;
  @Autowired
  VoteRepository voteRepository;
  @Autowired
  MemberVoteInfoRepository memberVoteInfoRepository;

  public PagingResponse<SearchFoodRes> searchFoods(@PageableDefault(sort = "id") Pageable pageable, SearchFoodReq searchFoodReq) {
    log.debug("[::searchFoods] search food request : {}", searchFoodReq.getName());
    Page<Food> byNameOrderByIdDesc = foodRepository.findByNameContainsOrderByIdDesc(Objects.requireNonNullElse(searchFoodReq.getName(), ""), pageable);
    log.debug("[::searchFoods] food size : {}", byNameOrderByIdDesc.stream().count());
    List<SearchFoodRes> collect = byNameOrderByIdDesc.stream()
          .map(SearchFoodRes::toDto)
          .toList();

    return PagingResponse.create(byNameOrderByIdDesc, collect);
  }

  public FoodDetailRes getFoodDetail(Long foodId, Long memberId) {
    Food food = foodRepository.findById(foodId).orElseThrow();
    Vote vote = voteRepository.findById(foodId).orElse(null);
    MemberVoteInfo memberVoteInfo = memberVoteInfoRepository.findByFoodIdAndMemberId(foodId, memberId).orElse(null);
    return new FoodDetailRes(food, vote, memberVoteInfo);
  }

  public Boolean vote(Long id, String type, Long memberId) {
    boolean exists = foodRepository.existsById(id);
    if (!exists) {
      throw new FoodException(ErrorCode.NOT_FOUND);
    }

    MemberVoteInfo voteInfo = memberVoteInfoRepository.findByFoodIdAndMemberId(id, memberId)
          .orElse(null);

    // 투표 기록 조회
    Vote vote = voteRepository.findById(id)
          .orElse(voteRepository.save(new Vote(id)));


    if (voteInfo != null && voteInfo.getVoteType().equalsIgnoreCase(type)) {
      // 이미 해당 투표를 한 경우
      return true;
    } else if (voteInfo != null && !voteInfo.getVoteType().equalsIgnoreCase(type)) {
      // 다른 곳에 투표한 경우
      vote.devote(type);
      voteInfo.setVoteType(type);
      memberVoteInfoRepository.save(voteInfo);
    } else {
      // 투표를 안한 경우
      MemberVoteInfo newVoteInfo = new MemberVoteInfo(id, memberId, type);
      memberVoteInfoRepository.save(newVoteInfo);
    }

    // 투표 결과 기록
    vote.vote(type);
    voteRepository.save(vote);

    return true;
  }
}
