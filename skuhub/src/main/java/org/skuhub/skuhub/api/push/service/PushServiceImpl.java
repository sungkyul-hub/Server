package org.skuhub.skuhub.api.push.service;

import com.google.api.client.auth.oauth2.TokenRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.skuhub.skuhub.common.enums.exception.ErrorCode;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.skuhub.skuhub.exceptions.CustomException;
import org.skuhub.skuhub.external.firebase.fcm.utils.FirebaseUtil;
import org.skuhub.skuhub.model.user.UserInfoJpaEntity;
import org.skuhub.skuhub.repository.users.UserInfoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Getter
@Service
@Slf4j
public class PushServiceImpl implements PushService {
    private final FirebaseUtil firebaseUtil;
    private final UserInfoRepository userInfoRepository;

    public PushServiceImpl(FirebaseUtil firebaseUtil, UserInfoRepository userInfoRepository) {
        this.firebaseUtil = firebaseUtil;
        this.userInfoRepository = userInfoRepository;
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


}
