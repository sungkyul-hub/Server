package org.skuhub.skuhub.api.push.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.skuhub.skuhub.api.push.dto.PushRequest;
import org.skuhub.skuhub.common.enums.alarm.PushType;
import org.skuhub.skuhub.common.enums.exception.ErrorCode;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.skuhub.skuhub.exceptions.CustomException;
import org.skuhub.skuhub.external.firebase.fcm.utils.FirebaseUtil;
import org.skuhub.skuhub.model.notice.NoticeJpaEntity;
import org.skuhub.skuhub.model.taxi.TaxiJoinJpaEntity;
import org.skuhub.skuhub.model.taxi.TaxiShareJpaEntity;
import org.skuhub.skuhub.model.user.KeywordInfoJpaEntity;
import org.skuhub.skuhub.model.user.UserInfoJpaEntity;
import org.skuhub.skuhub.repository.notice.NoticeRepository;
import org.skuhub.skuhub.repository.taxi.TaxiJoinRepository;
import org.skuhub.skuhub.repository.taxi.TaxiShareRepository;
import org.skuhub.skuhub.repository.users.KeywordInfoRepository;
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
    private final NoticeRepository noticeRepository;
    private final KeywordInfoRepository keywordInfoRepository;

    public PushServiceImpl(FirebaseUtil firebaseUtil, UserInfoRepository userInfoRepository, TaxiShareRepository taxiShareRepository, TaxiJoinRepository taxiJoinRepository, NoticeRepository noticeRepository, KeywordInfoRepository keywordInfoRepository) {
        this.firebaseUtil = firebaseUtil;
        this.userInfoRepository = userInfoRepository;
        this.taxiShareRepository = taxiShareRepository;
        this.taxiJoinRepository = taxiJoinRepository;
        this.noticeRepository = noticeRepository;
        this.keywordInfoRepository = keywordInfoRepository;
    }

    @Override
    public BaseResponse<String> saveToken(String userId, String tokenRequest) { //fcm token 저장
        log.info("saveToken: userId: {}, tokenRequest: {}", userId, tokenRequest);
        UserInfoJpaEntity userEntity = userInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        userEntity.setAccessToken(tokenRequest);
        userInfoRepository.save(userEntity);
        return new BaseResponse<>(true, "200", "토큰 저장 성공", null, "토큰 저장 성공");
    }

    @Override
    public BaseResponse<String> deleteToken(String userId) { //fcm token 삭제
        log.info("saveToken: userId: {}", userId);
        UserInfoJpaEntity userEntity = userInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        userEntity.setAccessToken(null);
        userInfoRepository.save(userEntity);
        return new BaseResponse<>(true, "200", "토큰 삭제 성공", null, "토큰 삭제 성공");
    }

    @Override
    public boolean pushKeywordAlarm(Long postId, String notice) throws IOException {    //키워드 알림 전송
        log.info("pushKeywordAlarm: notice: {}", notice);
        List<KeywordInfoJpaEntity> noticeList = keywordInfoRepository.findByKeyword(notice);
        for (KeywordInfoJpaEntity keyword : noticeList) {
            Long userKey = keyword.getUserKey().getUserKey();
            UserInfoJpaEntity userEntity = userInfoRepository.findByUserKey(userKey)
                    .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "사용자를 찾을 수 없습니다. userKey: " + userKey, HttpStatus.NOT_FOUND));
            PushRequest.SendPushRequest sendPushRequest = PushRequest.SendPushRequest.builder()
                    .userKey(userKey)
                    .title("키워드 알림")
                    .content(notice)
                    .pushType(PushType.NOTICE)
                    .moveToId("NOTICE" + postId)
                    .build();
            firebaseUtil.sendFcmTo(sendPushRequest, userEntity.getAccessToken());
        }
        return true;
    }

    @Override
    public boolean pushTaxiJoinAlarm(Long postId) throws IOException {

        TaxiShareJpaEntity shareEntity = taxiShareRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        Long userKey = shareEntity.getUserKey().getUserKey();
        UserInfoJpaEntity userEntity = userInfoRepository.findByUserKey(userKey)
                .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "사용자를 찾을 수 없습니다. userKey: " + userKey, HttpStatus.NOT_FOUND));

        PushRequest.SendPushRequest sendPushRequest = PushRequest.SendPushRequest.builder()
                .userKey(userKey)
                .title("택시 파티 성공")
                .content(userEntity.getUserId() + "님이 올린 게시글에 모두 참여했습니다.")
                .pushType(PushType.TAXI)
                .moveToId("TAXI" + postId)
                .build();
        firebaseUtil.sendFcmTo(sendPushRequest, userEntity.getAccessToken());

        List<TaxiJoinJpaEntity> joins = taxiJoinRepository.findByPostId(shareEntity);
        for (TaxiJoinJpaEntity join : joins) {
            Long joinUserKey = join.getUserKey().getUserKey();
            UserInfoJpaEntity joinUserEntity = userInfoRepository.findByUserKey(joinUserKey)
                    .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "사용자를 찾을 수 없습니다. userKey: " + joinUserKey, HttpStatus.NOT_FOUND));
            PushRequest.SendPushRequest sendJoinPushRequest = PushRequest.SendPushRequest.builder()
                    .userKey(userKey)
                    .title("택시 파티 성공")
                    .content(userEntity.getUserId() + "님이 참가한 게시글에 모두 참여했습니다.")
                    .pushType(PushType.TAXI)
                    .moveToId("TAXI" + postId)
                    .build();
            firebaseUtil.sendFcmTo(sendJoinPushRequest, joinUserEntity.getAccessToken());
        }
        return true;
    }





    @Override
    public boolean pushTaxiCommentAlarm(Long postId, String content) throws IOException {   //택시 게시글 댓글 알림 전송
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
