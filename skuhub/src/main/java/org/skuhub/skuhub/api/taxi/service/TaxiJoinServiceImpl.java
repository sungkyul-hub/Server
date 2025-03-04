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

import java.time.LocalDateTime;
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
        TaxiShareJpaEntity shareEntity = taxiShareRepository.findById(request.getPostId())
                .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        TaxiJoinJpaEntity taxiJoin = new TaxiJoinJpaEntity();
        // 최근 30분 이내 참여 여부 확인
        LocalDateTime thirtyMinutesAgo = LocalDateTime.now().minusMinutes(30);
        Optional<TaxiJoinJpaEntity> joinPost = taxiJoinRepository.findByUserKey(userEntity);
        if (joinPost.isPresent()) {
            taxiJoin = joinPost.get();
            if (taxiJoin.getCreatedAt().isAfter(thirtyMinutesAgo)) {
                return new BaseResponse<>(false, "400", "최근 30분 이내에 참여한 게시글 있습니다.", OffsetDateTime.now(), "참여 불가");
            }
        }
        if(shareEntity.getHeadCount() < shareEntity.getNumberOfPeople()) {
            shareEntity.setHeadCount(shareEntity.getHeadCount() + 1);
            taxiShareRepository.save(shareEntity);

            if(taxiJoinRepository.findByPostIdAndUserKey(shareEntity, userEntity).isPresent()){
                return new BaseResponse<>(false, "400", "이미 참여한 게시글입니다.", OffsetDateTime.now(), "이미 참여한 게시글입니다.");
            }
            taxiJoin.setPostId(shareEntity);
            taxiJoin.setUserKey(userEntity);
            taxiJoinRepository.save(taxiJoin);
            return new BaseResponse<>(true, "200", "택시합승 참여 성공", OffsetDateTime.now(), "참여 성공");
        }else{
            return new BaseResponse<>(false, "400", "택시합승 참여 실패", OffsetDateTime.now(), "참여 실패");
        }
    }

    @Override
    public BaseResponse<String> leaveTaxiShare(TaxiJoinRequest request, String userId) {
        UserInfoJpaEntity userEntity = userInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        TaxiShareJpaEntity shareEntity = taxiShareRepository.findById(request.getPostId())
                .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        Optional<TaxiJoinJpaEntity> optionalJoin = Optional.ofNullable(taxiJoinRepository.findByPostIdAndUserKey(shareEntity, userEntity)
                .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "참여한 게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND)));
        TaxiJoinJpaEntity taxiJoin;

        if(optionalJoin.isPresent() && shareEntity.getHeadCount() > 0){
            taxiJoin = optionalJoin.get();
            taxiJoinRepository.delete(taxiJoin);
            shareEntity.setHeadCount(shareEntity.getHeadCount() - 1);
            taxiShareRepository.save(shareEntity);
            return new BaseResponse<>(true, "200", "택시합승 참여 취소 성공", OffsetDateTime.now(), "참여 취소 성공");
        }else{
            return new BaseResponse<>(false, "400", "택시합승 참여 취소 실패", OffsetDateTime.now(), "참여 취소 실패");
        }
    }

}
