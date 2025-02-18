package org.skuhub.skuhub.api.taxi.service;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiCommentRequest;
import org.skuhub.skuhub.common.enums.exception.ErrorCode;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.skuhub.skuhub.common.utills.jwt.JWTUtil;
import org.skuhub.skuhub.exceptions.CustomException;
import org.skuhub.skuhub.model.taxi.TaxiCommentJpaEntity;
import org.skuhub.skuhub.model.taxi.TaxiShareJpaEntity;
import org.skuhub.skuhub.model.user.UserInfoJpaEntity;
import org.skuhub.skuhub.repository.taxi.TaxiCommentRepository;
import org.skuhub.skuhub.repository.taxi.TaxiShareRepository;
import org.skuhub.skuhub.repository.users.UserInfoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;

@Getter
@Service
@Slf4j
public class TaxiCommentServiceImpl implements TaxiCommentService{
    private final JWTUtil jwtUtil;
    private final TaxiShareRepository taxiShareRepository;
    private final UserInfoRepository userInfoRepository;
    private final TaxiCommentRepository taxiCommentRepository;

    public TaxiCommentServiceImpl(JWTUtil jwtUtil, TaxiShareRepository taxiShareRepository, UserInfoRepository userInfoRepository, TaxiCommentRepository taxiCommentRepository) {
        this.jwtUtil = jwtUtil;
        this.taxiShareRepository = taxiShareRepository;
        this.userInfoRepository = userInfoRepository;
        this.taxiCommentRepository = taxiCommentRepository;
    }

    @Operation(summary = "댓글 작성", description = "택시합승 게시글에 댓글을 작성하는 API")
    public BaseResponse<String> postTaxiComment(TaxiCommentRequest request, String userId) {
        // userId로 사용자 정보를 찾기
        UserInfoJpaEntity userEntity = userInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        // TaxiShareJpaEntity 조회
        TaxiShareJpaEntity post = taxiShareRepository.findById(request.getPostId())
                .orElseThrow(() -> new CustomException(ErrorCode.NotFound, "게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        // 사용자 이름을 가져오기
        String userName = userEntity.getName();

        TaxiCommentJpaEntity entity = new TaxiCommentJpaEntity();
        entity.setPostId(post);
        entity.setUserKey(userEntity);
        entity.setCommentContent(request.getCommentContent());
        entity.setCreatedAt(OffsetDateTime.now());


        if(userName.equals(request.getName())) {
            TaxiCommentJpaEntity saved =  taxiCommentRepository.save(entity);
            return new BaseResponse<>(true, "201", "택시합승 댓글 저장 성공", OffsetDateTime.now(), "댓글 작성 성공");
        }else {
            throw new CustomException(ErrorCode.BadRequest, "수정 권한이 없습니다.", HttpStatus.BAD_REQUEST);
        }

    }
}
