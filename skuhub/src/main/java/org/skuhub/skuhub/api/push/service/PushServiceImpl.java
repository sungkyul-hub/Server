package org.skuhub.skuhub.api.push.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.skuhub.skuhub.api.push.dto.PushRequest;
import org.skuhub.skuhub.common.enums.alarm.PushType;
import org.skuhub.skuhub.common.enums.exception.ErrorCode;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.skuhub.skuhub.exceptions.CustomException;
import org.skuhub.skuhub.external.firebase.fcm.utils.FirebaseUtil;
import org.skuhub.skuhub.model.taxi.TaxiShareJpaEntity;
import org.skuhub.skuhub.model.user.UserInfoJpaEntity;
import org.skuhub.skuhub.repository.taxi.TaxiJoinRepository;
import org.skuhub.skuhub.repository.taxi.TaxiShareRepository;
import org.skuhub.skuhub.repository.users.UserInfoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Getter
@Service
@Slf4j
public class PushServiceImpl implements PushService {
    private final FirebaseUtil firebaseUtil;
    private final UserInfoRepository userInfoRepository;
    private final TaxiShareRepository taxiShareRepository;
    private final TaxiJoinRepository taxiJoinRepository;

    public PushServiceImpl(FirebaseUtil firebaseUtil, UserInfoRepository userInfoRepository, TaxiShareRepository taxiShareRepository, TaxiJoinRepository taxiJoinRepository) {
        this.firebaseUtil = firebaseUtil;
        this.userInfoRepository = userInfoRepository;
        this.taxiShareRepository = taxiShareRepository;
        this.taxiJoinRepository = taxiJoinRepository;
    }

    @Override
    public BaseResponse<String> saveToken(String userId, String tokenRequest) {
        log.info("saveToken: userId: {}, tokenRequest: {}", userId, tokenRequest);
        UserInfoJpaEntity userEntity = userInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        userEntity.setAccessToken(tokenRequest);
        userInfoRepository.save(userEntity);
        return new BaseResponse<>(true, "200", "토큰 저장 성공", null, "토큰 저장 성공");
    }

    @Override
    public BaseResponse<String> deleteToken(String userId) {
        log.info("saveToken: userId: {}", userId);
        UserInfoJpaEntity userEntity = userInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        userEntity.setAccessToken(null);
        userInfoRepository.save(userEntity);
        return new BaseResponse<>(true, "200", "토큰 삭제 성공", null, "토큰 삭제 성공");
    }

    @Override
    public BaseResponse<String> pushKeywordAlarm() {
        return null;
    }

    @Override
    public BaseResponse<String> pushTaxiJoinAlarm(Long postId) {
        TaxiShareJpaEntity shareEntity = taxiShareRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        return null;
    }

    @Override
    public boolean pushTaxiCommentAlarm(Long postId, String content) throws IOException {
        TaxiShareJpaEntity shareEntity = taxiShareRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        Long userKey = shareEntity.getUserKey().getUserKey();
        Optional<UserInfoJpaEntity> userEntity = userInfoRepository.findByUserKey(userKey);
        if (userEntity.isEmpty()) {
            throw new CustomException(ErrorCode.NotFound, "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
        PushRequest.SendPushRequest sendPushRequest = PushRequest.SendPushRequest.builder()
                .userKey(userKey)
                .title("택시 게시글에 댓글이 달렸습니다.")
                .content(content)
                .pushType(PushType.COMMENT)
                .moveToId("TAXI" + postId)
                .build();

        return firebaseUtil.sendFcmTo(sendPushRequest, userEntity.get().getAccessToken());
    }



}
