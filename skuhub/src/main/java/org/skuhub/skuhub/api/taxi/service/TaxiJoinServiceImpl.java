package org.skuhub.skuhub.api.taxi.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiJoinRequest;
import org.skuhub.skuhub.common.enums.exception.ErrorCode;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.skuhub.skuhub.common.utills.jwt.JWTUtil;
import org.skuhub.skuhub.exceptions.CustomException;
import org.skuhub.skuhub.model.taxi.TaxiJoinJpaEntity;
import org.skuhub.skuhub.model.taxi.TaxiShareJpaEntity;
import org.skuhub.skuhub.model.user.UserInfoJpaEntity;
import org.skuhub.skuhub.repository.taxi.TaxiJoinRepository;
import org.skuhub.skuhub.repository.taxi.TaxiShareRepository;
import org.skuhub.skuhub.repository.users.UserInfoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;
@Getter
@Service
@Slf4j
public class TaxiJoinServiceImpl implements TaxiJoinService{
    private final TaxiShareRepository taxiShareRepository;
    private final TaxiJoinRepository taxiJoinRepository;
    private final UserInfoRepository userInfoRepository;

    public TaxiJoinServiceImpl(TaxiShareRepository taxiShareRepository, TaxiJoinRepository taxiJoinRepository, UserInfoRepository userInfoRepository) {
        this.taxiShareRepository = taxiShareRepository;
        this.taxiJoinRepository = taxiJoinRepository;
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public BaseResponse<String> joinTaxiShare(TaxiJoinRequest request, String userId) {
        UserInfoJpaEntity userEntity = userInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        TaxiShareJpaEntity joinTaxi = taxiShareRepository.findById(request.getPostId())
                .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        if(joinTaxi.getHeadCount() < joinTaxi.getNumberOfPeople()) {
            joinTaxi.setHeadCount(joinTaxi.getHeadCount() + 1);
            taxiShareRepository.save(joinTaxi);

            if(taxiJoinRepository.findByPostIdAndUserKey(joinTaxi, userEntity).isPresent()){
                return new BaseResponse<>(false, "400", "이미 참여한 게시글입니다.", OffsetDateTime.now(), "이미 참여한 게시글입니다.");
            }
            TaxiJoinJpaEntity entity = new TaxiJoinJpaEntity();
            entity.setPostId(joinTaxi);
            entity.setUserKey(userEntity);
            taxiJoinRepository.save(entity);
            return new BaseResponse<>(true, "200", "택시합승 참여 성공", OffsetDateTime.now(), "참여 성공");
        }else{
            return new BaseResponse<>(false, "400", "택시합승 참여 실패", OffsetDateTime.now(), "참여 실패");
        }
    }

    @Override
    public BaseResponse<String> leaveTaxiShare(TaxiJoinRequest request, String userId) {
        return null;
    }

}
