package kr.co.cr.food.service;

import kr.co.cr.food.dto.water.CreateWaterRequest;
import kr.co.cr.food.dto.water.UpdateWaterRequest;
import kr.co.cr.food.entity.Member;
import kr.co.cr.food.entity.Water;
import kr.co.cr.food.exception.NotFoundException;
import kr.co.cr.food.repository.MemberRepository;
import kr.co.cr.food.repository.WaterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WaterService {

    private final WaterRepository waterRepository;
    private final MemberRepository memberRepository;

    public Long insert(CreateWaterRequest createWaterRequest){
        // member 아이디 찾기
        Member member = memberRepository.findById(createWaterRequest.getMemberId())
                .orElseThrow(() -> new NotFoundException("해당 멤버를 찾을 수 없습니다."));

        // request -> entity
        Water water = toEntity(createWaterRequest, member);

        // 양방향 메서드
        water.changeMember(member);

        // 저장
        Water savedWater = waterRepository.save(water);

        return savedWater.getId();
    }

    @Transactional
    public Long update(Long id, UpdateWaterRequest updateWaterRequest){

        Water water = waterRepository.findById(id)
                .orElseThrow(() ->
                        new NullPointerException("해당 식단 아이디를 찾을 수 없음"));

        water.updateWater(updateWaterRequest.getAmount());

        return water.getId();
    }

    public boolean delete(Long id){

        Water water = waterRepository.findById(id)
                .orElseThrow(() ->
                        new NullPointerException("해당 식단 아이디를 찾을 수 없음"));

        waterRepository.delete(water);
        return true;
    }

    private Water toEntity(CreateWaterRequest request, Member member) {
        return Water.builder()
                .member(member)
                .amount(request.getAmount())
                .build();

    }

}
